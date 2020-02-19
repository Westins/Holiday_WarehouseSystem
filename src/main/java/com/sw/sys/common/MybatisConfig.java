package com.sw.sys.common;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @description: Mybatis配置
 * @author: 单威
 * @time: 2020/2/12 17:02
 */
@Configuration
public class MybatisConfig {
    /**
     * mybatis 分页插件
     *
     * @return
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }
}
