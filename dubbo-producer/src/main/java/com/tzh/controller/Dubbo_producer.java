package com.tzh.controller;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;

import java.io.IOException;

//dubbo生产者
public class Dubbo_producer {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"classpath:/*.xml"});

        context.start();
        System.out.println("正在等待消费者来消费.........");
        try {
            System.in.read(); // 为保证服务一直开着，利用输入流的阻塞来模拟
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
