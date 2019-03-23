package com.tzh.controller;

import com.tzh.entity.Business;
import com.tzh.entity.Page;
import com.tzh.entity.Role;
import com.tzh.entity.User;
import com.tzh.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * @author:        tangzihao
     * @methodName：   query
     * @createDate:    2019/3/4 15:02
     * @param:         [user, page]
     * @return         com.yr.entity.Page<com.tzh.entity.User>
     * @version:       1.0
     * 分页的形式查询user表的数据
     */
    @RequestMapping(value="/user")
    @ResponseBody
    public Page<Business> query(Business business, Page<Business> page){
        page.setT(business);
        userService.query(page);
        return page;
    }


    /**
     * @author:        tangzihao
     * @methodName：   jumpAdd
     * @createDate:    2019/3/5 19:57
     * @param:         [map]
     * @return         java.lang.String
     * @version:       1.0
     * 跳入到添加页面
     */
    @RequestMapping(value="/add")
    public String jumpAdd(Map<String,Object> map,User user){
        Map<String, Object> map1 = new HashMap<>();
        map1.put("0", "停用");
        map1.put("1", "启用");
        map.put("status", map1);
        map.put("user",user);//传入一个空的user对象
        return "addorupdate";
    }

    /**
     * @author:        tangzihao
     * @methodName：   jumpUpdate
     * @createDate:    2019/3/5 21:21
     * @param:         [id, map, userPojo, page]
     * @return         java.lang.String
     * @version:       1.0
     * 跳入到修改页面
     */
    @RequestMapping(value="/user/{id}",method= RequestMethod.GET)
    public String jumpUpdate(@PathVariable Integer id, Map<String, Object> map){
        Map<String, Object> map1 = new HashMap<>();
        map1.put("1", "启用");
        map1.put("0", "停用");
        map.put("status", map1);
        map.put("user", userService.getById(id));//根据id获取对象放入request中
        return "addorupdate";
    }

    /**
     * @author:        tangzihao
     * @methodName：   saveAdd
     * @createDate:    2019/3/5 21:38
     * @param:         [user]
     * @return         java.lang.String
     * @version:       1.0
     * 保存添加
     */
    @RequestMapping(value="/user", method=RequestMethod.POST)
    public String saveAdd(User user){
        userService.add(user);
        return "user";
    }


    /**
     * 保存修改
     * @return String
     */
    @RequestMapping(value="/user",method=RequestMethod.PUT)
    public String saveUpdate(User user){
        userService.update(user);
        return "user";
    }


    /**
     * 删除用户
     * @return String
     */
    @RequestMapping(value="/user/{id}",method=RequestMethod.DELETE)
    @ResponseBody
    public void delete(@PathVariable Integer id){
        userService.delete(id);
    }

    //角色回显
    @ResponseBody
    @RequestMapping(value="/role_echo",method={RequestMethod.GET})
    public Set<Role> roleEcho(Integer id){
        Set<Role> roleList = userService.queryAllRole(id);;
        return roleList;
    }

    /**
     * @author:        tangzihao
     * @methodName：   addRole
     * @createDate:    2019/3/11 8:38
     * @param:         [userId, roleId]
     * @return         java.lang.String
     * @version:       1.0
     *
     * 需要判断roleId是否等于空
     */
    //添加角色
    @ResponseBody
    @RequestMapping(value="/addRole",method={RequestMethod.POST})
    public String addRole(Integer userId,Long[] roleId){
        String value=null;
        try{
            userService.addRole(userId,roleId);
            value="success";
        }catch (Exception e){
            e.printStackTrace();
        }
        return value;
    }



}
