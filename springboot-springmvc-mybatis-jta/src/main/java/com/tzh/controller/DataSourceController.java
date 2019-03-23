package com.tzh.controller;

import com.tzh.service.DataSourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class DataSourceController {

    @Autowired
    private DataSourceService dataSourceService;


    @RequestMapping(value = "/query")
    public String query(){

        dataSourceService.query();

        return "index";
    }

    @RequestMapping(value="/insert")
    public String insert(){
        dataSourceService.insert();
        return "index";
    }


}
