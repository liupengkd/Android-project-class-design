package com.pss.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.pss.pojo.LoseSpill;
import com.pss.pojo.Product;
import com.pss.service.LoseSpillService;
import com.pss.service.StockService;

public class StockAction {
	StockService stockService=new StockService();
	LoseSpillService loseSpillService=new LoseSpillService();
	public String getAlarmProduct(HttpServletRequest request,HttpServletResponse response){
		List<Product> list=stockService.getAlarmProduct();
		if (list.size()>0) {
			return JSONArray.fromObject(list).toString();
		}else{
			return "fail";
		}
		
	}
	//�õ�������Ʒ�Ŀ��
	public String getALLProductList(HttpServletRequest request,HttpServletResponse response){
		List<Product> list =stockService.getALLProductList();
		if (list.size()>0) {
			return JSONArray.fromObject(list).toString();
		}else {
			return "fail";
		}
	}
	//������Ʒ���Ƶõ���Ʒ����Ϣ
	public String getProductByName(HttpServletRequest request,HttpServletResponse response){
		String nameOrId=request.getParameter("name");
		Product product=stockService.getProductByName(nameOrId);
		if (product!=null) {
			return JSONObject.fromObject(product).toString();
		}else {
			return "fail";
		}
	}
	//�жϸ���Ʒ�Ƿ����
    public String isExistProduct(HttpServletRequest request,HttpServletResponse response){
    	String json=request.getParameter("stockJson");
    	String result="";
    	Product product=null;
    	LoseSpill loseSpill=null;
    	int count=0;
    	JSONObject jo=JSONObject.fromObject(json);
    	loseSpill=loseSpillService.isExistLoseSpill(jo.optString("loseId"));
    	product=stockService.isExistProduct(jo.optString("productId"));
    	
    	if (product!=null && loseSpill==null) {
    		loseSpill=new LoseSpill();
        	loseSpill.setLosespillid(jo.optString("loseId"));
        	loseSpill.setProductid(jo.optString("productId"));
        	loseSpill.setCheckdate(jo.optString("date"));
			count=jo.optInt("count")-product.getQuantity();
			if (count>0) {
				loseSpill.setFlags(1);			
			}else if (count<0) {
				loseSpill.setFlags(-1);
			}else {
				loseSpill.setFlags(0);
			}
			
			loseSpill.setCounts(count);
			int ss=loseSpillService.addLoseSpill(loseSpill);
			if (ss!=0) {
				result="success";
			}else {
				result="fail";
			}
    	}else if (loseSpill!=null) {
			result="exist";
		
		}else if (product==null) {
			result="notexist";
		} 
    	return result;
    }
}
