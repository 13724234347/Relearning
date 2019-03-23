package com.sojson.common.dao;

import java.util.List;
import java.util.Set;

import com.sojson.common.model.UPermission;
import com.sojson.permission.bo.UPermissionBo;

public interface UPermissionMapper {
	/**
	 * 根据关键字id删除权限
	 * @param id
	 * @return
	 */
    int deleteByPrimaryKey(Long id);
    /**
     * 添加权限
     * @param record
     * @return
     */
    int insert(UPermission record);
    /**
     * 选择性添加权限
     * @param record
     * @return
     */
    int insertSelective(UPermission record);
    /**
     * 根据关键字id查询权限
     * @param id
     * @return
     */
    UPermission selectByPrimaryKey(Long id);
    /**
     * 根据关键字id选择性修改权限
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(UPermission record);
    /**
     * 根据关键字id修改权限
     * @param record
     * @return
     */
    int updateByPrimaryKey(UPermission record);
    /**
     * 更加id查询权限
     * @param id
     * @return
     */
	List<UPermissionBo> selectPermissionById(Long id);
	/**
	 * 根据用户ID查询permission
	 * @param id
	 * @return
	 */
	Set<UPermission> findPermissionByUserId(Long id);
	/**
	 * 根据用户ID查询当前用户所有权限（permission），放入到Authorization里。
	 * @param userId
	 * @return
	 */
	//Set<UPermission> findpermissionAll(Long userId);
}