package com.hthl.jwt.sdk.model;

/**
 * jwt所面向的用户
 * @author xuxile
 *
 */
public class SubjectModel {

	private String userId;
	
	private String userName;

	
	public SubjectModel(String userId, String userName) {
		super();
		this.userId = userId;
		this.userName = userName;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	
}