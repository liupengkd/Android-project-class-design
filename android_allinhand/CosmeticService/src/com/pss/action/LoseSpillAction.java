package com.pss.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.pss.pojo.LoseSpill;
import com.pss.service.LoseSpillService;

public class LoseSpillAction {
	LoseSpillService loseSpillService=new LoseSpillService();
	//�õ������̵������嵥
	public String getAllLoseSpills(HttpServletRequest request,HttpServletResponse response){
		List<LoseSpill> list= loseSpillService.getAllLoseSpills();
		if (list.size()>0) {
			return JSONArray.fromObject(list).toString();
		}else {
			return "fail";
		}
	}
	//������Ʒ���Ƶõ��̵���ļ�¼
	public String getLoseSpillByName(HttpServletRequest request,HttpServletResponse response){
		String nameOrId=request.getParameter("name");
		LoseSpill loseSpill=loseSpillService.getLoseSpillByName(nameOrId);
		if (loseSpill!=null) {
			System.out.println(loseSpill.toString());
			return JSONObject.fromObject(loseSpill).toString();
		}else {
			return "fail";
		}
	}
	//ʹ����Զ����� 
	public String getAutoLoseSpillId(HttpServletRequest request,HttpServletResponse response){
		List<LoseSpill> list= loseSpillService.getAutoLoseSpillId();
		if (list.size()>0) {
			return JSONArray.fromObject(list).toString();
		}else {
			return "fail";
		}
	}
}
