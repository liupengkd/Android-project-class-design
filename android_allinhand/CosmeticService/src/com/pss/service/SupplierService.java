package com.pss.service;

import java.util.List;

import com.pss.dao.SupplierDao;
import com.pss.pojo.DeliveryDetail;
import com.pss.pojo.Product;
import com.pss.pojo.Supplier;

public class SupplierService {

	SupplierDao sd = new SupplierDao();

	// ��ȡ��Ӧ��������Ϣ
	public List<Supplier> getAllSupplier() {
		return sd.selectAllSupplier();
	}

	// ���ݹ�Ӧ�̱�Ų�ѯ��Ʒ���Ƿ��м�¼
	public List<Product> selectAllproductByname(int id) {
		return sd.selectAllproductByname(id);
	}

	// ���ݹ�Ӧ�̱�Ų�ѯ��������
	public List<DeliveryDetail> SelectSupplierID(int id) {
		return sd.SelectSupplierID(id);
	}

	// ���ӹ�Ӧ����Ϣ
	public int AddSupplier(Supplier sup) {
		return sd.AddSupplier(sup);
	}

	public String SelectSuppliername(String name) {
		return sd.SelectSuppliername(name);
	}

	// �޸Ĺ�Ӧ����Ϣ
	public int updateSupplier(Supplier sup) {
		return sd.updateSupplier(sup);

	}

	// ���ݱ��ɾ����Ӧ����Ϣ

	public int deleteSupplier(int supid) {
		return sd.deleteSupplier(supid);

	}

	// ���ݹ�Ӧ�����ƻ�ȡ��Ӧ����Ϣ
	public Supplier getSupplierByName(String supname) {
		return sd.getSupplierByName(supname);
	}

	// dxf
	public String getOneName(String id) {
		return sd.getOneName(id);
	}
}
