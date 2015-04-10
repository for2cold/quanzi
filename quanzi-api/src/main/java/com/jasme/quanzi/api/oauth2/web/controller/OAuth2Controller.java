package com.jasme.quanzi.api.oauth2.web.controller;

import java.util.Date;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.oltu.oauth2.as.issuer.MD5Generator;
import org.apache.oltu.oauth2.as.issuer.OAuthIssuer;
import org.apache.oltu.oauth2.as.issuer.OAuthIssuerImpl;
import org.apache.oltu.oauth2.as.request.OAuthTokenRequest;
import org.apache.oltu.oauth2.as.response.OAuthASResponse;
import org.apache.oltu.oauth2.common.OAuth;
import org.apache.oltu.oauth2.common.error.OAuthError;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.OAuthResponse;
import org.apache.oltu.oauth2.common.message.types.GrantType;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jasme.quanzi.api.oauth2.constants.OAuthConstants;
import com.jasme.quanzi.api.oauth2.service.AccessTokenService;
import com.jasme.quanzi.api.userinfo.service.UserService;
import com.jasme.quanzi.core.oauth2.entity.AccessToken;
import com.jasme.quanzi.core.oauth2.entity.OAuthAuthenticationToken;
import com.jasme.quanzi.core.oauth2.entity.OAuthToken;
import com.jasme.swiiket.common.web.controller.BaseApiController;

@Controller
@RequestMapping(value = "/api", produces = "application/json;charset=UTF-8")
public class OAuth2Controller extends BaseApiController<AccessToken, String> {

	@Autowired
	private UserService userService;
	@Autowired
	private AccessTokenService accessTokenService;
	
	@Value("${oauth2.client_id}")
	private String CLIENT_ID;
	
	@Value("${oauth2.client_secret}")
	private String CLIENT_SECRET;
	
	@Value("${oauth2.expires_in}")
	private long EXPIRES_IN;
	
	/**
	 * 登录鉴权
	 * 
	 * @param request
	 * @return
	 * @throws OAuthSystemException
	 */
	@RequestMapping("oauth2/token")
	public HttpEntity<String> token(HttpServletRequest request) throws OAuthSystemException {
		
		try {
			// OAuth2请求
			OAuthTokenRequest oauthTokenRequest = new OAuthTokenRequest(request);
			
			// 校验client_id、client_secret
			if (!CLIENT_ID.equals(oauthTokenRequest.getClientId()) && !CLIENT_SECRET.equals(oauthTokenRequest.getClientSecret())) {
				
				LOG.error(OAuthConstants.INVALID_CLIENT_DESCRIPTION);
				
				return responseOAuthError(
						HttpServletResponse.SC_BAD_REQUEST, 
						OAuthError.TokenResponse.INVALID_CLIENT, 
						OAuthConstants.INVALID_CLIENT_DESCRIPTION);
			}
			
			// 校验认证方式
			String grantType = oauthTokenRequest.getParam(OAuth.OAUTH_GRANT_TYPE);
			
			if (!GrantType.PASSWORD.name().equalsIgnoreCase(grantType)) {
				
				LOG.error(OAuthConstants.INVALID_CLIENT_GRANT_TYPE_DESCRIPTION);
				
				return responseOAuthError(
						HttpServletResponse.SC_BAD_REQUEST, 
						OAuthError.TokenResponse.INVALID_GRANT, 
						OAuthConstants.INVALID_CLIENT_GRANT_TYPE_DESCRIPTION);
			}
			
			// 生成token
			OAuthIssuer oauthIssuerImpl = new OAuthIssuerImpl(new MD5Generator());
			final String accessToken = oauthIssuerImpl.accessToken();
			
			// 用户身份认证
			Subject subject = SecurityUtils.getSubject();
			if (subject.isAuthenticated()) {
				subject.logout();
			}
			
			OAuthAuthenticationToken token = new OAuthAuthenticationToken(oauthTokenRequest.getUsername(), oauthTokenRequest.getPassword(), accessToken);
			try {
				subject.login(token);
			} catch (UnknownAccountException e) {
				
				LOG.error(e.getMessage());
				
				return responseOAuthError(HttpServletResponse.SC_UNAUTHORIZED,
						OAuthError.OAUTH_ERROR, OAuthConstants.INVALID_USERNAME_OR_PASSWORD);
				
	        } catch (ExcessiveAttemptsException e) {
	        	
	        	LOG.error(e.getMessage());
				
				return responseOAuthError(HttpServletResponse.SC_UNAUTHORIZED,
						OAuthError.OAUTH_ERROR, OAuthConstants.INVALID_PASSWORD_LIMIT);
	        	
	        } catch (LockedAccountException e) {
	        	
	        	LOG.error(e.getMessage());
				
				return responseOAuthError(HttpServletResponse.SC_UNAUTHORIZED,
						OAuthError.OAUTH_ERROR, e.getMessage());
	        	
			} catch (AuthenticationException e) {
				LOG.error(e.getMessage());
				
				return responseOAuthError(HttpServletResponse.SC_UNAUTHORIZED,
						OAuthError.OAUTH_ERROR, OAuthConstants.INVALID_USERNAME_OR_PASSWORD);
			}
			
			// 登录认证成功
			OAuthToken tokenInfo = subject.getPrincipals().oneByType(OAuthToken.class);
			final String refreshToken = oauthIssuerImpl.refreshToken();
			AccessToken pojo = new AccessToken(accessToken, refreshToken, tokenInfo.getUser(), EXPIRES_IN);
			accessTokenService.save(pojo);
			
			return responseOAuthSuccess(accessToken, refreshToken, EXPIRES_IN, pojo.getScope());
			
		} catch (OAuthProblemException e) {
			// TODO Auto-generated catch block
			LOG.error(OAuthConstants.OAUTH2_ERROR_DESCRIPTION);
			
			OAuthResponse response = OAuthASResponse.errorResponse(HttpServletResponse.SC_BAD_REQUEST)
					.error(e).buildJSONMessage();
			
			return new ResponseEntity<String>(response.getBody(), HttpStatus.valueOf(response.getResponseStatus()));
		}
	}
}
