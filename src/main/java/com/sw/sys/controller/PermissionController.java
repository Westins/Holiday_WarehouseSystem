package com.sw.sys.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sw.sys.common.*;
import com.sw.sys.pojo.Permission;
import com.sw.sys.service.PermissionService;
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
 * @description: 菜单权限 控制器
 * @author: 单威
 * @time: 2020/2/14 16:40
 */
@RestController
@RequestMapping(value = "/permission")
public class PermissionController {

    /**
     * 菜单权限Service 注入
     */
    @Autowired
    private PermissionService permissionService;

    /**
     * 加载左边树结构参数
     *
     * @return
     */
    @RequestMapping(value = "/loadPermissionLeftTree")
    public DataGridView loadPermissionLeftTree() {
        QueryWrapper<Permission> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("type", Constant.TYPE_MENU);
        List<Permission> list = this.permissionService.list(queryWrapper);
        List<TreeNode> treeNodes = new ArrayList<>();
        for (Permission permission : list) {
            Boolean spread = permission.getOpen() == 1 ? true : false;
            treeNodes.add(new TreeNode(permission.getId(), permission.getPid(), permission.getTitle(), spread));
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

        // 只能查询权限
        wrapper.eq("type", Constant.TYPE_PERMISSION);
        wrapper.like(StringUtils.isNotBlank(permissionVo.getTitle()), "title", permissionVo.getTitle());
        wrapper.like(StringUtils.isNotBlank(permissionVo.getPerCode()), "perCode", permissionVo.getPerCode());
        wrapper.eq(permissionVo.getId() != null, "pid", permissionVo.getId());
        wrapper.orderByAsc("orderNum");

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
            permissionVo.setType(Constant.TYPE_PERMISSION);
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
    @RequestMapping(value = "/loadPermissionMaxOrderNum")
    public Map<String, Object> loadPermissionMaxOrderNum() {
        Map<String, Object> map = new HashMap<>(16);

        QueryWrapper<Permission> wrapper = new QueryWrapper();
        wrapper.orderByDesc("orderNum");
        IPage<Permission> page = new Page<>(1, 1);

        List<Permission> permissionList = this.permissionService.page(page, wrapper).getRecords();
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
            this.permissionService.removeById(permissionVo.getId());
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
            this.permissionService.updateById(permissionVo);
            return ResultObj.UPDATE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.UPDATE_ERROR;
        }
    }
}
