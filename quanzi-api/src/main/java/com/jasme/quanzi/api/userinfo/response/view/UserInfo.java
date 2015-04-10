package com.jasme.quanzi.api.userinfo.response.view;

import java.io.Serializable;
import java.util.Date;

import com.jasme.quanzi.core.component.user.enums.GenderType;
import com.jasme.quanzi.core.component.user.enums.UserStatus;
import com.jasme.quanzi.core.component.user.enums.UserType;

/**
 * 账号信息
 * <p>User: Jasme
 * <p>Date: 2015年2月28日 上午10:09:40
 * <p>Version: 1.0
 */
public class UserInfo implements Serializable {

	private Long id;
	
	private String username;
	
	private String icon;
	
	private String nickname;
	
	private GenderType gender;
	
	private UserType type;
	
	private UserStatus status;

	private Date registDate;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public GenderType getGender() {
		return gender;
	}

	public void setGender(GenderType gender) {
		this.gender = gender;
	}

	public UserType getType() {
		return type;
	}

	public void setType(UserType type) {
		this.type = type;
	}

	public UserStatus getStatus() {
		return status;
	}

	public void setStatus(UserStatus status) {
		this.status = status;
	}

	public Date getRegistDate() {
		return registDate;
	}

	public void setRegistDate(Date registDate) {
		this.registDate = registDate;
	}
}
