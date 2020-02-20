package com.sw;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = {"com.sw.*.dao"})
public class HolidayDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(HolidayDemoApplication.class, args);
    }

}
