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

	// 查询所有商品
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
	//得到用户的ID，以便自动生成下一次编号
		public String getAutoProductId(HttpServletRequest request,HttpServletResponse response){
			List<Product> list=ps.getAutoProductId();
			if (list.size()>0) {
				return JSONArray.fromObject(list).toString();
			}else{
				return "fail";
			}
		}
	// 按商品名或商品编号查询商品
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

	// 添加商品
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

	// 修改商品信息
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

	// 根据编号删除商品
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
	// 根据ID得到name
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
	 * 刘晴
	 */
	// 根据商品名获取商品信息
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
