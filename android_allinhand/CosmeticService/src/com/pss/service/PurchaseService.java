package com.pss.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.pss.dao.ProductDAO;
import com.pss.dao.PurchaseDetailDao;
import com.pss.dao.PurchaseMasterDao;
import com.pss.dbcon.DBConn;
import com.pss.pojo.Product;
import com.pss.pojo.PurchaseDetail;
import com.pss.pojo.PurchaseMaster;

public class PurchaseService {
	PurchaseMasterDao pmdao = new PurchaseMasterDao();
	PurchaseDetailDao pddao = new PurchaseDetailDao();
	ProductDAO pdao = new ProductDAO();
	DBConn dbconn = new DBConn();
	Connection conn = null;

	// ��Ӳɹ�����Ϣ
	public void insert(PurchaseMaster pc) throws SQLException {
		try {
			conn = dbconn.getConn();
			// �������ݿ���Զ��ύΪfalse
			conn.setAutoCommit(false);
			// ��Ӳɹ�����Ϣ�����ݿ���
			pmdao.insert(pc, conn);

			// ��Ӳɹ�����ϸ��Ϣ�����ݿ���
			List<PurchaseDetail> pcdList = pc.getAllDetail();
			for (PurchaseDetail pdetail : pcdList) {
				pddao.insert(pdetail, conn);// �޸Ŀ����Ϣ
				// ���Ȳ�ѯ��Ʒ,ȡ���䵥�ۺ���������ϲɹ����еĸ���Ʒ�ĵ��ۺ����������м�Ȩ��ƽ����Ȼ�󽫵õ������ݸ��µ����ݿ���
				String productid = pdetail.getProductid();
				// ͨ��id�õ�һ����Ʒ
				Product product = pdao.getOneById(productid, conn);
				
				int quantity=product.getQuantity()+ pdetail.getPurchasequantity();
				
				if(pc.getPurchaseproperty()==-1){
					quantity=product.getQuantity()- pdetail.getPurchasequantity();
				}
				
				double newPrice=product.getProductprice();
				if(pc.getPurchaseproperty()==1){
					newPrice = (product.getQuantity()
						* product.getProductprice() + pdetail
						.getPurchasequantity() * pdetail.getPurchaseunitprice())
						/ quantity;
				}
				product.setQuantity(quantity);
				product.setProductprice(newPrice);
				//������Ʒ��Ϣ
				pdao.UpdateQuantityByProductID(product,conn);
			}
			conn.commit(); // �ɹ�������£��ύ���¡�
		} catch (SQLException ex) {
			// �������쳣����ȫ���ع�
			conn.rollback();
			throw new SQLException(ex);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {	
			conn.close();	
		}
	}

	// �õ����вɹ���ⵥ
	public List<PurchaseMaster> getAllRu(String purchaseid) {
		List<PurchaseMaster> allpm = null;
		try {
			allpm = pmdao.getAllRu(purchaseid,dbconn.getConn());
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return allpm;
	}

	// �õ����вɹ��˻���
	public List<PurchaseMaster> getAllTui(String purchaseid) {
		List<PurchaseMaster> allpm = null;
		try {
			allpm = pmdao.getAllTui(purchaseid,dbconn.getConn());
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return allpm;
	}

	// ͨ��id�õ��ɹ���ⵥ
	public PurchaseMaster getOneByIdRu(String purchaseid) {
		PurchaseMaster pm = null;
		try {
			pm = pmdao.getOneByIdRu(purchaseid, dbconn.getConn());
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return pm;
	}
	// ͨ��id�õ��ɹ��˻���
	public PurchaseMaster getOneByIdTui(String purchaseid) {
		PurchaseMaster pm = null;
		try {
			pm = pmdao.getOneByIdTui(purchaseid, dbconn.getConn());
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return pm;
	}
}
