package com.mj.mmanage.model;

import java.util.List;

public class MySign extends BaseEntity {
	/**
	 * 图书ID
	 */
	private Integer gdId;
	/**
	 * 图书标题
	 */
	private String gdTitle;
	/**
	 * 共读口号
	 * 
	 * @return gdSlogan - 共读口号
	 */
	private String gdSlogan;
	/**
	 * 微信昵称
	 * 
	 * @return nickName - 微信昵称
	 */
	private String nickName;
	/**
	 * 头像url
	 * 
	 * @return headUrl - 头像url
	 */
	private String headUrl;
	/**
	 * 连续签到次数
	 * 
	 * @return signContinuousDays - 连续签到次数
	 */
	private String signContinuousDays;
	/**
	 * 签到排名
	 * 
	 * @return rownum - 签到排名
	 */
	private String rownum;
	/**
	 * 签到日期
	 * 
	 * @return list - 签到日期
	 */
	private List<String> list;
	/**
	 * 共读开始时间
	 * 
	 * @return totalSign - 共度开始时间
	 */
	private String gdBeginDate;
	/**
	 * 共读结束时间
	 * 
	 * @return totalSign - 共读结束时间
	 */
	private String gdEndDate;
	/**
	 * 签到次数
	 * 
	 * @return totalSign - 签到次数
	 */
	private String totalSign;
	/**
	 * 当期累计签到率
	 * 
	 * @return signRate - 当期累计签到率
	 */
	private double signRate;

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getHeadUrl() {
		return headUrl;
	}

	public void setHeadUrl(String headUrl) {
		this.headUrl = headUrl;
	}

	public String getGdTitle() {
		return gdTitle;
	}

	public void setGdTitle(String gdTitle) {
		this.gdTitle = gdTitle;
	}

	public String getGdSlogan() {
		return gdSlogan;
	}

	public void setGdSlogan(String gdSlogan) {
		this.gdSlogan = gdSlogan;
	}

	public List<String> getList() {
		return list;
	}

	public void setList(List<String> list) {
		this.list = list;
	}

	public String getGdBeginDate() {
		return gdBeginDate;
	}

	public void setGdBeginDate(String gdBeginDate) {
		this.gdBeginDate = gdBeginDate;
	}

	public String getGdEndDate() {
		return gdEndDate;
	}

	public void setGdEndDate(String gdEndDate) {
		this.gdEndDate = gdEndDate;
	}

	public String getTotalSign() {
		return totalSign;
	}

	public void setTotalSign(String totalSign) {
		this.totalSign = totalSign;
	}

	public Integer getGdId() {
		return gdId;
	}

	public void setGdId(Integer gdId) {
		this.gdId = gdId;
	}

	public String getSignContinuousDays() {
		return signContinuousDays;
	}

	public void setSignContinuousDays(String signContinuousDays) {
		this.signContinuousDays = signContinuousDays;
	}

	public String getRownum() {
		return rownum;
	}

	public void setRownum(String rownum) {
		this.rownum = rownum;
	}

	public double getSignRate() {
		return signRate;
	}

	public void setSignRate(double signRate) {
		this.signRate = signRate;
	}
}
