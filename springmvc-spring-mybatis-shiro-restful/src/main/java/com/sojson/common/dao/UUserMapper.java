package com.sojson.common.dao;

import java.util.List;
import java.util.Map;

import com.sojson.common.model.UUser;
import com.sojson.permission.bo.URoleBo;

public interface UUserMapper {
	/**
	 * 根据关键字id删除用户
	 * @param id
	 * @return
	 */
    int deleteByPrimaryKey(Long id);
    /**
     *添加用户
     * @param record
     * @return
     */
    int insert(UUser record);
    
    /**
     *  <!-- 添加用户 -->
     * @param record
     * @return
     */
    int insertSelective(UUser record);
    /**
     *根据关键字id查询用户 
     * @param id
     * @return
     */
    UUser selectByPrimaryKey(Long id);
    /**
     *  根据关键字id，选择性修改用户数据
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(UUser record);
    /**
     * 根据关键字id修改用户
     * @param record
     * @return
     */
    int updateByPrimaryKey(UUser record);
    /**
     * 登录
     * @param map
     * @return
     */
	UUser login(Map<String, Object> map);
	/**
	 * 根据邮箱|帐号查询
	 */
	UUser findUserByEmail(String email);
	/**
	 * 根据用户id查询角色
	 * @param id
	 * @return
	 */
	List<URoleBo> selectRoleByUserId(Long id);

}