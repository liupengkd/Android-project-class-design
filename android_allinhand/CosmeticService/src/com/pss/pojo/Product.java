package com.pss.pojo;

public class Product {
	private String productid;//��Ʒ���
	private String productname;//��Ʒ����
	private double productprice;//����
	private String supplierid;//��Ӧ�̱��
	private String suppliername;//��Ӧ������
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
	private int quantity;//��ǰ����
	private int MaxSafeStock;//���ȫ����
	private int SafeStock;//��С��ȫ����
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
