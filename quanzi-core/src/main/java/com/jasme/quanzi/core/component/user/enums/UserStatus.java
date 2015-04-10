package com.jasme.quanzi.core.component.user.enums;

public enum UserStatus {

	NORMAL("正常"),
	BLOCKED("禁用");
	
	private String info;

	private UserStatus(String info) {
		this.info = info;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}
	
}
