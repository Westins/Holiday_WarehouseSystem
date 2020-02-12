package com.sw.sys.common;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @description: Web工具类
 * @author: sw
 * @time: 2020/2/10 15:11
 */
public class WebUtil {

    /**
     * 获取Request
     * @return request
     */
    public static HttpServletRequest getRequest() {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        return request;
    }

    /**
     * 获取session
     * @return session
     */
    public static HttpSession getSession(){
        return getRequest().getSession();
    }
}
