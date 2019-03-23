package com.tzh.service;

import com.tzh.secondary.UserDao;
import com.tzh.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImplement implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public List<User> queryUser() {
        List<User> userList = userDao.queryUser();
        return userList;
    }
}
