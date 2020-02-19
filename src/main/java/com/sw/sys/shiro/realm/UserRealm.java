package com.sw.sys.shiro.realm;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sw.sys.common.ActiveUser;
import com.sw.sys.pojo.User;
import com.sw.sys.service.impl.UserServiceImpl;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @description:
 * @author: sw
 * @time: 2020/2/10 10:35
 */
public class UserRealm extends AuthorizingRealm {

    @Autowired
    private UserServiceImpl userServiceImpl;

    @Override
    public String getName() {
        return this.getClass().getSimpleName();
    }

    /**
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     * @description: 认证
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        // 获取登陆账号
        queryWrapper.eq("loginName", authenticationToken.getPrincipal().toString());
        // 查询账号是否存在
        User user = userServiceImpl.getOne(queryWrapper);
        if (user != null) {
            ActiveUser activeUser = new ActiveUser();
            // 登陆者记录到action
            activeUser.setUser(user);
            ByteSource credentialsSalt = ByteSource.Util.bytes(user.getSalt());
            SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(activeUser, user.getPwd(), credentialsSalt, this.getName());
            return info;
        }else {
            return null;
        }
    }

    /**
     * @param principalCollection
     * @return
     * @description: 授权
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }
}
