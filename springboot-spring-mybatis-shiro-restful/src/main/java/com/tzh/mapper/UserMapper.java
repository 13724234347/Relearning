package com.tzh.mapper;

import com.tzh.entity.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

public interface UserMapper {

    /**
     * 查询总条数
     * @return Integer
     */
    Integer getCount(Page<Business> page);//@Param指定的是别名


    /**
     * 分页的形式查询user表的数据
     * @return List<User>
     */
    List<Business> query(Page<Business> page);


    /**
     * 根据id查询用户
     * @param id
     * @return
     */
    User getById(@Param("id")Integer id);

    /**
     * 添加角色
     * @param user
     * @return
     */
    void add(User user);


    /**
     * 修改用户
     * @param user
     * @return
     */
    void update(User user);

    /**
     * 删除用户
     * @param id
     * @return
     */
    void delete(Integer id);

    /**
     * 返回角色集合
     * @param username
     * @return
     */
    Set<Role> getRole(String username);


    /**
     * 返回权限集合
     * @param username
     * @return
     */
    Set<Permission> getPermission(String username);

    /**
     *  查询所有的角色
     *
     */
    Set<Role> queryAllRole();

    /**
     * 根据用户id查询角色
     * @param id
     * @return
     */
    Set<Role> getRoleByUserId(@Param("id")Integer id);

    /**
     * 将该用户的角色全部删除(删关联表)
     * @param id
     * @return
     */
    Integer deleteUser_role(@Param("id")Integer id);

    /**
     * 在将页面上添加或修改的用户权限添加进关联表中
     * @param userId
     * @param roleId
     * @return
     */
    void addRole(@Param("uid")Integer userId,@Param("rid")Long[] roleId);

}
