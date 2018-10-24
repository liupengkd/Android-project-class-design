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

	// 添加记录
	public int insert(PurchaseMaster pc, Connection conn) throws SQLException {
		// 编写sql语句
		String sql = "insert into purchasemaster values('" + pc.getPurchaseid()
				+ "','" + pc.getPurchasedate() + "','" + pc.getSupplierid()
				+ "'," + pc.getPurchaseproperty() + "," + pc.getSubtotal()
				+ ")";
		con = conn;
		pstmt = conn.prepareStatement(sql); // 得到PreparedStatement对象
		int result = pstmt.executeUpdate(); // 执行SQL语句
		return result;
	}

	// 得到所有采购入库单
	public List<PurchaseMaster> getAllRu(String purchaseid, Connection con) {
		// 实例化集合对象
		List<PurchaseMaster> purchaseList = new ArrayList<PurchaseMaster>();
		// 编写sql语句
		String sql = "select * from purchasemaster where PurchaseProperty=1 ";
		if (purchaseid != "" && purchaseid != null) {
			sql += "and purchaseid like '%" + purchaseid + "%' ";
		}
		sql += " order by PurchaseID desc";
		try {
			// 1 获得数据连接
			conn = con;
			// 2 创建执行SQL语句对象PreparedStatement
			pstmt = conn.prepareStatement(sql);
			// 调用executeQuery()方法
			rs = pstmt.executeQuery();
			// 从查询结集中读取数据
			while (rs.next()) {
				PurchaseMaster pm = new PurchaseMaster();
				pm.setPurchaseid(rs.getString(1));
				pm.setPurchasedate(rs.getString(2));
				pm.setSupplierid(rs.getString(3));
				pm.setPurchaseproperty(rs.getInt(4));
				pm.setSubtotal(rs.getDouble(5));
				pm.setAllDetail(pdetaildao.getAll(pm.getPurchaseid()));
				// 加入到集合中
				purchaseList.add(pm);
			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			try {
				// 关闭数据库连接
				super.closeAll();
			} catch (Exception ex) {
				// TODO: handle exception
				ex.printStackTrace();
			}
		}
		// 返回结果集
		return purchaseList;
	}

	// 得到所有采购退货单
	public List<PurchaseMaster> getAllTui(String purchaseid, Connection con) {
		// 实例化集合对象
		List<PurchaseMaster> purchaseList = new ArrayList<PurchaseMaster>();
		// 编写sql语句
		String sql = "select * from purchasemaster where PurchaseProperty=-1 ";
		if (purchaseid != "") {
			sql += "and purchaseid like '%" + purchaseid + "%' ";
		}
		sql += " order by PurchaseID desc";
		try {
			// 1 获得数据连接
			conn = con;
			// 2 创建执行SQL语句对象PreparedStatement
			pstmt = conn.prepareStatement(sql);
			// 调用executeQuery()方法
			rs = pstmt.executeQuery();
			// 从查询结集中读取数据
			while (rs.next()) {
				PurchaseMaster pm = new PurchaseMaster();
				pm.setPurchaseid(rs.getString(1));
				pm.setPurchasedate(rs.getString(2));
				pm.setSupplierid(rs.getString(3));
				pm.setPurchaseproperty(rs.getInt(4));
				pm.setSubtotal(rs.getDouble(5));
				pm.setAllDetail(pdetaildao.getAll(pm.getPurchaseid()));
				// 加入到集合中
				purchaseList.add(pm);
			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			try {
				// 关闭数据库连接
				super.closeAll();
			} catch (Exception ex) {
				// TODO: handle exception
				ex.printStackTrace();
			}
		}
		// 返回结果集
		return purchaseList;
	}

	// 通过id得到采购入库单
	public PurchaseMaster getOneByIdRu(String purchaseid, Connection con) {
		// 实例化对象
		PurchaseMaster pm = new PurchaseMaster();
		// 编写sql语句
		String sql = "select * from purchasemaster where PurchaseProperty=1 ";
		if (purchaseid != "") {
			sql += " and purchaseid like '%" + purchaseid + "%'";
		}
		try {
			// 1 获得数据连接
			conn = con;
			// 2 创建执行SQL语句对象PreparedStatement
			pstmt = conn.prepareStatement(sql);
			// 调用executeQuery()方法
			rs = pstmt.executeQuery();
			// 从查询结集中读取数据
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
				// 关闭数据库连接
				super.closeAll();
			} catch (Exception ex) {
				// TODO: handle exception
				ex.printStackTrace();
			}
		}
		// 返回结果集
		return pm;
	}

	// 通过id得到采购退货单
	public PurchaseMaster getOneByIdTui(String purchaseid, Connection con) {
		// 实例化对象
		PurchaseMaster pm = new PurchaseMaster();
		// 编写sql语句
		String sql = "select * from purchasemaster where PurchaseProperty=-1 ";
		if (purchaseid != "") {
			sql += " and purchaseid like '%" + purchaseid + "%'";
		}
		try {
			// 1 获得数据连接
			conn = con;
			// 2 创建执行SQL语句对象PreparedStatement
			pstmt = conn.prepareStatement(sql);
			// 调用executeQuery()方法
			rs = pstmt.executeQuery();
			// 从查询结集中读取数据
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
				// 关闭数据库连接
				super.closeAll();
			} catch (Exception ex) {
				// TODO: handle exception
				ex.printStackTrace();
			}
		}
		// 返回结果集
		return pm;
	}
}
