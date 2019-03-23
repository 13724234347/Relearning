package com.tzh.mapper;

import com.tzh.entity.User;
import org.apache.ibatis.annotations.Param;


public interface LoginMapper {

    User getUserInformation(@Param("username") String userName);


    void setLastLoginTime(User user);
}
