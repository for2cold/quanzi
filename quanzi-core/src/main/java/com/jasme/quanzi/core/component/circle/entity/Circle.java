package com.jasme.quanzi.core.component.circle.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.format.annotation.DateTimeFormat;

import com.jasme.quanzi.core.component.circle.enums.CircleStatus;
import com.jasme.quanzi.core.component.circle.enums.CircleType;
import com.jasme.quanzi.core.component.user.entity.User;
import com.jasme.swiiket.common.entity.BaseAutoEntity;

/**
 * 圈子
 * 
 * <p>User: Jasme
 * <p>Date: 2015年2月28日 上午10:36:51
 * <p>Version: 1.0
 */
@Entity
@Table(name = "component_circle")
public class Circle extends BaseAutoEntity<Long> {

	/**
	 * 创建人
	 */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "uid")
	@Fetch(FetchMode.JOIN)
	private User user;
	
	/**
	 * 名称
	 */
	@Column(name = "name")
	private String name;
	
	/**
	 * 类型
	 */
	@Column(name = "type")
	@Enumerated(EnumType.STRING)
	private CircleType type;
	
	/**
	 * 状态
	 */
	@Column(name = "status")
	@Enumerated(EnumType.STRING)
	private CircleStatus status;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "create_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createDate;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
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
