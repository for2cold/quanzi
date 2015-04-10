package com.jasme.quanzi.core.component.circle.enums;

public enum RelationType {

	CREATER("圈主"),
	MEMBER("成员");
	
	private String info;

	private RelationType(String info) {
		this.info = info;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}
	
}
