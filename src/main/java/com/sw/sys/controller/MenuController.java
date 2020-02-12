package com.sw.sys.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sw.sys.common.*;
import com.sw.sys.pojo.Permission;
import com.sw.sys.pojo.User;
import com.sw.sys.service.impl.PermissionServiceImpl;
import com.sw.sys.vo.PermissionVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @description: 菜单管理前端控制器
 * @author: sw
 * @time: 2020/2/11 11:54
 */
@RestController
@RequestMapping(value = "/menu")
public class MenuController {

    @Autowired
    private PermissionServiceImpl permissionServiceImpl;

    @RequestMapping(value = "loadIndexLeftMenuJson")
    public DataGridView loadIndexLeftMenuJson(PermissionVo permissionVo) {
        // 查询菜单
        QueryWrapper<Permission> wrapper = new QueryWrapper<>();
        // 查询条件 menu
        wrapper.eq("type", Constant.TYPE_MENU);
        wrapper.eq("available", Constant.AVAILABLE_TRUE);

        User user = (User) WebUtil.getSession().getAttribute("user");
        List<Permission> permissionList = null;
        if (user.getType() == Constant.USER_TYPE_SUPER) { // 如果登陆者为超级管理员
            permissionList = permissionServiceImpl.list(wrapper);
        } else {                                          // 登录者为普通用户
            permissionList = permissionServiceImpl.list(wrapper);
        }
        List<TreeNode> treeNodeList = new ArrayList<TreeNode>();

        for (Permission permission : permissionList) {
            Integer id = permission.getId();
            Integer pid = permission.getPid();
            String title = permission.getTitle();
            String icon = permission.getIcon();
            String href = permission.getHref();
            Boolean spread = permission.getOpen() == Constant.OPEN_TRUE ? true : false;
            treeNodeList.add(new TreeNode(id, pid, title, icon, href, spread));
        }
        // 构造 层级关系
        List<TreeNode> treeNodeList2 =  TreeNodeBuilder.build(treeNodeList,1);
        return new DataGridView(treeNodeList2);
    }
}