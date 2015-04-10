package com.jasme.quanzi.api.circle.response.view;

import java.io.Serializable;
import java.util.Date;

import com.jasme.quanzi.core.component.circle.enums.CircleStatus;
import com.jasme.quanzi.core.component.circle.enums.CircleType;

public class CircleItem implements Serializable {

	private Long id;
	
	private String name;
	
	private CircleType type;
	
	private CircleStatus status;
	
	private Date createDate;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public CircleType getType() {
		return type;
	}

	public void setType(CircleType type) {
		this.type = type;
	}

	public CircleStatus getStatus() {
		return status;
	}

	public void setStatus(CircleStatus status) {
		this.status = status;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	
}
