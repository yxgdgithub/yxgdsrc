package com.mj.mmanage.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "t_webchat_user")
public class WebChatUser extends BaseEntity {
	
	@Id
    @Column(name = "wxUserId")
	private String wxUserId;
	
	@Column(name = "nickName")
	private String nickName;
	
	@Column(name = "sex")
	private String sex;
	
	@Column(name = "authTime")
	private Date authTime;
	
	@Column(name = "headUrl")
	private String headUrl;

	public String getWxUserId() {
		return wxUserId;
	}

	public void setWxUserId(String wxUserId) {
		this.wxUserId = wxUserId;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public Date getAuthTime() {
		return authTime;
	}

	public void setAuthTime(Date authTime) {
		this.authTime = authTime;
	}

	public String getHeadUrl() {
		return headUrl;
	}

	public void setHeadUrl(String headUrl) {
		this.headUrl = headUrl;
	}
	
}
