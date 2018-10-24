package com.pss.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.pss.pojo.Users;
import com.pss.service.UsersService;

public class UsersAction {
	UsersService usersService=new UsersService();
	//ͨ���û����������ж��û��Ƿ����
	public String LoginByNameAndPwd(HttpServletRequest request,HttpServletResponse response){
		Users users=null;
		String jsonString=request.getParameter("stockJson");
		JSONObject jo=JSONObject.fromObject(jsonString);
		users=usersService.LoginByNameAndPwd(jo.optString("name"), jo.optString("pwd"));
		if (users!=null) {
			return JSONObject.fromObject(users).toString();
		}else {
			return "fail";
		}
	}
	//�õ�����������Ա�ı�ź�����
	public String getAllSalesMan(HttpServletRequest request,HttpServletResponse response){
		List<Users> list=usersService.getAllSalesMan();
		if (list.size()>0) {
			return JSONArray.fromObject(list).toString();
		}else {
			return "fail";
		}
	}
	//�õ��û���ID���Ա��Զ�������һ�α��
	public String getAutoUserId(HttpServletRequest request,HttpServletResponse response){
		List<Users> list=usersService.getAutoUserId();
		if (list.size()>0) {
			return JSONArray.fromObject(list).toString();
		}else{
			return "fail";
		}
	}
	//�õ������û�����Ϣ
	public String getAllUsers(HttpServletRequest request,HttpServletResponse response){
		List<Users> list=usersService.getAllUsers();
		if (list.size()>0) {
			return JSONArray.fromObject(list).toString();
		}else {
			return "fail";
		}
	}
	//�����û����������ŵõ��û���Ϣ
	public String getUsersByNameOrId(HttpServletRequest request,HttpServletResponse response){
		String nameorid=request.getParameter("name");
		Users users=usersService.getUsersByNameOrId(nameorid);
		if (users!=null) {
			return JSONObject.fromObject(users).toString();
		}else {
			return "fail";
		}
	}
	//����û�
	public String addUsers(HttpServletRequest request,HttpServletResponse response){
		String result;
		String jsonString=request.getParameter("stockJson");
		JSONObject jo=JSONObject.fromObject(jsonString);
		Users users=null;
		Users users2=null;
		users=usersService.isExistUsersByuserId(jo.optString("userId"));
		users2=usersService.isExistUsersbyName(jo.optString("userName"));
		if (users==null && users2==null) {
			users=new Users();
			users.setUserid(jo.optString("userId"));
			users.setUsername(jo.optString("userName"));
			users.setPasswordcode(jo.optString("password"));
			users.setUserauthority(jo.optInt("roles"));
			if (usersService.addUsers(users)!=0) {
				result="success";
			}else {
				result="fail";
			}
		}else if (users!=null) {
			result="idexist";
		}else if (users2!=null) {
			result="nameexist";
		}else {
			result="double";
		}
		return result;
	}
	//ɾ���û�
	public String deleteUser(HttpServletRequest request,HttpServletResponse response){
		String result;
		String userId=request.getParameter("userId");
		List<Users> list=usersService.isUseUserByName(userId);
		if (list.size()>0) {
			result="notdelete";
		}else {
			if (usersService.deleteUser(userId)>0) {
				result="success";
			}else {
				result="fail";
			}
		}
		
		return result;
	}
	//�޸�����
	public String updatePassword(HttpServletRequest request,HttpServletResponse response){
		String result;
		String jsonString=request.getParameter("stockJson");
		JSONObject jo=JSONObject.fromObject(jsonString);
		if (usersService.updatePassword(jo.optString("userId"), jo.optString("pwd"))>0) {
			result="success";
		}else {
			result="fail";
		}
		return result;
	}
	//��������
	public String resetPassword(HttpServletRequest request,HttpServletResponse response){
		String result;
		String userId=request.getParameter("userId");
		if (usersService.resetPassword(userId)>0) {
			result="success";
		}else {
			result="fail";
		}
		return result;
	}
	//�޸��û���Ϣ
	public String updateUserInfo(HttpServletRequest request,HttpServletResponse response){
		String result;
		String jsonString=request.getParameter("stockJson");
		JSONObject jo=JSONObject.fromObject(jsonString);
		if (usersService.updateUserInfo(jo.optInt("roles"), jo.optString("userId"))>0) {
			result="success";
		}else {
			result="fail";
		}
		return result;
	}
}
