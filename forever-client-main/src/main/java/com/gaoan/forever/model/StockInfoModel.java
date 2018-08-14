package com.gaoan.forever.model;

import java.io.Serializable;
import java.util.Calendar;

import com.gaoan.forever.utils.date.DateUtil;

public class StockInfoModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9013718917380465087L;

	private String purchaseOrderName;
	private String goodsName;
	private Long colorId;
	private String colorName;
	private Long sizeId;
	private String sizeName;

	private Long id;
	/** 数量 **/
	private Long quantity;

	private String createTime;
	private String updateTime;
	private String createDate;
	private String updateDate;

	public String getPurchaseOrderName() {
		return purchaseOrderName;
	}

	public void setPurchaseOrderName(String purchaseOrderName) {
		this.purchaseOrderName = purchaseOrderName;
	}

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getQuantity() {
		return quantity;
	}

	public void setQuantity(Long quantity) {
		this.quantity = quantity;
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

	public Long getColorId() {
		return colorId;
	}

	public void setColorId(Long colorId) {
		this.colorId = colorId;
	}

	public String getColorName() {
		return colorName;
	}

	public void setColorName(String colorName) {
		this.colorName = colorName;
	}

	public Long getSizeId() {
		return sizeId;
	}

	public void setSizeId(Long sizeId) {
		this.sizeId = sizeId;
	}

	public String getSizeName() {
		return sizeName;
	}

	public void setSizeName(String sizeName) {
		this.sizeName = sizeName;
	}

}