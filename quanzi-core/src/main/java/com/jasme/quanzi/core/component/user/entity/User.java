package com.jasme.quanzi.core.component.user.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.format.annotation.DateTimeFormat;

import com.jasme.quanzi.core.component.user.enums.GenderType;
import com.jasme.quanzi.core.component.user.enums.UserStatus;
import com.jasme.quanzi.core.component.user.enums.UserType;
import com.jasme.swiiket.common.entity.BaseAutoEntity;
import com.jasme.swiiket.common.plugin.entity.LogicDeleteable;

/**
 * 用户管理
 * 
 * <p>User: Jasme
 * <p>Date: 2015年2月26日 上午11:59:56
 * <p>Version: 1.0
 */
@Entity
@Table(name = "component_user")
public class User extends BaseAutoEntity<Long> implements LogicDeleteable {

	/**
	 * 登录账号
	 */
	@Column(name = "username")
	private String username;
	
	/**
	 * 登录密码
	 */
	@Column(name = "password")
	private String password;
	
	/**
	 * 加密盐值
	 */
	@Column(name = "salt")
	private String salt;
	
	/**
	 * 头像
	 */
	@Column(name = "icon")
	private String icon;
	
	/**
	 * 昵称
	 */
	@Column(name = "nick_name")
	private String nickname;
	
	/**
	 * 性别
	 */
	@Column(name = "gender")
	@Enumerated(EnumType.STRING)
	private GenderType gender = GenderType.SECRET;
	
	/**
	 * 用户类型
	 */
	@Column(name = "type")
	@Enumerated(EnumType.STRING)
	private UserType type = UserType.REGIST;
	
	/**
	 * 用户状态
	 */
	@Column(name = "status")
	@Enumerated(EnumType.STRING)
	private UserStatus status = UserStatus.NORMAL;

	/**
	 * 是否为管理员
	 */
	@Column(name = "is_admin")
	private boolean admin = Boolean.FALSE;
	
	/**
	 * 是否已删除
	 */
	@Column(name = "is_deleted")
	private boolean deleted = Boolean.FALSE;
	
	/**
	 * 注册时间
	 */
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "regist_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date registDate;
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}
	
	public void randomSalt() {
		setSalt(RandomStringUtils.randomAlphabetic(10));
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

	public boolean isAdmin() {
		return admin;
	}

	public void setAdmin(boolean admin) {
		this.admin = admin;
	}

	public Boolean getDeleted() {
		return deleted;
	}

	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}

	public Date getRegistDate() {
		return registDate;
	}

	public void setRegistDate(Date registDate) {
		this.registDate = registDate;
	}

	@Override
	public void markDeleted() {
		// TODO Auto-generated method stub
		this.deleted = Boolean.TRUE;
	}
}
