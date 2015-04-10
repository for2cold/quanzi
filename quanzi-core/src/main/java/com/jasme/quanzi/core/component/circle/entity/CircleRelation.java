package com.jasme.quanzi.core.component.circle.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.jasme.quanzi.core.component.circle.enums.RelationType;
import com.jasme.quanzi.core.component.user.entity.User;
import com.jasme.swiiket.common.entity.BaseAutoEntity;

/**
 * <p>User: Jasme
 * <p>Date: 2015年2月28日 上午10:52:34
 * <p>Version: 1.0
 */
@Entity
@Table(name = "component_circle_relation")
public class CircleRelation extends BaseAutoEntity<Long> {

	/**
	 * 关系人
	 */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "uid")
	@Fetch(FetchMode.JOIN)
	private User user;
	
	/**
	 * 
	 */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "cid")
	@Fetch(FetchMode.JOIN)
	private Circle circle;
	
	/**
	 * 关系
	 */
	@Column(name = "type")
	@Enumerated(EnumType.STRING)
	private RelationType type;

	/**
	 * 是否特别关注
	 */
	@Column(name = "is_special")
	private Boolean special = Boolean.FALSE;
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Circle getCircle() {
		return circle;
	}

	public void setCircle(Circle circle) {
		this.circle = circle;
	}
	
	
}
