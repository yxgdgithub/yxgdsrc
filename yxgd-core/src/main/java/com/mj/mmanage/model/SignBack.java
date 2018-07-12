package com.mj.mmanage.model;

public class SignBack extends BaseEntity{
	 /**
     * 图书ID
     */
    private String gdId;

    /**
     * 获取图书名称
     *
     * @return bookName - 图书名称
     */
    private String bookName;

    /**
     * 获取图书路径
     *
     * @return bookSltPath - 图书路径
     */
    private String bookSltPath;


    /**
     * 连续签到次数
     *
     * @return signContinuousDays - 连续签到次数
     */
    private String signContinuousDays;
    /**
     * 获取序号
     *
     * @return rownum - 序号
     */
    private String rownum;
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
    
    private String nikeName;
    private String headUrl;
    
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


    public String getGdId() {
  		return gdId;
  	}

  	public void setGdId(String gdId) {
  		this.gdId = gdId;
  	}

  	public String getBookName() {
  		return bookName;
  	}

  	public void setBookName(String bookName) {
  		this.bookName = bookName;
  	}

  	public String getBookSltPath() {
  		return bookSltPath;
  	}

  	public void setBookSltPath(String bookSltPath) {
  		this.bookSltPath = bookSltPath;
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

	public String getNikeName() {
		return nikeName;
	}

	public void setNikeName(String nikeName) {
		this.nikeName = nikeName;
	}

	public String getHeadUrl() {
		return headUrl;
	}

	public void setHeadUrl(String headUrl) {
		this.headUrl = headUrl;
	}
}
