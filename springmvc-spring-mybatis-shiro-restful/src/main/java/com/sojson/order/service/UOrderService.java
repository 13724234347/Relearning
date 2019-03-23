package com.sojson.order.service;

import com.sojson.common.model.Page;
import com.sojson.common.model.UOrder;

public interface UOrderService {
	/**
	 * ��ҳ����ʽ��ѯuser�������
	 * @param page
	 */
	void query(Page<UOrder> page);
	/**
	 * ��Ӷ���
	 */
	void insert(UOrder uorder);
	/**
	 * ɾ��������Ϣ
	 * @param id
	 */
	void delete(Integer id);
	
	UOrder getById(Integer id);
	
	void update(UOrder uorder);
}
