package com.mj.mmanage.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "t_gd_apply")
public class GdApply extends BaseEntity {

	@Id
    @Column(name = "gdId")
    private Integer gdId;
	
	@Column(name = "wxUserId")
    private String wxUserId;
	
	@Column(name = "applyTime")
    private Date applyTime;
	
	@Column(name = "signNum")
    private Integer signNum;
	
	@Column(name = "signContinuousDays")
    private Integer signContinuousDays;

	public Integer getGdId() {
		return gdId;
	}

	public void setGdId(Integer gdId) {
		this.gdId = gdId;
	}

	public String getWxUserId() {
		return wxUserId;
	}

	public void setWxUserId(String wxUserId) {
		this.wxUserId = wxUserId;
	}

	public Date getApplyTime() {
		return applyTime;
	}

	public void setApplyTime(Date applyTime) {
		this.applyTime = applyTime;
	}

	public Integer getSignNum() {
		return signNum;
	}

	public void setSignNum(Integer signNum) {
		this.signNum = signNum;
	}

	public Integer getSignContinuousDays() {
		return signContinuousDays;
	}

	public void setSignContinuousDays(Integer signContinuousDays) {
		this.signContinuousDays = signContinuousDays;
	}

	
	
	
}
