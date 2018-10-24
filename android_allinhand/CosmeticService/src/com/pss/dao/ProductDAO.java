package com.pss.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.pss.dbcon.DBConn;
import com.pss.pojo.DeliveryDetail;
import com.pss.pojo.Product;
import com.pss.pojo.PurchaseDetail;

public class ProductDAO extends DBConn {
	
	// 查询所有商品信息
	public List<Product> SelectAllPorduct() {
		List<Product> product = new ArrayList<Product>();
		String sql = "select product.ProductID,product.ProductName,product.MaxSafeStock,product.MinSafeStock,Supplier.SupplierName,product.productprice,product.Quantity from product,supplier where product.SupplierID=supplier.SupplierID;";
		try {
			super.getConn();
			pstmt = conn.prepareStatement(sql);
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
			ex.printStackTrace();
		} finally {
			try {
				super.closeAll();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		return product;
	}

	// 添加商品
	public int AddProiduct(Product pro) {
		int i = 0;
		String sql = "insert into product(product.ProductID,product.ProductName,product.SupplierID,product.MaxSafeStock,product.MinSafeStock)values(?,?,?,?,?);";
		try {
			// 1 获得数据连接
			super.getConn();
			// 2 创建执行SQL语句对象PreparedStatement
			pstmt = conn.prepareStatement(sql);
			// 3 替换SQL语句中的？
			pstmt.setString(1, pro.getProductid());
			pstmt.setString(2, pro.getProductname());
			pstmt.setString(3, pro.getSuppliername());
			pstmt.setInt(4, pro.getMaxSafeStock());
			pstmt.setInt(5, pro.getSafeStock());
			// 4 调用executeUpdate方法执行SQL语句，并返回执行结果
			i = pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				super.closeAll();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		return i;
	}

	/**
	 * 刘晴
	 * @param proname
	 * @return
	 */
	// 根据商品名获取商品信息
	public Product getProductByName(String proname) {
		Product pr = null;
		String sql = "select * from product where productname='"+proname+"' or productid='"+proname+"'";
		ResultSet rs = null;
		try {
			super.getConn();
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				pr = new Product();
				pr.setProductid(rs.getString(1));
				pr.setProductname(rs.getString(2));
				pr.setSuppliername(rs.getString(3));
				pr.setMaxSafeStock(rs.getInt(6));
				pr.setSafeStock(rs.getInt(7));
				pr.setProductprice(rs.getDouble(4));
				pr.setQuantity(rs.getInt(5));
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
		return pr;
	}

	// 删除商品信息
	public int DeleteProduct(String proid) {
		int i = 0;
		String sql = "delete from product where productid = ?";
		try {
			super.getConn();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, proid);
			i = pstmt.executeUpdate();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			try {
				super.closeAll();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		return i;
	}

	// 修改商品信息
	public int UpdateProduct(Product pro) {
		int i = 0;
		String sql = "update product set product.ProductName=?,product.SupplierID=?,product.MaxSafeStock=?,product.MinSafeStock=? where product.ProductID=?";
		try {
			super.getConn();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, pro.getProductname());
			pstmt.setString(2, pro.getSuppliername());
			pstmt.setInt(3, pro.getMaxSafeStock());
			pstmt.setInt(4, pro.getSafeStock());
			pstmt.setString(5, pro.getProductid());
			i = pstmt.executeUpdate();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			try {
				super.closeAll();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		return i;
	}

	// 按商品名称或编号查询商品信息
	public List<Product> getProductByNameorID(String nameid) {
		List<Product> product = new ArrayList<Product>();
		String sql = "select product.ProductID,product.ProductName,product.MaxSafeStock,product.MinSafeStock,Supplier.SupplierName,product.productprice from product,supplier where product.SupplierID=supplier.SupplierID and (product.ProductName=? or product.ProductID=? or supplier.SupplierName=?)";
		ResultSet rs = null;
		try {
			super.getConn();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, nameid);
			pstmt.setString(2, nameid);
			pstmt.setString(3, nameid);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				Product pr = new Product();
				pr.setProductid(rs.getString(1));
				pr.setProductname(rs.getString(2));
				pr.setMaxSafeStock(rs.getInt(3));
				pr.setSafeStock(rs.getInt(4));
				pr.setSuppliername(rs.getString(5));
				pr.setProductprice(rs.getDouble(6));
				product.add(pr);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			super.closeAll();
		}
		return product;
	}

	// 根据商品名称判断采购表中是否存在主外键关系
	public List<PurchaseDetail> isExistPurchaseByProductId(String productId) {
		List<PurchaseDetail> puDetailslList = new ArrayList<PurchaseDetail>();
		String sql = "select * from purchasedetail where productId=?";
		try {
			super.getConn();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, productId);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				PurchaseDetail purchaseDetail = new PurchaseDetail();
				purchaseDetail.setPurchaseid(rs.getString(1));
				purchaseDetail.setProductid(rs.getString(2));
				purchaseDetail.setPurchasequantity(rs.getInt(3));
				purchaseDetail.setPurchaseunitprice(rs.getDouble(4));
				puDetailslList.add(purchaseDetail);
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
		return puDetailslList;
	}

	// 根据商品名称判断采购表中是否存在主外键关系
	public List<DeliveryDetail> isExistDeliveryByProductId(String productId) {
		List<DeliveryDetail> deList = new ArrayList<DeliveryDetail>();
		String sql = "select * from deliverydetail where productId=?";
		try {
			super.getConn();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, productId);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				DeliveryDetail deliveryDetail = new DeliveryDetail();
				deliveryDetail.setDeliveryid(rs.getString(1));
				deliveryDetail.setProductid(rs.getString(3));
				deliveryDetail.setSalesquantity(rs.getInt(3));
				deList.add(deliveryDetail);
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
		return deList;
	}

	// DXF
	// 根据id得到一个商品
	public Product getOneById(String productid, Connection con) {
		// 实例化对象
		Product product = new Product();
		// 编写sql语句
		String sql = "select * from product where productid='" + productid
				+ "'";
		try {
			// 1 获得数据连接
			conn = con;
			// 2 创建执行SQL语句对象PreparedStatement
			pstmt = conn.prepareStatement(sql);
			// 调用executeQuery()方法
			rs = pstmt.executeQuery();
			// 从查询结集中读取数据
			if (rs.next()) {
				product.setProductid(rs.getString(1));
				product.setProductname(rs.getString(2));
				product.setSupplierid(rs.getString(3));
				product.setProductprice(rs.getDouble(4));
				product.setQuantity(rs.getInt(5));
				product.setMaxSafeStock(rs.getInt(6));
				product.setSafeStock(rs.getInt(7));
			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		// 返回结果集
		return product;
	}

	// DXF
	// 修改商品信息
	public int UpdateQuantityByProductID(Product pro, Connection conn) {
		int i = 0;
		String sql = "update product set quantity=" + pro.getQuantity()
				+ ",productprice=" + pro.getProductprice()
				+ " where product.ProductID='" + pro.getProductid() + "'";
		try {
			pstmt = conn.prepareStatement(sql);
			i = pstmt.executeUpdate();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return i;
	}

	/**
	 * 刘晴,delivery
	 */
	// 根据商品编号查询商品信息
	public Product getOneProduct(String proId, Connection conn) {
		String sql = "select * from product where productID='" + proId + "'";
		ResultSet rs = null;
		Product pro=new Product();
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				pro.setProductid(rs.getString(1));
				pro.setProductname(rs.getString(2));
				pro.setSuppliername(rs.getString(3));
				pro.setProductprice(rs.getDouble(4));
				pro.setQuantity(rs.getInt(5));
				pro.setMaxSafeStock(rs.getInt(6));
				pro.setSafeStock(rs.getInt(7));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return pro;
	}
	//得到用户的ID，以便自动生成下一次编号
		public List<Product> getAutoProductId(){
			List<Product> productList=new ArrayList<Product>();
			String sql="select *  from product order by productid desc";
			try {
				super.getConn();
				pstmt=conn.prepareStatement(sql);
				rs=pstmt.executeQuery();
				while (rs.next()) {
					Product product=new Product();
					product.setProductid(rs.getString(1));
					product.setProductname(rs.getString(2));
					productList.add(product);
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
			return productList;
		}
}
