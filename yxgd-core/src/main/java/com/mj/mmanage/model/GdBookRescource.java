package com.mj.mmanage.model;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Table(name = "t_gd_book_resource")
public class GdBookRescource extends BaseOperateEntity {
	@Id
	@GeneratedValue(generator="JDBC")
	@Column(name = "fileId")
	private Integer fileId;
	
	@Column(name = "bookId")
	private Integer bookId;
	
	@Column(name = "bookChapter")
	private String bookChapter;
	
	@Column(name = "filePath")
	private String filePath;
	
	@Column(name = "fileName")
	private String fileName;
	
	@Column(name = "fileSize")
	private double fileSize;
	
	@Column(name = "fileType")
	private String fileType;
	
	@Column(name = "fileState")
	private String fileState;
	
	@Transient
	private Integer gdId;
	@Transient
	private String signFlag;
	@Transient
	private String txtFilePathAndName;
	@Transient
	private double txtFileSize;
	@Transient
	private String voiceFilePathAndName;
	@Transient
	private double voiceFileSize;
	@Transient
	private String videoFilePathAndName;
	@Transient
	private double videoFileSize;
	
	public Integer getGdId() {
		return gdId;
	}

	public void setGdId(Integer gdId) {
		this.gdId = gdId;
	}

	public double getTxtFileSize() {
		return txtFileSize;
	}

	public void setTxtFileSize(double txtFileSize) {
		this.txtFileSize = txtFileSize;
	}

	public double getVoiceFileSize() {
		return voiceFileSize;
	}

	public void setVoiceFileSize(double voiceFileSize) {
		this.voiceFileSize = voiceFileSize;
	}

	public double getVideoFileSize() {
		return videoFileSize;
	}

	public void setVideoFileSize(double videoFileSize) {
		this.videoFileSize = videoFileSize;
	}

	public String getSignFlag() {
		return signFlag;
	}

	public void setSignFlag(String signFlag) {
		this.signFlag = signFlag;
	}

	public Integer getBookId() {
		return bookId;
	}

	public void setBookId(Integer bookId) {
		this.bookId = bookId;
	}

	public String getBookChapter() {
		return bookChapter;
	}

	public void setBookChapter(String bookChapter) {
		this.bookChapter = bookChapter;
	}

	public Integer getFileId() {
		return fileId;
	}

	public void setFileId(Integer fileId) {
		this.fileId = fileId;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public double getFileSize() {
		return fileSize;
	}

	public void setFileSize(double fileSize) {
		this.fileSize = fileSize;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public String getFileState() {
		return fileState;
	}

	public void setFileState(String fileState) {
		this.fileState = fileState;
	}

	public String getTxtFilePathAndName() {
		return txtFilePathAndName;
	}

	public void setTxtFilePathAndName(String txtFilePathAndName) {
		this.txtFilePathAndName = txtFilePathAndName;
	}

	public String getVoiceFilePathAndName() {
		return voiceFilePathAndName;
	}

	public void setVoiceFilePathAndName(String voiceFilePathAndName) {
		this.voiceFilePathAndName = voiceFilePathAndName;
	}

	public String getVideoFilePathAndName() {
		return videoFilePathAndName;
	}

	public void setVideoFilePathAndName(String videoFilePathAndName) {
		this.videoFilePathAndName = videoFilePathAndName;
	}

}
