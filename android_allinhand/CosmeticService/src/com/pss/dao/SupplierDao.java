package com.pss.dao;

import java.util.ArrayList;
import java.util.List;

import com.pss.dbcon.DBConn;
import com.pss.pojo.DeliveryDetail;
import com.pss.pojo.Product;
import com.pss.pojo.Supplier;

public class SupplierDao extends DBConn {

	private String id;

	// 获取所有供应商信息
	public List<Supplier> selectAllSupplier() {
		List<Supplier> supplier = new ArrayList<Supplier>();
		String sql = "select * from supplier order by supplierid desc;";
		try {
			super.getConn();
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				Supplier sup = new Supplier();
				sup.setSupplierid(rs.getString(1));
				sup.setSuppliername(rs.getString(2));
				sup.setTelephone(rs.getString(3));
				sup.setCompanyaddress(rs.getString(4));
				supplier.add(sup);

			}
		} catch (Exception ex) {
			// TODO: handle exception
			ex.printStackTrace();
		} finally {

			try {
				super.closeAll();
			} catch (Exception ex) {
				// TODO: handle exception
				ex.printStackTrace();
			}
		}
		return supplier;
	}

	// 根据供应商编号查询商品表是否有记录
	public List<Product> selectAllproductByname(int id) {
		List<Product> product = new ArrayList<Product>();
		String sql = "select * from product where supplierid=?";
		try {
			super.getConn();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, id);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				Product pro = new Product();
				pro.setProductid(rs.getString(1));
				pro.setProductname(rs.getString(2));
				pro.setMaxSafeStock(rs.getInt(3));
				pro.setSafeStock(rs.getInt(4));
				pro.setSuppliername(rs.getString(5));
				pro.setProductprice(rs.getDouble(6));
				pro.setQuantity(rs.getInt(7));
				product.add(pro);

			}
		} catch (Exception ex) {
			// TODO: handle exception
			ex.printStackTrace();
		} finally {

			try {
				super.closeAll();
			} catch (Exception ex) {
				// TODO: handle exception
				ex.printStackTrace();
			}
		}
		return product;
	}

	// 根据供应商编号查询销售主表
	public List<DeliveryDetail> SelectSupplierID(int id) {
		List<DeliveryDetail> deList = new ArrayList<DeliveryDetail>();

		String sql = "select * from deliverymaster where supplierid=?";

		try {
			super.getConn();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, id);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				DeliveryDetail deliveryDetail = new DeliveryDetail();
				deliveryDetail.setDeliveryid(rs.getString(1));
				deliveryDetail.setProductid(rs.getString(2));
				deliveryDetail.setSalesquantity(rs.getInt(3));
				deList.add(deliveryDetail);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				super.closeAll();
			} catch (Exception e2) {
				// TODO: handle exception
				e2.printStackTrace();
			}
		}
		return deList;
	}

	// 增加供应商信息
	public int AddSupplier(Supplier sup) {
		int i = 0;
		String sql = "insert into supplier(supplier.SupplierID,supplier.SupplierName,supplier.Telephone,supplier.Companyaddress) values(?,?,?,?)";

		try {
			// h获取数据库连接
			super.getConn();
			// 创建执行SQL语句对象PreparedStatement
			pstmt = conn.prepareStatement(sql);
			// 替换SQL语句中的
			pstmt.setString(1, sup.getSupplierid());
			pstmt.setString(2, sup.getSuppliername());
			pstmt.setString(3, sup.getTelephone());
			pstmt.setString(4, sup.getCompanyaddress());
			// 调用executeUpdate方法执行SQL语句，返回执行结果
			i = pstmt.executeUpdate();

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();

		} finally {
			try {
				super.closeAll();
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
		return i;
	}

	// 根据供应商名称获取供应商信息
	public Supplier getSupplierByName(String supname) {
		Supplier sup = null;
		String sql = "Select * from supplier where suppliername='" + supname
				+ "'";

		try {
			super.getConn();
			pstmt = conn.prepareStatement(sql);
			// pstmt.setString(1, supname);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				sup = new Supplier();
				sup.setSupplierid(rs.getString(1));
				sup.setSuppliername(rs.getString(2));
				sup.setTelephone(rs.getString(3));
				sup.setCompanyaddress(rs.getString(4));

			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			try {
				super.closeAll();
			} catch (Exception e2) {
				// TODO: handle exception
				e2.printStackTrace();
			}
		}
		return sup;
	}

	// 修改供应商信息
	public int updateSupplier(Supplier sup) {
		int i = 0;
		String sql = "update supplier set supplier.SupplierName=?,Supplier.Telephone=?,Supplier.Companyaddress=? where Supplier.SupplierID=?";
		try {
			super.getConn();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, sup.getSuppliername());
			pstmt.setString(2, sup.getTelephone());
			pstmt.setString(3, sup.getCompanyaddress());
			pstmt.setString(4, sup.getSupplierid());
			i = pstmt.executeUpdate();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				super.closeAll();
			} catch (Exception e2) {
				// TODO: handle exception
				e2.printStackTrace();
			}
		}

		return i;
	}

	// 根据供应商编号删除供应商
	public int deleteSupplier(int supid) {
		int i = 0;
		String sql = "delete from supplier where SupplierID = ?";
		try {
			super.getConn();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, supid);
			i = pstmt.executeUpdate();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				super.closeAll();
			} catch (Exception e2) {
				// TODO: handle exception
				e2.printStackTrace();
			}
		}
		return i;
	}

	public String SelectSuppliername(String name) {
		String sql = "Select supplier.SupplierID from supplier where supplier.SupplierName = ?";

		try {
			super.getConn();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, name);
			rs = pstmt.executeQuery();
			// id = rs.toString();
			if (rs.next()) {
				id = rs.getString(1);
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
		return id;
	}

	// dxf
	public String getOneName(String id) {
		String sql = "Select suppliername from supplier where supplierid = ?";

		try {
			super.getConn();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			// id = rs.toString();
			if (rs.next()) {
				id = rs.getString(1);
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
		return id;
	}
}
