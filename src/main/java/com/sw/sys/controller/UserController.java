package com.sw.sys.controller;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sw.sys.common.*;
import com.sw.sys.pojo.Dept;
import com.sw.sys.pojo.Role;
import com.sw.sys.pojo.User;
import com.sw.sys.service.DeptService;
import com.sw.sys.service.RoleService;
import com.sw.sys.service.UserService;
import com.sw.sys.vo.UserVo;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description: 用户控制器
 * @author: 单威
 * @time: 2020/2/10 10:15
 */
@RestController
@RequestMapping(value = "/user")
public class UserController {


    /**
     * 用户Service 注入
     */
    @Autowired
    private UserService userService;
    /**
     * 部门Service 注入
     */
    @Autowired
    private DeptService deptService;
    /**
     * 角色Service 注入
     */
    @Autowired
    private RoleService roleService;


    /**
     * 查询全部用户
     * - 条件查询
     *
     * @param userVo
     * @return
     */
    @RequestMapping(value = "/loadAllUser")
    public DataGridView loadAllUser(UserVo userVo) {
        QueryWrapper<User> wrapper = new QueryWrapper();
        wrapper.eq(StringUtils.isNotBlank(userVo.getName()), "loginName", userVo.getName())
                .or().eq(StringUtils.isNotBlank(userVo.getName()), "name", userVo.getName());
        wrapper.eq("type", Constant.USER_TYPE_ORDINARY);
        wrapper.eq(userVo.getDeptId() != null, "deptId", userVo.getDeptId());
        wrapper.eq(StringUtils.isNotBlank(userVo.getName()), "name", userVo.getName());
        wrapper.eq(StringUtils.isNotBlank(userVo.getAddress()), "address", userVo.getAddress());

        IPage<User> page = new Page(userVo.getPage(), userVo.getLimit());
        this.userService.page(page, wrapper);

        List<User> userList = page.getRecords();
        for (User user : userList) {
            Integer deptId = user.getDeptId();
            if (deptId != null) {
                Dept dept = this.deptService.getById(deptId);
                user.setDeptName(dept.getTitle());
            }
            Integer mgr = user.getMgr();
            if (mgr != null) {
                User u = this.userService.getById(mgr);
                user.setLeaderName(u.getName());
            }
        }
        return new DataGridView(page.getTotal(), userList);
    }

    /**
     * 加载最大的排序码
     *
     * @return
     */
    @RequestMapping(value = "/loadUserMaxOrderNum")
    @ResponseBody
    public Map<String, Object> loadUserMaxOrderNum() {
        Map<String, Object> map = new HashMap<>(16);

        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("orderNum");
        IPage<User> page = new Page<>(1, 1);
        List<User> list = this.userService.page(page, queryWrapper).getRecords();
        if (list.size() > 0) {
            map.put("value", list.get(0).getOrderNum() + 1);
        } else {
            map.put("value", 1);
        }
        return map;
    }

    /**
     * 根据部门ID查询用户
     *
     * @param deptId
     * @return
     */
    @RequestMapping(value = "/loadUsersByDeptId")
    public DataGridView loadUsersByDeptId(Integer deptId) {
        System.out.println(deptId == null);
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(deptId != null, "deptId", deptId);
        queryWrapper.eq("available", Constant.AVAILABLE_TRUE);
        queryWrapper.eq("type", Constant.USER_TYPE_ORDINARY);
        List<User> list = this.userService.list(queryWrapper);
        return new DataGridView(list);
    }

    /**
     * 根据用户ID查询一个用户
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/loadUserById")
    public DataGridView loadUserById(Integer id) {
        return new DataGridView(this.userService.getById(id));
    }

    /**
     * 添加用户
     *
     * @param userVo
     * @return
     */
    @RequestMapping(value = "/saveUser")
    public ResultObj saveUser(UserVo userVo) {
        try {
            //设置类型
            userVo.setType(Constant.USER_TYPE_ORDINARY);
            userVo.setHireDate(new Date());
            String salt = IdUtil.simpleUUID().toUpperCase();
            userVo.setSalt(salt);
            //设置密码
            userVo.setPwd(new Md5Hash(Constant.USER_DEFAULT_PWD, salt, 2).toString());
            this.userService.save(userVo);
            return ResultObj.SAVE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.SAVE_ERROR;
        }
    }

    /**
     * 删除用户
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/delUser")
    public ResultObj deleteUser(Integer id) {
        try {
            this.userService.removeById(id);
            return ResultObj.DELETE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.DELETE_ERROR;
        }
    }

    /**
     * 用户信息修改
     *
     * @param userVo
     * @return
     */
    @RequestMapping(value = "/updUser")
    public ResultObj updUser(UserVo userVo) {
        try {
            this.userService.updateById(userVo);
            return ResultObj.UPDATE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.UPDATE_ERROR;
        }
    }

    @RequestMapping(value = "resetPwd")
    public ResultObj resetPwd(Integer id) {
        try {
            User user = new User();
            user.setId(id);
            String salt = IdUtil.simpleUUID().toUpperCase();
            user.setSalt(salt);
            //设置密码
            user.setPwd(new Md5Hash(Constant.USER_DEFAULT_PWD, salt, 2).toString());
            this.userService.updateById(user);
            return ResultObj.UPDATE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.UPDATE_ERROR;
        }
    }

    /**
     * 根据用户ID查询角色并选中已拥有的角色
     *
     * @param id
     * @return
     */
    @RequestMapping("/initRoleByUserId")
    public DataGridView initRoleByUserId(Integer id) {
        //1,查询所有可用的角色
        QueryWrapper<Role> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("available", Constant.AVAILABLE_TRUE);
        List<Map<String, Object>> listMaps = this.roleService.listMaps(queryWrapper);

        //2,查询当前用户拥有的角色ID集合
        List<Integer> currentUserRoleIds = this.roleService.queryUserRoleIdsByUid(id);
        for (Map<String, Object> map : listMaps) {
            Boolean checked = false;

            Integer roleId = (Integer) map.get("id");
            for (Integer rid : currentUserRoleIds) {
                if (rid.equals(roleId)) {
                    checked = true;
                    break;
                }
            }
            map.put("LAY_CHECKED", checked);
        }
        return new DataGridView(Long.valueOf(listMaps.size()), listMaps);
    }

    /**
     * 保存用户和角色的关系
     *
     * @param uid
     * @param ids
     * @return
     */
    @RequestMapping("saveUserRole")
    public ResultObj saveUserRole(Integer uid, Integer[] ids) {
        try {
            this.userService.saveUserRole(uid, ids);
            return ResultObj.SAVE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.SAVE_ERROR;
        }

    }
}

