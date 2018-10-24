package com.pss.service;

import java.util.List;

import com.pss.dao.CustomerDao;
import com.pss.pojo.Customer;
import com.pss.pojo.DeliveryDetail;

public class CustomerService {
	CustomerDao cd = new CustomerDao();
	//获取所有客户信息
	public List<Customer> SelectAllCustomer(){
		return cd.SelectAllCustomer();
	}
	public List<DeliveryDetail> SelectCustomerID(String id){
		return cd.SelectCustomerID(id);
	}
	//添加客户信息
	public String AddCustomer(Customer cus){
		String flag="";
		Customer cus_check = cd.getCustomerByName(cus.getCustomername());
		if(null!=cus_check){
			flag="doublename";
		}else{
			int i=cd.AddCustomer(cus);
			if(i>0){
				flag="success";
			}else{
				flag="faild";
			}
		}
		return flag;
	}
	//删除客户信息
	public int deleteCustomer(String id){
		return cd.DeleteCustomerById(id);
	}
	//修改客户信息
	public int updateCustomer(Customer cus){
		return cd.UpdateCustomer(cus);
	}
	//根据客户姓名查询客户信息
	public Customer getCustomerByName(String name){
		return cd.getCustomerByName(name);
	}
}
