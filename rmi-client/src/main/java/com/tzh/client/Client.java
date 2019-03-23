package com.tzh.client;

import com.tzh.server.GreetService;

import javax.naming.Context;
import javax.naming.InitialContext;
import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Client {

    public static void main(String[] args) {
        try {
            String name = "rmi.service.GreetService";
            /***************** 以下为查找服务方法一 ************/
//            // 获取注册表
//            Registry registry = LocateRegistry.getRegistry("localhost", 1099);
//            // 查找对应的服务
//            GreetService service = (GreetService) registry.lookup(name);

            /***************** 以下为查找服务方法二 ************/
//            GreetService service = (GreetService) Naming.lookup(name);

            /***************** 以下为查找服务方法三 ************/
            Context namingContext = new InitialContext();
            GreetService service = (GreetService) namingContext.lookup("rmi:" + name);

            // 调用服务
            System.out.println(service.get());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}