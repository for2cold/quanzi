package com.jasme.quanzi.core.component.user.enums;

public enum UserType {

	REGIST("注册用户"),
	QQ("QQ用户"),
	WEIBO("微博用户"),
	WEIXI("微信用户")
	;
	
	private String info;

	private UserType(String info) {
		this.info = info;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}
	
}
