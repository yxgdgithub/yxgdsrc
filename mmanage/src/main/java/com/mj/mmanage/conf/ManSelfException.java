package com.mj.mmanage.conf;
/**
 * 自定义异常
 * @author guobaoli
 * @date 2017年3月5日
 */
public class ManSelfException extends Exception{
	
	public ManSelfException(String errorCode, String msg) {
		super(msg + " [" +errorCode +"]");
	}
	
}
