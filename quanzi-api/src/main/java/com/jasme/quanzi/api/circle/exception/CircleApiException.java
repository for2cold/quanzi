package com.jasme.quanzi.api.circle.exception;

import com.jasme.swiiket.common.exception.BaseException;

/**
 * 圈子异常类
 * 
 * <p>User: Jasme
 * <p>Date: 2015年3月30日 上午10:35:52
 * <p>Version: 1.0
 */
public class CircleApiException extends BaseException {

	public CircleApiException(String code, Object... args) {
		// TODO Auto-generated constructor stub
		super("circle", code, args);
	}
}
