package com.jasme.quanzi.core.component.circle.enums;

public enum CircleType {

	THE_PRIVATE("私密圈"),
	THE_PUBLIC("公众圈");
	
	private String info;

	private CircleType(String info) {
		this.info = info;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}
	
}
