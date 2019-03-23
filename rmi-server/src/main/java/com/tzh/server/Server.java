package com.tzh.server;

import javax.naming.Context;
import javax.naming.InitialContext;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Server {
    public static void main(String[] args) {
        try {
            String name = "rmi.service.GreetService";
//            // 创建服务
            GreetService greetService =  new GreetServiceImpl();

//            // 创建本机 1099 端口上的 RMI 注册表
            Registry registry = LocateRegistry.createRegistry(1099);
//            https://www.cnblogs.com/zawier/p/7043855.html

//
//            /***************** 以下为注册方法一 ************/
//            // 将服务绑定到注册表中
//            registry.rebind(name, greetService);
//
//            /***************** 以下为注册方法二 ************/
//             Naming.rebind(name, greetService);
//
//            /***************** 以下为注册方法三 ************/
            Context namingContext = new InitialContext();
            namingContext.rebind("rmi:" + name,greetService); // 此方式 name 需要以 rmi: 开头
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}