package com.pss.service;

import java.util.List;
import com.pss.dao.StockDao;
import com.pss.pojo.Product;
/**
 * 
 * @author yrr_2012/11/7
 *
 */
public class StockService {
	StockDao stockDao=new StockDao();
	//���Ԥ��
	public List<Product> getAlarmProduct(){
		return stockDao.getAlarmProducts();
	}
	//�õ�������Ʒ�Ŀ��
	public List<Product> getALLProductList(){
		return stockDao.getALLProductList();
	}
	//������Ʒ���Ƶõ���Ʒ����Ϣ
	public Product getProductByName(String productName){
		return stockDao.getProductByName(productName);
	}
	//�жϸ���Ʒ�Ƿ����
	public Product isExistProduct(String productId){
		return stockDao.isExistProduct(productId);
	}
}
