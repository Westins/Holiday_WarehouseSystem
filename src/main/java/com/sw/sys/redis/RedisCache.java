package com.sw.sys.redis;

import java.lang.annotation.*;

/**
 * @description: 元注解 用来标识查询数据库的方法
 * @author: sw
 * @time: 2020/2/14 14:53
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RedisCache {

}
