package com.sw.sys.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sw.sys.common.DataGridView;
import com.sw.sys.common.ResultObj;
import com.sw.sys.pojo.LogInfo;
import com.sw.sys.service.LogInfoService;
import com.sw.sys.vo.LogInfoVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

/**
 * @description: 登录日志 控制器
 * @author: 单威
 * @time: 2020/2/11 16:57
 */
@RestController
@RequestMapping(value = "/LogInfo")
public class LogInfoController {

    /**
     * 登录日志接口 注入
     */
    @Autowired
    private LogInfoService LogInfoService;

    /**
     * 加载所有 登录日志信息 and 查询
     *
     * @param logInfoVo
     * @return
     */
    @RequestMapping(value = "/loadAllLogInfo")
    public DataGridView loadAllLogInfo(LogInfoVo logInfoVo) {
        QueryWrapper<LogInfo> wrapper = new QueryWrapper<>();
        // 模糊查询 登录名
        wrapper.like(StringUtils.isNotBlank(logInfoVo.getLoginName()), "loginName", logInfoVo.getLoginName());
        // 模糊查询 登录IP地址
        wrapper.like(StringUtils.isNotBlank(logInfoVo.getLoginName()), "loginIp", logInfoVo.getLoginIp());
        // 起初时间
        wrapper.ge(logInfoVo.getStartTime() != null, "loginTime", logInfoVo.getStartTime());
        // 结束时间
        wrapper.le(logInfoVo.getEndTime() != null, "loginTime", logInfoVo.getEndTime());
        // 时间降序
        wrapper.orderByDesc("loginTime");
        // 分页查询
        IPage<LogInfo> page = new Page<>(logInfoVo.getPage(), logInfoVo.getLimit());


        this.LogInfoService.page(page, wrapper);
        return new DataGridView(page.getTotal(), page.getRecords());
    }

    /**
     * 登录日志 - 删除
     * 单个删除
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/delLogInfo")
    public ResultObj delLogInfo(Integer id) {
        try {
            this.LogInfoService.removeById(id);
            return ResultObj.DELETE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.DELETE_ERROR;
        }
    }

    /**
     * 登录日志 - 删除
     * 批量多删
     *
     * @param loginfoVo
     * @return
     */
    @RequestMapping(value = "/batchDeleteLogInfo")
    public ResultObj batchDeleteLogInfo(LogInfoVo loginfoVo) {
        try {
            Collection<Serializable> idList=new ArrayList<>();
            for (Integer id : loginfoVo.getIds()) {
                idList.add(id);
            }
            this.LogInfoService.removeByIds(idList);
            return ResultObj.DELETE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.DELETE_ERROR;
        }
    }
}
