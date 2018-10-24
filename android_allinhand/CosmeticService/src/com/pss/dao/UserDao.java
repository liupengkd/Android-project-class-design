package com.pss.dao;

import java.util.ArrayList;
import java.util.List;

import com.pss.dbcon.DBConn;
import com.pss.pojo.Users;

public class UserDao extends DBConn{
	//ͨ���û����������ж��û��Ƿ����
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
					users.setAuthorityType("����Ա");
				}else if (rs.getInt(4)==1) {
					users.setAuthorityType("����Ա");
				}else if (rs.getInt(4)==2) {
					users.setAuthorityType("�ɹ�Ա");
				}else if (rs.getInt(4)==3) {
					users.setAuthorityType("���Ա");
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
	//�õ�����������Ա�ı�ź�����
	public List<Users> getAllSalesMan(){
		//ʵ�������϶���
		List<Users> allUsers=new ArrayList<Users>();
		//��дsql���
		String sql="select * from users where UserAuthority=1";
		try {
			//1 �����������
			super.getConn();
			//2 ����ִ��SQL������PreparedStatement
			pstmt=conn.prepareStatement(sql);
			//����executeQuery()����
			rs=pstmt.executeQuery();
			//�Ӳ�ѯ�Ἧ�ж�ȡ����
			while (rs.next()) {
				Users user=new Users();
				user.setUserid(rs.getString(1));
				user.setUsername(rs.getString(2));
				//���뵽������
				allUsers.add(user);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally{
			try {
				//�ر����ݿ�����
				super.closeAll();
			} catch (Exception e2) {
				// TODO: handle exception
				e2.printStackTrace();
			}
		}
		//���ؽ����
		return allUsers;
	}
	//�õ������û�����Ϣ
	public List<Users> getAllUsers(){
		//ʵ�������϶��� 
		List<Users> list=new ArrayList<Users>();
		//��дsql���
		String sql="select * from users";
		try {
			//1 �����������
			super.getConn();
			//2 ����ִ��SQL������PreparedStatement
			pstmt=conn.prepareStatement(sql);
			//����executeQuery()����
			rs=pstmt.executeQuery();
			//�Ӳ�ѯ�Ἧ�ж�ȡ����
			while (rs.next()) {
				Users users=new Users();
				users.setUserid(rs.getString(1));
				users.setUsername(rs.getString(2));
				users.setPasswordcode(rs.getString(3));
				if(rs.getInt(4)==0){
					users.setAuthorityType("����Ա");
				}else if (rs.getInt(4)==1) {
					users.setAuthorityType("����Ա");
				}else if (rs.getInt(4)==2) {
					users.setAuthorityType("�ɹ�Ա");
				}else if (rs.getInt(4)==3) {
					users.setAuthorityType("���Ա");
				}
				users.setUserauthority(rs.getInt(4));
				//���뵽������
				list.add(users);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally{
			//�ر����ݿ�����
			super.closeAll();
		}
		//���ؽ����
		return list;
	}
	//�����û����������ŵõ��û���Ϣ
	public Users getUsersByNameOrId(String nameorid){
		//ʵ��������
		Users users=null;
		//��дsql���
		String sql="select * from users where UserID=? or UserName=?";
		try {
			//1 �����������
			super.getConn();
			//2 ����ִ��SQL������PreparedStatement
			pstmt=conn.prepareStatement(sql);
			//3 �滻SQL����еģ�
			pstmt.setString(1, nameorid);
			pstmt.setString(2, nameorid);
			//�Ӳ�ѯ�Ἧ�ж�ȡ����
			rs=pstmt.executeQuery();
			//�Ӳ�ѯ�Ἧ�ж�ȡ����
			if (rs.next()) {
				users=new Users();
				users.setUserid(rs.getString(1));
				users.setUsername(rs.getString(2));
				users.setPasswordcode(rs.getString(3));
				if(rs.getInt(4)==0){
					users.setAuthorityType("����Ա");
				}else if (rs.getInt(4)==1) {
					users.setAuthorityType("����Ա");
				}else if (rs.getInt(4)==2) {
					users.setAuthorityType("�ɹ�Ա");
				}else if (rs.getInt(4)==3) {
					users.setAuthorityType("���Ա");
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally{
			try {
				//�ر����ݿ�����
				super.closeAll();
			} catch (Exception e2) {
				// TODO: handle exception
				e2.printStackTrace();
			}
			
		}
		//���ؽ������
		return users;
	}
	//����û�
	public int addUsers(Users users){
		//����һ������ֵ
		int result=0;
		//��дsql���
		String sql="insert into users values(?,?,?,?)";
		try {
			//��ȡ���ݿ������
			super.getConn();
			//2 ����ִ��SQL������PreparedStatement
			pstmt=conn.prepareStatement(sql);
			//3 �滻SQL����еģ�
			pstmt.setString(1,users.getUserid());
			pstmt.setString(2,users.getUsername());
			pstmt.setString(3, users.getPasswordcode());
			pstmt.setInt(4, users.getUserauthority());
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
	//ɾ���û�
	public int deleteUser(String userId){
		//����һ������ֵ
		int result=0;
		//��дsql���
		String sql="delete from users where UserID=?";
		try {
			//��ȡ���ݿ������
			super.getConn();
			//2 ����ִ��SQL������PreparedStatement
			pstmt=conn.prepareStatement(sql);
			//3 �滻SQL����еģ�
			pstmt.setString(1, userId);
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
		//���ؽ��
		return result;
	}
	//�޸�����
	public int updatePassword(String userId,String password){
		//����һ������ֵ
		int result=0;
		//��дsql���
		String sql="update users set PasswordCode=? where UserID=?";
		try {
			//��ȡ���ݿ������
			super.getConn();
			//2 ����ִ��SQL������PreparedStatement
			pstmt=conn.prepareStatement(sql);
			//3 �滻SQL����еģ�
			pstmt.setString(1, password);
			pstmt.setString(2, userId);
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
		//���ؽ��
		return result;
	}
	//��������
	public int resetPassword(String userId){
		//����һ������ֵ
		int result=0;
		//��дsql���
		String sql="update users set PasswordCode='123456' where UserID=?";
		try {
			//��ȡ���ݿ������
			super.getConn();
			//2 ����ִ��SQL������PreparedStatement
			pstmt=conn.prepareStatement(sql);
			//3 �滻SQL����еģ�
			pstmt.setString(1, userId);
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
		//���ؽ��
		return result;
	}
	//�ж�Ҫ��ӵ��û�����Ƿ����
	public Users isExistUsersByuserId(String userId){
		//����һ������
		Users users=null;
		//��дsql���
		String sql="select * from users where UserID=?";
		try {
			//��ȡ���ݿ������
			super.getConn();
			//2 ����ִ��SQL������PreparedStatement
			pstmt=conn.prepareStatement(sql);
			//3 �滻SQL����еģ�
			pstmt.setString(1, userId);
			//4 ����executeUpdate����ִ��SQL��䣬������ִ�н��
			rs=pstmt.executeQuery();
			//�Ӳ�ѯ�Ἧ�ж�ȡ����
			if (rs.next()) {
				users=new Users();
				users.setUserid(rs.getString(1));
				users.setUsername(rs.getString(2));
				users.setPasswordcode(rs.getString(3));
				if(rs.getInt(4)==0){
					users.setAuthorityType("����Ա");
				}else if (rs.getInt(4)==1) {
					users.setAuthorityType("����Ա");
				}else if (rs.getInt(4)==2) {
					users.setAuthorityType("�ɹ�Ա");
				}else if (rs.getInt(4)==3) {
					users.setAuthorityType("���Ա");
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally{
			try {
				//�ر����ݿ�����
				super.closeAll();
			} catch (Exception e2) {
				// TODO: handle exception
				e2.printStackTrace();
			}
		}
		return users;
	}
	//�ж�Ҫ��ӵ��û������Ƿ����
		public Users isExistUsersbyName(String username){
			//����һ������
			Users users=null;
			//��дsql���
			String sql="select * from users where UserName=?";
			try {
				//��ȡ���ݿ������
				super.getConn();
				//2 ����ִ��SQL������PreparedStatement
				pstmt=conn.prepareStatement(sql);
				//3 �滻SQL����еģ�
				pstmt.setString(1, username);
				//4 ����executeUpdate����ִ��SQL��䣬������ִ�н��
				rs=pstmt.executeQuery();
				//�Ӳ�ѯ�Ἧ�ж�ȡ����
				if (rs.next()) {
					users=new Users();
					users.setUserid(rs.getString(1));
					users.setUsername(rs.getString(2));
					users.setPasswordcode(rs.getString(3));
					if(rs.getInt(4)==0){
						users.setAuthorityType("����Ա");
					}else if (rs.getInt(4)==1) {
						users.setAuthorityType("����Ա");
					}else if (rs.getInt(4)==2) {
						users.setAuthorityType("�ɹ�Ա");
					}else if (rs.getInt(4)==3) {
						users.setAuthorityType("���Ա");
					}
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}finally{
				try {
					//�ر����ݿ�����
					super.closeAll();
				} catch (Exception e2) {
					// TODO: handle exception
					e2.printStackTrace();
				}
			}
			return users;
		}
	//�ж����۱����Ƿ��й���
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
					users.setAuthorityType("����Ա");
				}else if (rs.getInt(4)==1) {
					users.setAuthorityType("����Ա");
				}else if (rs.getInt(4)==2) {
					users.setAuthorityType("�ɹ�Ա");
				}else if (rs.getInt(4)==3) {
					users.setAuthorityType("���Ա");
				}
				userList.add(users);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally{
			try {
				//�ر����ݿ�����
				super.closeAll();
			} catch (Exception e2) {
				// TODO: handle exception
				e2.printStackTrace();
			}
		}
		return userList;
	}
	//�޸��û���Ϣ
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
				//�ر����ݿ�����
				super.closeAll();
			} catch (Exception e2) {
				// TODO: handle exception
				e2.printStackTrace();
			}
		}
		return result;
	}
	//�õ��û���ID���Ա��Զ�������һ�α��
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
					users.setAuthorityType("����Ա");
				}else if (rs.getInt(4)==1) {
					users.setAuthorityType("����Ա");
				}else if (rs.getInt(4)==2) {
					users.setAuthorityType("�ɹ�Ա");
				}else if (rs.getInt(4)==3) {
					users.setAuthorityType("���Ա");
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
