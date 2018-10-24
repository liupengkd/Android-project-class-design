package com.pss.dao;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.pss.dbcon.DBConn;
import com.pss.pojo.Customer;
import com.pss.pojo.DeliveryDetail;

public class CustomerDao extends DBConn {
	// ��ѯ���пͻ���Ϣ
	public List<Customer> SelectAllCustomer() {
		List<Customer> cus = new ArrayList<Customer>();
		String sql = "select * from customer";
		try {
			super.getConn();
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				Customer cu = new Customer();
				cu.setCustomerid(rs.getInt(1));
				cu.setCustomername(rs.getString(2));
				cu.setTelephone(rs.getString(3));
				cu.setCustomeraddress(rs.getString(4));
				cus.add(cu);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			try {
				super.closeAll();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		return cus;
	}

	// ��ӿͻ���Ϣ
	public int AddCustomer(Customer cus) {
		int i = 0;
		String sql = "insert into customer(CustomerName,Telephone,CutomerAddress) values ( ?,?,?)";
		try {
			// ��ȡ����
			super.getConn();
			// 2 ����ִ��SQL������PreparedStatement
			pstmt = conn.prepareStatement(sql);
			// 3 �滻SQL����еģ�
			pstmt.setString(1, cus.getCustomername());
			pstmt.setString(2, cus.getTelephone());
			pstmt.setString(3, cus.getCustomeraddress());
			// 4 ����executeUpdate����ִ��SQL��䣬������ִ�н��
			i = pstmt.executeUpdate();
		} catch (Exception ex) {
			ex.printStackTrace();
		}finally{
			try{
				super.closeAll();
			}catch(Exception ex){
				ex.printStackTrace();
			}
		}
		return i;
	}

	// ����������ѯ�ͻ���Ϣ
	public Customer getCustomerByName(String name){
		Customer customer = null;
		String sql = "select * from customer where CustomerName=? or CustomerID=?";
		ResultSet rs = null;
		try {
			super.getConn();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, name);
			pstmt.setString(2, name);
			rs=pstmt.executeQuery();
			if(rs.next()){
				customer = new Customer();
				customer.setCustomerid(rs.getInt(1));
				customer.setCustomername(rs.getString(2));
				customer.setTelephone(rs.getString(3));
				customer.setCustomeraddress(rs.getString(4));
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}finally{
			try{
				super.closeAll();
			}catch(Exception ex){
				ex.printStackTrace();
			}
		}
		return customer;
	}
	// ɾ���ͻ���Ϣ
	public int DeleteCustomerById(String id) {
		int i = 0;
		String sql = "delete from customer where customerid = ?";
		try {
			// ��ȡ����
			super.getConn();
			// 2 ����ִ��SQL������PreparedStatement
			pstmt = conn.prepareStatement(sql);
			// 3 �滻SQL����еģ�
			pstmt.setString(1, id);
			// 4 ����executeUpdate����ִ��SQL��䣬������ִ�н��
			i = pstmt.executeUpdate();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally{
				try{
					super.closeAll();
				}catch(Exception ex){
					ex.printStackTrace();
				}
			}
		return i;
	}
	// �޸Ŀͻ���Ϣ
	public int UpdateCustomer(Customer cus){
		int i=0;
		String sql = "update customer set CustomerName=?,Telephone=?,CutomerAddress=? where CustomerID=?";
		try {
			//��ȡ����
			super.getConn();
			//����SQL������prepareStatement
			pstmt = conn.prepareStatement(sql);
			//�滻SQL����е�?
			pstmt.setString(1, cus.getCustomername());
			pstmt.setString(2,cus.getTelephone());
			pstmt.setString(3, cus.getCustomeraddress());
			pstmt.setInt(4, cus.getCustomerid());
			//���÷����޸ģ��������
			i=pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			try{
				super.closeAll();
			}catch(Exception ex){
				ex.printStackTrace();
			}
		}
		return i;
		
	}
	// ���ݿͻ���Ų�ѯ���������Ƿ�������
	public List<DeliveryDetail> SelectCustomerID(String id){
			List<DeliveryDetail> deList=new ArrayList<DeliveryDetail>();
			String sql = "select * from deliverymaster where customerid=?";
			try {
				super.getConn();
				pstmt=conn.prepareStatement(sql);
				pstmt.setString(1, id);
				rs=pstmt.executeQuery();
				while (rs.next()) {
					DeliveryDetail deliveryDetail=new DeliveryDetail();
					deliveryDetail.setDeliveryid(rs.getString(1));
					deliveryDetail.setProductid(rs.getString(2));
					deliveryDetail.setSalesquantity(rs.getInt(3));
					deList.add(deliveryDetail);
					
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}finally{
				try {
					super.closeAll();
				} catch (Exception e2) {
					// TODO: handle exception
					e2.printStackTrace();
				}
			}
			return deList;
			
		}
}
