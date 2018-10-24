package com.pss.dao;

import java.util.ArrayList;
import java.util.List;

import com.pss.dbcon.DBConn;
import com.pss.pojo.Users;

public class UserDao extends DBConn{
	//通过用户名和密码判断用户是否存在
	public Users LoginByNameAndPwd(String name,String pwd){
		Users users=null;
		String sql="select * from users where UserName=? and PasswordCode=?";
		try {
			super.getConn();
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, name);
			pstmt.setString(2, pwd);
			rs=pstmt.executeQuery();
			if (rs.next()) {
				users=new Users();
				users.setUserid(rs.getString(1));
				users.setUsername(rs.getString(2));
				users.setPasswordcode(rs.getString(3));
				if(rs.getInt(4)==0){
					users.setAuthorityType("管理员");
				}else if (rs.getInt(4)==1) {
					users.setAuthorityType("销售员");
				}else if (rs.getInt(4)==2) {
					users.setAuthorityType("采购员");
				}else if (rs.getInt(4)==3) {
					users.setAuthorityType("库管员");
				}
				users.setUserauthority(rs.getInt(4));
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
		return users;
	}
	//得到所有销售人员的编号和姓名
	public List<Users> getAllSalesMan(){
		//实例化集合对象
		List<Users> allUsers=new ArrayList<Users>();
		//编写sql语句
		String sql="select * from users where UserAuthority=1";
		try {
			//1 获得数据连接
			super.getConn();
			//2 创建执行SQL语句对象PreparedStatement
			pstmt=conn.prepareStatement(sql);
			//调用executeQuery()方法
			rs=pstmt.executeQuery();
			//从查询结集中读取数据
			while (rs.next()) {
				Users user=new Users();
				user.setUserid(rs.getString(1));
				user.setUsername(rs.getString(2));
				//加入到集合中
				allUsers.add(user);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally{
			try {
				//关闭数据库连接
				super.closeAll();
			} catch (Exception e2) {
				// TODO: handle exception
				e2.printStackTrace();
			}
		}
		//返回结果集
		return allUsers;
	}
	//得到所有用户的信息
	public List<Users> getAllUsers(){
		//实例化集合对象 
		List<Users> list=new ArrayList<Users>();
		//编写sql语句
		String sql="select * from users";
		try {
			//1 获得数据连接
			super.getConn();
			//2 创建执行SQL语句对象PreparedStatement
			pstmt=conn.prepareStatement(sql);
			//调用executeQuery()方法
			rs=pstmt.executeQuery();
			//从查询结集中读取数据
			while (rs.next()) {
				Users users=new Users();
				users.setUserid(rs.getString(1));
				users.setUsername(rs.getString(2));
				users.setPasswordcode(rs.getString(3));
				if(rs.getInt(4)==0){
					users.setAuthorityType("管理员");
				}else if (rs.getInt(4)==1) {
					users.setAuthorityType("销售员");
				}else if (rs.getInt(4)==2) {
					users.setAuthorityType("采购员");
				}else if (rs.getInt(4)==3) {
					users.setAuthorityType("库管员");
				}
				users.setUserauthority(rs.getInt(4));
				//加入到集合中
				list.add(users);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally{
			//关闭数据库连接
			super.closeAll();
		}
		//返回结果集
		return list;
	}
	//根据用户的姓名或编号得到用户信息
	public Users getUsersByNameOrId(String nameorid){
		//实例化对象
		Users users=null;
		//编写sql语句
		String sql="select * from users where UserID=? or UserName=?";
		try {
			//1 获得数据连接
			super.getConn();
			//2 创建执行SQL语句对象PreparedStatement
			pstmt=conn.prepareStatement(sql);
			//3 替换SQL语句中的？
			pstmt.setString(1, nameorid);
			pstmt.setString(2, nameorid);
			//从查询结集中读取数据
			rs=pstmt.executeQuery();
			//从查询结集中读取数据
			if (rs.next()) {
				users=new Users();
				users.setUserid(rs.getString(1));
				users.setUsername(rs.getString(2));
				users.setPasswordcode(rs.getString(3));
				if(rs.getInt(4)==0){
					users.setAuthorityType("管理员");
				}else if (rs.getInt(4)==1) {
					users.setAuthorityType("销售员");
				}else if (rs.getInt(4)==2) {
					users.setAuthorityType("采购员");
				}else if (rs.getInt(4)==3) {
					users.setAuthorityType("库管员");
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally{
			try {
				//关闭数据库连接
				super.closeAll();
			} catch (Exception e2) {
				// TODO: handle exception
				e2.printStackTrace();
			}
			
		}
		//返回结果对象
		return users;
	}
	//添加用户
	public int addUsers(Users users){
		//声明一个返回值
		int result=0;
		//编写sql语句
		String sql="insert into users values(?,?,?,?)";
		try {
			//获取数据库的连接
			super.getConn();
			//2 创建执行SQL语句对象PreparedStatement
			pstmt=conn.prepareStatement(sql);
			//3 替换SQL语句中的？
			pstmt.setString(1,users.getUserid());
			pstmt.setString(2,users.getUsername());
			pstmt.setString(3, users.getPasswordcode());
			pstmt.setInt(4, users.getUserauthority());
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
	//删除用户
	public int deleteUser(String userId){
		//声明一个返回值
		int result=0;
		//编写sql语句
		String sql="delete from users where UserID=?";
		try {
			//获取数据库的连接
			super.getConn();
			//2 创建执行SQL语句对象PreparedStatement
			pstmt=conn.prepareStatement(sql);
			//3 替换SQL语句中的？
			pstmt.setString(1, userId);
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
		//返回结果
		return result;
	}
	//修改密码
	public int updatePassword(String userId,String password){
		//声明一个返回值
		int result=0;
		//编写sql语句
		String sql="update users set PasswordCode=? where UserID=?";
		try {
			//获取数据库的连接
			super.getConn();
			//2 创建执行SQL语句对象PreparedStatement
			pstmt=conn.prepareStatement(sql);
			//3 替换SQL语句中的？
			pstmt.setString(1, password);
			pstmt.setString(2, userId);
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
		//返回结果
		return result;
	}
	//重置密码
	public int resetPassword(String userId){
		//声明一个返回值
		int result=0;
		//编写sql语句
		String sql="update users set PasswordCode='123456' where UserID=?";
		try {
			//获取数据库的连接
			super.getConn();
			//2 创建执行SQL语句对象PreparedStatement
			pstmt=conn.prepareStatement(sql);
			//3 替换SQL语句中的？
			pstmt.setString(1, userId);
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
		//返回结果
		return result;
	}
	//判断要添加的用户编号是否存在
	public Users isExistUsersByuserId(String userId){
		//声明一个对象
		Users users=null;
		//编写sql语句
		String sql="select * from users where UserID=?";
		try {
			//获取数据库的连接
			super.getConn();
			//2 创建执行SQL语句对象PreparedStatement
			pstmt=conn.prepareStatement(sql);
			//3 替换SQL语句中的？
			pstmt.setString(1, userId);
			//4 调用executeUpdate方法执行SQL语句，并返回执行结果
			rs=pstmt.executeQuery();
			//从查询结集中读取数据
			if (rs.next()) {
				users=new Users();
				users.setUserid(rs.getString(1));
				users.setUsername(rs.getString(2));
				users.setPasswordcode(rs.getString(3));
				if(rs.getInt(4)==0){
					users.setAuthorityType("管理员");
				}else if (rs.getInt(4)==1) {
					users.setAuthorityType("销售员");
				}else if (rs.getInt(4)==2) {
					users.setAuthorityType("采购员");
				}else if (rs.getInt(4)==3) {
					users.setAuthorityType("库管员");
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally{
			try {
				//关闭数据库连接
				super.closeAll();
			} catch (Exception e2) {
				// TODO: handle exception
				e2.printStackTrace();
			}
		}
		return users;
	}
	//判断要添加的用户名称是否存在
		public Users isExistUsersbyName(String username){
			//声明一个对象
			Users users=null;
			//编写sql语句
			String sql="select * from users where UserName=?";
			try {
				//获取数据库的连接
				super.getConn();
				//2 创建执行SQL语句对象PreparedStatement
				pstmt=conn.prepareStatement(sql);
				//3 替换SQL语句中的？
				pstmt.setString(1, username);
				//4 调用executeUpdate方法执行SQL语句，并返回执行结果
				rs=pstmt.executeQuery();
				//从查询结集中读取数据
				if (rs.next()) {
					users=new Users();
					users.setUserid(rs.getString(1));
					users.setUsername(rs.getString(2));
					users.setPasswordcode(rs.getString(3));
					if(rs.getInt(4)==0){
						users.setAuthorityType("管理员");
					}else if (rs.getInt(4)==1) {
						users.setAuthorityType("销售员");
					}else if (rs.getInt(4)==2) {
						users.setAuthorityType("采购员");
					}else if (rs.getInt(4)==3) {
						users.setAuthorityType("库管员");
					}
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}finally{
				try {
					//关闭数据库连接
					super.closeAll();
				} catch (Exception e2) {
					// TODO: handle exception
					e2.printStackTrace();
				}
			}
			return users;
		}
	//判断销售表中是否有关联
	public List<Users> isUseUserByName(String userId){
		List<Users> userList=new ArrayList<Users>();
		String sql="select * from users,deliverymaster where users.UserAuthority=2 and deliverymaster.SalesManID=?";
		try {
			super.getConn();
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, userId);
			rs=pstmt.executeQuery();
			while(rs.next()) {
				Users users=new Users();
				users.setUserid(rs.getString(1));
				users.setUsername(rs.getString(2));
				users.setPasswordcode(rs.getString(3));
				if(rs.getInt(4)==0){
					users.setAuthorityType("管理员");
				}else if (rs.getInt(4)==1) {
					users.setAuthorityType("销售员");
				}else if (rs.getInt(4)==2) {
					users.setAuthorityType("采购员");
				}else if (rs.getInt(4)==3) {
					users.setAuthorityType("库管员");
				}
				userList.add(users);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally{
			try {
				//关闭数据库连接
				super.closeAll();
			} catch (Exception e2) {
				// TODO: handle exception
				e2.printStackTrace();
			}
		}
		return userList;
	}
	//修改用户信息
	public int updateUserInfo(int role,String userId){
		int result=0;
		String sql="update users set UserAuthority=? where UserID=?";
		try {
			super.getConn();
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1, role);
			pstmt.setString(2, userId);
			result=pstmt.executeUpdate();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally{
			try {
				//关闭数据库连接
				super.closeAll();
			} catch (Exception e2) {
				// TODO: handle exception
				e2.printStackTrace();
			}
		}
		return result;
	}
	//得到用户的ID，以便自动生成下一次编号
	public List<Users> getAutoUserId(){
		List<Users> userList=new ArrayList<Users>();
		String sql="select *  from users order by userid desc";
		try {
			super.getConn();
			pstmt=conn.prepareStatement(sql);
			rs=pstmt.executeQuery();
			while (rs.next()) {
				Users users=new Users();
				users.setUserid(rs.getString(1));
				users.setUsername(rs.getString(2));
				users.setPasswordcode(rs.getString(3));
				if(rs.getInt(4)==0){
					users.setAuthorityType("管理员");
				}else if (rs.getInt(4)==1) {
					users.setAuthorityType("销售员");
				}else if (rs.getInt(4)==2) {
					users.setAuthorityType("采购员");
				}else if (rs.getInt(4)==3) {
					users.setAuthorityType("库管员");
				}
				userList.add(users);
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
		return userList;
	}
}
