package com.pss.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.pss.dao.ProductDAO;
import com.pss.dbcon.DBConn;
import com.pss.pojo.DeliveryDetail;
import com.pss.pojo.Product;
import com.pss.pojo.PurchaseDetail;

public class ProductService {
	ProductDAO pd = new ProductDAO();
	DBConn dbconn = new DBConn();
	Connection conn = null;

	// 获取所有商品信息
	public List<Product> getAllProduct() {
		return pd.SelectAllPorduct();
	}
	//得到用户的ID，以便自动生成下一次编号
		public List<Product> getAutoProductId(){
			return pd.getAutoProductId();
		}
	// 添加商品
	public String AddProiduct(Product pro) {
		String flag = "";
		Product pro_check = pd.getProductByName(pro.getProductname());
		if (null != pro_check) {
			flag = "doublename";
		} else {
			int i = pd.AddProiduct(pro);
			if (i > 0) {
				flag = "success";
			} else {
				flag = "faild";
			}
		}
		return flag;
	}

	// 修改商品信息
	public int UpdateProduct(Product pro) {
		return pd.UpdateProduct(pro);
	}

	// 根据编号删除商品
	public int DeleteProduct(String proid) {
		return pd.DeleteProduct(proid);
	}

	// 按商品名或商品编号查询商品信息
	public List<Product> getProductByNameorID(String nameid) {
		return pd.getProductByNameorID(nameid);
	}

	// 根据商品编号判断采购明细表中是否有主外键关系
	public List<PurchaseDetail> isExistPurchaseByProductId(String productId) {
		return pd.isExistPurchaseByProductId(productId);
	}

	// 根据商品编号判断销售明细表中是否有主外键关系
	public List<DeliveryDetail> isExistDeliveryByProductId(String productId) {
		return pd.isExistDeliveryByProductId(productId);
	}

	// DXF
	// 根据id得到name
	public String getOneById(String productid) {
		try {
			conn = dbconn.getConn();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Product product = pd.getOneById(productid, conn);
		return product.getProductname();
	}

	/**
	 * 刘晴
	 */
	// 根据商品名获取商品信息
	public Product getProductByName(String proname) {
		return pd.getProductByName(proname);
	}
}
