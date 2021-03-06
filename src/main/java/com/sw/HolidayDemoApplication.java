package com.sw;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @description: 启动器
 * @author: 单威
 * @time: 2020/2/10 9:10
 */
@SpringBootApplication
@MapperScan(basePackages = {"com.sw.*.dao"})
public class HolidayDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(HolidayDemoApplication.class, args);
    }

}
