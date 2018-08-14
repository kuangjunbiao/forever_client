package com.gaoan.forever.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class PurchaseOrderInfoModel implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -9013718917380465087L;

    private String purchaseOrderName;
    private String goodsName;
    private String realName;

    private Long id;
    /** 商品编号 **/
    private Long goodsId;
    /** 成本价 **/
    private BigDecimal costPrice;
    /** 数量 **/
    private Long quantity;
    /** 进货总额 **/
    private BigDecimal total;
    /** 用户编号 **/
    private Long userId;

    /** 创建时间 **/
    private Date createTime;
    /** 修改时间 **/
    private Date updateTime;

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

    public String getRealName() {
	return realName;
    }

    public void setRealName(String realName) {
	this.realName = realName;
    }

    public Long getId() {
	return id;
    }

    public void setId(Long id) {
	this.id = id;
    }

    public Long getGoodsId() {
	return goodsId;
    }

    public void setGoodsId(Long goodsId) {
	this.goodsId = goodsId;
    }

    public BigDecimal getCostPrice() {
	return costPrice;
    }

    public void setCostPrice(BigDecimal costPrice) {
	this.costPrice = costPrice;
    }

    public Long getQuantity() {
	return quantity;
    }

    public void setQuantity(Long quantity) {
	this.quantity = quantity;
    }

    public BigDecimal getTotal() {
	return total;
    }

    public void setTotal(BigDecimal total) {
	this.total = total;
    }

    public Long getUserId() {
	return userId;
    }

    public void setUserId(Long userId) {
	this.userId = userId;
    }

    public Date getCreateTime() {
	return createTime;
    }

    public void setCreateTime(Date createTime) {
	this.createTime = createTime;
    }

    public Date getUpdateTime() {
	return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
	this.updateTime = updateTime;
    }

}