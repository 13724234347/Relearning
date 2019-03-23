package com.tzh.server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class GreetServiceImpl extends UnicastRemoteObject implements GreetService {


    //必须要写这个构造方法
    public GreetServiceImpl() throws RemoteException {
        super();
    }

    @Override
    public User get() throws RemoteException {
        User user = new User();
        user.setId(1);
        user.setName("刘聪");
        user.setAge(20);
        user.setSex("男");
        user.setAddr("衡阳");
        return user;
    }
}
