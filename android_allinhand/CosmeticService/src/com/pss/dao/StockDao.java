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
	//库存预警
	public List<Product> getAlarmProducts(){
		//实例化集合对象 
		List<Product> productList=new ArrayList<Product>();
		//编写sql语句
		String sql="select p.productname,s.suppliername,p.quantity,p.maxsafestock,p.minsafestock from product p,supplier s where p.supplierid=s.supplierid and (Quantity < MinSafeStock or Quantity>MaxSafeStock)";
		try {
			//1 获得数据连接
			super.getConn();
			//2 创建执行SQL语句对象PreparedStatement
			pstmt=conn.prepareStatement(sql);
			//调用executeQuery()方法
			rs=pstmt.executeQuery();
			//从查询结集中读取数据
			while(rs.next()){
				Product product=new Product();
				product.setProductname(rs.getString(1));
				product.setSuppliername(rs.getString(2));
				product.setQuantity(rs.getInt(3));
				product.setMaxSafeStock(rs.getInt(4));
				product.setSafeStock(rs.getInt(5));
				//加入到集合中
				productList.add(product);
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally{
			try {
				//关闭数据库连接
				super.closeAll();
			} catch (Exception ex) {
				// TODO: handle exception
				ex.printStackTrace();
			}
		}
		//返回结果集
		return productList;
		
	}
	//得到所有商品的库存
	public List<Product> getALLProductList(){
		//实例化集合对象 
		List<Product> allProductList=new ArrayList<Product>();
		//编写sql语句
		String sql="select p.productid, p.ProductName,p.Quantity,s.SupplierName from product p,supplier s where p.SupplierID=s.SupplierID";
		try {
			//1 获得数据连接
			super.getConn();
			//2 创建执行SQL语句对象PreparedStatement
			pstmt=conn.prepareStatement(sql);
			//调用executeQuery()方法
			rs=pstmt.executeQuery();
			//从查询结集中读取数据
			while (rs.next()) {
				Product product=new Product();
				product.setProductid(rs.getString(1));
				product.setProductname(rs.getString(2));
				product.setQuantity(rs.getInt(3));
				product.setSuppliername(rs.getString(4));
				//加入到集合中
				allProductList.add(product);
				
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally{
			try {
				//关闭数据库连接
				super.closeAll();
			} catch (Exception ex) {
				// TODO: handle exception
				ex.printStackTrace();
			}
		}
		//返回结果集
		return allProductList;
	}
	//根据商品名称得到商品的信息
	public Product getProductByName(String productName){
		//实例化商品对像
		Product product=null;
		String sql="select p.productid, p.ProductName,p.Quantity,s.SupplierName from product p,supplier s where p.SupplierID=s.SupplierID and (p.ProductName=? or p.ProductID=?)";
		try {
			//1 获得数据连接
			super.getConn();
			//2 创建执行SQL语句对象PreparedStatement
			pstmt=conn.prepareStatement(sql);
			//3 替换SQL语句中的？
			pstmt.setString(1, productName);
			pstmt.setString(2, productName);
			//调用executeQuery()方法
			rs=pstmt.executeQuery();
			//获取结果
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
				//关闭数据库连接
				super.closeAll();
			} catch (Exception ex) {
				// TODO: handle exception
				ex.printStackTrace();
			}
		}
		//返回结果
		return product;
	}
	//判断该商品是否存在
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
