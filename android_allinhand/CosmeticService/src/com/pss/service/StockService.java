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
	//库存预警
	public List<Product> getAlarmProduct(){
		return stockDao.getAlarmProducts();
	}
	//得到所有商品的库存
	public List<Product> getALLProductList(){
		return stockDao.getALLProductList();
	}
	//根据商品名称得到商品的信息
	public Product getProductByName(String productName){
		return stockDao.getProductByName(productName);
	}
	//判断该商品是否存在
	public Product isExistProduct(String productId){
		return stockDao.isExistProduct(productId);
	}
}
