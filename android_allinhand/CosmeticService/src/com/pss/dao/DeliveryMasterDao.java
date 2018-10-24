package com.pss.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.pss.dbcon.DBConn;
import com.pss.pojo.DeliveryMaster;

/**
 * ���۵����ݷ�����
 * 
 * @author ����
 * 
 */
public class DeliveryMasterDao extends DBConn {
	DeliveryDetailDao ddd = new DeliveryDetailDao();
	// ��ѯ�������۵�(DeliveryProperty,1)
	public List<DeliveryMaster> getAllDelivery(int dp) {
		List<DeliveryMaster> delmtList = new ArrayList<DeliveryMaster>();
		String sql = "select * from deliverymaster where DeliveryProperty = '"+dp+"' order by deliveryid desc";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			// ��ȡ���ݿ�����
			super.getConn();
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				DeliveryMaster delmt = new DeliveryMaster();
				delmt.setDeliveryid(rs.getString(1));
				delmt.setDeliveryproperty(rs.getInt(2));
				delmt.setDeliverydate(rs.getString(3));
				delmt.setSalesmanid(rs.getString(4));
				delmt.setCustomerid(rs.getInt(5));
				delmt.setSubtotal(rs.getDouble(6));
				delmt.setDeldtList(ddd.getAllDeliveryByDeliveryID(rs.getString(1)));
				delmtList.add(delmt);
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
		return delmtList;
	}

	// ������۵�
	public int addDeliveryMaster(DeliveryMaster delmt, Connection conn)
			throws SQLException {
		String sql = "insert into deliverymaster values ('"
				+ delmt.getDeliveryid() + "'," + delmt.getDeliveryproperty()
				+ ",'" + delmt.getDeliverydate() + "','"
				+ delmt.getSalesmanid() + "','" + delmt.getCustomerid() + "','"
				+ delmt.getSubtotal() + "')";
		int i = 0;
		
		// ��ȡ���ݿ�����
		pstmt = conn.prepareStatement(sql);

		// ����executeSQL�������������
		i = pstmt.executeUpdate();

		return i;
	}

	// �������۵��Ų�ѯ���۵�
	public DeliveryMaster getOneDelivery(String ID,Connection conn) {
		String sql = "select * from deliverymaster where DeliveryID like '%" + ID
				+ "%'";
		ResultSet rs=null;
		DeliveryMaster delmt=null;
		try {
			pstmt=conn.prepareStatement(sql);
			rs=pstmt.executeQuery();
			if(rs.next()){
				delmt=new DeliveryMaster();
				delmt.setDeliveryid(rs.getString(1));
				delmt.setDeliveryproperty(rs.getInt(2));
				delmt.setDeliverydate(rs.getString(3));
				delmt.setSalesmanid(rs.getString(4));
				delmt.setCustomerid(rs.getInt(5));
				delmt.setSubtotal(rs.getDouble(6));
				delmt.setDeldtList(ddd.getAllDeliveryByDeliveryID(rs.getString(1)));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return delmt;
	}
}
