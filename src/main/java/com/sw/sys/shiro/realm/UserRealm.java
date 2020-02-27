package com.sw.sys.shiro.realm;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sw.sys.common.ActiveUser;
import com.sw.sys.common.Constant;
import com.sw.sys.pojo.Permission;
import com.sw.sys.pojo.User;
import com.sw.sys.service.PermissionService;
import com.sw.sys.service.RoleService;
import com.sw.sys.service.UserService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @description: shiro 用户验证 用户权限控制
 * @author: 单威
 * @time: 2020/2/10 10:35
 */
public class UserRealm extends AuthorizingRealm {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private PermissionService permissionService;

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
        User user = userService.getOne(queryWrapper);
        if (user != null) {
            ActiveUser activeUser = new ActiveUser();
            activeUser.setUser(user);

            //根据用户ID查询perCode
            //查询所有菜单
            QueryWrapper<Permission> qw = new QueryWrapper<>();
            //设置只能查询菜单
            qw.eq("type", Constant.TYPE_PERMISSION);
            qw.eq("available", Constant.AVAILABLE_TRUE);

            //根据用户ID+角色+权限去查询
            Integer userId = user.getId();
            //根据用户ID查询角色
            List<Integer> currentUserRoleIds = roleService.queryUserRoleIdsByUid(userId);
            //根据角色ID取到权限和菜单ID
            Set<Integer> pidSet = new HashSet<>();
            for (Integer rid : currentUserRoleIds) {
                List<Integer> permissionIds = roleService.getRolePermissionByRid(rid);
                pidSet.addAll(permissionIds);
            }
            List<Permission> list = new ArrayList<>();
            //根据角色ID查询权限
            if (pidSet.size() > 0) {
                qw.in("id", pidSet);
                list = permissionService.list(qw);
            }
            List<String> perCodes = new ArrayList<>();
            for (Permission permission : list) {
                perCodes.add(permission.getPerCode());
            }
            //放到
            activeUser.setPermissions(perCodes);

            ByteSource credentialsSalt = ByteSource.Util.bytes(user.getSalt());
            SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(activeUser, user.getPwd(), credentialsSalt, this.getName());
            return info;
        } else {
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
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        ActiveUser activeUser = (ActiveUser) principalCollection.getPrimaryPrincipal();
        User user = activeUser.getUser();
        List<String> permissions = activeUser.getPermissions();
        if (user.getType().equals(Constant.USER_TYPE_SUPER)) {
            authorizationInfo.addStringPermission("*:*");
        } else {
            if (null != permissions && permissions.size() > 0) {
                authorizationInfo.addStringPermissions(permissions);
            }
        }
        return authorizationInfo;
    }
}
