package com.sojson.order.service;

import com.sojson.common.model.Page;
import com.sojson.common.model.UOrder;

public interface UOrderService {
	/**
	 * 分页的形式查询user表的数据
	 * @param page
	 */
	void query(Page<UOrder> page);
	/**
	 * 添加订单
	 */
	void insert(UOrder uorder);
	/**
	 * 删除订单信息
	 * @param id
	 */
	void delete(Integer id);
	
	UOrder getById(Integer id);
	
	void update(UOrder uorder);
}
