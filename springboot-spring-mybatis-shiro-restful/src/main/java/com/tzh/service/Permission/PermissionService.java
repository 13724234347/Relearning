package com.tzh.service.Permission;

import com.tzh.entity.Business;
import com.tzh.entity.Page;
import com.tzh.entity.Permission;

import java.util.Set;

public interface PermissionService {

    void query(Page<Business> page);

    void add(Permission permission);

    Permission getById(Integer id);

    void update(Permission permission);

    void delete(Integer id);

    Set<Permission> queryAll();

    Integer authorTag(Permission permission);

}
