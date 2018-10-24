package com.pss.pojo;

public class Product {
	private String productid;//商品编号
	private String productname;//商品名称
	private double productprice;//单价
	private String supplierid;//供应商编号
	private String suppliername;//供应商名称
	public String getSuppliername() {
		return suppliername;
	}
	public void setSuppliername(String suppliername) {
		this.suppliername = suppliername;
	}
	public String getSupplierid() {
		return supplierid;
	}
	public void setSupplierid(String supplierid) {
		this.supplierid = supplierid;
	}
	private int quantity;//当前数量
	private int MaxSafeStock;//最大安全存量
	private int SafeStock;//最小安全存量
	public String getProductid() {
		return productid;
	}
	public void setProductid(String productid) {
		this.productid = productid;
	}
	public String getProductname() {
		return productname;
	}
	public void setProductname(String productname) {
		this.productname = productname;
	}
	public double getProductprice() {
		return productprice;
	}
	public void setProductprice(double productprice) {
		this.productprice = productprice;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public int getMaxSafeStock() {
		return MaxSafeStock;
	}
	public void setMaxSafeStock(int maxSafeStock) {
		MaxSafeStock = maxSafeStock;
	}
	public int getSafeStock() {
		return SafeStock;
	}
	public void setSafeStock(int safeStock) {
		SafeStock = safeStock;
	}
}
