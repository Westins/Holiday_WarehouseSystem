package com.sw.bus.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sw.bus.pojo.Customer;
import com.sw.bus.service.CustomerService;
import com.sw.bus.vo.CustomerVo;
import com.sw.sys.common.Constant;
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
 * @description: 客户管理 控制器
 * @author: 单威
 * @time: 2020/2/20 9:21
 */
@RestController
@RequestMapping(value = "/customer")
public class CustomerController {

    /**
     * 客户接口 注入
     */
    @Autowired
    private CustomerService customerService;

    /**
     * 加载所有可用客户 -- 查询
     *
     * @param customerVo
     * @return
     */
    @RequestMapping(value = "/loadAllCustomer")
    public DataGridView loadAllCustomer(CustomerVo customerVo) {
        QueryWrapper<Customer> wrapper = new QueryWrapper<>();
        wrapper.like(StringUtils.isNotBlank(customerVo.getCustomerName()), "customerName",
                customerVo.getCustomerName());
        wrapper.like(StringUtils.isNotBlank(customerVo.getPhone()), "phone", customerVo.getPhone());
        wrapper.like(StringUtils.isNotBlank(customerVo.getConnectionPerson()), "connectionPerson",
                customerVo.getConnectionPerson());
        IPage<Customer> page = new Page<>(customerVo.getPage(), customerVo.getLimit());

        this.customerService.page(page, wrapper);
        return new DataGridView(page.getTotal(), page.getRecords());
    }

    /**
     * 新增客户信息
     *
     * @param customerVo
     * @return
     */
    @RequestMapping(value = "/saveCustomer")
    public ResultObj saveCustomer(CustomerVo customerVo) {
        try {
            this.customerService.save(customerVo);
            return ResultObj.SAVE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.SAVE_ERROR;
        }
    }

    /**
     * 客户信息修改
     *
     * @param customerVo
     * @return
     */
    @RequestMapping(value = "/updCustomer")
    public ResultObj updCustomer(CustomerVo customerVo) {
        try {
            this.customerService.updateById(customerVo);
            return ResultObj.UPDATE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.UPDATE_ERROR;
        }
    }

    /**
     * 删除客户信息
     * 单删
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/delCustomer")
    public ResultObj delCustomer(Integer id) {
        try {
            this.customerService.removeById(id);
            return ResultObj.DELETE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.DELETE_ERROR;
        }
    }

    /**
     * 删除客户信息
     * 多删
     *
     * @param customerVo
     * @return
     */
    @RequestMapping("/batchDeleteCustomer")
    public ResultObj batchDeleteCustomer(CustomerVo customerVo) {
        try {
            Collection<Serializable> idList = new ArrayList<Serializable>();
            for (Integer id : customerVo.getIds()) {
                idList.add(id);
            }
            this.customerService.removeByIds(idList);
            return ResultObj.DELETE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.DELETE_ERROR;
        }
    }
}
