package com.pss.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.pss.dbcon.DBConn;
import com.pss.pojo.PurchaseDetail;
/**
 * 
 * @author dxf_2012/11/7
 *
 */
public class PurchaseDetailDao extends DBConn{
	
	Connection con=null;
	//��Ӽ�¼
	public int insert(PurchaseDetail pd,Connection conn) throws SQLException {
		//��дsql���
		String sql = "insert into purchasedetail values('"+pd.getPurchaseid()+"','"+pd.getProductid()+"',"+pd.getPurchasequantity()+","+pd.getPurchaseunitprice()+")";
		con=conn;
		pstmt = conn.prepareStatement(sql); // �õ�PreparedStatement����
		int result = pstmt.executeUpdate(); // ִ��SQL���
		return result;	 
	}

	//ɾ����¼,�����ܲ���ɾ��
	public int delete(String purchaseMasterId) throws SQLException {
		//��дsql���
		String sql = "delete from PurchaseDetail where PurchaseID='?'";
		String[] params=new String[]{purchaseMasterId};
		//���ø��෽������������������
		int result=super.executeSQL(sql, params);
		return result;	 
	}
	
	//���ݲɹ����ŵõ����вɹ���ϸ
	public List<PurchaseDetail> getAll(String purchaseid){
		//ʵ�������϶��� 
		List<PurchaseDetail> purchaseList=new ArrayList<PurchaseDetail>();
		//��дsql���
		String sql="select * from PurchaseDetail where PurchaseID='"+purchaseid+"'";
		try {
			//1 �����������
			super.getConn();
			//2 ����ִ��SQL������PreparedStatement
			pstmt=conn.prepareStatement(sql);
			//����executeQuery()����
			rs=pstmt.executeQuery();
			//�Ӳ�ѯ�Ἧ�ж�ȡ����
			while(rs.next()){
				PurchaseDetail pm=new PurchaseDetail();
				pm.setPurchaseid(rs.getString(1));
				pm.setProductid(rs.getString(2));
				pm.setPurchasequantity(rs.getInt(3));
				pm.setPurchaseunitprice(rs.getDouble(4));
				//���뵽������
				purchaseList.add(pm);
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally{
			try {
				//�ر����ݿ�����
				super.closeAll();
			} catch (Exception ex) {
				// TODO: handle exception
				ex.printStackTrace();
			}
		}
		//���ؽ����
		return purchaseList;	
	}

}
