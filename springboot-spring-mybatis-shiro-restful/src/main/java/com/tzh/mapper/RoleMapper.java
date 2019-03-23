package com.tzh.mapper;

import com.tzh.entity.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

public interface RoleMapper {



    /**
     * 查询总条数
     * @return Integer
     */
    Integer getCount(Page<Business> page);//@Param指定的是别名
    /**
     * 分页的形式查询role表的数据
     * @return List<User>
     */
    List<Business> query(Page<Business> page);

    /**
     * 返回权限集合
     * @param rolename
     * @return
     */
    Set<Permission> getPermission(String rolename);



    /**
     * 根据id查询角色
     * @param id
     * @return
     */
    Role getById(@Param("id")Integer id);


    void add(Role role);


    /**
     * 修改角色
     * @param role
     * @return
     */
    void update(Role role);

    /**
     * 删除角色
     * @param id
     * @return
     */
    void delete(Integer id);


    /**
     *  查询所有的权限
     *
     */
    Set<Permission> queryAllPermission();

    /**
     * 根据角色id查询权限
     * @param id
     * @return
     */
    Set<Permission> getPermissionByRoleId(@Param("id")Integer id);


    /**
     * 将该角色的权限全部删除(删关联表)
     * @param id
     * @return
     */
    Integer deleteRole_permission(@Param("id")Integer id);

    /**
     * 在将页面上添加或修改的角色权限添加进关联表中
     * @param roleId
     * @param permissionId
     * @return
     */
    void addPermission(@Param("rid")Integer roleId,@Param("pid")Long[] permissionId);

}
