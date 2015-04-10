package com.jasme.quanzi.api.userinfo.exception;

/**
 * 密码不匹配异常
 * 
 * <p>User: Jasme
 * <p>Date: 2015年2月27日 下午2:08:08
 * <p>Version: 1.0
 */
public class UserPasswordNotMatchException extends UserException {

	public UserPasswordNotMatchException() {
		// TODO Auto-generated constructor stub
		super("user.password.not.match");
	}
}
