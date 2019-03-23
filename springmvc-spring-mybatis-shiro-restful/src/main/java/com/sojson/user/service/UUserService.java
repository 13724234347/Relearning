package com.sojson.user.service;

import java.util.List;
import java.util.Map;

import org.springframework.ui.ModelMap;

import com.sojson.common.model.UUser;
import com.sojson.core.mybatis.page.Pagination;
import com.sojson.permission.bo.URoleBo;
import com.sojson.permission.bo.UserRoleAllocationBo;

public interface UUserService {
	/**
	 * ���ݹؼ���idɾ��user
	 * @param id
	 * @return
	 */
	int deleteByPrimaryKey(Long id);
	/**
	 * ����û�
	 * @param record
	 * @return
	 */
	UUser insert(UUser record);
	/**
	 * ����ѡ��������û�
	 * @param record
	 * @return
	 */
    UUser insertSelective(UUser record);
    /**
     * ���ݹؼ���id��ѯ�û�
     * @param id
     * @return
     */
    UUser selectByPrimaryKey(Long id);
    /**
     * ���ݶ����޸��û�
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(UUser record);
    /**
     * 
     * @param record
     * @return
     */
    int updateByPrimaryKey(UUser record);
    /**
     * ��¼
     * @param email
     * @param pswd
     * @return
     */
    UUser login(String email, String pswd);
    /**
     * ����email��ѯUser
     * @param email
     * @return
     */
	UUser findUserByEmail(String email);
	/**
	 * ��ҳ��ѯ
	 * @param resultMap
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	Pagination<UUser> findByPage(Map<String, Object> resultMap, Integer pageNo,
                                 Integer pageSize);
	/**
	 * ����idɾ�������û�
	 * @param ids
	 * @return
	 */
	Map<String, Object> deleteUserById(String ids);

	Map<String, Object> updateForbidUserById(Long id, Long status);

	Pagination<UserRoleAllocationBo> findUserAndRole(ModelMap modelMap,
                                                     Integer pageNo, Integer pageSize);

	List<URoleBo> selectRoleByUserId(Long id);

	Map<String, Object> addRole2User(Long userId, String ids);
	Map<String, Object> deleteRoleByUserIds(String userIds);
}
