package com.jasme.quanzi.api.userinfo.exception;

/**
 * 用户名已被占用
 * 
 * <p>User: Jasme
 * <p>Date: 2015年2月27日 下午2:10:34
 * <p>Version: 1.0
 */
public class UsernameExistsException extends UserException {

	public UsernameExistsException() {
		// TODO Auto-generated constructor stub
		super("user.username.exist");
	}
}
