package com.sw.sys.common;

/**
 * @description: 常量类
 * @author: 单威
 * @time: 2020/2/10 14:56
 */

public interface Constant {

    /**
     * 状态码
     */
    public static final Integer SUCCESS = 1;
    public static final Integer ERROR = -1;

    /**
     * 菜单权限类型
     */
    public static final String TYPE_MENU = "menu";
    public static final String TYPE_PERMISSION = "permission";

    /**
     * 状态是否可用
     * 1:可用  0:不可用
     */
    public static final Object AVAILABLE_TRUE = 1;
    public static final Object AVAILABLE_FALSE = 0;

    /**
     * 用户类型
     * 0:超级管理员  1:普通用户
     */
    public static final Integer USER_TYPE_SUPER = 0;
    public static final Integer USER_TYPE_ORDINARY = 1;

    /**
     * 展开类型
     * 展开:1   收起:0
     */
    public static final Integer OPEN_TRUE = 1;
    public static final Integer OPEN_FALSE = 0;

    /**
     * 用户默认密码
     */
    public static final String USER_DEFAULT_PWD = "123456";
}
