package com.pss.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.pss.dbcon.DBConn;
import com.pss.pojo.DeliveryDetail;

/**
 * 销售详细数据访问类
 * 
 * @author 刘晴
 * 
 */
public class DeliveryDetailDao extends DBConn {
	// 根据销售单号查询所有销售明细
	public List<DeliveryDetail> getAllDeliveryByDeliveryID(String deliveryid) {
		List<DeliveryDetail> deldtList = new ArrayList<DeliveryDetail>();
		String sql = "select * from deliverydetail where deliveryid like '%"
				+ deliveryid + "%'";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			// 获取数据库连接
			super.getConn();
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				DeliveryDetail deldt = new DeliveryDetail();
				deldt.setDeliveryid(rs.getString(1));
				deldt.setProductid(rs.getString(2));
				deldt.setSalesquantity(rs.getInt(3));
				deldt.setSalesprice(rs.getDouble(4));
				deldtList.add(deldt);
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				pstmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			super.closeAll();
		}
		return deldtList;
	}

	// 添加销售明细
	public int addDeliveryDetail(DeliveryDetail deldt, Connection conn)
			throws SQLException {

		String sql = "insert into deliverydetail values ('"
				+ deldt.getDeliveryid() + "','" + deldt.getProductid() + "','"
				+ deldt.getSalesquantity() + "','"+deldt.getSalesprice()+"')";
		int i = 0;
		try {
			// 获取数据库连接
			pstmt = conn.prepareStatement(sql);
			// 调用executeSQL方法，进行添加
			i = pstmt.executeUpdate();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return i;
	}

}
