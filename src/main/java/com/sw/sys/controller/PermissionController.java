package com.sw.sys.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sw.sys.common.*;
import com.sw.sys.pojo.Permission;
import com.sw.sys.service.impl.PermissionServiceImpl;
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
 * @description:
 * @author: sw
 * @time: 2020/2/14 16:40
 */
@RestController
@RequestMapping(value = "/permission")
public class PermissionController {
    @Autowired
    private PermissionServiceImpl permissionServiceImpl;

    /**
     * 加载左边树结构参数
     *
     * @param permissionVo
     * @return
     */
    @RequestMapping(value = "/loadPermissionLeftTree")
    public DataGridView loadPermissionLeftTree(PermissionVo permissionVo) {
        QueryWrapper<Permission> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("type", Constant.TYPE_MENU);
        List<Permission> list = this.permissionServiceImpl.list(queryWrapper);
        List<TreeNode> treeNodes = new ArrayList<>();
        for (Permission Permission : list) {
            Boolean spread = Permission.getOpen() == 1 ? true : false;
            treeNodes.add(new TreeNode(Permission.getId(), Permission.getPid(), Permission.getTitle(), spread));
        }
        return new DataGridView(treeNodes);
    }

    /**
     * 加载所有数据 - 查询
     *
     * @param permissionVo
     * @return
     */
    @RequestMapping(value = "/loadPermissionMsg")
    public DataGridView loadModelMsg(PermissionVo permissionVo) {

        QueryWrapper<Permission> wrapper = new QueryWrapper();

        wrapper.eq("type",Constant.TYPE_PERMISSION); // 只能查询权限
        wrapper.like(StringUtils.isNotBlank(permissionVo.getTitle()), "title", permissionVo.getTitle());
        wrapper.like(StringUtils.isNotBlank(permissionVo.getPerCode()), "perCode", permissionVo.getPerCode());
        wrapper.eq(permissionVo.getId() != null, "pid", permissionVo.getId());
        wrapper.orderByAsc("orderNum");

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
    @RequestMapping(value = "/loadPermissionMaxOrderNum")
    public Map<String, Object> loadPermissionMaxOrderNum() {
        Map<String, Object> map = new HashMap<>();

        QueryWrapper<Permission> wrapper = new QueryWrapper();
        wrapper.orderByDesc("orderNum");
        IPage<Permission> page = new Page<>(1, 1);

        List<Permission> permissionList = this.permissionServiceImpl.page(page, wrapper).getRecords();
        System.out.println("ListData:" + permissionList.toString());
        if (permissionList.size() > 0) {
            map.put("value", permissionList.get(0).getOrderNum() + 1);
        } else {
            map.put("value", 1);
        }
        return map;
    }

    /**
     * 权限删除
     *
     * @param permissionVo
     * @return
     */
    @RequestMapping(value = "/delPermission")
    public ResultObj delPermission(PermissionVo permissionVo) {
        try {
            this.permissionServiceImpl.removeById(permissionVo.getId());
            return ResultObj.DELETE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.DELETE_ERROR;
        }
    }

    /**
     * 权限信息修改
     *
     * @param permissionVo
     * @return
     */
    @RequestMapping(value = "/updPermission")
    public ResultObj updPermission(PermissionVo permissionVo) {
        try {
            this.permissionServiceImpl.updateById(permissionVo);
            return ResultObj.UPDATE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.UPDATE_ERROR;
        }
    }
}
