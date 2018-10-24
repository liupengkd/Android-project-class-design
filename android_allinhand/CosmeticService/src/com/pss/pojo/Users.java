package com.pss.pojo;

public class Users {
	private String userid;//用户ID
	private String username;//用户名
	private String passwordcode;//密码
	private int isuse;//是否禁用
	private int userauthority;//用户权限
	private String useType;//是否禁用类型
	private String authorityType;//用户类型
	public String getUseType() {
		return useType;
	}
	public void setUseType(String useType) {
		this.useType = useType;
	}
	public String getAuthorityType() {
		return authorityType;
	}
	public void setAuthorityType(String authorityType) {
		this.authorityType = authorityType;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPasswordcode() {
		return passwordcode;
	}
	public void setPasswordcode(String passwordcode) {
		this.passwordcode = passwordcode;
	}
	public int getIsuse() {
		return isuse;
	}
	public void setIsuse(int isuse) {
		this.isuse = isuse;
	}
	public int getUserauthority() {
		return userauthority;
	}
	public void setUserauthority(int userauthority) {
		this.userauthority = userauthority;
	}
	
}
