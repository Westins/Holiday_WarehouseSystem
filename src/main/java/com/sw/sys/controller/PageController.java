package com.sw.sys.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @description: 页面跳转
 * @author: 单威
 * @time: 2020/2/10 12:54
 */
@Controller
@RequestMapping(value = "/")
public class PageController {

    private static final String SYSTEM_URL = "sys/";
    private static final String BUSINESS_URL = "bus/";

    @RequestMapping(value = "sys/{path}")
    public String systemPath(@PathVariable String path){
        System.out.println("systemPath:"+path);
        if(path.isEmpty()){
            path += "index";
        }
        return SYSTEM_URL + path;
    }
    @RequestMapping(value = "bus/{path}")
    public String businessPath(@PathVariable String path){
        System.out.println("businessPath:"+path);
        if(path.isEmpty()){
            path += "index";
        }
        return BUSINESS_URL + path;
    }
}
