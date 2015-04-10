package com.jasme.quanzi.core.component.user.enums;

public enum GenderType {

	SECRET("保密"),
	MALE("男"),
	FEMALE("女");
	
	private String info;

	private GenderType(String info) {
		this.info = info;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}
	
}
