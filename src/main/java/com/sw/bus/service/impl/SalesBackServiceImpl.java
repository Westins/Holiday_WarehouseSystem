package com.sw.bus.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sw.bus.dao.SalesBackMapper;
import com.sw.bus.pojo.Goods;
import com.sw.bus.pojo.Sales;
import com.sw.bus.pojo.SalesBack;
import com.sw.bus.service.GoodsService;
import com.sw.bus.service.SalesBackService;
import com.sw.bus.service.SalesService;
import com.sw.sys.common.Proportion;
import com.sw.sys.common.WebUtil;
import com.sw.sys.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @author ：单威
 * @description： 销售退货 业务实现类
 * @date ：Created in 2020/2/29 9:21
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class SalesBackServiceImpl extends ServiceImpl<SalesBackMapper, SalesBack> implements SalesBackService {

    @Autowired
    private SalesService salesService;

    @Autowired
    private GoodsService goodsService;

    @Override
    public void saveSalesBack(Integer id, Integer number, String remark) {

        // 根据销售订单信息 填充退货信息
        Sales sales = this.salesService.getById(id);
        // 获取操作员信息
        User user = (User) WebUtil.getSession().getAttribute("user");
        // 商品数量变动
        Goods goods = goodsService.getById(sales.getGoodsId());
        if (goods.getNumber() - number >= 0) {
            goods.setNumber(goods.getNumber() - number);
        }
        // 修改商品销售数量
        if(sales.getNumber() - number >= 0){
            sales.setNumber(sales.getNumber() - number);
            this.salesService.updateById(sales);
        }

        SalesBack salesBack = new SalesBack();
        salesBack.setCustomerId(sales.getCustomerId());
        salesBack.setPayType(sales.getPayType());
        salesBack.setSalesBackTime(new Date());
        salesBack.setSalesBackPrice(sales.getSalePrice());
        salesBack.setOperatePerson(user.getName());
        salesBack.setNumber(number);
        salesBack.setRemark(remark);
        salesBack.setGoodsId(sales.getGoodsId());

        this.getBaseMapper().insert(salesBack);
    }

    @Override
    public Integer loadSalesBackGoodsByMonth() {
        return this.getBaseMapper().loadSalesBackGoodsByMonth();
    }
}
