package com.pss.pojo;

import java.util.List;

public class PurchaseMaster {
	private String purchaseid;// ���
	private String purchasedate;// �ɹ�����/�˻�����
	private String supplierid;// ��Ӧ�̱��
	private int purchaseproperty;// �ɹ������1���ɹ��˻�-1
	private double subtotal;// �ܽ��
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
