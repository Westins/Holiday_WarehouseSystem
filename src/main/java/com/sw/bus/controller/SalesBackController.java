package com.sw.bus.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sw.bus.pojo.*;
import com.sw.bus.service.*;
import com.sw.bus.vo.SalesBackVo;
import com.sw.sys.common.DataGridView;
import com.sw.sys.common.ResultObj;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author ：单威
 * @description： 销售退货 控制器
 * @date ：Created in 2020/2/29 9:18
 */
@RestController
@RequestMapping(value = "salesBack")
public class SalesBackController {

    /**
     * 退货Service 注入
     */
    @Autowired
    private SalesBackService salesBackService;

    /**
     * 商品Service 注入
     */
    @Autowired
    private GoodsService goodsService;

    /**
     * 供应商Service 注入
     */
    @Autowired
    private CustomerService customerService;

    /**
     * 加载所有退货信息
     *
     * @param salesBackVo
     * @return
     */
    @RequestMapping(value = "/loadAllSalesBack")
    public DataGridView loadAllExport(SalesBackVo salesBackVo) {
        IPage<SalesBack> page = new Page<>(salesBackVo.getPage(), salesBackVo.getLimit());
        QueryWrapper<SalesBack> wrapper = new QueryWrapper<>();
        wrapper.eq(salesBackVo.getCustomerId() != null && salesBackVo.getCustomerId() != 0, "customerId", salesBackVo.getCustomerId());
        wrapper.eq(salesBackVo.getGoodsId() != null && salesBackVo.getGoodsId() != 0, "goodsId", salesBackVo.getGoodsId());
        wrapper.ge(salesBackVo.getStartTime() != null, "salesBackTime", salesBackVo.getStartTime());
        wrapper.le(salesBackVo.getEndTime() != null, "salesBackTime", salesBackVo.getEndTime());
        wrapper.like(StringUtils.isNotBlank(salesBackVo.getOperatePerson()), "operatePerson", salesBackVo.getOperatePerson());
        wrapper.like(StringUtils.isNotBlank(salesBackVo.getRemark()), "remark", salesBackVo.getRemark());
        wrapper.orderByDesc("salesBackTime");
        this.salesBackService.page(page, wrapper);
        List<SalesBack> records = page.getRecords();
        for (SalesBack salesBack : records) {
            if (salesBack.getGoodsId() != null) {
                Goods goods = this.goodsService.getById(salesBack.getGoodsId());
                salesBack.setGoodsImg(goods.getGoodsImg());
                salesBack.setGoodsName(goods.getGoodsName());
                salesBack.setSize(goods.getSize());
            }
            if (salesBack.getCustomerId() != null) {
                Customer customer = this.customerService.getById(salesBack.getCustomerId());
                salesBack.setCustomerName(customer.getCustomerName());
            }
        }
        return new DataGridView(page.getTotal(), records);
    }

    /**
     * 新增 销售退货信息
     *
     * @param id
     * @param number
     * @param remark
     * @return
     */
    @RequestMapping(value = "saveSalesBack")
    public ResultObj saveSalesBack(Integer id, Integer number, String remark) {
        try {
            this.salesBackService.saveSalesBack(id, number, remark);
            return ResultObj.SAVE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResultObj.SAVE_ERROR;
    }
}
