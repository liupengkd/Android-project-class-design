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
 * ������ϸ���ݷ�����
 * 
 * @author ����
 * 
 */
public class DeliveryDetailDao extends DBConn {
	// �������۵��Ų�ѯ����������ϸ
	public List<DeliveryDetail> getAllDeliveryByDeliveryID(String deliveryid) {
		List<DeliveryDetail> deldtList = new ArrayList<DeliveryDetail>();
		String sql = "select * from deliverydetail where deliveryid like '%"
				+ deliveryid + "%'";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			// ��ȡ���ݿ�����
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

	// ���������ϸ
	public int addDeliveryDetail(DeliveryDetail deldt, Connection conn)
			throws SQLException {

		String sql = "insert into deliverydetail values ('"
				+ deldt.getDeliveryid() + "','" + deldt.getProductid() + "','"
				+ deldt.getSalesquantity() + "','"+deldt.getSalesprice()+"')";
		int i = 0;
		try {
			// ��ȡ���ݿ�����
			pstmt = conn.prepareStatement(sql);
			// ����executeSQL�������������
			i = pstmt.executeUpdate();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return i;
	}

}
