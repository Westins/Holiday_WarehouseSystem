package com.sw.sys.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sw.sys.common.Constant;
import com.sw.sys.common.DataGridView;
import com.sw.sys.common.ResultObj;
import com.sw.sys.common.TreeNode;
import com.sw.sys.pojo.Permission;
import com.sw.sys.pojo.Role;
import com.sw.sys.service.PermissionService;
import com.sw.sys.service.RoleService;
import com.sw.sys.vo.RoleVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Date;

/**
 * @description: 角色 控制器
 * @author: 单威
 * @time: 2020/2/16 14:56
 */
@RestController
@RequestMapping(value = "/role")
public class RoleController {

    /**
     * 角色Service 注入
     */
    @Autowired
    private RoleService roleService;

    /**
     * 菜单权限Service 注入
     */
    @Autowired
    private PermissionService permissionService;


    /**
     * 加载全部可用角色 --查询
     *
     * @param roleVo
     * @return
     */
    @RequestMapping(value = "/loadAllRole")
    public DataGridView loadAllLogInfo(RoleVo roleVo) {
        QueryWrapper<Role> wrapper = new QueryWrapper<>();

        wrapper.like(StringUtils.isNotBlank(roleVo.getName()), "name", roleVo.getName());
        wrapper.like(StringUtils.isNotBlank(roleVo.getRemark()), "remark", roleVo.getRemark());
        wrapper.eq(roleVo.getAvailable() != null, "available", roleVo.getAvailable());

        IPage<Role> page = new Page<>(roleVo.getPage(), roleVo.getLimit());


        this.roleService.page(page, wrapper);
        return new DataGridView(page.getTotal(), page.getRecords());
    }

    /**
     * 添加角色
     *
     * @param roleVo
     * @return
     */
    @RequestMapping(value = "/saveRole")
    public ResultObj saveRole(RoleVo roleVo) {
        try {
            roleVo.setCreateTime(new Date());
            this.roleService.save(roleVo);
            return ResultObj.SAVE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.SAVE_ERROR;
        }

    }

    /**
     * 修改角色
     *
     * @param roleVo
     * @return
     */
    @RequestMapping(value = "/updRole")
    public ResultObj updRole(RoleVo roleVo) {
        try {
            this.roleService.updateById(roleVo);
            return ResultObj.UPDATE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.UPDATE_ERROR;
        }
    }

    /**
     * 删除角色
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/delRole")
    public ResultObj delRole(Integer id) {
        try {
            this.roleService.removeById(id);
            return ResultObj.DELETE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.DELETE_ERROR;
        }
    }

    /**
     * 根据角色 加载权力树
     *
     * @param roleId
     * @return
     */
    @RequestMapping(value = "/loadRolePermissionTreeByRoleId")
    public DataGridView loadRolePermissionTree(Integer roleId) {
        /**
         * 查询可用的菜单和权限
         */
        QueryWrapper<Permission> wrapper = new QueryWrapper();
        wrapper.eq("available", Constant.AVAILABLE_TRUE);
        List<Permission> allPermissionList = this.permissionService.list(wrapper);
        /**
         * 1.根据角色ID查询当前角色拥有的权限或菜单ID
         * 2.根据查询出来的菜单ID查询权限和菜单数据
         */
        List<Integer> rolePermissionList = this.roleService.getRolePermissionByRid(roleId);
        List<Permission> PermissionList = null;
        if (rolePermissionList.size() > 0) { // 如果有ID才查
            wrapper.in("id", rolePermissionList);
            PermissionList = this.permissionService.list(wrapper);
        } else {
            PermissionList = new ArrayList<>();
        }

        /**
         * 构造权力树
         */
        List<TreeNode> nodeList = new ArrayList<>();
        System.out.println("长度:" + PermissionList.size());
        for (Permission p1 : allPermissionList) {
            String check = "0";
            for (Permission p2 : PermissionList) {
                if (p1.getId() == p2.getId()) {
                    check = "1";
                }
            }
            Boolean spread = (p1.getOpen() == null || p1.getOpen() == 1) ? true : false;
            nodeList.add(new TreeNode(p1.getId(), p1.getPid(), p1.getTitle(), spread, check));
        }
        return new DataGridView(nodeList);
    }

    /**
     * 权限分配
     *
     * @param rid
     * @param ids
     * @return
     */
    @RequestMapping(value = "/saveRolePermission")
    public ResultObj saveRolePermission(Integer rid, Integer[] ids) {
        try {
            this.roleService.saveRolePermission(rid, ids);
            return ResultObj.SAVE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.SAVE_ERROR;
        }
    }
}
