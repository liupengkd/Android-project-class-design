package com.pss.pojo;

public class LoseSpill {
	private String losespillid;//���
	private String productid;//��Ʒ���
	private int counts;//����
	private int flags;//��ʶ
	private String type;//����
	private String productname;//��Ʒ����
	private String checkdate;//�̵�����
	public String getCheckdate() {
		return checkdate;
	}
	public void setCheckdate(String checkdate) {
		this.checkdate = checkdate;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getLosespillid() {
		return losespillid;
	}
	public String getProductname() {
		return productname;
	}
	public void setProductname(String productname) {
		this.productname = productname;
	}
	public void setLosespillid(String losespillid) {
		this.losespillid = losespillid;
	}
	public String getProductid() {
		return productid;
	}
	public void setProductid(String productid) {
		this.productid = productid;
	}
	public int getCounts() {
		return counts;
	}
	public void setCounts(int counts) {
		this.counts = counts;
	}
	public int getFlags() {
		return flags;
	}
	public void setFlags(int flags) {
		this.flags = flags;
	}

}
