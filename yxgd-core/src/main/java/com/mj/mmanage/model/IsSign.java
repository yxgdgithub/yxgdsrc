package com.mj.mmanage.model;

public class IsSign extends BaseEntity{
	 /**
     * 图书ID
     *
     * @return bookSummary - 图书简介
     */
	private String bookSummary;
	 /**
     * 签到标识
     *
     * @return bookName - 签到标识
     */
	private String signFlag;
	
	/**
     * 图书名称
     *
     * @return bookName - 图书名称
     */
	private String bookName;
	
	public String getBookName() {
		return bookName;
	}
	public void setBookName(String bookName) {
		this.bookName = bookName;
	}
	public String getBookSummary() {
		return bookSummary;
	}
	public void setBookSummary(String bookSummary) {
		this.bookSummary = bookSummary;
	}
	public String getSignFlag() {
		return signFlag;
	}
	public void setSignFlag(String signFlag) {
		this.signFlag = signFlag;
	}

}
