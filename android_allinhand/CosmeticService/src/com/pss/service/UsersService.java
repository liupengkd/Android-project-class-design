package com.pss.service;

import java.util.List;

import com.pss.dao.UserDao;
import com.pss.pojo.Users;

public class UsersService {
	UserDao userDao=new UserDao();
	//通过用户名和密码判断用户是否存在
	public Users LoginByNameAndPwd(String name,String pwd){
		return userDao.LoginByNameAndPwd(name, pwd);
	}
	//得到所有销售人员的编号和姓名
	public List<Users> getAllSalesMan(){
		return userDao.getAllSalesMan();
	}
	//得到所有用户的信息
	public List<Users> getAllUsers(){
		return userDao.getAllUsers();
	}
	//根据用户的姓名或编号得到用户信息
	public Users getUsersByNameOrId(String nameorid){
		return userDao.getUsersByNameOrId(nameorid);
	}
	//添加用户
	public int addUsers(Users users){
		return userDao.addUsers(users);
	}
	//删除用户密码
	public int deleteUser(String userId){
		return userDao.deleteUser(userId);
	}
	//修改密码
	public int updatePassword(String userId,String password){
		return userDao.updatePassword(userId, password);
	}
	//重置密码
	public int resetPassword(String userId){
		return userDao.resetPassword(userId);
	}
	//根据用户编号判断要添加的用户是否存在
	public Users isExistUsersByuserId(String userId){
		return userDao.isExistUsersByuserId(userId);
	}
	//根据用户名判断要添来看的用户是否在存
	public Users isExistUsersbyName(String username){
		return userDao.isExistUsersbyName(username);
	}
	//判断销售表中是否有关联
	public List<Users> isUseUserByName(String userId){
		return userDao.isUseUserByName(userId);
	}
	//修改用户信息
	public int updateUserInfo(int role,String userId){
		return userDao.updateUserInfo(role, userId);
	}
	//得到用户的ID，以便自动生成下一次编号
	public List<Users> getAutoUserId(){
		return userDao.getAutoUserId();
	}
}
