package com.mj.mmanage.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Table(name = "t_gd_plan")
public class GdPlan extends BaseOperateEntity {

	@Id
    @Column(name = "gdId")
	@GeneratedValue(strategy = GenerationType.IDENTITY,generator="JDBC")
    private Integer gdId;
	
	@Column(name = "gdTitle")
    private String gdTitle;
	
	@Column(name = "signupBeginDate")
    private String signupBeginDate;
	
	@Column(name = "signupEndDate")
    private String signupEndDate;
	
	@Column(name = "gdBeginDate")
    private String gdBeginDate;
	
	@Column(name = "gdEndDate")
    private String gdEndDate;
	
	@Column(name = "gdState")
    private String gdState;
	
	@Column(name = "sponsorUser")
    private String sponsorUser;
	
	@Column(name = "gdSlogan")
    private String gdSlogan;
	
	@Column(name = "gdInstr")
    private String gdInstr;
	
	@Transient
	private String applyFlag;
	@Transient
	private String wxUserId;
	@Transient
	private String applyNum;
	@Transient
	private int signNum;
	@Transient
	private String nowFlag;
	
	@Transient
	private int signExpectNum;
	
	@Transient
	private int signContinuousDays;
	
	@Transient
	private double signRate;

	@Transient
	private List<GdBook> gdBook;
	
	
	/**
	 * 当天共读的图书名称
	 */
	@Transient
	private String bookName;

	public List<GdBook> getGdBook() {
		return gdBook;
	}

	public void setGdBook(List<GdBook> gdBook) {
		this.gdBook = gdBook;
	}

	public Integer getGdId() {
		return gdId;
	}

	public void setGdId(Integer gdId) {
		this.gdId = gdId;
	}

	public String getGdTitle() {
		return gdTitle;
	}

	public void setGdTitle(String gdTitle) {
		this.gdTitle = gdTitle;
	}

	public String getSignupBeginDate() {
		return signupBeginDate;
	}

	public void setSignupBeginDate(String signupBeginDate) {
		this.signupBeginDate = signupBeginDate;
	}

	public String getSignupEndDate() {
		return signupEndDate;
	}

	public void setSignupEndDate(String signupEndDate) {
		this.signupEndDate = signupEndDate;
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

	public String getGdState() {
		return gdState;
	}

	public void setGdState(String gdState) {
		this.gdState = gdState;
	}

	public String getSponsorUser() {
		return sponsorUser;
	}

	public void setSponsorUser(String sponsorUser) {
		this.sponsorUser = sponsorUser;
	}

	public String getGdSlogan() {
		return gdSlogan;
	}

	public void setGdSlogan(String gdSlogan) {
		this.gdSlogan = gdSlogan;
	}

	public String getApplyFlag() {
		return applyFlag;
	}

	public void setApplyFlag(String applyFlag) {
		this.applyFlag = applyFlag;
	}

	public String getWxUserId() {
		return wxUserId;
	}

	public void setWxUserId(String wxUserId) {
		this.wxUserId = wxUserId;
	}

	public String getApplyNum() {
		return applyNum;
	}

	public void setApplyNum(String applyNum) {
		this.applyNum = applyNum;
	}

	/**
	 * @return the gdInstr
	 */
	public String getGdInstr() {
		return gdInstr;
	}

	/**
	 * @param gdInstr the gdInstr to set
	 */
	public void setGdInstr(String gdInstr) {
		this.gdInstr = gdInstr;
	}

	/**
	 * @return the signNum
	 */
	public int getSignNum() {
		return signNum;
	}

	/**
	 * @param signNum the signNum to set
	 */
	public void setSignNum(int signNum) {
		this.signNum = signNum;
	}

	public int getSignExpectNum() {
		return signExpectNum;
	}

	public void setSignExpectNum(int signExpectNum) {
		this.signExpectNum = signExpectNum;
	}

	public int getSignContinuousDays() {
		return signContinuousDays;
	}

	public void setSignContinuousDays(int signContinuousDays) {
		this.signContinuousDays = signContinuousDays;
	}

	public double getSignRate() {
		return signRate;
	}

	public void setSignRate(double signRate) {
		this.signRate = signRate;
	}

	public String getBookName() {
		return bookName;
	}

	public void setBookName(String bookName) {
		this.bookName = bookName;
	}

	public String getNowFlag() {
		return nowFlag;
	}

	public void setNowFlag(String nowFlag) {
		this.nowFlag = nowFlag;
	}

}
