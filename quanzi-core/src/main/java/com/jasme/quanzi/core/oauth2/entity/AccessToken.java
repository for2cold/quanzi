package com.jasme.quanzi.core.oauth2.entity;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.jasme.quanzi.core.component.user.entity.User;
import com.jasme.swiiket.common.entity.AbstractEntity;


/**
 * 访问令牌
 * 
 * <p>User: Jasme
 * <p>Date: 2015年2月26日 下午5:25:25
 * <p>Version: 1.0
 */
@Entity
@Table(name = "oauth2_access_token")
public class AccessToken extends AbstractEntity<String> {

	@Id
	@Column(name = "access_token")
	private String accessToken;					// 访问令牌 唯一
	
	@Column(name = "refresh_token")
	private String refreshToken;				// 刷新访问令牌
	
	@Column(name = "scope")
	private String scope;						// 授权范围

	@OneToOne(optional = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "uid")
    @Fetch(FetchMode.SELECT)
	private User user;							// 认证用户

	@Column(name = "expires_in")
	private long expiresIn;						// 有效时长 毫秒
	
	@Column(name = "create_time")
	private long createTime = Calendar.getInstance().getTimeInMillis();

	public AccessToken() {
		super();
		// TODO Auto-generated constructor stub
	}

	public AccessToken(String accessToken, String refreshToken, User user,
			long expiresIn) {
		this(accessToken, refreshToken, null, user, expiresIn);
	}
	
	public AccessToken(String accessToken, String refreshToken, String scope,
			User user, long expiresIn) {
		super();
		this.accessToken = accessToken;
		this.refreshToken = refreshToken;
		this.scope = scope;
		this.user = user;
		this.expiresIn = expiresIn;
	}
	

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}

	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public long getExpiresIn() {
		return expiresIn;
	}

	public void setExpiresIn(long expiresIn) {
		this.expiresIn = expiresIn;
	}

	public long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}
	
	public boolean isExpired() {
		return expiresIn - (Calendar.getInstance().getTimeInMillis() - this.getCreateTime()) / 1000 < 0;
	}
	
	@Override
	public String getId() {
		// TODO Auto-generated method stub
		return this.getAccessToken();
	}
	
	@Override
	public void setId(String id) {
		// TODO Auto-generated method stub
		this.accessToken = id;
	}
}
