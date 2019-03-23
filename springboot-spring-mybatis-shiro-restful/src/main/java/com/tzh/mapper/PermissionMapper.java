package com.tzh.mapper;

import com.tzh.entity.Business;
import com.tzh.entity.Page;
import com.tzh.entity.Permission;
import com.tzh.entity.Role;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

public interface PermissionMapper {

    /**
     * 查询总条数
     * @return Integer
     */
    Integer getCount(Page<Business> page);//@Param指定的是别名

    /**
     * 分页的形式查询权限表的数据
     * @return List<User>
     */
    List<Business> query(Page<Business> page);

    /**
     * 返回权限集合
     * @param roleName
     * @return
     */
    Set<Permission> getPermission(String roleName);



    /**
     * 根据id查询权限
     * @param id
     * @return
     */
    Permission getById(@Param("id")Integer id);


    /**
     * 添加权限
     * @param permission
     */
    void add(Permission permission);


    /**
     * 修改权限
     * @param permission
     * @return
     */
    void update(Permission permission);

    /**
     * 删除权限
     * @param id
     * @return
     */
    void delete(Integer id);

    /**
     * 查询所有权限
     * @return
     */
    Set<Permission> queryAll();

    Integer authorTag(@Param("username")String username,@Param("url")String url,@Param("requestMode")String requestMode);
}
