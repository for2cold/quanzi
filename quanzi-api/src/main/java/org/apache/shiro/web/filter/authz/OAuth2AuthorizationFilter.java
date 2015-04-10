package org.apache.shiro.web.filter.authz;

import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.oltu.oauth2.common.OAuth;
import org.apache.oltu.oauth2.common.error.OAuthError;
import org.apache.oltu.oauth2.common.message.OAuthResponse;
import org.apache.oltu.oauth2.common.message.types.ParameterStyle;
import org.apache.oltu.oauth2.rs.request.OAuthAccessResourceRequest;
import org.apache.oltu.oauth2.rs.response.OAuthRSResponse;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.servlet.AdviceFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import com.jasme.quanzi.api.oauth2.constants.OAuthConstants;
import com.jasme.quanzi.api.oauth2.service.AccessTokenService;
import com.jasme.quanzi.core.oauth2.entity.AccessToken;
import com.jasme.quanzi.core.oauth2.entity.OAuthAuthenticationToken;
import com.jasme.swiiket.common.plugin.http.json.response.ResponseEntity;
import com.jasme.swiiket.common.plugin.http.json.response.ResponseStatusCode;

/**
 * 
 * <p>Author: Beiming
 * <p>Date: 2014年7月25日 上午10:39:42
 * <p>Version: 1.0
 */
public class OAuth2AuthorizationFilter extends AdviceFilter {

	private static final Logger LOG =  LoggerFactory.getLogger("access-token");
	
	private AccessTokenService accessTokenService;
	
	public void setAccessTokenService(AccessTokenService accessTokenService) {
		this.accessTokenService = accessTokenService;
	}

	@Override
	protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		// 校验token请求
		
		OAuthAccessResourceRequest oauthRequest = null;
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		try {
			oauthRequest = new OAuthAccessResourceRequest(httpRequest, ParameterStyle.BODY);
		} catch (Exception e) {
			LOG.error("OAuth2请求构建失败，即将重新构建：{}", e.getMessage());
			try {
				oauthRequest = new OAuthAccessResourceRequest(httpRequest, ParameterStyle.QUERY);
			} catch (Exception e1) {
				LOG.error("OAuth2请求构建失败：{}", e1.getMessage());
				
				OAuthResponse oauthResponse = OAuthRSResponse.errorResponse(HttpServletResponse.SC_UNAUTHORIZED)
						.setRealm(OAuthConstants.OAUTH2_RESOURCE_SERVER_REALM)
						.setError(OAuthError.ResourceResponse.INVALID_TOKEN)
						.buildHeaderMessage();
				
				httpResponse.setHeader(OAuth.HeaderType.WWW_AUTHENTICATE, oauthResponse.getHeader(OAuth.HeaderType.WWW_AUTHENTICATE));
				httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
				PrintWriter out = httpResponse.getWriter();
				httpResponse.setContentType("application/json;charset=UTF-8");
				ResponseEntity responseEntity = new ResponseEntity(ResponseStatusCode.UNAUTHORIZED.getCode(), OAuthConstants.INVALID_TOKEN_DESCRIPTION);
				out.write(JSON.toJSONString(responseEntity));
				out.flush();
				
				return false;
			}
		}
		
		// 获取token
		String accessToken = oauthRequest.getAccessToken();
		AccessToken token = accessTokenService.findOne(accessToken);
		
		// token已失效
		if (null == token || token.isExpired()) {
			if (null != token) {
				accessTokenService.delete(token);
			}
			
			OAuthResponse oauthResponse = OAuthRSResponse.errorResponse(HttpServletResponse.SC_UNAUTHORIZED)
					.setRealm(OAuthConstants.OAUTH2_RESOURCE_SERVER_REALM)
					.setError(OAuthError.ResourceResponse.INVALID_TOKEN)
					.buildHeaderMessage();
			
			httpResponse.setHeader(OAuth.HeaderType.WWW_AUTHENTICATE, oauthResponse.getHeader(OAuth.HeaderType.WWW_AUTHENTICATE));
			httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			PrintWriter out = httpResponse.getWriter();
			httpResponse.setContentType("application/json;charset=UTF-8");
			ResponseEntity responseEntity = new ResponseEntity(ResponseStatusCode.UNAUTHORIZED.getCode(), OAuthConstants.INVALID_TOKEN_DESCRIPTION);
			out.write(JSON.toJSONString(responseEntity));
			out.flush();
			
			return false;
		}
		
		// 检验是否存在当前上下文
		Subject subject = SecurityUtils.getSubject();
		if (!subject.isAuthenticated()) {
			OAuthAuthenticationToken authAuthenticationToken = new OAuthAuthenticationToken(token.getUser().getUsername(), token.getUser().getPassword(), accessToken, Boolean.TRUE);
			try {
				subject.login(authAuthenticationToken);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				LOG.error("自动登录失败，{}", e);
				OAuthResponse oauthResponse = OAuthRSResponse.errorResponse(HttpServletResponse.SC_UNAUTHORIZED)
						.setError(OAuthError.ResourceResponse.INVALID_TOKEN)
						.setErrorDescription(OAuthConstants.INVALID_TOKEN_DESCRIPTION)
						.buildJSONMessage();
				
				httpResponse.setHeader(OAuth.HeaderType.WWW_AUTHENTICATE, oauthResponse.getHeader(OAuth.HeaderType.WWW_AUTHENTICATE));
				httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
				PrintWriter out = httpResponse.getWriter();
				httpResponse.setContentType("application/json;charset=UTF-8");
				Map<String, Object> json = Maps.newHashMap();
				json.put("errno", 1);
				json.put("error", oauthResponse.getBody());
				out.write(JSON.toJSONString(json));
				out.flush();
				
				return false;
			}
		}
		
		return true;
	}
}
