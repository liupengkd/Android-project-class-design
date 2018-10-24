package com.pss.dao;
import java.util.ArrayList;
import java.util.List;

import com.pss.dbcon.DBConn;

import com.pss.pojo.Product;
/**
 * 
 * @author yrr_2012/11/7
 *
 */
public class StockDao extends DBConn{
	//���Ԥ��
	public List<Product> getAlarmProducts(){
		//ʵ�������϶��� 
		List<Product> productList=new ArrayList<Product>();
		//��дsql���
		String sql="select p.productname,s.suppliername,p.quantity,p.maxsafestock,p.minsafestock from product p,supplier s where p.supplierid=s.supplierid and (Quantity < MinSafeStock or Quantity>MaxSafeStock)";
		try {
			//1 �����������
			super.getConn();
			//2 ����ִ��SQL������PreparedStatement
			pstmt=conn.prepareStatement(sql);
			//����executeQuery()����
			rs=pstmt.executeQuery();
			//�Ӳ�ѯ�Ἧ�ж�ȡ����
			while(rs.next()){
				Product product=new Product();
				product.setProductname(rs.getString(1));
				product.setSuppliername(rs.getString(2));
				product.setQuantity(rs.getInt(3));
				product.setMaxSafeStock(rs.getInt(4));
				product.setSafeStock(rs.getInt(5));
				//���뵽������
				productList.add(product);
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally{
			try {
				//�ر����ݿ�����
				super.closeAll();
			} catch (Exception ex) {
				// TODO: handle exception
				ex.printStackTrace();
			}
		}
		//���ؽ����
		return productList;
		
	}
	//�õ�������Ʒ�Ŀ��
	public List<Product> getALLProductList(){
		//ʵ�������϶��� 
		List<Product> allProductList=new ArrayList<Product>();
		//��дsql���
		String sql="select p.productid, p.ProductName,p.Quantity,s.SupplierName from product p,supplier s where p.SupplierID=s.SupplierID";
		try {
			//1 �����������
			super.getConn();
			//2 ����ִ��SQL������PreparedStatement
			pstmt=conn.prepareStatement(sql);
			//����executeQuery()����
			rs=pstmt.executeQuery();
			//�Ӳ�ѯ�Ἧ�ж�ȡ����
			while (rs.next()) {
				Product product=new Product();
				product.setProductid(rs.getString(1));
				product.setProductname(rs.getString(2));
				product.setQuantity(rs.getInt(3));
				product.setSuppliername(rs.getString(4));
				//���뵽������
				allProductList.add(product);
				
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally{
			try {
				//�ر����ݿ�����
				super.closeAll();
			} catch (Exception ex) {
				// TODO: handle exception
				ex.printStackTrace();
			}
		}
		//���ؽ����
		return allProductList;
	}
	//������Ʒ���Ƶõ���Ʒ����Ϣ
	public Product getProductByName(String productName){
		//ʵ������Ʒ����
		Product product=null;
		String sql="select p.productid, p.ProductName,p.Quantity,s.SupplierName from product p,supplier s where p.SupplierID=s.SupplierID and (p.ProductName=? or p.ProductID=?)";
		try {
			//1 �����������
			super.getConn();
			//2 ����ִ��SQL������PreparedStatement
			pstmt=conn.prepareStatement(sql);
			//3 �滻SQL����еģ�
			pstmt.setString(1, productName);
			pstmt.setString(2, productName);
			//����executeQuery()����
			rs=pstmt.executeQuery();
			//��ȡ���
			if (rs.next()) {
				product=new Product();
				product.setProductid(rs.getString(1));
				product.setProductname(rs.getString(2));
				product.setQuantity(rs.getInt(3));
				product.setSuppliername(rs.getString(4));
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally{
			try {
				//�ر����ݿ�����
				super.closeAll();
			} catch (Exception ex) {
				// TODO: handle exception
				ex.printStackTrace();
			}
		}
		//���ؽ��
		return product;
	}
	//�жϸ���Ʒ�Ƿ����
	public Product isExistProduct(String productId){
		Product product=null;
		String sql="select * from product where productId=?";
		try {
			super.getConn();
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, productId);
			rs=pstmt.executeQuery();
			if (rs.next()) {
				product=new Product();
				product.setProductid(rs.getString(1));
				product.setProductname(rs.getString(2));
				product.setProductid(rs.getString(3));
				product.setQuantity(rs.getInt(5));
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
		return product;
	}
	
}
