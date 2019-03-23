package com.tzh.slave;

import com.tzh.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;


public interface SlaveMapping {


    public List<User> query();

    public void insert();

}
