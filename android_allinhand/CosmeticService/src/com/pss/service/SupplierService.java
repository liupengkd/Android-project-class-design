package com.pss.service;

import java.util.List;

import com.pss.dao.SupplierDao;
import com.pss.pojo.DeliveryDetail;
import com.pss.pojo.Product;
import com.pss.pojo.Supplier;

public class SupplierService {

	SupplierDao sd = new SupplierDao();

	// 获取供应商所有信息
	public List<Supplier> getAllSupplier() {
		return sd.selectAllSupplier();
	}

	// 根据供应商编号查询商品表是否有记录
	public List<Product> selectAllproductByname(int id) {
		return sd.selectAllproductByname(id);
	}

	// 根据供应商编号查询销售主表
	public List<DeliveryDetail> SelectSupplierID(int id) {
		return sd.SelectSupplierID(id);
	}

	// 增加供应商信息
	public int AddSupplier(Supplier sup) {
		return sd.AddSupplier(sup);
	}

	public String SelectSuppliername(String name) {
		return sd.SelectSuppliername(name);
	}

	// 修改供应商信息
	public int updateSupplier(Supplier sup) {
		return sd.updateSupplier(sup);

	}

	// 根据编号删除供应商信息

	public int deleteSupplier(int supid) {
		return sd.deleteSupplier(supid);

	}

	// 根据供应商名称获取供应商信息
	public Supplier getSupplierByName(String supname) {
		return sd.getSupplierByName(supname);
	}

	// dxf
	public String getOneName(String id) {
		return sd.getOneName(id);
	}
}
