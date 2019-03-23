package com.sojson.common.dao;

import java.util.List;

import com.sojson.common.model.Page;
import com.sojson.common.model.UOrder;

public interface UOrderMapper {
	/**
	 * 分页的形式查询order表的数据
	 * @return List<User>
	 */
	List<UOrder> query(Page<UOrder> page);

	/**
	 * 查询总条数
	 * @param search
	 * @return Integer
	 */
	Integer getCount(Page<UOrder> page);//@Param指定的是别名
	/**
	 * 添加订单
	 */
	void insert(UOrder uorder);
	/**
	 * 删除订单信息
	 * @param id
	 */
	void delete(Integer id);
	/**
	 * 根据id查询用户数据
	 * @param id
	 */
	UOrder getById(Integer id);
	/**
	 * 修改订单
	 * @param id
	 */
	void update(UOrder uorder);
	
}
