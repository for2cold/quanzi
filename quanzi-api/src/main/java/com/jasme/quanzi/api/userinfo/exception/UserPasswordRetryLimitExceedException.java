package com.jasme.quanzi.api.userinfo.exception;

/**
 * 密码输入次数过多异常
 * 
 * <p>User: Jasme
 * <p>Date: 2015年2月27日 下午1:57:44
 * <p>Version: 1.0
 */
public class UserPasswordRetryLimitExceedException extends UserException {

	public UserPasswordRetryLimitExceedException(int maxRetryCount) {
		// TODO Auto-generated constructor stub
		super("user.password.retry.limite.exceed", maxRetryCount);
	}
}
