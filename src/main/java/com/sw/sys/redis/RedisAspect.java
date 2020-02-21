package com.sw.sys.redis;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * @description: redis 切面
 * @author: 单威
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
     * 环绕处理，先从Redis里获取缓存,查询不到，就查询MySQL数据库，
     * 然后再保存到Redis缓存里
     *
     * @param joinPoint
     * @return
     */

    @Around("@annotation(redisCache)")
    public Object around(ProceedingJoinPoint joinPoint, RedisCache redisCache) {//前置：从Redis里获取缓存
        String type = redisCache.type();
        switch (type) {
            case "select":
                return select(joinPoint, redisCache);
            case "update":
                return null;
            case "delete":
                return null;
            case "insert":
                return null;
            default:
                return null;
        }
    }

    /**
     * 缓存 -- 查询
     *
     * @param joinPoint
     * @param redisCache
     * @return
     */
    public Object select(ProceedingJoinPoint joinPoint, RedisCache redisCache) {
        String key = getKey(joinPoint);
        Object obj = redisUtil.get(key);
        if (obj != null) {
            System.out.println("********** 从缓存中查询到了数据 **************");
            return obj;
        }
        try {
            obj = joinPoint.proceed();
            System.out.println("************ 未命中缓存 从数据库中查询并放入缓存 ************");
        } catch (Throwable e) {
            e.printStackTrace();
        }
        redisUtil.set(key, obj);
        return null;
    }

    /**
     * 获取Key
     *
     * @param joinPoint
     * @return
     */
    public String getKey(ProceedingJoinPoint joinPoint) {
        //获取目标方法所在类
        String target = joinPoint.getTarget().toString();
        String className = target.split("@")[0];

        //先获取目标方法参数
        String applId = null;
        Object[] args = joinPoint.getArgs();
        if (args != null && args.length > 0) {

        }

        //获取目标方法的方法名称
        String methodName = joinPoint.getSignature().getName();

        //redis中key
        String redisKey = className + "." + methodName;

        return redisKey;
    }

    /**
     * 获取目标方法的返回值类型
     *
     * @param joinPoint
     * @return
     */
    public Class getReturnType(ProceedingJoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        return signature.getReturnType();
    }
}
