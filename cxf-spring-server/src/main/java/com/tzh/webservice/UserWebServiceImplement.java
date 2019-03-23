package com.tzh.webservice;

import com.tzh.entity.User;
import com.tzh.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.jws.WebService;
import java.util.List;

@WebService
public class UserWebServiceImplement implements UserWebService {

    @Autowired
    private UserService userService;
    @Override
    public List<User> queryUser() {
        List<User> userList = userService.queryUser();
        return userList;
    }
}
