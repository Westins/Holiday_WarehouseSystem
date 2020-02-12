package com.sw.sys.controller;

import com.sw.sys.common.ActiveUser;
import com.sw.sys.common.ResultObj;
import com.sw.sys.common.WebUtil;
import com.sw.sys.pojo.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * @description: 用户控制器
 * @author: sw
 * @time: 2020/2/10 10:15
 */
@Controller
@RequestMapping(value = "user")
public class UserController {

    @PostMapping(value = "login")
    @ResponseBody
    public ResultObj login(String loginName,String loginPassword,HttpSession session){
        Subject subject = SecurityUtils.getSubject();
        AuthenticationToken token=new UsernamePasswordToken(loginName, loginPassword);
        try {
            subject.login(token);
            ActiveUser activeUser = (ActiveUser) subject.getPrincipal();
            WebUtil.getSession().setAttribute("user",activeUser.getUser());
            return ResultObj.SUCCESS;
        } catch (UnknownAccountException e){
            return ResultObj.ERROR_INFO;
        } catch (AuthenticationException e){
            return ResultObj.ERROR_INFO;
        }
    }
}
