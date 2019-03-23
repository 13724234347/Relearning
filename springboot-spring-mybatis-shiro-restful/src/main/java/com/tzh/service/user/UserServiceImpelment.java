package com.tzh.service.user;

import com.tzh.entity.*;
import com.tzh.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserServiceImpelment implements UserService {

    @Autowired
    private UserMapper userMapper;


    @Override
    public void query(Page<Business> page) {
        page.setTotalCount(userMapper.getCount(page));//查询总条数加入page中
        List<Business> list = userMapper.query(page);//分页查询的数据
        for (Business business : list) {
            Set<Role> roleSet = userMapper.getRole(business.getUser().getUsername());
            for (Role role : roleSet) {
                business.getUser().getRoleSet().add(role);
            }
        }
        page.setDataList(list);
    }

    @Override
    public User getById(Integer id) {
        User user = userMapper.getById(id);
        return user;
    }

    @Override
    public void add(User user) {
        Date date = new Date();
        Timestamp timestamp = new Timestamp(date.getTime());
        user.setCreateTime(timestamp);
        user.setStatus(1l);
        user.setLastLoginTime(null);
        userMapper.add(user);
    }

    @Override
    public void update(User user) {
        userMapper.update(user);
    }

    @Override
    public void delete(Integer id) {
        userMapper.delete(id);
    }

    @Override
    public Set<Role> getRole(String username) {
        Set<Role> roleSet = userMapper.getRole(username);
        return roleSet;
    }

    @Override
    public Set<Permission> getPermission(String username) {
        Set<Permission> permissionSet = userMapper.getPermission(username);
        return permissionSet;
    }

    @Override
    public Set<Role> queryAllRole(Integer id) {
        Set<Role> roleSet = userMapper.queryAllRole();
        Set<Role> set = new HashSet<>();
        for (Role role : roleSet) {
            Integer is = obtainId(id, role.getId());
            if (is == 1) {
                role.setSentence("1");
            } else if (is == 0) {
                role.setSentence("0");
            }
            set.add(role);
        }
        return set;
    }


    public Integer obtainId(Integer id, Integer listId) {
        Integer value = 0;
        Set<Role> roleListByid = userMapper.getRoleByUserId(id);
        for (Role role : roleListByid) {
            if (listId.equals(role.getId())) {
                value = 1;
            } else {
                System.out.println("");
            }
        }
        return value;
    }

    @Override
    public void addRole(Integer userId, Long[] roleId) {
        if (roleId == null || roleId.equals("")) {
            userMapper.deleteUser_role(userId);
        } else {
            Integer values = userMapper.deleteUser_role(userId);
            if (values !=null || values.equals("")) {
                userMapper.addRole(userId, roleId);
            }else{
                System.out.println("添加不成功");
            }
        }
    }

}
