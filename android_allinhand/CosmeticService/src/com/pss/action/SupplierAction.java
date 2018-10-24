package com.pss.action;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.pss.pojo.DeliveryDetail;
import com.pss.pojo.Product;
import com.pss.pojo.Supplier;
import com.pss.service.SupplierService;

public class SupplierAction {
	SupplierService se = new SupplierService();

	// 获取供应商所有信息
	public String selectAllSupplier(HttpServletRequest request,
			HttpServletResponse response) {
		List<Supplier> supplier = se.getAllSupplier();
		if (supplier.size() > 0) {
			JSONArray ja = JSONArray.fromObject(supplier);
			return ja.toString();
		} else {
			return "failed";
		}

	}

	// 添加供应商信息
	public String AddSupplier(HttpServletRequest request,
			HttpServletResponse response) {
		int i = 0;
		String flag = "";
		String addsup_json = request.getParameter("addsup_json");
		JSONObject jb = JSONObject.fromObject(addsup_json);
		Supplier sup = new Supplier();
		sup.setSupplierid(jb.optString("SupplierID"));
		sup.setSuppliername(jb.optString("SupplierName"));
		sup.setTelephone(jb.optString("Telephone"));
		sup.setCompanyaddress(jb.optString("Companyaddress"));
		
		Supplier sup_check = se.getSupplierByName(sup.getSuppliername());
		if (null != sup_check) {
			flag = "doublename";

		} else {
			 i = se.AddSupplier(sup);
			if (i > 0) {
				flag = "success";

			} else {

				flag = "faild";
			}
		}
		return flag;
		

	}

	// 根据供应商名称获取供应商信息
	public String getSupplierByName(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			request.setCharacterEncoding("utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Supplier sup = se.getSupplierByName(request.getParameter("supname"));
		if (sup != null) {
			return JSONArray.fromObject(sup).toString();

		} else {
			return "faild";
		}

	}

	// 修改供应商信息
	public String updateSupplier(HttpServletRequest request,
			HttpServletResponse response) {
		int i = 0;
		String UpdateSup_json = request.getParameter("updatesup_json");
		JSONObject jb = JSONObject.fromObject(UpdateSup_json);
		Supplier sup = new Supplier();
		sup.setSupplierid(jb.optString("SupplierID"));
		sup.setSuppliername(jb.optString("SupplierName"));
		sup.setTelephone(jb.optString("Telephone"));
		sup.setCompanyaddress(jb.optString("Companyaddress"));

		i = se.updateSupplier(sup);

		if (i > 0) {
			return "SUCCESS";
		}
		return "faild";
	}

	// 根据编号删除供应商
	public String deleteSupplier(HttpServletRequest request,
			HttpServletResponse response) {
		int supplierid = Integer.parseInt(request.getParameter("supid"));
		List<Product> product = se.selectAllproductByname(supplierid);
		
		List<DeliveryDetail> deliveryDetail = se.SelectSupplierID(supplierid);
		
		if (product.size() > 0 || deliveryDetail.size() > 0) {
			return "notdelete";
		} else{ 
				int i = se.deleteSupplier(supplierid);
				if (i>0){
			
							return "SUCCESS";
			}else {
				return "faild";
			}
		}
	}
	

	public String getOneSupplierName(HttpServletRequest request,
			HttpServletResponse response) {
		String nameOrId = request.getParameter("pc_json");
		JSONObject jo = JSONObject.fromObject(nameOrId);
		String name = jo.optString("name");
		String id = se.SelectSuppliername(name);
		if (id != "") {
			return id;
		} else {
			return "faild";
		}
	}
	
	//dxf
		public String getOneSupplierNames(HttpServletRequest request,
				HttpServletResponse response) {
			String nameOrId=request.getParameter("pc_json");
			JSONObject jo=JSONObject.fromObject(nameOrId);
			String id=jo.optString("id");
			String name=se.getOneName(id);
			if(id!=""){
				return name;
			}else{
				return "faild";
			}
		}
}
