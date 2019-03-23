package com.tzh.main;

import com.tzh.config.Master;
import com.tzh.config.Slave;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;


@ComponentScan(basePackages = "com.tzh")
@EnableConfigurationProperties(value = { Slave.class, Master.class }) //多数据源
@EnableAutoConfiguration
@EnableAsync //启动异步调用
public class Main {

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

}
