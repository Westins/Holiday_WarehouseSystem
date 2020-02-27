package com.sw.sys.redis;

import com.alibaba.fastjson.JSONObject;
import com.sw.sys.pojo.Dept;
import com.sw.sys.pojo.User;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;


/**
 * @description: redis 切面
 * @author: 单威
 * @time: 2020/2/14 15:15
 */
@Component
@Aspect
@EnableAspectJAutoProxy
public class RedisAspect {

    private static final Logger log = LoggerFactory.getLogger(RedisAspect.class);

    @Autowired
    private RedisUtil redisUtil;

    /**
     * 环绕处理，先从Redis里获取缓存,查询不到，就查询MySQL数据库，
     * 然后再保存到Redis缓存里
     */


    /**
     * 声明切面表达式
     */
    private static final String POINTCUT_DEPT_ADD = "execution(* com.sw.sys.service.impl.DeptServiceImpl.save(..))";
    private static final String POINTCUT_DEPT_UPDATE = "execution(* com.sw.sys.service.impl.DeptServiceImpl.updateById(..))";
    private static final String POINTCUT_DEPT_GET = "execution(* com.sw.sys.service.impl.DeptServiceImpl.getById(..))";
    private static final String POINTCUT_DEPT_DELETE = "execution(* com.sw.sys.service.impl.DeptServiceImpl.removeById(..))";

    private static final String CACHE_DEPT_PROFIX = "DEPT:";

    /**
     * 部门添加切入
     *
     * @throws Throwable
     */
    @Around(value = POINTCUT_DEPT_ADD)
    public Object cacheDeptAdd(ProceedingJoinPoint joinPoint) throws Throwable {
        // 取出第一个参数
        Dept object = (Dept) joinPoint.getArgs()[0];
        Boolean res = (Boolean) joinPoint.proceed();
        if (res) {
            redisUtil.set(CACHE_DEPT_PROFIX + object.getId(), object);
        }
        return res;
    }

    /**
     * 查询切入
     *
     * @throws Throwable
     */
    @Around(value = POINTCUT_DEPT_GET)
    public Object cacheDeptGet(ProceedingJoinPoint joinPoint) throws Throwable {
        // 取出第一个参数
        Integer object = (Integer) joinPoint.getArgs()[0];
        // 从缓存里面取
        Object obj = redisUtil.get(CACHE_DEPT_PROFIX + object);
        if (obj != null) {
            log.info("已从缓存里面找到部门对象" + CACHE_DEPT_PROFIX + object);
            return obj;
        } else {
            obj = joinPoint.proceed();
            redisUtil.set(CACHE_DEPT_PROFIX + object, obj);
            log.info("未从缓存里面找到部门对象，去数据库查询并放到缓存" + CACHE_DEPT_PROFIX + object);
            return obj;
        }
    }

    /**
     * 更新切入
     *
     * @throws Throwable
     */
    @Around(value = POINTCUT_DEPT_UPDATE)
    public Object cacheDeptUpdate(ProceedingJoinPoint joinPoint) throws Throwable {
        // 取出第一个参数
        Dept deptVo = (Dept) joinPoint.getArgs()[0];
        Boolean isSuccess = (Boolean) joinPoint.proceed();
        if (isSuccess) {
            Dept dept = (Dept) redisUtil.get(CACHE_DEPT_PROFIX + deptVo.getId());
            if (null == dept) {
                dept = new Dept();
            }
            BeanUtils.copyProperties(deptVo, dept);
            log.info("部门对象缓存已更新" + CACHE_DEPT_PROFIX + deptVo.getId());
            redisUtil.set(CACHE_DEPT_PROFIX + dept.getId(), dept);
        }
        return isSuccess;
    }

    /**
     * 删除切入
     *
     * @throws Throwable
     */
    @Around(value = POINTCUT_DEPT_DELETE)
    public Object cacheDeptDelete(ProceedingJoinPoint joinPoint) throws Throwable {
        // 取出第一个参数
        Integer id = (Integer) joinPoint.getArgs()[0];
        Boolean isSuccess = (Boolean) joinPoint.proceed();
        if (isSuccess) {
            // 删除缓存 
            redisUtil.del(CACHE_DEPT_PROFIX + id);
            log.info("部门对象缓存已删除" + CACHE_DEPT_PROFIX + id);
        }
        return isSuccess;
    }

    /**
     * 生成切面表达式
     */
    private static final String POINTCUT_USER_UPDATE = "execution(* com.sw.sys.service.impl.UserServiceImpl.updateById(..))";
    private static final String POINTCUT_USER_ADD = "execution(* com.sw.sys.service.impl.UserServiceImpl.save(..))";
    private static final String POINTCUT_USER_GET = "execution(* com.sw.sys.service.impl.UserServiceImpl.getById(..))";
    private static final String POINTCUT_USER_DELETE = "execution(* com.sw.sys.service.impl.UserServiceImpl.removeById(..))";

    private static final String CACHE_USER_PROFIX = "USER:";

    /**
     * 用户添加切入
     *
     * @throws Throwable
     */
    @Around(value = POINTCUT_USER_ADD)
    public Object cacheUserAdd(ProceedingJoinPoint joinPoint) throws Throwable {
        // 取出第一个参数
        User object = (User) joinPoint.getArgs()[0];
        Boolean res = (Boolean) joinPoint.proceed();
        if (res) {
            redisUtil.set(CACHE_USER_PROFIX + object.getId(), object);
        }
        return res;
    }

    /**
     * 查询切入
     *
     * @throws Throwable
     */
    @Around(value = POINTCUT_USER_GET)
    public Object cacheUserGet(ProceedingJoinPoint joinPoint) throws Throwable {
        // 取出第一个参数
        Integer object = (Integer) joinPoint.getArgs()[0];
        // 从缓存里面取
        Object res1 = redisUtil.get(CACHE_USER_PROFIX + object);
        if (res1 != null) {
            log.info("已从缓存里面找到用户对象" + CACHE_USER_PROFIX + object);
            return res1;
        } else {
            User res2 = (User) joinPoint.proceed();
            redisUtil.set(CACHE_USER_PROFIX + res2.getId(), res2);
            log.info("未从缓存里面找到用户对象，去数据库查询并放到缓存" + CACHE_USER_PROFIX + res2.getId());
            return res2;
        }
    }

    /**
     * 更新切入
     *
     * @throws Throwable
     */
    @Around(value = POINTCUT_USER_UPDATE)
    public Object cacheUserUpdate(ProceedingJoinPoint joinPoint) throws Throwable {
        // 取出第一个参数
        User userVo = (User) joinPoint.getArgs()[0];
        Boolean isSuccess = (Boolean) joinPoint.proceed();
        if (isSuccess) {
            User user = (User) redisUtil.get(CACHE_USER_PROFIX + userVo.getId());
            if (null == user) {
                user = new User();
            }
            BeanUtils.copyProperties(userVo, user);
            log.info("用户对象缓存已更新" + CACHE_USER_PROFIX + userVo.getId());
            redisUtil.set(CACHE_USER_PROFIX + user.getId(), user);
        }
        return isSuccess;
    }

    /**
     * 删除切入
     *
     * @throws Throwable
     */
    @Around(value = POINTCUT_USER_DELETE)
    public Object cacheUserDelete(ProceedingJoinPoint joinPoint) throws Throwable {
        // 取出第一个参数
        Integer id = (Integer) joinPoint.getArgs()[0];
        Boolean isSuccess = (Boolean) joinPoint.proceed();
        if (isSuccess) {
            // 删除缓存
            redisUtil.del(CACHE_USER_PROFIX + id);
            log.info("用户对象缓存已删除" + CACHE_USER_PROFIX + id);
        }
        return isSuccess;
    }

}
