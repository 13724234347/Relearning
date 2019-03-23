package com.tzh.controller;

import com.tzh.entity.Business;
import com.tzh.entity.Page;
import com.tzh.entity.Permission;
import com.tzh.entity.Role;
import com.tzh.service.Role.RoleService;
import org.apache.logging.log4j.core.util.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;
import java.util.Set;

@Controller
@RequestMapping("/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @RequestMapping(value = "/role")
    @ResponseBody
    public Page<Business> query(Business Business,Page<Business> page){
        page.setT(Business);
        roleService.query(page);
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
    public String jumpAdd(Map<String,Object> map, Role role){
        map.put("role",role);//传入一个空的user对象
        return "addorupdaterole";
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
    @RequestMapping(value="/role", method= RequestMethod.POST)
    public String saveAdd(Role role){
        roleService.add(role);
        return "role";
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
    @RequestMapping(value="/role/{id}",method= RequestMethod.GET)
    public String jumpUpdate(@PathVariable Integer id,Map<String,Object> map){
        map.put("role", roleService.getById(id));//根据id获取对象放入request中
        return "addorupdaterole";
    }

    /**
     * 保存修改
     * @return String
     */
    @RequestMapping(value="/role",method=RequestMethod.PUT)
    public String saveUpdate(Role role){
        roleService.update(role);
        return "role";
    }


    /**
     * 删除用户
     * @return String
     */
    @RequestMapping(value="/role/{id}",method=RequestMethod.DELETE)
    @ResponseBody
    public void delete(@PathVariable Integer id){
        roleService.delete(id);
    }


    //权限回显
    @ResponseBody
    @RequestMapping(value="/permission_echo",method={RequestMethod.GET})
    public Set<Permission> permissionEcho(Integer id){
        Set<Permission> permissionSet = roleService.queryAllPermission(id);
        return permissionSet;
    }


    @ResponseBody
    @RequestMapping(value="/addPermission",method={RequestMethod.POST})
    public String addPermission(Integer roleId,Long[] permissionId){
        String value=null;
        try{
            roleService.addPermission(roleId,permissionId);
            value="success";
        }catch (Exception e){
            e.printStackTrace();
        }
        return value;
    }

}
