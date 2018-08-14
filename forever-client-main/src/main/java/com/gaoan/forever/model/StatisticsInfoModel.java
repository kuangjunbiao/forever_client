package com.gaoan.forever.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class StatisticsInfoModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3044604401484686793L;

	private String date;
	private Long total;
	private BigDecimal sellPriceTotal;
	private BigDecimal costPriceTotal;
	private BigDecimal salesProfitTotal;
	private BigDecimal consumeTotal;
	private BigDecimal profitTotal;

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public Long getTotal() {
		return total;
	}

	public void setTotal(Long total) {
		this.total = total;
	}

	public BigDecimal getSellPriceTotal() {
		return sellPriceTotal;
	}

	public void setSellPriceTotal(BigDecimal sellPriceTotal) {
		this.sellPriceTotal = sellPriceTotal;
	}

	public BigDecimal getCostPriceTotal() {
		return costPriceTotal;
	}

	public void setCostPriceTotal(BigDecimal costPriceTotal) {
		this.costPriceTotal = costPriceTotal;
	}

	public BigDecimal getProfitTotal() {
		return profitTotal;
	}

	public void setProfitTotal(BigDecimal profitTotal) {
		this.profitTotal = profitTotal;
	}

	public BigDecimal getSalesProfitTotal() {
		return salesProfitTotal;
	}

	public void setSalesProfitTotal(BigDecimal salesProfitTotal) {
		this.salesProfitTotal = salesProfitTotal;
	}

	public BigDecimal getConsumeTotal() {
		return consumeTotal;
	}

	public void setConsumeTotal(BigDecimal consumeTotal) {
		this.consumeTotal = consumeTotal;
	}

}