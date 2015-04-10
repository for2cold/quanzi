package com.jasme.quanzi.core.oauth2.entity;

import java.io.Serializable;

import com.jasme.quanzi.core.component.user.entity.User;

public class OAuthToken implements Serializable {

	private String accessToken;

	private User user;

	public OAuthToken() {
		super();
		// TODO Auto-generated constructor stub
	}

	public OAuthToken(String accessToken, User user) {
		super();
		this.accessToken = accessToken;
		this.user = user;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
