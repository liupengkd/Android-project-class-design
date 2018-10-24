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
 * ���۵����ݴ�����
 * 
 * @author ����
 * 
 */
public class DeliveryMasterService {

	DeliveryMasterDao delmtd = new DeliveryMasterDao();
	DeliveryDetailDao deldtd = new DeliveryDetailDao();
	ProductDAO prod = new ProductDAO();
	DeliveryMaster dm = new DeliveryMaster();
	DBConn db = new DBConn();
	Connection conn = null;

	// ��ѯ�������۵�(DeliveryProperty,1)
	public List<DeliveryMaster> getAllDelivery(int dp) {
		return delmtd.getAllDelivery(dp);
	}

	// �������۵��Ų�ѯ����������ϸ
	public List<DeliveryDetail> getAllDeliveryByDeliveryID(String deliveryid) {
		return deldtd.getAllDeliveryByDeliveryID(deliveryid);
	}

	// ������۵�
	public void addDeliveryMaster(DeliveryMaster delmt) throws SQLException {

		try {
			// �������ݿ�
			conn = db.getConn();
			// �����ݿ���Զ��ύΪfalse
			conn.setAutoCommit(false);
			// ������۵�
			delmtd.addDeliveryMaster(delmt, conn);
			// ��ȡ���۵�ID
			String id = delmtd.getOneDelivery(delmt.getDeliveryid(), conn)
					.getDeliveryid();
			// ���������ϸ
			List<DeliveryDetail> deldtList = delmt.getDeldtList();
			for (DeliveryDetail deldt : deldtList) {
				deldt.setDeliveryid(id);
				deldtd.addDeliveryDetail(deldt, conn);
				// �޸Ŀ����Ϣ
				String productID = deldt.getProductid();
				Product pro = prod.getOneProduct(productID, conn);
				int proNum = pro.getQuantity() - deldt.getSalesquantity();

				// �ж��Ƿ�Ϊ�����˻�
				if (delmt.getDeliveryproperty()==-1) {
					//�˻�����
					proNum=pro.getQuantity()+deldt.getSalesquantity();
					// ���۵���
					Double price = deldt.getSalesprice();
					price = price / 1.5;
					// ������Ʒ���ۣ�����
					Double proPrice = pro.getProductprice();
					// ��Ȩ��ƽ��
					Double productPrice = (price * deldt.getSalesquantity() + proPrice
							* pro.getQuantity())
							/ (deldt.getSalesquantity() + pro.getQuantity());
					pro.setProductprice(productPrice);
				}
				pro.setQuantity(proNum);
				prod.UpdateQuantityByProductID(pro, conn);
			}
			conn.commit();// �ɹ�������£��ύ
		} catch (SQLException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			// �����쳣���ع�
			conn.rollback();
			e.printStackTrace();
			throw new SQLException();
		} finally {
			conn.close();
		}
	}

	// �������۵��Ų�ѯ���۵�
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
