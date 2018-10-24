package com.pss.dao;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.pss.dbcon.DBConn;
import com.pss.pojo.Customer;
import com.pss.pojo.DeliveryDetail;

public class CustomerDao extends DBConn {
	// 查询所有客户信息
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

	// 添加客户信息
	public int AddCustomer(Customer cus) {
		int i = 0;
		String sql = "insert into customer(CustomerName,Telephone,CutomerAddress) values ( ?,?,?)";
		try {
			// 获取连接
			super.getConn();
			// 2 创建执行SQL语句对象PreparedStatement
			pstmt = conn.prepareStatement(sql);
			// 3 替换SQL语句中的？
			pstmt.setString(1, cus.getCustomername());
			pstmt.setString(2, cus.getTelephone());
			pstmt.setString(3, cus.getCustomeraddress());
			// 4 调用executeUpdate方法执行SQL语句，并返回执行结果
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

	// 根据姓名查询客户信息
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
	// 删除客户信息
	public int DeleteCustomerById(String id) {
		int i = 0;
		String sql = "delete from customer where customerid = ?";
		try {
			// 获取连接
			super.getConn();
			// 2 创建执行SQL语句对象PreparedStatement
			pstmt = conn.prepareStatement(sql);
			// 3 替换SQL语句中的？
			pstmt.setString(1, id);
			// 4 调用executeUpdate方法执行SQL语句，并返回执行结果
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
	// 修改客户信息
	public int UpdateCustomer(Customer cus){
		int i=0;
		String sql = "update customer set CustomerName=?,Telephone=?,CutomerAddress=? where CustomerID=?";
		try {
			//获取连接
			super.getConn();
			//创建SQL语句对象prepareStatement
			pstmt = conn.prepareStatement(sql);
			//替换SQL语句中的?
			pstmt.setString(1, cus.getCustomername());
			pstmt.setString(2,cus.getTelephone());
			pstmt.setString(3, cus.getCustomeraddress());
			pstmt.setInt(4, cus.getCustomerid());
			//调用方法修改，返回语句
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
	// 根据客户编号查询销售主表是否有数据
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
