package com.sojson.order.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sojson.common.dao.UOrderMapper;
import com.sojson.common.model.Page;
import com.sojson.common.model.UOrder;
import com.sojson.order.service.UOrderService;

@Service
public class UOrderServiceImpl implements UOrderService {
	@Autowired
	private UOrderMapper uOrderMapper;

	public void query(Page<UOrder> page) {
		page.setTotalCount(uOrderMapper.getCount(page));//��ѯ����������page��
		List<UOrder> list = uOrderMapper.query(page);//��ҳ��ѯ������
		page.setDataList(list);
	}

	public void insert(UOrder uorder) {
		 uOrderMapper.insert(uorder);
	}

	public void delete(Integer id) {
		uOrderMapper.delete(id);
		
	}

	public UOrder getById(Integer id) {
		
		return uOrderMapper.getById(id);
	}

	public void update(UOrder uorder) {
		uOrderMapper.update(uorder);
		
	}


}
