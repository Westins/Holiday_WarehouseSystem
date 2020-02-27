package com.sw.sys.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sw.sys.common.*;
import com.sw.sys.pojo.Permission;
import com.sw.sys.pojo.User;
import com.sw.sys.service.PermissionService;
import com.sw.sys.service.RoleService;
import com.sw.sys.service.UserService;
import com.sw.sys.vo.PermissionVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 * @description: 菜单管理  控制器
 * @author: 单威
 * @time: 2020/2/11 11:54
 */
@RestController
@RequestMapping(value = "/menu")
public class MenuController {

    @Autowired
    private PermissionService permissionService;

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @RequestMapping(value = "/loadIndexLeftMenuJson")
    public DataGridView loadIndexLeftMenuJson(PermissionVo permissionVo) {
        // 查询菜单
        QueryWrapper<Permission> wrapper = new QueryWrapper<>();
        // 查询条件 menu
        wrapper.eq("type", Constant.TYPE_MENU);
        wrapper.eq("available", Constant.AVAILABLE_TRUE);

        User user = (User) WebUtil.getSession().getAttribute("user");
        List<Permission> permissionList = null;
        // 如果登陆者为超级管理员
        if (user.getType().equals(Constant.USER_TYPE_SUPER)) {
            permissionList = permissionService.list(wrapper);
        } else {// 登录者为普通用户
            // 获取用户ID
            Integer uid = user.getId();
            // 获取用户身份
            List<Integer> userRoleList = roleService.queryUserRoleIdsByUid(uid);
            // 根据用户身份 查询对应权限和菜单ID
            Set<Integer> pidSet = new HashSet<>();
            for (Integer rid : userRoleList) {
                List<Integer> pidList = roleService.getRolePermissionByRid(rid);
                pidSet.addAll(pidList);
            }
            // 有权限
            if (pidSet.size() > 0) {
                wrapper.in("id", pidSet);
                permissionList = permissionService.list(wrapper);
            } else {  //没有任何权限
                permissionList = new ArrayList<>();
            }
        }
        List<TreeNode> treeNodeList = new ArrayList<>();

        for (Permission permission : permissionList) {
            Integer id = permission.getId();
            Integer pid = permission.getPid();
            String title = permission.getTitle();
            String icon = permission.getIcon();
            String href = permission.getHref();
            Boolean spread = permission.getOpen().equals(Constant.OPEN_TRUE) ? true : false;
            treeNodeList.add(new TreeNode(id, pid, title, icon, href, spread));
        }
        // 构造 层级关系
        List<TreeNode> treeNodeList2 = TreeNodeBuilder.build(treeNodeList, 1);
        return new DataGridView(treeNodeList2);
    }
    /*********************************************  权限管理 Start ****************************************************/

    /**
     * 加载左边树结构参数
     *
     * @return
     */
    @RequestMapping(value = "/loadMenuLeftTree")
    public DataGridView loadMenuLeftTree() {
        QueryWrapper<Permission> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("type", Constant.TYPE_MENU);
        List<Permission> list = this.permissionService.list(queryWrapper);
        List<TreeNode> treeNodes = new ArrayList<>();
        for (Permission menu : list) {
            Boolean spread = menu.getOpen() == 1 ? true : false;
            treeNodes.add(new TreeNode(menu.getId(), menu.getPid(), menu.getTitle(), spread));
        }
        return new DataGridView(treeNodes);
    }

    /**
     * 加载所有数据 - 查询
     *
     * @param permissionVo
     * @return
     */
    @RequestMapping(value = "/loadModelMsg")
    public DataGridView loadModelMsg(PermissionVo permissionVo) {

        QueryWrapper<Permission> wrapper = new QueryWrapper();

        wrapper.eq(permissionVo.getId() != null, "id", permissionVo.getId())
                .or().eq(permissionVo.getId() != null, "pid", permissionVo.getId());
        wrapper.eq("type", Constant.TYPE_MENU);
        wrapper.like(StringUtils.isNotBlank(permissionVo.getTitle()), "title", permissionVo.getTitle());
        wrapper.orderByAsc("pid");

        IPage<Permission> page = new Page<>(permissionVo.getPage(), permissionVo.getLimit());

        this.permissionService.page(page, wrapper);
        return new DataGridView(page.getTotal(), page.getRecords());
    }

    /**
     * 添加
     *
     * @param permissionVo
     * @return
     */
    @RequestMapping(value = "/savePermission")
    public ResultObj savePermission(PermissionVo permissionVo) {
        try {
            permissionVo.setType(Constant.TYPE_MENU);
            this.permissionService.save(permissionVo);
            return ResultObj.SAVE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.SAVE_ERROR;
        }
    }

    /**
     * 获取最大排序码
     *
     * @return
     */
    @RequestMapping(value = "/loadMenuMaxOrderNum")
    public Map<String, Object> loadMenuMaxOrderNum() {
        Map<String, Object> map = new HashMap<>(16);

        QueryWrapper<Permission> wrapper = new QueryWrapper();
        wrapper.orderByDesc("orderNum");
        IPage<Permission> page = new Page<>(1, 1);

        List<Permission> permissionList = this.permissionService.page(page, wrapper).getRecords();
        System.out.println("ListData:" + permissionList.toString());
        if (permissionList.size() > 0) {
            map.put("value", permissionList.get(0).getOrderNum() + 1);
        } else {
            map.put("value", 1);
        }
        System.out.println("最大排序码:" + map.get("value"));
        return map;
    }

    /**
     * 查询是否有次级部门(子节点)
     *
     * @param permissionVo
     * @return
     */
    @RequestMapping(value = "/queryMenuChildNode")
    public Map<String, Object> queryMenuChildNode(PermissionVo permissionVo) {
        Map<String, Object> map = new HashMap<>(16);
        QueryWrapper<Permission> wrapper = new QueryWrapper();
        wrapper.eq("pid", permissionVo.getId());

        List<Permission> permissionList = this.permissionService.list(wrapper);
        if (permissionList.size() > 0) {
            map.put("value", true);
        } else {
            map.put("value", false);
        }
        return map;
    }

    /**
     * 部门删除
     *
     * @param permissionVo
     * @return
     */
    @RequestMapping(value = "/delMenu")
    public ResultObj delMenu(PermissionVo permissionVo) {
        try {
            this.permissionService.removeById(permissionVo.getId());
            return ResultObj.DELETE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.DELETE_ERROR;
        }
    }

    /**
     * 部门信息修改
     *
     * @param permissionVo
     * @return
     */
    @RequestMapping(value = "/updMenu")
    public ResultObj updMenu(PermissionVo permissionVo) {
        try {
            this.permissionService.updateById(permissionVo);
            return ResultObj.UPDATE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.UPDATE_ERROR;
        }
    }
}