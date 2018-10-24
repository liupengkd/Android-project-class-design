package com.pss.action;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import com.pss.pojo.PurchaseDetail;
import com.pss.pojo.PurchaseMaster;
import com.pss.service.PurchaseService;

public class PurchaseAction {
	PurchaseService purchaseService = new PurchaseService();

	// 添加采购单信息
	public String insert(HttpServletRequest request,
			HttpServletResponse response) {
		String json = request.getParameter("p_json");
		JSONObject jo = JSONObject.fromObject(json);

		PurchaseMaster pc = new PurchaseMaster();
		pc.setPurchaseid(jo.optString("pid"));
		pc.setPurchasedate(jo.optString("pdate"));
		pc.setSupplierid(jo.optString("supplierid"));
		pc.setPurchaseproperty(jo.optInt("purchasep"));
		pc.setSubtotal(jo.optDouble("sub"));

		List<PurchaseDetail> pcdList = new ArrayList<PurchaseDetail>();
		JSONArray ja = JSONArray.fromObject(jo.optString("detaillist"));
		for (int i = 0; i < ja.size(); i++) {
			PurchaseDetail pd = new PurchaseDetail();
			JSONObject ob = JSONObject.fromObject(ja.get(i));
			pd.setPurchaseid(pc.getPurchaseid());
			pd.setProductid(ob.optString("productid"));
			pd.setPurchasequantity(ob.optInt("number"));
			pd.setPurchaseunitprice(ob.optDouble("price"));
			pcdList.add(pd);
		}
		pc.setAllDetail(pcdList);
		try {
			purchaseService.insert(pc);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "faild";
		}
		return "success";

	}

	// 得到所有采购入库单
	public String getAllRu(HttpServletRequest request,
			HttpServletResponse response) {
		String purchaseid = request.getParameter("purchaseid");
		List<PurchaseMaster> list = purchaseService.getAllRu(purchaseid);
		if (list.size() > 0) {
			return JSONArray.fromObject(list).toString();
		} else {
			return "fail";
		}
	}

	// 得到所有采购退货单
	public String getAllTui(HttpServletRequest request,
			HttpServletResponse response) {
		String purchaseid = request.getParameter("purchaseid");
		List<PurchaseMaster> list = purchaseService.getAllTui(purchaseid);
		if (list.size() > 0) {
			return JSONArray.fromObject(list).toString();
		} else {
			return "fail";
		}
	}

	// 通过id得到采购入库单
	public String getOneByIdRu(HttpServletRequest request,
			HttpServletResponse response) {
		String purchaseid = request.getParameter("purchaseid");
		PurchaseMaster product = purchaseService.getOneByIdRu(purchaseid);
		if (product != null) {
			return JSONObject.fromObject(product).toString();
		} else {
			return "fail";
		}
	}

	// 通过id得到采购退货单
	public String getOneByIdTui(HttpServletRequest request,
			HttpServletResponse response) {
		String purchaseid = request.getParameter("purchaseid");
		PurchaseMaster product = purchaseService.getOneByIdTui(purchaseid);
		if (product != null) {
			return JSONObject.fromObject(product).toString();
		} else {
			return "fail";
		}
	}

}
