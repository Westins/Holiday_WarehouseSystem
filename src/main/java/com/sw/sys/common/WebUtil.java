package com.sw.sys.common;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @description: Web工具类
 * @author: 单威
 * @time: 2020/2/10 15:11
 */
public class WebUtil {

    /**
     * ip 地址未知 常量
     */
    private static final String IP_UNKNOWN = "unknown";

    /**
     * 本地IP 常量
     */
    private static final String IP_LOCAL = "127.0.0.1";
    private static final String IP_LOCAL_TWO = "0:0:0:0:0:0:0:1";

    /**
     * 分隔符
     */
    private static final String DELIMITER = ",";

    /**
     * 循环
     */
    private static final Integer MATH = 15;

    /**
     * 获取Request
     *
     * @return request
     */
    public static HttpServletRequest getRequest() {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        return request;
    }

    /**
     * 获取session
     *
     * @return session
     */
    public static HttpSession getSession() {
        return getRequest().getSession();
    }

    /**
     * 获取当前客户端 真实IP
     *
     * @return
     */
    public static String getIpAddr() {
        HttpServletRequest request = getRequest();
        String ipAddress = request.getHeader("x-forwarded-for");
        if (ipAddress == null || ipAddress.length() == 0 || IP_UNKNOWN.equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("Proxy-Client-IP");
        }
        if (ipAddress == null || ipAddress.length() == 0 || IP_UNKNOWN.equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ipAddress == null || ipAddress.length() == 0 || IP_UNKNOWN.equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getRemoteAddr();
            if (IP_LOCAL.equals(ipAddress) || IP_LOCAL_TWO.equals(ipAddress)) {
                //根据网卡取本机配置的IP
                InetAddress inet = null;
                try {
                    inet = InetAddress.getLocalHost();
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                }
                ipAddress = inet.getHostAddress();
            }
        }
        //对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
        if (ipAddress != null && ipAddress.length() > MATH) {
            if (ipAddress.indexOf(DELIMITER) > 0) {
                ipAddress = ipAddress.substring(0, ipAddress.indexOf(DELIMITER));
            }
        }
        return ipAddress;
    }
}
