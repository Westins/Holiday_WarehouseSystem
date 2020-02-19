package com.sw.sys.redis;


import com.alibaba.fastjson.JSONArray;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @description:
 * @author: sw
 * @time: 2020/2/14 15:15
 */
@Component
@Aspect
public class RedisAspect {

    private static final Logger LOGGER = LoggerFactory.getLogger(RedisAspect.class);

    @Autowired
    private RedisUtil redisUtil;

    /**
     * 拦截所有元注解RedisCache注解的方法
     */
    @Pointcut(value = "@annotation(com.sw.sys.redis.RedisCache)")
    public void pointcutMethod() {

    }

    /**
     * 环绕处理，先从Redis里获取缓存,查询不到，就查询MySQL数据库，
     * 然后再保存到Redis缓存里
     *
     * @param joinPoint
     * @return
     */
    @Around("pointcutMethod()")
    public Object around(ProceedingJoinPoint joinPoint) {//前置：从Redis里获取缓存

        //获取目标方法所在类
        String target = joinPoint.getTarget().toString();
        String className = target.split("@")[0];

        //先获取目标方法参数
        String applId = null;
        Object[] args = joinPoint.getArgs();
        if (args != null && args.length > 0) {
            applId = String.valueOf(args[0]);
        }

        //获取目标方法的方法名称
        String methodName = joinPoint.getSignature().getName();

        //redis中key
        String redisKey = applId + "|" + className + "." + methodName;

        Object obj = redisUtil.get(redisKey);
        if (obj != null) {
            String val = JSONArray.toJSONString(obj);
            System.out.println("obj:" + val);
            return obj;
        }
        try {
            obj = joinPoint.proceed();
        } catch (Throwable e) {
            e.printStackTrace();
        }
        redisUtil.set(redisKey, obj);
        return obj;
    }

    /**
     * 获取目标方法的返回值类型
     *
     * @param joinPoint
     * @return
     */
    private Class getReturnType(ProceedingJoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        return signature.getReturnType();
    }
}
