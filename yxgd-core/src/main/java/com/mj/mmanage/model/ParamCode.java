package com.mj.mmanage.model;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "t_param_code")
public class ParamCode extends BaseEntity {

	@Id
    @Column(name = "largeClass")
    private String largeClass;

	@Id
    @Column(name = "detailClass")
	private String detailClass;
	
	@Id
    @Column(name = "paramNo")
	private String paramNo;
	
	@Column(name = "paramValue")
	private String paramValue;
	
	@Column(name = "paramExplain")
	private String paramExplain;
	
	@Column(name = "paramState")
	private String paramState;

	public String getLargeClass() {
		return largeClass;
	}

	public void setLargeClass(String largeClass) {
		this.largeClass = largeClass;
	}

	public String getDetailClass() {
		return detailClass;
	}

	public void setDetailClass(String detailClass) {
		this.detailClass = detailClass;
	}

	public String getParamNo() {
		return paramNo;
	}

	public void setParamNo(String paramNo) {
		this.paramNo = paramNo;
	}

	public String getParamValue() {
		return paramValue;
	}

	public void setParamValue(String paramValue) {
		this.paramValue = paramValue;
	}

	public String getParamExplain() {
		return paramExplain;
	}

	public void setParamExplain(String paramExplain) {
		this.paramExplain = paramExplain;
	}

	public String getParamState() {
		return paramState;
	}

	public void setParamState(String paramState) {
		this.paramState = paramState;
	}
	

	
}
