package com.pss.pojo;

import java.util.List;

/**
 * 销售单实体类
 * 
 * @author 刘晴
 * 
 */
public class DeliveryMaster {
	private String deliveryid;// 销售单单号
	private int deliveryproperty;// 销售1，销售退货－1
	private String deliverydate;// 销售日期/退货日期
	private String salesmanid;// 销售员编号
	private int customerid;// 客户编号
	private Double subtotal;// 总金额
	private List<DeliveryDetail> deldtList;// 销售单明细

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
