package com.gaoan.forever.model;

import java.io.Serializable;

public class OrderQueryConditionModel implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 8474933862389943580L;

    private String purchaseOrderName;
    private String goodsName;
    private String startTime;
    private String endTime;

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

    public String getStartTime() {
	return startTime;
    }

    public void setStartTime(String startTime) {
	this.startTime = startTime;
    }

    public String getEndTime() {
	return endTime;
    }

    public void setEndTime(String endTime) {
	this.endTime = endTime;
    }

}