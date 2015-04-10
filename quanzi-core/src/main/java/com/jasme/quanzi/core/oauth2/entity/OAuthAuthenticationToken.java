package com.jasme.quanzi.core.oauth2.entity;

import org.apache.shiro.authc.UsernamePasswordToken;

public class OAuthAuthenticationToken extends UsernamePasswordToken {

	private String accessToken;

	private boolean autoLogin;

	public OAuthAuthenticationToken(String username, String password,
			String accessToken) {

		super(username, password);
		this.accessToken = accessToken;
	}

	public OAuthAuthenticationToken(String username, String password,
			String accessToken, boolean autoLogin) {

		this(username, password, accessToken);
		this.autoLogin = autoLogin;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public boolean isAutoLogin() {
		return autoLogin;
	}

	public void setAutoLogin(boolean autoLogin) {
		this.autoLogin = autoLogin;
	}
}
