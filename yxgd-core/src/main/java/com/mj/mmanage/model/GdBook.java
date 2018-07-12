package com.mj.mmanage.model;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.web.multipart.MultipartFile;

@Table(name = "t_gd_book")
public class GdBook extends BaseOperateEntity {

	@Id
	@GeneratedValue(generator="JDBC")
	@Column(name = "bookId")
	private Integer bookId;
	
	@Column(name = "bookName")
	private String bookName;
	
	@Column(name = "bookAuthor")
	private String bookAuthor;
	
	@Column(name = "bookTag")
	private String bookTag;
	
	@Column(name = "bookSltPath")
	private String bookSltPath;
	
	@Column(name = "bookSummary")
	private String bookSummary;
	
	@Column(name = "chapterSum")
	private Integer chapterSum;
	
	@Transient
	private MultipartFile bookImg;
	
	public Integer getBookId() {
		return bookId;
	}

	public void setBookId(Integer bookId) {
		this.bookId = bookId;
	}

	public String getBookName() {
		return bookName;
	}

	public void setBookName(String bookName) {
		this.bookName = bookName;
	}

	public String getBookAuthor() {
		return bookAuthor;
	}

	public void setBookAuthor(String bookAuthor) {
		this.bookAuthor = bookAuthor;
	}

	public String getBookTag() {
		return bookTag;
	}

	public void setBookTag(String bookTag) {
		this.bookTag = bookTag;
	}

	public String getBookSltPath() {
		return bookSltPath;
	}

	public void setBookSltPath(String bookSltPath) {
		this.bookSltPath = bookSltPath;
	}

	public String getBookSummary() {
		return bookSummary;
	}

	public void setBookSummary(String bookSummary) {
		this.bookSummary = bookSummary;
	}

	/**
	 * @return the chapterSum
	 */
	public Integer getChapterSum() {
		return chapterSum;
	}

	/**
	 * @param chapterSum the chapterSum to set
	 */
	public void setChapterSum(Integer chapterSum) {
		this.chapterSum = chapterSum;
	}

	/**
	 * @return the bookImg
	 */
	public MultipartFile getBookImg() {
		return bookImg;
	}

	/**
	 * @param bookImg the bookImg to set
	 */
	public void setBookImg(MultipartFile bookImg) {
		this.bookImg = bookImg;
	}
	
	
}
