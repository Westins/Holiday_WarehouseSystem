package com.sw.bus.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sw.bus.pojo.*;
import com.sw.bus.pojo.Sales;
import com.sw.bus.service.*;
import com.sw.bus.service.SalesService;
import com.sw.bus.vo.SalesVo;
import com.sw.sys.common.DataGridView;
import com.sw.sys.common.ResultObj;
import com.sw.sys.common.WebUtil;
import com.sw.sys.pojo.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

/**
 * @author ：单威
 * @description： 销售 控制器
 * @date ：Created in 2020/2/28 10:34
 */
@RestController
@RequestMapping(value = "sales")
public class SalesController {
    /**
     * 进货Service 注入
     */
    @Autowired
    private SalesService salesService;

    /**
     * 客户Service 注入
     */
    @Autowired
    private CustomerService customerService;

    /**
     * 商品Service 注入
     */
    @Autowired
    private GoodsService goodsService;

    /**
     * 供应商Service 注入
     */
    @Autowired
    private ProviderService providerService;

    /**
     * 加载全部销售信息 -- 查询
     *
     * @param salesVo
     * @return
     */
    @RequestMapping(value = "/loadAllSales")
    public DataGridView loadAllSales(SalesVo salesVo) {
        IPage<Sales> page = new Page<>(salesVo.getPage(), salesVo.getLimit());
        QueryWrapper<Sales> wrapper = new QueryWrapper<>();
        wrapper.eq(salesVo.getCustomerId() != null && salesVo.getCustomerId() != 0, "customerId", salesVo.getCustomerId());
        wrapper.eq(salesVo.getGoodsId() != null && salesVo.getGoodsId() != 0, "goodsId", salesVo.getGoodsId());
        wrapper.ge(salesVo.getStartTime() != null, "salesTime", salesVo.getStartTime());
        wrapper.le(salesVo.getEndTime() != null, "salesTime", salesVo.getEndTime());
        wrapper.like(StringUtils.isNotBlank(salesVo.getOperatePerson()), "operatePerson", salesVo.getOperatePerson());
        wrapper.like(StringUtils.isNotBlank(salesVo.getRemark()), "remark", salesVo.getRemark());
        wrapper.orderByDesc("salesTime");
        this.salesService.page(page, wrapper);
        List<Sales> records = page.getRecords();
        for (Sales sales : records) {
            Customer customer = this.customerService.getById(sales.getCustomerId());
            if (null != customer) {
                sales.setCustomerName(customer.getCustomerName());
            }
            Goods goods = this.goodsService.getById(sales.getGoodsId());
            if (null != goods) {
                sales.setGoodsName(goods.getGoodsName());
                sales.setSize(goods.getSize());
            }
            if (goods.getProviderId() != null ){
                sales.setProviderId(goods.getProviderId());
            }
        }
        return new DataGridView(page.getTotal(), records);
    }

    /**
     * 添加销售信息
     *
     * @param salesVo
     * @return
     */
    @RequestMapping(value = "/saveSales")
    public ResultObj saveSales(SalesVo salesVo) {
        try {
            salesVo.setSalesTime(new Date());
            User user = (User) WebUtil.getSession().getAttribute("user");
            salesVo.setOperatePerson(user.getName());
            this.salesService.save(salesVo);
            return ResultObj.SAVE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.SAVE_ERROR;
        }
    }

    /**
     * 修改销售信息
     *
     * @param salesVo
     * @return
     */
    @RequestMapping(value = "/updSales")
    public ResultObj updSales(SalesVo salesVo) {
        try {
            this.salesService.updateById(salesVo);
            return ResultObj.UPDATE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.UPDATE_ERROR;
        }
    }

    /**
     * 删除销售信息
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/delSales")
    public ResultObj delSales(Integer id) {
        try {
            this.salesService.removeById(id);
            return ResultObj.DELETE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.DELETE_ERROR;
        }
    }
}
