package com.pss.pojo;

import java.util.List;

public class PurchaseMaster {
	private String purchaseid;// 编号
	private String purchasedate;// 采购日期/退货日期
	private String supplierid;// 供应商编号
	private int purchaseproperty;// 采购单入库1，采购退货-1
	private double subtotal;// 总金额
	private List<PurchaseDetail> allDetail;

	public List<PurchaseDetail> getAllDetail() {
		return allDetail;
	}

	public void setAllDetail(List<PurchaseDetail> allDetail) {
		this.allDetail = allDetail;
	}

	public String getPurchaseid() {
		return purchaseid;
	}

	public void setPurchaseid(String purchaseid) {
		this.purchaseid = purchaseid;
	}

	public String getPurchasedate() {
		return purchasedate;
	}

	public void setPurchasedate(String purchasedate) {
		this.purchasedate = purchasedate;
	}

	public String getSupplierid() {
		return supplierid;
	}

	public void setSupplierid(String supplierid) {
		this.supplierid = supplierid;
	}

	public int getPurchaseproperty() {
		return purchaseproperty;
	}

	public void setPurchaseproperty(int purchaseproperty) {
		this.purchaseproperty = purchaseproperty;
	}

	public double getSubtotal() {
		return subtotal;
	}

	public void setSubtotal(double subtotal) {
		this.subtotal = subtotal;
	}

}
