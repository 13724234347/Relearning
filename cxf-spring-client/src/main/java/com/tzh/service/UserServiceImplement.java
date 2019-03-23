package com.tzh.service;

import com.tzh.entity.User;
import com.tzh.webservice.UserWebService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImplement implements UserService {


    @Autowired
    @Qualifier(value = "userWebService")
    private UserWebService userWebService;


    @Override
    public List<User> queryUserWebService() {
        List<User> userList =userWebService.queryUser();
        return userList;
    }

}
