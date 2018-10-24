package com.pss.service;

import java.util.List;

import com.pss.dao.CustomerDao;
import com.pss.pojo.Customer;
import com.pss.pojo.DeliveryDetail;

public class CustomerService {
	CustomerDao cd = new CustomerDao();
	//��ȡ���пͻ���Ϣ
	public List<Customer> SelectAllCustomer(){
		return cd.SelectAllCustomer();
	}
	public List<DeliveryDetail> SelectCustomerID(String id){
		return cd.SelectCustomerID(id);
	}
	//��ӿͻ���Ϣ
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
	//ɾ���ͻ���Ϣ
	public int deleteCustomer(String id){
		return cd.DeleteCustomerById(id);
	}
	//�޸Ŀͻ���Ϣ
	public int updateCustomer(Customer cus){
		return cd.UpdateCustomer(cus);
	}
	//���ݿͻ�������ѯ�ͻ���Ϣ
	public Customer getCustomerByName(String name){
		return cd.getCustomerByName(name);
	}
}
