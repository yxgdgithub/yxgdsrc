package com.mj.mmanage.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Transient;

/**
 * @author lihl
 * 表中如果包括创建人、创建时间、修改人、修改时间，则继承本类，否则直接继承BaseEntity类
 *
 */
public class BaseOperateEntity extends BaseEntity {

	@Column(name = "createUserId")
	private String createUserId;
	
	@Column(name = "createTime")
	private Date createTime;
	
	@Column(name = "updateUserId")
	private String updateUserId;
	
	@Column(name = "updateTime")
	private Date updateTime;

	public String getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(String createUserId) {
		this.createUserId = createUserId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getUpdateUserId() {
		return updateUserId;
	}

	public void setUpdateUserId(String updateUserId) {
		this.updateUserId = updateUserId;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	
}
