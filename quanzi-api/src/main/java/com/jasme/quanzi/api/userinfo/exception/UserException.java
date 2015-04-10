package com.jasme.quanzi.api.userinfo.exception;

import com.jasme.swiiket.common.exception.BaseException;

/**
 * 用户异常
 * 
 * <p>User: Jasme
 * <p>Date: 2015年2月26日 下午3:57:12
 * <p>Version: 1.0
 */
public class UserException extends BaseException {

	public UserException(String code, Object... args) {
		// TODO Auto-generated constructor stub
		super("user", code, args);
	}
}
