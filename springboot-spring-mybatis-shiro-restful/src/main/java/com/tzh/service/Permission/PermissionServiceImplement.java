package com.tzh.service.Permission;

import com.tzh.entity.Business;
import com.tzh.entity.Page;
import com.tzh.entity.Permission;
import com.tzh.mapper.PermissionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class PermissionServiceImplement implements PermissionService{

    @Autowired
    private PermissionMapper permissionMapper;


    @Override
    public void query(Page<Business> page) {
        page.setTotalCount(permissionMapper.getCount(page));//查询总条数加入page中
        List<Business> list = permissionMapper.query(page);
        page.setDataList(list);
    }

    @Override
    public void add(Permission permission) {
        permissionMapper.add(permission);
    }

    @Override
    public Permission getById(Integer id) {
        Permission permission = permissionMapper.getById(id);
        return permission;
    }

    @Override
    public void update(Permission permission) {
        permissionMapper.update(permission);
    }

    @Override
    public void delete(Integer id) {
        permissionMapper.delete(id);
    }

    @Override
    public Set<Permission> queryAll() {
        Set<Permission> permissionSet = permissionMapper.queryAll();
        return permissionSet;
    }

    @Override
    public Integer authorTag(Permission permission) {
        Integer value = permissionMapper.authorTag(permission.getUsername(),permission.getUrl(),permission.getRequestMode());
        return value;
    }
}
