package com.tzh.quartz;

import java.util.Date;

public class Quartz {
    public void query(){
        System.out.println("query--------------"+new Date());
    }

    public void add(){
        System.out.println("add--------------"+new Date());
    }

    public void delete(){
        System.out.println("delete-------------"+new Date());
    }

    public void update(){
        System.out.println("update--------------"+new Date());
    }

}
