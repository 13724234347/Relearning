package com.tzh.service.Role;

import com.tzh.entity.*;
import com.tzh.mapper.RoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class RoleServiceImplement implements RoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public void query(Page<Business> page) {
        page.setTotalCount(roleMapper.getCount(page));//查询总条数加入page中
        List<Business> list = roleMapper.query(page);//分页查询的数据
        for (Business business : list) {
            Set<Permission> permissionSet =  roleMapper.getPermission(business.getRole().getRoleName());
            for (Permission permission : permissionSet) {
                business.getRole().getPermissionSet().add(permission);
            }
        }
        page.setDataList(list);
    }

    @Override
    public void add(Role role) {
        roleMapper.add(role);
    }

    @Override
    public Role getById(Integer id) {
        Role role = roleMapper.getById(id);
        return role;
    }

    @Override
    public void update(Role role) {
        roleMapper.update(role);
    }

    @Override
    public void delete(Integer id) {
        roleMapper.delete(id);

    }

    @Override
    public Set<Permission> getPermission(String roleName) {
        Set<Permission> permissionSet =  roleMapper.getPermission(roleName);
        return permissionSet;
    }

    @Override
    public Set<Permission> queryAllPermission(Integer id) {
        Set<Permission> permissionSet = roleMapper.queryAllPermission();
        Set<Permission> set = new HashSet<>();
        for (Permission permission : permissionSet) {
            Integer is = obtainId(id, permission.getId());
            if (is == 1) {
                permission.setSentence("1");
            } else{
                permission.setSentence("0");
            }
            set.add(permission);
        }
        return set;
    }

    @Override
    public void addPermission(Integer roleId, Long[] permissionId) {
        if (permissionId == null || permissionId.equals("")){
            roleMapper.deleteRole_permission(roleId);
        }else{
            Integer values = roleMapper.deleteRole_permission(roleId);
            if (values !=null || values.equals("")) {
                roleMapper.addPermission(roleId, permissionId);
            }else {
                System.out.println("添加不成功");
            }
        }
    }

    public Integer obtainId(Integer id, Integer listId) {
        Integer value = 0;
        Set<Permission> permissionListByid = roleMapper.getPermissionByRoleId(id);
        for (Permission permission : permissionListByid) {
            if (listId.equals(permission.getId())) {
                value = 1;
            } else {
                System.out.println("");
            }
        }
        return value;
    }
}
