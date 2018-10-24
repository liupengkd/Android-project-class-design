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

	// 添加采购单信息
	public void insert(PurchaseMaster pc) throws SQLException {
		try {
			conn = dbconn.getConn();
			// 设置数据库的自动提交为false
			conn.setAutoCommit(false);
			// 添加采购单信息到数据库中
			pmdao.insert(pc, conn);

			// 添加采购单明细信息到数据库中
			List<PurchaseDetail> pcdList = pc.getAllDetail();
			for (PurchaseDetail pdetail : pcdList) {
				pddao.insert(pdetail, conn);// 修改库存信息
				// 首先查询商品,取出其单价和数量，结合采购单中的该商品的单价和数量，进行加权求平均，然后将得到的数据更新到数据库中
				String productid = pdetail.getProductid();
				// 通过id得到一个商品
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
				//更新商品信息
				pdao.UpdateQuantityByProductID(product,conn);
			}
			conn.commit(); // 成功的情况下，提交更新。
		} catch (SQLException ex) {
			// 当出现异常，则全部回滚
			conn.rollback();
			throw new SQLException(ex);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {	
			conn.close();	
		}
	}

	// 得到所有采购入库单
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

	// 得到所有采购退货单
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

	// 通过id得到采购入库单
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
	// 通过id得到采购退货单
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
