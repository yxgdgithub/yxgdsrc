package com.mj.mmanage.model;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import com.alibaba.druid.pool.PreparedStatementPool;

@Table(name = "t_gd_plan_book_rel")
public class GdPlanBookRel extends BaseOperateEntity {

	@Id
    @Column(name = "gdId")
    private int gdId;
	
	@Id
    @Column(name = "bookId")
    private int bookId;

	public int getGdId() {
		return gdId;
	}

	public void setGdId(int gdId) {
		this.gdId = gdId;
	}

	public int getBookId() {
		return bookId;
	}

	public void setBookId(int bookId) {
		this.bookId = bookId;
	}

	
	
}
