package com.gaoan.forever.model;

import java.io.Serializable;
import java.util.Calendar;

import com.gaoan.forever.utils.date.DateUtil;

public class RoleInfoModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9013718917380465087L;

	private Long id;
	private String roleName;

	private String createTime;
	private String updateTime;
	private String createDate;
	private String updateDate;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public String getCreateDate() {
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(Long.valueOf(this.createTime));
		return DateUtil.dateFormat1(cal.getTime());
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public String getUpdateDate() {
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(Long.valueOf(this.updateTime));
		return DateUtil.dateFormat1(cal.getTime());
	}

	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}
}