package com.pss.pojo;

public class PurchaseDetail {
	private String purchaseid;//采购单编号   
	private String productid;  //商品编号
	private int purchasequantity;//数量
	private double purchaseunitprice;//单价
	public String getPurchaseid() {
		return purchaseid;
	}
	public void setPurchaseid(String purchaseid) {
		this.purchaseid = purchaseid;
	}
	public String getProductid() {
		return productid;
	}
	public void setProductid(String productid) {
		this.productid = productid;
	}
	public int getPurchasequantity() {
		return purchasequantity;
	}
	public void setPurchasequantity(int purchasequantity) {
		this.purchasequantity = purchasequantity;
	}
	public double getPurchaseunitprice() {
		return purchaseunitprice;
	}
	public void setPurchaseunitprice(double purchaseunitprice) {
		this.purchaseunitprice = purchaseunitprice;
	}

}
