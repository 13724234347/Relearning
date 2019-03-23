package com.tzh.service;

import com.tzh.dubbo_interface.DubboInterface;
import com.tzh.entity.User;


public class Dubbo_service_implement implements DubboInterface {
    @Override
    public User add() {
        User user = new User();
        user.setSex("男");
        user.setAge(20);
        user.setName("刘聪");
        System.out.println(user.toString());
        return user;
    }
}
