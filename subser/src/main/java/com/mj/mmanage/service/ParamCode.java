package com.mj.mmanage.service;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

import com.mj.mmanage.model.BaseEntity;

@Table(name = "T_PARAM_CODE")
public class ParamCode extends BaseEntity {

	@Id
    @Column(name = "large_class")
    private String largeClass;

	@Id
    @Column(name = "detail_class")
	private String detailClass;
	
	@Id
    @Column(name = "param_no")
	private String paramNo;
	
	@Column(name = "param_value")
	private String paramValue;
	
	@Column(name = "param_explain")
	private String paramExplain;
	
	@Column(name = "param_state")
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
