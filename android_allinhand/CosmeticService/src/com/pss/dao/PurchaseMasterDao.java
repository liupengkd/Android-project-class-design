package com.pss.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.pss.dbcon.DBConn;
import com.pss.pojo.PurchaseMaster;

/**
 * 
 * @author dxf_2012/11/7
 * 
 */
public class PurchaseMasterDao extends DBConn {

	PurchaseDetailDao pdetaildao = new PurchaseDetailDao();
	Connection con = null;

	// ��Ӽ�¼
	public int insert(PurchaseMaster pc, Connection conn) throws SQLException {
		// ��дsql���
		String sql = "insert into purchasemaster values('" + pc.getPurchaseid()
				+ "','" + pc.getPurchasedate() + "','" + pc.getSupplierid()
				+ "'," + pc.getPurchaseproperty() + "," + pc.getSubtotal()
				+ ")";
		con = conn;
		pstmt = conn.prepareStatement(sql); // �õ�PreparedStatement����
		int result = pstmt.executeUpdate(); // ִ��SQL���
		return result;
	}

	// �õ����вɹ���ⵥ
	public List<PurchaseMaster> getAllRu(String purchaseid, Connection con) {
		// ʵ�������϶���
		List<PurchaseMaster> purchaseList = new ArrayList<PurchaseMaster>();
		// ��дsql���
		String sql = "select * from purchasemaster where PurchaseProperty=1 ";
		if (purchaseid != "" && purchaseid != null) {
			sql += "and purchaseid like '%" + purchaseid + "%' ";
		}
		sql += " order by PurchaseID desc";
		try {
			// 1 �����������
			conn = con;
			// 2 ����ִ��SQL������PreparedStatement
			pstmt = conn.prepareStatement(sql);
			// ����executeQuery()����
			rs = pstmt.executeQuery();
			// �Ӳ�ѯ�Ἧ�ж�ȡ����
			while (rs.next()) {
				PurchaseMaster pm = new PurchaseMaster();
				pm.setPurchaseid(rs.getString(1));
				pm.setPurchasedate(rs.getString(2));
				pm.setSupplierid(rs.getString(3));
				pm.setPurchaseproperty(rs.getInt(4));
				pm.setSubtotal(rs.getDouble(5));
				pm.setAllDetail(pdetaildao.getAll(pm.getPurchaseid()));
				// ���뵽������
				purchaseList.add(pm);
			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			try {
				// �ر����ݿ�����
				super.closeAll();
			} catch (Exception ex) {
				// TODO: handle exception
				ex.printStackTrace();
			}
		}
		// ���ؽ����
		return purchaseList;
	}

	// �õ����вɹ��˻���
	public List<PurchaseMaster> getAllTui(String purchaseid, Connection con) {
		// ʵ�������϶���
		List<PurchaseMaster> purchaseList = new ArrayList<PurchaseMaster>();
		// ��дsql���
		String sql = "select * from purchasemaster where PurchaseProperty=-1 ";
		if (purchaseid != "") {
			sql += "and purchaseid like '%" + purchaseid + "%' ";
		}
		sql += " order by PurchaseID desc";
		try {
			// 1 �����������
			conn = con;
			// 2 ����ִ��SQL������PreparedStatement
			pstmt = conn.prepareStatement(sql);
			// ����executeQuery()����
			rs = pstmt.executeQuery();
			// �Ӳ�ѯ�Ἧ�ж�ȡ����
			while (rs.next()) {
				PurchaseMaster pm = new PurchaseMaster();
				pm.setPurchaseid(rs.getString(1));
				pm.setPurchasedate(rs.getString(2));
				pm.setSupplierid(rs.getString(3));
				pm.setPurchaseproperty(rs.getInt(4));
				pm.setSubtotal(rs.getDouble(5));
				pm.setAllDetail(pdetaildao.getAll(pm.getPurchaseid()));
				// ���뵽������
				purchaseList.add(pm);
			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			try {
				// �ر����ݿ�����
				super.closeAll();
			} catch (Exception ex) {
				// TODO: handle exception
				ex.printStackTrace();
			}
		}
		// ���ؽ����
		return purchaseList;
	}

	// ͨ��id�õ��ɹ���ⵥ
	public PurchaseMaster getOneByIdRu(String purchaseid, Connection con) {
		// ʵ��������
		PurchaseMaster pm = new PurchaseMaster();
		// ��дsql���
		String sql = "select * from purchasemaster where PurchaseProperty=1 ";
		if (purchaseid != "") {
			sql += " and purchaseid like '%" + purchaseid + "%'";
		}
		try {
			// 1 �����������
			conn = con;
			// 2 ����ִ��SQL������PreparedStatement
			pstmt = conn.prepareStatement(sql);
			// ����executeQuery()����
			rs = pstmt.executeQuery();
			// �Ӳ�ѯ�Ἧ�ж�ȡ����
			if (rs.next()) {
				pm.setPurchaseid(rs.getString(1));
				pm.setPurchasedate(rs.getString(2));
				pm.setSupplierid(rs.getString(3));
				pm.setPurchaseproperty(rs.getInt(4));
				pm.setSubtotal(rs.getDouble(5));
				pm.setAllDetail(pdetaildao.getAll(pm.getPurchaseid()));
			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			try {
				// �ر����ݿ�����
				super.closeAll();
			} catch (Exception ex) {
				// TODO: handle exception
				ex.printStackTrace();
			}
		}
		// ���ؽ����
		return pm;
	}

	// ͨ��id�õ��ɹ��˻���
	public PurchaseMaster getOneByIdTui(String purchaseid, Connection con) {
		// ʵ��������
		PurchaseMaster pm = new PurchaseMaster();
		// ��дsql���
		String sql = "select * from purchasemaster where PurchaseProperty=-1 ";
		if (purchaseid != "") {
			sql += " and purchaseid like '%" + purchaseid + "%'";
		}
		try {
			// 1 �����������
			conn = con;
			// 2 ����ִ��SQL������PreparedStatement
			pstmt = conn.prepareStatement(sql);
			// ����executeQuery()����
			rs = pstmt.executeQuery();
			// �Ӳ�ѯ�Ἧ�ж�ȡ����
			if(rs.next()) {
				pm.setPurchaseid(rs.getString(1));
				pm.setPurchasedate(rs.getString(2));
				pm.setSupplierid(rs.getString(3));
				pm.setPurchaseproperty(rs.getInt(4));
				pm.setSubtotal(rs.getDouble(5));
				pm.setAllDetail(pdetaildao.getAll(pm.getPurchaseid()));
			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			try {
				// �ر����ݿ�����
				super.closeAll();
			} catch (Exception ex) {
				// TODO: handle exception
				ex.printStackTrace();
			}
		}
		// ���ؽ����
		return pm;
	}
}
