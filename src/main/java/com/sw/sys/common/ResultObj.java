package com.sw.sys.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @description:  返回结果
 * @author: sw
 * @time: 2020/2/10 14:52
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResultObj {

    /**
     * 登录验证
     */
    public static final ResultObj SUCCESS = new ResultObj(Constant.SUCCESS,"登陆成功！");
    public static final ResultObj ERROR_CODE = new ResultObj(Constant.ERROR,"验证码错误！");
    public static final ResultObj ERROR_INFO = new ResultObj(Constant.ERROR,"账号或密码错误！");

    /**
     * 删除状态码
     */
    public static final ResultObj DELETE_SUCCESS = new ResultObj(Constant.SUCCESS,"删除成功！");
    public static final ResultObj DELETE_ERROR = new ResultObj(Constant.ERROR,"删除失败！");

    private Integer Code;
    private String msg;
}
