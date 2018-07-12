package com.mj.mmanage.util;
/**
 * 公共返回对象
 * @author 郭保利
 *
 */
public class RetMessageVo {
	private String retCode = "AAAAAAA";
	private String retMsg = "操作成功!";
	private String token = "";//手机端会话使用
	private String nowTime = "";//当前系统时间
	private int count = 0; //列表查询记录数
	private Object content;
	
	
	public String getRetCode() {
		return retCode;
	}
	public void setRetCode(String retCode) {
		this.retCode = retCode;
	}
	public String getRetMsg() {
		return retMsg;
	}
	public void setRetMsg(String retMsg) {
		this.retMsg = retMsg;
	}
	public Object getContent() {
		return content;
	}
	public void setContent(Object content) {
		this.content = content;
	}
	/**
	 * @return the token
	 */
	public String getToken() {
		return token;
	}
	/**
	 * @param token the token to set
	 */
	public void setToken(String token) {
		this.token = token;
	}
	/**
	 * @return the nowTime
	 */
	public String getNowTime() {
		return nowTime = DateUtil.now();
	}
	/**
	 * @param nowTime the nowTime to set
	 */
	public void setNowTime(String nowTime) {
		this.nowTime = nowTime;
	}
	/**
	 * @return the count
	 */
	public int getCount() {
		return count;
	}
	/**
	 * @param count the count to set
	 */
	public void setCount(int count) {
		this.count = count;
	}
	
}
