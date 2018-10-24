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

	// ��ȡ������Ʒ��Ϣ
	public List<Product> getAllProduct() {
		return pd.SelectAllPorduct();
	}
	//�õ��û���ID���Ա��Զ�������һ�α��
		public List<Product> getAutoProductId(){
			return pd.getAutoProductId();
		}
	// �����Ʒ
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

	// �޸���Ʒ��Ϣ
	public int UpdateProduct(Product pro) {
		return pd.UpdateProduct(pro);
	}

	// ���ݱ��ɾ����Ʒ
	public int DeleteProduct(String proid) {
		return pd.DeleteProduct(proid);
	}

	// ����Ʒ������Ʒ��Ų�ѯ��Ʒ��Ϣ
	public List<Product> getProductByNameorID(String nameid) {
		return pd.getProductByNameorID(nameid);
	}

	// ������Ʒ����жϲɹ���ϸ�����Ƿ����������ϵ
	public List<PurchaseDetail> isExistPurchaseByProductId(String productId) {
		return pd.isExistPurchaseByProductId(productId);
	}

	// ������Ʒ����ж�������ϸ�����Ƿ����������ϵ
	public List<DeliveryDetail> isExistDeliveryByProductId(String productId) {
		return pd.isExistDeliveryByProductId(productId);
	}

	// DXF
	// ����id�õ�name
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
	 * ����
	 */
	// ������Ʒ����ȡ��Ʒ��Ϣ
	public Product getProductByName(String proname) {
		return pd.getProductByName(proname);
	}
}
