package com.pss.service;

import java.util.List;

import com.pss.dao.LoseSpillDao;
import com.pss.pojo.LoseSpill;

public class LoseSpillService {
	LoseSpillDao loseSpillDao=new LoseSpillDao();
	//得到所有盘点过后的清单
	public List<LoseSpill> getAllLoseSpills(){
		return loseSpillDao.getAllLoseSpills();
	}
	//根据商品名称得到盘点过的记录
	public LoseSpill getLoseSpillByName(String productName){
		return loseSpillDao.getLoseSpillByName(productName);
	}
	//添加库存盘点
	public int addLoseSpill(LoseSpill loseSpill){
		return loseSpillDao.addLoseSpill(loseSpill);
	}
	//判断库存盘点编号是否存在
	public LoseSpill isExistLoseSpill(String loseId){
		return loseSpillDao.isExistLoseSpill(loseId);
	}
	//使编号自动增加 
	public List<LoseSpill> getAutoLoseSpillId(){
		return loseSpillDao.getAutoLoseSpillId();
	}
}
