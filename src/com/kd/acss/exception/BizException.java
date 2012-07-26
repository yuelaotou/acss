package com.kd.acss.exception;

/**
 * <p> Title: [自定义异常类]</p>
 * <p> Description: [继承RuntimeException，在系统运行时发现异常统一抛出BizException，无法进行其他处理，只能终止当前操作。]</p>
 * <p> Created on 2012-7-10</p>
 * <p> Copyright: Copyright (c) 2012</p>
 * @version 1.0
 */
public class BizException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public BizException() {
	}

	public BizException(String msg) {
		super(msg);
	}

	public BizException(Throwable ex) {
		super(ex);
	}

	public BizException(String msg, Throwable ex) {
		super(msg, ex);
	}
}
