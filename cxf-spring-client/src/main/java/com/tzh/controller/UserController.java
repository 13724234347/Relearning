package com.tzh.controller;


import com.tzh.entity.User;
import com.tzh.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/query")
    public String query(){

        List<User> userList = userService.queryUserWebService();
        for (User user:userList) {
            System.out.println(user);
        }
        return "index";
    }






}
