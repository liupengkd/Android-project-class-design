package com.pss.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.pss.pojo.DeliveryDetail;
import com.pss.pojo.Product;
import com.pss.pojo.PurchaseDetail;
import com.pss.service.ProductService;

public class ProductAction {

	ProductService ps = new ProductService();

	// ��ѯ������Ʒ
	public String SelectAllProduct(HttpServletRequest request,
			HttpServletResponse response) {
		List<Product> product = ps.getAllProduct();
		if (product.size() > 0) {
			JSONArray ja = JSONArray.fromObject(product);
			return ja.toString();
		} else {
			return "faild";
		}
	}
	//�õ��û���ID���Ա��Զ�������һ�α��
		public String getAutoProductId(HttpServletRequest request,HttpServletResponse response){
			List<Product> list=ps.getAutoProductId();
			if (list.size()>0) {
				return JSONArray.fromObject(list).toString();
			}else{
				return "fail";
			}
		}
	// ����Ʒ������Ʒ��Ų�ѯ��Ʒ
	public String getProductByNameorID(HttpServletRequest request,
			HttpServletResponse response) {
		List<Product> product = ps.getProductByNameorID(request
				.getParameter("nameid"));
		if (product.size()>0) {
			return JSONArray.fromObject(product).toString();
		} else {
			return "faild";
		}
	}

	// �����Ʒ
	public String AddProiduct(HttpServletRequest request,
			HttpServletResponse response) {
		String addpro_json = request.getParameter("addpro_json");
		JSONObject jb = JSONObject.fromObject(addpro_json);
		Product pro = new Product();
		pro.setProductid(jb.optString("ProductID"));
		pro.setProductname(jb.optString("ProductName"));
		pro.setSuppliername(jb.optString("SupplierID"));
		pro.setMaxSafeStock(jb.optInt("MaxSafeStock"));
		pro.setSafeStock(jb.optInt("MinSafeStock"));

		return ps.AddProiduct(pro);
	}

	// �޸���Ʒ��Ϣ
	public String UpdateProduct(HttpServletRequest request,
			HttpServletResponse response) {
		String UpdatePro_json = request.getParameter("UpdatePro_json");
		JSONObject jb = JSONObject.fromObject(UpdatePro_json);
		Product pro = new Product();
		pro.setProductname(jb.optString("ProductName"));
		pro.setSuppliername(jb.optString("SupplierID"));
		pro.setMaxSafeStock(jb.optInt("MaxSafeStock"));
		pro.setSafeStock(jb.optInt("MinSafeStock"));
		pro.setProductid(jb.optString("ProductID"));
		int i = ps.UpdateProduct(pro);
		if (i > 0) {
			return "success";
		}
		return "faild";
	}

	// ���ݱ��ɾ����Ʒ
	public String DeleteProduct(HttpServletRequest request,
			HttpServletResponse response) {
		String productId = request.getParameter("proid");

		List<PurchaseDetail> puList = ps.isExistPurchaseByProductId(productId);
		List<DeliveryDetail> delList = ps.isExistDeliveryByProductId(productId);
		if (puList.size() > 0 || delList.size() > 0) {
			return "notdelete";
		} else {
			int i = ps.DeleteProduct(productId);
			if (i > 0) {
				return "success";
			} else {
				return "faild";
			}
		}

	}

	// dxf
	// ����ID�õ�name
	public String getOneProductName(HttpServletRequest request,
			HttpServletResponse response) {
		String nameOrId = request.getParameter("pc_json");
		JSONObject jo = JSONObject.fromObject(nameOrId);
		String id = jo.optString("id");
		String name = ps.getOneById(id);
		if (id != "") {
			return name;
		} else {
			return "faild";
		}
	}

	/**
	 * ����
	 */
	// ������Ʒ����ȡ��Ʒ��Ϣ
	public String getProductByName(HttpServletRequest request,
			HttpServletResponse response) {
		String name = request.getParameter("name");
		Product pro = ps.getProductByName(name);
		if (pro != null) {
			return JSONObject.fromObject(pro).toString();
		} else {
			return "faild";
		}
	}

}
