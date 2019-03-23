package com.sojson.common.dao;

import java.util.List;
import java.util.Map;

import com.sojson.common.model.UPermission;
import com.sojson.common.model.URole;
import com.sojson.common.model.UUserRole;

public interface UUserRoleMapper {
	/**
	 * 根据用户ID&&角色ID添加
	 * @param record
	 * @return
	 */
    int insert(UUserRole record);

    int insertSelective(UUserRole record);
    /**
     * 根据用户ID删除
     * @param id
     * @return
     */
	int deleteByUserId(Long id);
	/**
	 * 根据用户roleId查询用户ID
	 * @param resultMap
	 * @return
	 */
	int deleteRoleByUserIds(Map<String, Object> resultMap);

	List<Long> findUserIdByRoleId(Long id);
	
	 List<URole> getRoleByUserId(String nickname);
	 List<UPermission> getPermissionByUserId(String nickname);
}