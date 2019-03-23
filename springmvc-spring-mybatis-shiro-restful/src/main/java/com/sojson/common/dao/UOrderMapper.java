package com.sojson.common.dao;

import java.util.List;

import com.sojson.common.model.Page;
import com.sojson.common.model.UOrder;

public interface UOrderMapper {
	/**
	 * ��ҳ����ʽ��ѯorder�������
	 * @return List<User>
	 */
	List<UOrder> query(Page<UOrder> page);

	/**
	 * ��ѯ������
	 * @param search
	 * @return Integer
	 */
	Integer getCount(Page<UOrder> page);//@Paramָ�����Ǳ���
	/**
	 * ��Ӷ���
	 */
	void insert(UOrder uorder);
	/**
	 * ɾ��������Ϣ
	 * @param id
	 */
	void delete(Integer id);
	/**
	 * ����id��ѯ�û�����
	 * @param id
	 */
	UOrder getById(Integer id);
	/**
	 * �޸Ķ���
	 * @param id
	 */
	void update(UOrder uorder);
	
}
