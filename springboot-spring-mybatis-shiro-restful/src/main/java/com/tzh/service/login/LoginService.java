package com.tzh.service.login;


import com.tzh.entity.User;

public interface LoginService {

    User getUserInformation(String username);

    void setLastLoginTime(String username);


}
