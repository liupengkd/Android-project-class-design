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
	//得到所有盘点过后的清单
	public List<LoseSpill> getAllLoseSpills(){
		//实例化集合对象 
		List<LoseSpill> allLoseSpills=new ArrayList<LoseSpill>();
		//编写sql语句
		String sql="select l.LoseSpillID,p.productname,l.counts,l.flags,l.checkdate from product p,losespill l where p.productid=l.productid";
		try {
			//1 获得数据连接
			super.getConn();
			//2 创建执行SQL语句对象PreparedStatement
			pstmt=conn.prepareStatement(sql);
			//调用executeQuery()方法
			rs=pstmt.executeQuery();
			//从查询结集中读取数据
			while (rs.next()) {
				LoseSpill loseSpill=new LoseSpill();
				loseSpill.setLosespillid(rs.getString(1));
				loseSpill.setProductname(rs.getString(2));
				loseSpill.setCounts(rs.getInt(3));
				if (rs.getInt(4)==1) {
					loseSpill.setType("报溢");
				}else if (rs.getInt(4)==-1) {
					loseSpill.setType("报损");
				}
				loseSpill.setCheckdate(rs.getString(5));
				//加入到集合中
				allLoseSpills.add(loseSpill);
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally{
			try {
				//关闭数据库连接
				super.closeAll();
			} catch (Exception ex) {
				// TODO: handle exception
				ex.printStackTrace();
			}
		}
		//返回结果集
		return allLoseSpills;
	}
	//根据商品名称得到盘点过的记录
	public LoseSpill getLoseSpillByName(String productName){
		//实例化对象
		LoseSpill loseSpill=null;
		//编写sql语句
		String sql="select l.losespillid,p.productname,l.counts,l.flags,l.checkdate from product p,losespill l where p.productid=l.productid and (p.productname=? or p.productid=?)";
		try {
			//1 获得数据连接
			super.getConn();
			//2 创建执行SQL语句对象PreparedStatement
			pstmt=conn.prepareStatement(sql);
			//3 替换SQL语句中的？
			pstmt.setString(1,productName);
			pstmt.setString(2, productName);
			//调用executeQuery()方法
			rs=pstmt.executeQuery();
			//获取查询结果
			if (rs.next()) {
				loseSpill=new LoseSpill();
				loseSpill.setLosespillid(rs.getString(1));
				loseSpill.setProductname(rs.getString(2));
				loseSpill.setCounts(rs.getInt(3));
				if (rs.getInt(4)==1) {
					loseSpill.setType("报溢");
				}else if (rs.getInt(4)==-1) {
					loseSpill.setType("报损");
				}
				loseSpill.setCheckdate(rs.getString(5));
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally{
			try {
				//关闭数据库连接
				super.closeAll();
			} catch (Exception ex) {
				// TODO: handle exception
				ex.printStackTrace();
			}
		}
		//返回结果
		return loseSpill;
	}
	//添加盘点信息
	public int addLoseSpill(LoseSpill loseSpill){
		//声明一个返回值
		int result=0;
		//编写sql语句
		String sql="insert into losespill values(?,?,?,?,?)";
		try {
			//获取数据库的连接
			super.getConn();
			//2 创建执行SQL语句对象PreparedStatement
			pstmt=conn.prepareStatement(sql);
			//3 替换SQL语句中的？
			pstmt.setString(1, loseSpill.getLosespillid());
			pstmt.setString(2,loseSpill.getProductid());
			pstmt.setString(3,loseSpill.getCheckdate());
			pstmt.setInt(4, loseSpill.getCounts());
			pstmt.setInt(5, loseSpill.getFlags());
			//4 调用executeUpdate方法执行SQL语句，并返回执行结果
			result=pstmt.executeUpdate();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally{
			try {
				//关闭数据库连接
				super.closeAll();
			} catch (Exception ex) {
				// TODO: handle exception
				ex.printStackTrace();
			}
		}
		return result;
	}
	//判断库存盘点编号是否存在
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
	//根据商品编号判断报损报溢表中是否存在相应的数据
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
	//使编号自动增加 
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
