package com.sw.sys.controller;

import com.sw.sys.common.ActiveUser;
import com.sw.sys.common.ResultObj;
import com.sw.sys.common.WebUtil;
import com.sw.sys.pojo.LogInfo;
import com.sw.sys.service.impl.LogInfoServiceImpl;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * @description: 用户控制器
 * @author: sw
 * @time: 2020/2/10 10:15
 */
@Controller
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    private LogInfoServiceImpl logInfoServiceImpl;

    @PostMapping(value = "/login")
    @ResponseBody
    public ResultObj login(String loginName,String loginPassword){
        Subject subject = SecurityUtils.getSubject();
        AuthenticationToken token=new UsernamePasswordToken(loginName, loginPassword);
        try {
            subject.login(token);
            ActiveUser activeUser = (ActiveUser) subject.getPrincipal();
            WebUtil.getSession().setAttribute("user",activeUser.getUser());

            // 记录到登录日志
            LogInfo logInfo = new LogInfo();
            logInfo.setLoginName(activeUser.getUser().getName()+"-"+activeUser.getUser().getLoginName());
            logInfo.setLoginIp(WebUtil.getIpAddr());
            logInfo.setLoginTime(new Date());
            logInfoServiceImpl.save(logInfo);

            return ResultObj.LOGIN_SUCCESS;
        } catch (UnknownAccountException e){
            return ResultObj.LOGIN_ERROR_INFO;
        } catch (AuthenticationException e){
            return ResultObj.LOGIN_ERROR_INFO;
        }
    }

    @RequestMapping(value = "/logout")
    public String logout(){
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return "redirect:/sys/login";
    }
}
