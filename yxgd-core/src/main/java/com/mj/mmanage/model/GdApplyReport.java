package com.mj.mmanage.model;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "t_gd_apply_report")
public class GdApplyReport extends BaseEntity {
	@Id
	@Column(name = "dataDate")
	private String dataDate;
	
	@Column(name = "gdId")
	private Integer gdId;
	
	@Column(name = "signRate")
	private double signRate;

	public String getDataDate() {
		return dataDate;
	}

	public void setDataDate(String dataDate) {
		this.dataDate = dataDate;
	}

	public Integer getGdId() {
		return gdId;
	}

	public void setGdId(Integer gdId) {
		this.gdId = gdId;
	}

	public double getSignRate() {
		return signRate;
	}

	public void setSignRate(double signRate) {
		this.signRate = signRate;
	}

	

}
