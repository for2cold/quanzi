package com.jasme.quanzi.api.userinfo.request.view;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import com.jasme.swiiket.common.validation.constraints.Mobile;

/**
 * 注册表单
 * 
 * <p>User: Jasme
 * <p>Date: 2015年2月26日 下午2:24:04
 * <p>Version: 1.0
 */
public class RegistView implements Serializable {

	@NotNull(message = "{not.null}")
//	@Mobile
	private String username;
	
	@NotNull(message = "{not.null}")
	private String password;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
