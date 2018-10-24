package com.pss.service;

import java.util.List;

import com.pss.dao.LoseSpillDao;
import com.pss.pojo.LoseSpill;

public class LoseSpillService {
	LoseSpillDao loseSpillDao=new LoseSpillDao();
	//�õ������̵������嵥
	public List<LoseSpill> getAllLoseSpills(){
		return loseSpillDao.getAllLoseSpills();
	}
	//������Ʒ���Ƶõ��̵���ļ�¼
	public LoseSpill getLoseSpillByName(String productName){
		return loseSpillDao.getLoseSpillByName(productName);
	}
	//��ӿ���̵�
	public int addLoseSpill(LoseSpill loseSpill){
		return loseSpillDao.addLoseSpill(loseSpill);
	}
	//�жϿ���̵����Ƿ����
	public LoseSpill isExistLoseSpill(String loseId){
		return loseSpillDao.isExistLoseSpill(loseId);
	}
	//ʹ����Զ����� 
	public List<LoseSpill> getAutoLoseSpillId(){
		return loseSpillDao.getAutoLoseSpillId();
	}
}
