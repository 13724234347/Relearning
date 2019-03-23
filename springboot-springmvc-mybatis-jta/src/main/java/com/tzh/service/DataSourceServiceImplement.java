package com.tzh.service;

import com.tzh.entity.User;
import com.tzh.master.MasterMapping;
import com.tzh.slave.SlaveMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DataSourceServiceImplement implements DataSourceService {

    @Autowired
    private SlaveMapping slaveMapping;

    @Autowired
    private MasterMapping masterMapping;
    @Override
    public void query() {
        List<User> userList = slaveMapping.query();
        for (User user : userList) {
            System.out.println(user);
        }
        List<User> userList1 = masterMapping.query();
        for (User user : userList1) {
            System.out.println(user);
        }
    }
    @Override
    @Transactional
    public void insert() {
        slaveMapping.insert();
        int i = 1 / 0;
        masterMapping.insert();
    }
}
