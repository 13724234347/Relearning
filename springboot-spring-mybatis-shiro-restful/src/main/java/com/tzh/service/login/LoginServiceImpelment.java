package com.tzh.service.login;

import com.tzh.entity.User;
import com.tzh.mapper.LoginMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;

/**
  * @ClassName：    LoginServiceImpelment
  * @Author:        tangzihao
  * @CreateDate:    2019/3/1 21:42
  * @Version:       1.0
  *
  * 处理登陆信息的业务逻辑
  */
@Service
public class LoginServiceImpelment implements  LoginService{

    @Autowired
    private LoginMapper loginMaper;

    /**
     * @author:        tangzihao
     * @methodName：   getUserInformation
     * @createDate:    2019/3/1 21:53
     * @param:         [userName, passWord]
     * @return         com.tzh.entity.User
     * @version:       1.0
     *
     * 根据用户名查询该用户的所有信息
     */
    @Override
    public User getUserInformation(String userName) {
        User user = loginMaper.getUserInformation(userName);
        return user;
    }
    /**
     * @author:        tangzihao
     * @methodName：   setLastLoginTime
     * @createDate:    2019/3/5 20:41
     * @param:         []
     * @return         void
     * @version:       1.0
     * 将账号最后一次登陆时间放进数据库
     */
    @Override
    public void setLastLoginTime(String username) {
        User user = new User();
        Date date = new Date();
        Timestamp timestamp = new Timestamp(date.getTime());
        user.setLastLoginTime(timestamp);
        user.setUsername(username);
        loginMaper.setLastLoginTime(user);
    }


}
