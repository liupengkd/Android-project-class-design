package com.pss.pojo;

public class LoseSpill {
	private String losespillid;//编号
	private String productid;//商品编号
	private int counts;//数量
	private int flags;//标识
	private String type;//类型
	private String productname;//商品名称
	private String checkdate;//盘点日期
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
