package com.pss.pojo;

public class Customer {
	private int customerid; //���
	private String customername; //�ͻ�����
	private String telephone; //��ϵ�绰
	private String customeraddress; //�ͻ��ͻ���ַ
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
