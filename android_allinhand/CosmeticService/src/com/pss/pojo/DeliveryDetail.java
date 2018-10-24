package com.pss.pojo;

/**
 * 销售明细实体
 * 
 * @author 刘晴
 * 
 */
public class DeliveryDetail {
	private String deliveryid;// 销售单单号
	private String productid; // 商品编号
	private int salesquantity;// 销售数量
	private Double salesprice;// 商品销售单价

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
