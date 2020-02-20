package com.sw.bus.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sw.bus.pojo.Provider; 
import com.sw.bus.service.ProviderService;
import com.sw.bus.vo.ProviderVo;
import com.sw.sys.common.DataGridView;
import com.sw.sys.common.ResultObj;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

/**
 * @description： 供应商管理 控制器
 * @author ：单威
 * @date ：Created in 2020/2/20 10:10
 */
@RestController
@RequestMapping(value = "/provider")
public class ProviderController {

    /**
     * 供应商接口 注入
     */
    @Autowired
    private ProviderService providerService;

    /**
     * 加载所有可用供应商 -- 查询
     *
     * @param providerVo
     * @return
     */
    @RequestMapping(value = "/loadAllProvider")
    public DataGridView loadAllProvider(ProviderVo providerVo) {
        QueryWrapper<Provider> wrapper = new QueryWrapper<>();
        wrapper.like(StringUtils.isNotBlank(providerVo.getProviderName()), "providerName",
                providerVo.getProviderName());
        wrapper.like(StringUtils.isNotBlank(providerVo.getPhone()), "phone", providerVo.getPhone());
        wrapper.like(StringUtils.isNotBlank(providerVo.getConnectionPerson()), "connectionPerson",
                providerVo.getConnectionPerson());
        IPage<Provider> page = new Page<>(providerVo.getPage(), providerVo.getLimit());

        this.providerService.page(page, wrapper);
        return new DataGridView(page.getTotal(), page.getRecords());
    }

    /**
     * 新增供应商信息
     *
     * @param providerVo
     * @return
     */
    @RequestMapping(value = "/saveProvider")
    public ResultObj saveProvider(ProviderVo providerVo) {
        try {
            this.providerService.save(providerVo);
            return ResultObj.SAVE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.SAVE_ERROR;
        }
    }

    /**
     * 供应商信息修改
     *
     * @param providerVo
     * @return
     */
    @RequestMapping(value = "/updProvider")
    public ResultObj updProvider(ProviderVo providerVo) {
        try {
            this.providerService.updateById(providerVo);
            return ResultObj.UPDATE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.UPDATE_ERROR;
        }
    }

    /**
     * 删除供应商信息
     * 单删
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/delProvider")
    public ResultObj delProvider(Integer id) {
        try {
            this.providerService.removeById(id);
            return ResultObj.DELETE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.DELETE_ERROR;
        }
    }

    /**
     * 删除供应商信息
     * 多删
     *
     * @param providerVo
     * @return
     */
    @RequestMapping("/batchDeleteProvider")
    public ResultObj batchDeleteProvider(ProviderVo providerVo) {
        try {
            Collection<Serializable> idList = new ArrayList<Serializable>();
            for (Integer id : providerVo.getIds()) {
                idList.add(id);
            }
            this.providerService.removeByIds(idList);
            return ResultObj.DELETE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.DELETE_ERROR;
        }
    }
}
