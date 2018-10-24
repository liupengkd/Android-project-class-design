package com.pss.dao;

import java.util.ArrayList;
import java.util.List;

import com.pss.dbcon.DBConn;
import com.pss.pojo.LoseSpill;
/**
 * 
 * @author yinranran_2012/11/8
 *
 */
public class LoseSpillDao extends DBConn{
	//�õ������̵������嵥
	public List<LoseSpill> getAllLoseSpills(){
		//ʵ�������϶��� 
		List<LoseSpill> allLoseSpills=new ArrayList<LoseSpill>();
		//��дsql���
		String sql="select l.LoseSpillID,p.productname,l.counts,l.flags,l.checkdate from product p,losespill l where p.productid=l.productid";
		try {
			//1 �����������
			super.getConn();
			//2 ����ִ��SQL������PreparedStatement
			pstmt=conn.prepareStatement(sql);
			//����executeQuery()����
			rs=pstmt.executeQuery();
			//�Ӳ�ѯ�Ἧ�ж�ȡ����
			while (rs.next()) {
				LoseSpill loseSpill=new LoseSpill();
				loseSpill.setLosespillid(rs.getString(1));
				loseSpill.setProductname(rs.getString(2));
				loseSpill.setCounts(rs.getInt(3));
				if (rs.getInt(4)==1) {
					loseSpill.setType("����");
				}else if (rs.getInt(4)==-1) {
					loseSpill.setType("����");
				}
				loseSpill.setCheckdate(rs.getString(5));
				//���뵽������
				allLoseSpills.add(loseSpill);
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
		return allLoseSpills;
	}
	//������Ʒ���Ƶõ��̵���ļ�¼
	public LoseSpill getLoseSpillByName(String productName){
		//ʵ��������
		LoseSpill loseSpill=null;
		//��дsql���
		String sql="select l.losespillid,p.productname,l.counts,l.flags,l.checkdate from product p,losespill l where p.productid=l.productid and (p.productname=? or p.productid=?)";
		try {
			//1 �����������
			super.getConn();
			//2 ����ִ��SQL������PreparedStatement
			pstmt=conn.prepareStatement(sql);
			//3 �滻SQL����еģ�
			pstmt.setString(1,productName);
			pstmt.setString(2, productName);
			//����executeQuery()����
			rs=pstmt.executeQuery();
			//��ȡ��ѯ���
			if (rs.next()) {
				loseSpill=new LoseSpill();
				loseSpill.setLosespillid(rs.getString(1));
				loseSpill.setProductname(rs.getString(2));
				loseSpill.setCounts(rs.getInt(3));
				if (rs.getInt(4)==1) {
					loseSpill.setType("����");
				}else if (rs.getInt(4)==-1) {
					loseSpill.setType("����");
				}
				loseSpill.setCheckdate(rs.getString(5));
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
		//���ؽ��
		return loseSpill;
	}
	//����̵���Ϣ
	public int addLoseSpill(LoseSpill loseSpill){
		//����һ������ֵ
		int result=0;
		//��дsql���
		String sql="insert into losespill values(?,?,?,?,?)";
		try {
			//��ȡ���ݿ������
			super.getConn();
			//2 ����ִ��SQL������PreparedStatement
			pstmt=conn.prepareStatement(sql);
			//3 �滻SQL����еģ�
			pstmt.setString(1, loseSpill.getLosespillid());
			pstmt.setString(2,loseSpill.getProductid());
			pstmt.setString(3,loseSpill.getCheckdate());
			pstmt.setInt(4, loseSpill.getCounts());
			pstmt.setInt(5, loseSpill.getFlags());
			//4 ����executeUpdate����ִ��SQL��䣬������ִ�н��
			result=pstmt.executeUpdate();
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
		return result;
	}
	//�жϿ���̵����Ƿ����
	public LoseSpill isExistLoseSpill(String loseId){
		LoseSpill loseSpill=null;
		String sql="select * from losespill where LoseSpillID=?";
		try {
			super.getConn();
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, loseId);
			rs=pstmt.executeQuery();
			if (rs.next()) {
				loseSpill=new LoseSpill();
				loseSpill.setLosespillid(rs.getString(1));
				loseSpill.setProductid(rs.getString(2));
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
		return loseSpill;
	}
	//������Ʒ����жϱ���������Ƿ������Ӧ������
	public LoseSpill isExistByProductId(String productid){
		LoseSpill loseSpill=null;
		String sql="select * from losespill where productId=?";
		try {
			super.getConn();
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, productid);
			rs=pstmt.executeQuery();
			if (rs.next()) {
				loseSpill=new LoseSpill();
				loseSpill.setLosespillid(rs.getString(1));
				loseSpill.setProductid(rs.getString(2));
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
		return loseSpill;
	}
	//ʹ����Զ����� 
	public List<LoseSpill> getAutoLoseSpillId(){
		List<LoseSpill> lslist=new ArrayList<LoseSpill>();
		String sql="select *  from losespill order by LoseSpillID desc";
		try {
			super.getConn();
			pstmt=conn.prepareStatement(sql);
			rs=pstmt.executeQuery();
			while (rs.next()) {
				LoseSpill loseSpill=new LoseSpill();
				loseSpill.setLosespillid(rs.getString(1));
				loseSpill.setProductid(rs.getString(2));
				lslist.add(loseSpill);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}finally{
			try {
				super.closeAll();
			} catch (Exception e2) {
				// TODO: handle exception
				e2.printStackTrace();
			}
		}
		return lslist;
	}
}
