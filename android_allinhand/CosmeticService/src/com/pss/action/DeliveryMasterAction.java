package com.pss.action;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.pss.pojo.DeliveryDetail;
import com.pss.pojo.DeliveryMaster;
import com.pss.service.CustomerService;
import com.pss.service.DeliveryMasterService;
import com.pss.service.ProductService;
import com.pss.service.UsersService;

/**
 * 销售单操作类，该类的方法中用来接收请求的 数值，处理完，返回string/xml/json
 * 
 * @author 刘晴
 * 
 */
public class DeliveryMasterAction {
	DeliveryMasterService delmts = new DeliveryMasterService();
	DeliveryMaster delmt = new DeliveryMaster();
	ProductService ps = new ProductService();
	CustomerService cs = new CustomerService();
	UsersService us = new UsersService();

	// 查询所有销售单(DeliveryProperty,1)
	public String getAllDelivery(HttpServletRequest request,
			HttpServletResponse response) {
		int dp = Integer.parseInt(request.getParameter("dp"));
		List<DeliveryMaster> list = delmts.getAllDelivery(dp);
		if (list.size() > 0) {
			JSONArray ja = JSONArray.fromObject(list);
			return ja.toString();
		}
		return "failed";
	}

	// 根据销售单号查询销售单详细
	public String getAllDeliveryByDeliveryID(HttpServletRequest request,
			HttpServletResponse response) {
		String deliveryid = request.getParameter("deliveryid");
		List<DeliveryDetail> list = delmts
				.getAllDeliveryByDeliveryID(deliveryid);
		if (list.size() > 0) {
			JSONArray ja = JSONArray.fromObject(list);
			return ja.toString();
		} else {
			return "failed";
		}
	}

	// 根据销售单号查询销售单
	public String getOneDelivery(HttpServletRequest request,
			HttpServletResponse response) {
		String deliveryid = request.getParameter("deliveryid");
		DeliveryMaster delmt = delmts.getOneDelivery(deliveryid);
		JSONObject jo = JSONObject.fromObject(delmt);
		return jo.toString();
	}

	// 添加销售单
	public String addDeliveryMaster(HttpServletRequest request,
			HttpServletResponse response) {
		String json = request.getParameter("dm_json");
		JSONObject jo = JSONObject.fromObject(json);

		delmt.setDeliveryid(jo.optString("dmID"));
		delmt.setDeliveryproperty(jo.optInt("dmProp"));
		delmt.setDeliverydate(jo.optString("dmDate"));
		String salesmanid = us.getUsersByNameOrId(jo.optString("dmSalesman"))
				.getUserid();
		delmt.setSalesmanid(salesmanid);
		if (cs.getCustomerByName(jo.optString("dmCustomer")) != null) {
			int customerid = cs.getCustomerByName(jo.optString("dmCustomer"))
					.getCustomerid();
			delmt.setCustomerid(customerid);
		} else {
			delmt.setCustomerid(1);
		}
		
		delmt.setSubtotal(jo.optDouble("dmtotal"));

		List<DeliveryDetail> deldList = new ArrayList<DeliveryDetail>();
		JSONArray ja = JSONArray.fromObject(jo.optString("deldtlist"));
		for (int i = 0; i < ja.size(); i++) {
			DeliveryDetail deldt = new DeliveryDetail();
			JSONObject job = JSONObject.fromObject(ja.get(i));

			String proid = ps.getProductByName(job.optString("productID"))
					.getProductid();
			deldt.setProductid(proid);
			deldt.setSalesquantity(job.optInt("pdnum"));
			deldt.setSalesprice(job.optDouble("pdprice"));
			deldList.add(deldt);
		}
		delmt.setDeldtList(deldList);
		try {
			delmts.addDeliveryMaster(delmt);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "failed";
		}
		return "success";
	}
}
