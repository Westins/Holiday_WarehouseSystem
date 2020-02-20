package com.sw.sys.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sw.sys.common.DataGridView;
import com.sw.sys.common.ResultObj;
import com.sw.sys.common.TreeNode;
import com.sw.sys.pojo.Dept;
import com.sw.sys.service.DeptService;
import com.sw.sys.vo.DeptVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description: 部门管理 控制器
 * @author: 单威
 * @time: 2020/2/14 10:30
 */
@RestController
@RequestMapping(value = "/dept")
public class DeptController {

    /**
     * 部门Service 注入
     */
    @Autowired
    private DeptService deptService;

    /**
     * 加载左边树结构参数
     *
     * @return
     */
    @RequestMapping(value = "/loadLeftTree")
    public DataGridView loadLeftTree() {
        List<Dept> deptList = this.deptService.list();
        List<TreeNode> treeNodeList = new ArrayList<>();
        for (Dept dept : deptList) {
            Boolean spread = dept.getOpen() == 1 ? true : false;
            treeNodeList.add(new TreeNode(dept.getId(), dept.getPid(), dept.getTitle(), spread));
        }
        return new DataGridView(treeNodeList);
    }

    /**
     * 加载所有数据 - 查询
     *
     * @param deptVo
     * @return
     */
    @RequestMapping(value = "/loadRightMsg")
    public DataGridView loadRightMsg(DeptVo deptVo) {

        QueryWrapper<Dept> wrapper = new QueryWrapper();

        wrapper.like(StringUtils.isNotBlank(deptVo.getTitle()), "title", deptVo.getTitle());
        wrapper.like(StringUtils.isNotBlank(deptVo.getRemark()), "remark", deptVo.getRemark());
        wrapper.like(StringUtils.isNotBlank(deptVo.getAddress()), "address", deptVo.getAddress());

        wrapper.eq(deptVo.getId() != null, "id", deptVo.getId())
                .or().eq(deptVo.getId() != null, "pid", deptVo.getId());
        wrapper.orderByAsc("pid");

        IPage<Dept> page = new Page<>(deptVo.getPage(), deptVo.getLimit());
        this.deptService.page(page, wrapper);
        return new DataGridView(page.getTotal(), page.getRecords());
    }

    /**
     * 添加
     *
     * @param deptVo
     * @return
     */
    @RequestMapping(value = "/saveDept")
    public ResultObj saveDept(DeptVo deptVo) {
        try {
            this.deptService.save(deptVo);
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
    @RequestMapping(value = "/loadMaxOrderNum")
    public Map<String, Object> loadMaxOrderNum() {
        Map<String, Object> map = new HashMap<String, Object>();

        QueryWrapper<Dept> wrapper = new QueryWrapper();
        wrapper.orderByAsc("orderNum");
        IPage<Dept> page = new Page<>(1, 1);

        List<Dept> deptList = this.deptService.page(page, wrapper).getRecords();
        if (deptList.size() > 0) {
            map.put("value", deptList.get(0).getOrderNum() + 1);
        } else {
            map.put("value", 1);
        }
        return map;
    }

    /**
     * 查询是否有次级部门(子节点)
     *
     * @param deptVo
     * @return
     */
    @RequestMapping(value = "/queryChildNode")
    public Map<String, Object> queryChildNode(DeptVo deptVo) {
        Map<String, Object> map = new HashMap<>();
        QueryWrapper<Dept> wrapper = new QueryWrapper();
        wrapper.eq("pid", deptVo.getId());

        List<Dept> deptList = this.deptService.list(wrapper);
        if (deptList.size() > 0) {
            map.put("value", true);
        } else {
            map.put("value", false);
        }
        return map;
    }

    /**
     * 部门删除
     *
     * @param deptVo
     * @return
     */
    @RequestMapping(value = "/delDept")
    public ResultObj delDept(DeptVo deptVo) {
        try {
            this.deptService.removeById(deptVo.getId());
            return ResultObj.DELETE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.DELETE_ERROR;
        }
    }

    /**
     * 部门信息修改
     *
     * @param deptVo
     * @return
     */
    @RequestMapping(value = "/updDept")
    public ResultObj updDept(DeptVo deptVo) {
        try {
            this.deptService.updateById(deptVo);
            return ResultObj.UPDATE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.UPDATE_ERROR;
        }
    }
}
