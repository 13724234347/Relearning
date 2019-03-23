package com.tzh.controller;

import com.tzh.entity.Business;
import com.tzh.entity.Page;
import com.tzh.entity.Permission;
import com.tzh.service.Permission.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
@RequestMapping("/permission")
public class PermissionController {

    @Autowired
    private PermissionService permissionService;


    @RequestMapping(value = "/permission")
    @ResponseBody
    public Page<Business> query(Business Business, Page<Business> page){
        page.setT(Business);
        permissionService.query(page);
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
    public String jumpAdd(Map<String,Object> map, Permission permission){
        map.put("permission",permission);//传入一个空的user对象
        return "addorupdatepermission";
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
    @RequestMapping(value="/permission", method= RequestMethod.POST)
    public String saveAdd(Permission permission){
        permissionService.add(permission);
        return "permission";
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
    @RequestMapping(value="/permission/{id}",method= RequestMethod.GET)
    public String jumpUpdate(@PathVariable Integer id, Map<String,Object> map){
        map.put("permission", permissionService.getById(id));//根据id获取对象放入request中
        return "addorupdatepermission";
    }

    /**
     * 保存修改
     * @return String
     */
    @RequestMapping(value="/permission",method=RequestMethod.PUT)
    public String saveUpdate(Permission permission){
        permissionService.update(permission);
        return "permission";
    }


    /**
     * 删除用户
     * @return String
     */
    @RequestMapping(value="/permission/{id}",method=RequestMethod.DELETE)
    @ResponseBody
    public void delete(@PathVariable Integer id){
        permissionService.delete(id);
    }


}
