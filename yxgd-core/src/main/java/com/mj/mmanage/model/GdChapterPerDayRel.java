package com.mj.mmanage.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "t_gd_chapter_per_day_rel")
public class GdChapterPerDayRel<date> extends BaseEntity {
	
	@Id
	@Column(name = "gdDate")
	private String gdDate;
	
	@Column(name = "gdPeriod")
	private String gdPeriod;
	
	@Column(name = "bookId")
	private Integer bookId;
	
	@Column(name = "bookChapter")
	private Integer bookChapter;
	
	@Column(name = "gdId")
	private Integer gdId;
	
	@Column(name = "recordTime")
	private Timestamp recordTime;

	public String getGdDate() {
		return gdDate;
	}

	public void setGdDate(String gdDate) {
		this.gdDate = gdDate;
	}

	public String getGdPeriod() {
		return gdPeriod;
	}

	public void setGdPeriod(String gdPeriod) {
		this.gdPeriod = gdPeriod;
	}

	public Integer getBookId() {
		return bookId;
	}

	public void setBookId(Integer bookId) {
		this.bookId = bookId;
	}

	public Integer getBookChapter() {
		return bookChapter;
	}

	public void setBookChapter(Integer bookChapter) {
		this.bookChapter = bookChapter;
	}

	public Integer getGdId() {
		return gdId;
	}

	public void setGdId(Integer gdId) {
		this.gdId = gdId;
	}

	public Timestamp getRecordTime() {
		return recordTime;
	}

	public void setRecordTime(Timestamp recordTime) {
		this.recordTime = recordTime;
	}



	

	
	
	
}
