package com.sw.sys.redis;

import java.lang.annotation.*;

/**
 * @description: 元注解 用来标识查询数据库的方法
 * @author: 单威
 * @time: 2020/2/14 14:53
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RedisCache {
    String key() default "";//存到缓存中的key，用户可以不写，如果为空串，表示自动生成key
    String type(); //当前执行方法的类型
    int seconds() default 0;//缓存超时时间，0表示用户设置该数据不需要超时时间，如果不等于0则说明用户自己定义了超时时间
}
