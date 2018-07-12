package com.mj.mmanage.core;
/**
 * 自定义异常
 * @author guobaoli
 * @date 2017年3月5日
 */
public class AppException extends Exception{
	
	public AppException(String errorCode, String msg) {
		super(errorCode + "|" +msg);
	}
	
	public AppException(String errorCode) {
		super(errorCode);
	}
	
}
