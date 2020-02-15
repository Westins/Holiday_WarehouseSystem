package com.sw.sys.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sw.sys.common.*;
import com.sw.sys.pojo.Dept;
import com.sw.sys.pojo.Permission;
import com.sw.sys.pojo.User;
import com.sw.sys.service.impl.PermissionServiceImpl;
import com.sw.sys.vo.DeptVo;
import com.sw.sys.vo.PermissionVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @RequestMapping(value = "/loadIndexLeftMenuJson")
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
    /*********************************************  菜单管理 Start ****************************************************/ 

    /**
     * 加载左边树结构参数
     *
     * @param permissionVo
     * @return
     */
    @RequestMapping(value = "/loadMenuLeftTree")
    public DataGridView loadMenuLeftTree(PermissionVo permissionVo) {
        QueryWrapper<Permission> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("type", Constant.TYPE_MENU);
        List<Permission> list = this.permissionServiceImpl.list(queryWrapper);
        List<TreeNode> treeNodes=new ArrayList<>();
        for (Permission menu : list) {
            Boolean spread=menu.getOpen()==1?true:false;
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

        wrapper.like(StringUtils.isNotBlank(permissionVo.getTitle()), "title", permissionVo.getTitle());

        wrapper.eq(permissionVo.getId() != null, "id", permissionVo.getId())
                .or().eq(permissionVo.getId() != null, "pid", permissionVo.getId());
        wrapper.orderByAsc("pid");

        IPage<Permission> page = new Page<>(permissionVo.getPage(), permissionVo.getLimit());

        this.permissionServiceImpl.page(page, wrapper);
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
            this.permissionServiceImpl.save(permissionVo);
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
        Map<String, Object> map = new HashMap<>();

        QueryWrapper<Permission> wrapper = new QueryWrapper();
        wrapper.orderByDesc("orderNum");
        IPage<Permission> page = new Page<>(1, 1);

        List<Permission> permissionList = this.permissionServiceImpl.page(page, wrapper).getRecords();
        System.out.println("ListData:"+permissionList.toString());
        if (permissionList.size() > 0) {
            map.put("value", permissionList.get(0).getOrderNum() + 1);
        } else {
            map.put("value", 1);
        }
        System.out.println("最大排序码:"+map.get("value"));
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
        Map<String, Object> map = new HashMap<>();
        QueryWrapper<Permission> wrapper = new QueryWrapper();
        wrapper.eq("pid", permissionVo.getId());

        List<Permission> permissionList = this.permissionServiceImpl.list(wrapper);
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
            this.permissionServiceImpl.removeById(permissionVo.getId());
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
            this.permissionServiceImpl.updateById(permissionVo);
            return ResultObj.UPDATE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.UPDATE_ERROR;
        }
    }
}