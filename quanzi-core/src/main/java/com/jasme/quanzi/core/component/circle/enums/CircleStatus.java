package com.jasme.quanzi.core.component.circle.enums;

public enum CircleStatus {

	NORMAL("正常状态"),
	BLOCKED("锁定状态"),
	AUDITING("审核中"),
	NOT_PASS("审核不通过")
	;
	
	private String info;
	
	private CircleStatus(String info) {
		this.info = info;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}
	
}
