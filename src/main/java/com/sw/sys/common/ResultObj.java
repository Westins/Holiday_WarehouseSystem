package com.sw.sys.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @description:  返回结果
 * @author: 单威
 * @time: 2020/2/10 14:52
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResultObj {

    /**
     * 登录验证
     */
    public static final ResultObj LOGIN_SUCCESS = new ResultObj(Constant.SUCCESS,"登陆成功！");
    public static final ResultObj LOGIN_ERROR_CODE = new ResultObj(Constant.ERROR,"验证码错误！");
    public static final ResultObj LOGIN_ERROR_ID_NOT_EXIST = new ResultObj(Constant.ERROR,"账号不存在！");
    public static final ResultObj LOGIN_ERROR_PASSWORD = new ResultObj(Constant.ERROR,"密码错误！");

    /**
     * 删除状态码
     */
    public static final ResultObj DELETE_SUCCESS = new ResultObj(Constant.SUCCESS,"删除成功！");
    public static final ResultObj DELETE_ERROR = new ResultObj(Constant.ERROR,"删除失败！");

    /**
     * 添加状态码
     */
    public static final ResultObj SAVE_SUCCESS = new ResultObj(Constant.SUCCESS,"添加成功！");
    public static final ResultObj SAVE_ERROR = new ResultObj(Constant.ERROR,"添加失败！");

    /**
     * 修改状态码
     */
    public static final ResultObj UPDATE_SUCCESS = new ResultObj(Constant.SUCCESS,"修改成功！");
    public static final ResultObj UPDATE_ERROR = new ResultObj(Constant.ERROR,"修改失败！");

    private Integer Code;
    private String msg;
}
