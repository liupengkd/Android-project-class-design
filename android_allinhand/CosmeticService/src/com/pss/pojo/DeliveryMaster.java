package com.pss.pojo;

import java.util.List;

/**
 * ���۵�ʵ����
 * 
 * @author ����
 * 
 */
public class DeliveryMaster {
	private String deliveryid;// ���۵�����
	private int deliveryproperty;// ����1�������˻���1
	private String deliverydate;// ��������/�˻�����
	private String salesmanid;// ����Ա���
	private int customerid;// �ͻ����
	private Double subtotal;// �ܽ��
	private List<DeliveryDetail> deldtList;// ���۵���ϸ

	public List<DeliveryDetail> getDeldtList() {
		return deldtList;
	}

	public void setDeldtList(List<DeliveryDetail> deldtList) {
		this.deldtList = deldtList;
	}

	public String getDeliveryid() {
		return deliveryid;
	}

	public void setDeliveryid(String deliveryid) {
		this.deliveryid = deliveryid;
	}

	public int getDeliveryproperty() {
		return deliveryproperty;
	}

	public void setDeliveryproperty(int deliveryproperty) {
		this.deliveryproperty = deliveryproperty;
	}

	public String getDeliverydate() {
		return deliverydate;
	}

	public void setDeliverydate(String deliverydate) {
		this.deliverydate = deliverydate;
	}

	public String getSalesmanid() {
		return salesmanid;
	}

	public void setSalesmanid(String salesmanid) {
		this.salesmanid = salesmanid;
	}

	public int getCustomerid() {
		return customerid;
	}

	public void setCustomerid(int customerid) {
		this.customerid = customerid;
	}

	public Double getSubtotal() {
		return subtotal;
	}

	public void setSubtotal(Double subtotal) {
		this.subtotal = subtotal;
	}
}
