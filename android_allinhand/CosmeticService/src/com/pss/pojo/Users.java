package com.pss.pojo;

public class Users {
	private String userid;//�û�ID
	private String username;//�û���
	private String passwordcode;//����
	private int isuse;//�Ƿ����
	private int userauthority;//�û�Ȩ��
	private String useType;//�Ƿ��������
	private String authorityType;//�û�����
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
