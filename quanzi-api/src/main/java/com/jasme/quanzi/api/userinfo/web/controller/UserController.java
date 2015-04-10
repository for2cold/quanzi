package com.jasme.quanzi.api.userinfo.web.controller;

import javax.validation.Valid;

import org.apache.oltu.oauth2.as.issuer.MD5Generator;
import org.apache.oltu.oauth2.as.issuer.OAuthIssuer;
import org.apache.oltu.oauth2.as.issuer.OAuthIssuerImpl;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jasme.quanzi.api.oauth2.service.AccessTokenService;
import com.jasme.quanzi.api.userinfo.request.view.RegistView;
import com.jasme.quanzi.api.userinfo.response.view.UserInfo;
import com.jasme.quanzi.api.userinfo.service.UserService;
import com.jasme.quanzi.api.userinfo.web.bind.annotation.CurrentUser;
import com.jasme.quanzi.core.component.user.entity.User;
import com.jasme.quanzi.core.oauth2.entity.AccessToken;
import com.jasme.quanzi.core.oauth2.entity.OAuthAuthenticationToken;
import com.jasme.quanzi.core.oauth2.entity.OAuthToken;
import com.jasme.swiiket.common.web.controller.BaseApiController;

/**
 * 用户管理
 * 
 * <p>User: Jasme
 * <p>Date: 2015年2月28日 上午10:12:19
 * <p>Version: 1.0
 */
@Controller
@RequestMapping(value = "/api/userinfo")
public class UserController extends BaseApiController<User, Long> {

	private AccessTokenService accessTokenService;
	private UserService userService;

	@Autowired
	public void setAccessTokenService(AccessTokenService accessTokenService) {
		this.accessTokenService = accessTokenService;
	}

	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	
	@Value("${oauth2.expires_in}")
	private long EXPIRES_IN;
	
	/**
	 * 用户注册
	 * 
	 * @param view
	 * @param result
	 * @return
	 */
	@RequestMapping(value = "regist", method = RequestMethod.POST)
	@ResponseBody
	public Object regist(@Valid RegistView view, BindingResult result) {
		
		// 数据校验
		if (result.hasErrors()) {
			
			return responseFiledError(result);
		}
		
		// 注册成功
		User user = userService.regist(view);
		LOG.info("【" + user.getUsername() + "】注册成功！");
		
		// 登录操作
		try {
			OAuthIssuer oauthIssuerImpl = new OAuthIssuerImpl(new MD5Generator());
			final String accessToken = oauthIssuerImpl.accessToken();
			Subject subject = SecurityUtils.getSubject();
			
			OAuthAuthenticationToken token = new OAuthAuthenticationToken(view.getUsername(), view.getPassword(), accessToken);
			
			try {
				subject.login(token);
			} catch (AuthenticationException e) {
				// 登录失败
				LOG.error("注册成功，自动登录失败", e);
			}
			// 登录认证成功
			OAuthToken tokenInfo = subject.getPrincipals().oneByType(OAuthToken.class);
			final String refreshToken = oauthIssuerImpl.refreshToken();
			AccessToken pojo = new AccessToken(accessToken, refreshToken, tokenInfo.getUser(), EXPIRES_IN);
			accessTokenService.save(pojo);
			return responseOAuthSuccess(accessToken, refreshToken, pojo.getExpiresIn(), pojo.getScope());
			
		} catch (OAuthSystemException e) {
			// 注册登录失败
			LOG.error("注册成功，自动登录失败", e);
		}
		
		return responseSuccessData("注册成功！请进行登录");
	}

	/**
	 * 账号信息
	 * 
	 * @param user
	 * @return
	 */
	@RequestMapping("center")
	@ResponseBody
	public Object info(@CurrentUser User user) {
		
		UserInfo ui = new UserInfo();
		
		ui.setId(user.getId());
		ui.setUsername(user.getUsername());
		ui.setIcon(user.getIcon());
		ui.setNickname(user.getNickname());
		ui.setGender(user.getGender());
		ui.setRegistDate(user.getRegistDate());
		ui.setStatus(user.getStatus());
		ui.setType(user.getType());
		
		return responseSuccessData(ui);
	}
}
