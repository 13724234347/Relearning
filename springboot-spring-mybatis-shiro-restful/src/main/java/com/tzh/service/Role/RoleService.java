package com.tzh.service.Role;

import com.tzh.entity.Business;
import com.tzh.entity.Page;
import com.tzh.entity.Permission;
import com.tzh.entity.Role;

import java.util.Set;

public interface RoleService {


    void query(Page<Business> page);

    void add(Role role);

    Role getById(Integer id);


    void update(Role role);

    void delete(Integer id);

    Set<Permission> getPermission(String roleName);

    Set<Permission> queryAllPermission(Integer id);

    void addPermission(Integer roleId,Long[] permissionId);



}
