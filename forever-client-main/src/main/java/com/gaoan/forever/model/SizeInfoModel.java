package com.gaoan.forever.model;

import java.io.Serializable;
import java.util.Calendar;

import com.gaoan.forever.utils.date.DateUtil;

public class SizeInfoModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9013718917380465087L;

	private Long id;
	private String sizeName;
	private String sizeCode;

	private String createTime;
	private String createDate;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSizeName() {
		return sizeName;
	}

	public void setSizeName(String sizeName) {
		this.sizeName = sizeName;
	}

	public String getSizeCode() {
		return sizeCode;
	}

	public void setSizeCode(String sizeCode) {
		this.sizeCode = sizeCode;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getCreateDate() {
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(Long.valueOf(this.createTime));
		return DateUtil.dateFormat1(cal.getTime());
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

}