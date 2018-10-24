package com.pss.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.pss.dao.DeliveryDetailDao;
import com.pss.dao.DeliveryMasterDao;
import com.pss.dao.ProductDAO;
import com.pss.dbcon.DBConn;
import com.pss.pojo.DeliveryDetail;
import com.pss.pojo.DeliveryMaster;
import com.pss.pojo.Product;

/**
 * 销售单数据处理类
 * 
 * @author 刘晴
 * 
 */
public class DeliveryMasterService {

	DeliveryMasterDao delmtd = new DeliveryMasterDao();
	DeliveryDetailDao deldtd = new DeliveryDetailDao();
	ProductDAO prod = new ProductDAO();
	DeliveryMaster dm = new DeliveryMaster();
	DBConn db = new DBConn();
	Connection conn = null;

	// 查询所有销售单(DeliveryProperty,1)
	public List<DeliveryMaster> getAllDelivery(int dp) {
		return delmtd.getAllDelivery(dp);
	}

	// 根据销售单号查询所有销售明细
	public List<DeliveryDetail> getAllDeliveryByDeliveryID(String deliveryid) {
		return deldtd.getAllDeliveryByDeliveryID(deliveryid);
	}

	// 添加销售单
	public void addDeliveryMaster(DeliveryMaster delmt) throws SQLException {

		try {
			// 连接数据库
			conn = db.getConn();
			// 设数据库的自动提交为false
			conn.setAutoCommit(false);
			// 添加销售单
			delmtd.addDeliveryMaster(delmt, conn);
			// 获取销售单ID
			String id = delmtd.getOneDelivery(delmt.getDeliveryid(), conn)
					.getDeliveryid();
			// 添加销售明细
			List<DeliveryDetail> deldtList = delmt.getDeldtList();
			for (DeliveryDetail deldt : deldtList) {
				deldt.setDeliveryid(id);
				deldtd.addDeliveryDetail(deldt, conn);
				// 修改库存信息
				String productID = deldt.getProductid();
				Product pro = prod.getOneProduct(productID, conn);
				int proNum = pro.getQuantity() - deldt.getSalesquantity();

				// 判断是否为销售退货
				if (delmt.getDeliveryproperty()==-1) {
					//退货数量
					proNum=pro.getQuantity()+deldt.getSalesquantity();
					// 销售单价
					Double price = deldt.getSalesprice();
					price = price / 1.5;
					// 现有商品单价，进价
					Double proPrice = pro.getProductprice();
					// 加权求平均
					Double productPrice = (price * deldt.getSalesquantity() + proPrice
							* pro.getQuantity())
							/ (deldt.getSalesquantity() + pro.getQuantity());
					pro.setProductprice(productPrice);
				}
				pro.setQuantity(proNum);
				prod.UpdateQuantityByProductID(pro, conn);
			}
			conn.commit();// 成功的情况下，提交
		} catch (SQLException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			// 出现异常，回滚
			conn.rollback();
			e.printStackTrace();
			throw new SQLException();
		} finally {
			conn.close();
		}
	}

	// 根据销售单号查询销售单
	public DeliveryMaster getOneDelivery(String ID) {
		try {
			conn = db.getConn();
			dm = delmtd.getOneDelivery(ID, conn);
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return dm;
	}
}
