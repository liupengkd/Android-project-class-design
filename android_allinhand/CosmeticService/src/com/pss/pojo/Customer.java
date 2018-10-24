package com.pss.pojo;

public class Customer {
	private int customerid; //编号
	private String customername; //客户姓名
	private String telephone; //联系电话
	private String customeraddress; //客户送货地址
	public int getCustomerid(){
		return customerid;
	}
	public void setCustomerid(int customerid) {
		this.customerid = customerid;
	}
	public String getCustomername(){
		return customername;
	}
	public void setCustomername(String customername) {
		this.customername = customername;
	}
	public String getTelephone(){
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public String getCustomeraddress(){
		return customeraddress;
	}
	public void setCustomeraddress(String customeraddress)  {
		this.customeraddress = customeraddress;
	}
}
