package com.mj.mmanage.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "t_gd_sign_in")
public class GdSignIn extends BaseEntity {
	@Id
	@Column(name = "gdId")
	private int gdId;
	
	@Column(name = "wxUserId")
	private String wxUserId;
	
	@Column(name = "signInDate")
	private String signInDate;
	
	@Column(name = "signInPeriod")
	private String signInPeriod;
	
	@Column(name = "signInTime")
	private Date signInTime;

	public int getGdId() {
		return gdId;
	}

	public void setGdId(int gdId) {
		this.gdId = gdId;
	}

	public String getWxUserId() {
		return wxUserId;
	}

	public void setWxUserId(String wxUserId) {
		this.wxUserId = wxUserId;
	}

	public String getSignInDate() {
		return signInDate;
	}

	public void setSignInDate(String signInDate) {
		this.signInDate = signInDate;
	}

	public String getSignInPeriod() {
		return signInPeriod;
	}

	public void setSignInPeriod(String signInPeriod) {
		this.signInPeriod = signInPeriod;
	}

	public Date getSignInTime() {
		return signInTime;
	}

	public void setSignInTime(Date signInTime) {
		this.signInTime = signInTime;
	}

	


}
