package com.gaoan.forever.model;

import java.io.Serializable;
import java.util.Calendar;

import com.gaoan.forever.utils.date.DateUtil;

public class UserInfoModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9013718917380465087L;

	private Long id;
	private String userName;
	private String password;
	private String realName;
	private Long roleId;
	private Integer status;

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

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
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

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

}