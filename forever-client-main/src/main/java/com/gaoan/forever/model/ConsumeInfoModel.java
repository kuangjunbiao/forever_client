package com.gaoan.forever.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Calendar;

import com.gaoan.forever.utils.date.DateUtil;

public class ConsumeInfoModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6738023678775253711L;

	private Long id;
	private String date;
	@SuppressWarnings("unused")
	private String dateStr;
	private BigDecimal amount;
	private String remark;
	private String createTime;
	@SuppressWarnings("unused")
	private String createDate;
	private String updateTime;
	@SuppressWarnings("unused")
	private String updateDate;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public String getUpdateDate() {
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(Long.valueOf(this.updateTime));
		return DateUtil.dateFormat1(cal.getTime());
	}

	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}

	public String getDateStr() {
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(Long.valueOf(this.date));
		return DateUtil.dateFormatY1(cal.getTime());
	}

	public void setDateStr(String dateStr) {
		this.dateStr = dateStr;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}