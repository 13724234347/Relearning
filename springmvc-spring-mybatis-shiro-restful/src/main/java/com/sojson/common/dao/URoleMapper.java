package com.sojson.common.dao;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.sojson.common.model.URole;

public interface URoleMapper {
    int deleteByPrimaryKey(Long id);

    int insert(URole record);

    int insertSelective(URole record);

    URole selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(URole record);
    /**
     * 根据角色对象修改用户数据
     * @param record
     * @return
     */
    int updateByPrimaryKey(URole record);
    
    /**
     * 根据用户id查找此用户角色
     * @param id
     * @return
     */
	Set<String> findRoleByUserId(Long id);
	/**
	 * 根据权限表返回角色
	 * @param map
	 * @return
	 */
	List<URole> findNowAllPermission(Map<String, Object> map);
	/**
	 * 初始化数据（调用存储过程）
	 */
	void initData();
}