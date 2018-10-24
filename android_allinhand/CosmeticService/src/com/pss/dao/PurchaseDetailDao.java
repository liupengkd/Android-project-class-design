package com.pss.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.pss.dbcon.DBConn;
import com.pss.pojo.PurchaseDetail;
/**
 * 
 * @author dxf_2012/11/7
 *
 */
public class PurchaseDetailDao extends DBConn{
	
	Connection con=null;
	//添加记录
	public int insert(PurchaseDetail pd,Connection conn) throws SQLException {
		//编写sql语句
		String sql = "insert into purchasedetail values('"+pd.getPurchaseid()+"','"+pd.getProductid()+"',"+pd.getPurchasequantity()+","+pd.getPurchaseunitprice()+")";
		con=conn;
		pstmt = conn.prepareStatement(sql); // 得到PreparedStatement对象
		int result = pstmt.executeUpdate(); // 执行SQL语句
		return result;	 
	}

	//删除记录,究竟能不能删除
	public int delete(String purchaseMasterId) throws SQLException {
		//编写sql语句
		String sql = "delete from PurchaseDetail where PurchaseID='?'";
		String[] params=new String[]{purchaseMasterId};
		//调用父类方法，并将结果赋予变量
		int result=super.executeSQL(sql, params);
		return result;	 
	}
	
	//根据采购单号得到所有采购明细
	public List<PurchaseDetail> getAll(String purchaseid){
		//实例化集合对象 
		List<PurchaseDetail> purchaseList=new ArrayList<PurchaseDetail>();
		//编写sql语句
		String sql="select * from PurchaseDetail where PurchaseID='"+purchaseid+"'";
		try {
			//1 获得数据连接
			super.getConn();
			//2 创建执行SQL语句对象PreparedStatement
			pstmt=conn.prepareStatement(sql);
			//调用executeQuery()方法
			rs=pstmt.executeQuery();
			//从查询结集中读取数据
			while(rs.next()){
				PurchaseDetail pm=new PurchaseDetail();
				pm.setPurchaseid(rs.getString(1));
				pm.setProductid(rs.getString(2));
				pm.setPurchasequantity(rs.getInt(3));
				pm.setPurchaseunitprice(rs.getDouble(4));
				//加入到集合中
				purchaseList.add(pm);
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
		return purchaseList;	
	}

}
