package com.pss.service;

import java.util.List;

import com.pss.dao.UserDao;
import com.pss.pojo.Users;

public class UsersService {
	UserDao userDao=new UserDao();
	//ͨ���û����������ж��û��Ƿ����
	public Users LoginByNameAndPwd(String name,String pwd){
		return userDao.LoginByNameAndPwd(name, pwd);
	}
	//�õ�����������Ա�ı�ź�����
	public List<Users> getAllSalesMan(){
		return userDao.getAllSalesMan();
	}
	//�õ������û�����Ϣ
	public List<Users> getAllUsers(){
		return userDao.getAllUsers();
	}
	//�����û����������ŵõ��û���Ϣ
	public Users getUsersByNameOrId(String nameorid){
		return userDao.getUsersByNameOrId(nameorid);
	}
	//����û�
	public int addUsers(Users users){
		return userDao.addUsers(users);
	}
	//ɾ���û�����
	public int deleteUser(String userId){
		return userDao.deleteUser(userId);
	}
	//�޸�����
	public int updatePassword(String userId,String password){
		return userDao.updatePassword(userId, password);
	}
	//��������
	public int resetPassword(String userId){
		return userDao.resetPassword(userId);
	}
	//�����û�����ж�Ҫ��ӵ��û��Ƿ����
	public Users isExistUsersByuserId(String userId){
		return userDao.isExistUsersByuserId(userId);
	}
	//�����û����ж�Ҫ���������û��Ƿ��ڴ�
	public Users isExistUsersbyName(String username){
		return userDao.isExistUsersbyName(username);
	}
	//�ж����۱����Ƿ��й���
	public List<Users> isUseUserByName(String userId){
		return userDao.isUseUserByName(userId);
	}
	//�޸��û���Ϣ
	public int updateUserInfo(int role,String userId){
		return userDao.updateUserInfo(role, userId);
	}
	//�õ��û���ID���Ա��Զ�������һ�α��
	public List<Users> getAutoUserId(){
		return userDao.getAutoUserId();
	}
}
