package com.pss.pojo;

public class PurchaseDetail {
	private String purchaseid;//�ɹ������   
	private String productid;  //��Ʒ���
	private int purchasequantity;//����
	private double purchaseunitprice;//����
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
