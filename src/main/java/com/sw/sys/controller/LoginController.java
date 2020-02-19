package com.sw.sys.controller;

import com.sw.sys.common.ActiveUser;
import com.sw.sys.common.ResultObj;
import com.sw.sys.common.WebUtil;
import com.sw.sys.pojo.LogInfo;
import com.sw.sys.service.LogInfoService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

/**
 * @description: 用户登录控制器
 * @author: 单威
 * @time: 2020/2/18 19:48
 */
@Controller
@RequestMapping(value = "/login")
public class LoginController {


    /**
     * 登录接口注入
     */
    @Autowired
    private LogInfoService logInfoService;

    /**
     * 用户登录
     *
     * @param loginName
     * @param loginPassword
     * @return
     */
    @RequestMapping(value = "/login")
    @ResponseBody
    public ResultObj login(String loginName, String loginPassword) {
        // 获取subject
        Subject subject = SecurityUtils.getSubject();
        // 将账号密码封装到token
        AuthenticationToken token = new UsernamePasswordToken(loginName, loginPassword);
        try {
            // 登陆验证
            subject.login(token);
            // 登陆者记录到action
            ActiveUser activeUser = (ActiveUser) subject.getPrincipal();
            // 登陆者放入Session
            WebUtil.getSession().setAttribute("user", activeUser.getUser());

            // 记录到登录日志
            LogInfo logInfo = new LogInfo();
            logInfo.setLoginName(activeUser.getUser().getName() + "-" + activeUser.getUser().getLoginName());
            logInfo.setLoginIp(WebUtil.getIpAddr());
            logInfo.setLoginTime(new Date());
            logInfoService.save(logInfo);

            return ResultObj.LOGIN_SUCCESS;
        } catch (UnknownAccountException e) {
            // 没有此账号 抛出异常
            return ResultObj.LOGIN_ERROR_ID_NOT_EXIST;
        } catch (AuthenticationException e) {
            // 密码不正确 抛出异常
            return ResultObj.LOGIN_ERROR_PASSWORD;
        }
    }

    /**
     * 用户登出
     *
     * @return
     */
    @RequestMapping(value = "/logout")
    public String logout() {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return "redirect:/sys/login";
    }
}
