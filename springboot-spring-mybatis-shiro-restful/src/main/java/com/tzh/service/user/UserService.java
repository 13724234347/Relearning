package com.tzh.service.user;

import com.tzh.entity.*;

import java.util.List;
import java.util.Set;

public interface UserService {

    void query(Page<Business> page);

    User getById(Integer id);

    void add(User user);

    void update(User user);

    void delete(Integer id);


    Set<Role> getRole(String username);

    Set<Permission> getPermission(String username);

    Set<Role> queryAllRole(Integer id);

    void addRole(Integer userId, Long[] roleId);
}
