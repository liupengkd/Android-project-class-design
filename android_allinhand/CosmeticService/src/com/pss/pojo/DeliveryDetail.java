package com.pss.pojo;

/**
 * ������ϸʵ��
 * 
 * @author ����
 * 
 */
public class DeliveryDetail {
	private String deliveryid;// ���۵�����
	private String productid; // ��Ʒ���
	private int salesquantity;// ��������
	private Double salesprice;// ��Ʒ���۵���

	public Double getSalesprice() {
		return salesprice;
	}

	public void setSalesprice(Double productprice) {
		this.salesprice = productprice;
	}

	public String getDeliveryid() {
		return deliveryid;
	}

	public void setDeliveryid(String deliveryid) {
		this.deliveryid = deliveryid;
	}

	public String getProductid() {
		return productid;
	}

	public void setProductid(String productid) {
		this.productid = productid;
	}

	public int getSalesquantity() {
		return salesquantity;
	}

	public void setSalesquantity(int salesquantity) {
		this.salesquantity = salesquantity;
	}

}
