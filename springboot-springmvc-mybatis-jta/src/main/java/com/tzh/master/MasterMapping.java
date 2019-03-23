package com.tzh.master;

import com.tzh.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;


public interface MasterMapping {
    public List<User> query();

    public void insert();
}
