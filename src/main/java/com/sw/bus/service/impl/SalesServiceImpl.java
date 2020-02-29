package com.sw.bus.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sw.bus.dao.SalesMapper;
import com.sw.bus.pojo.Goods;
import com.sw.bus.pojo.Sales;
import com.sw.bus.service.GoodsService;
import com.sw.bus.service.SalesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author ：单威
 * @description： 销售 业务实现类
 * @date ：Created in 2020/2/28 10:36
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class SalesServiceImpl extends ServiceImpl<SalesMapper, Sales> implements SalesService {

    /**
     * 商品Service 注入
     */
    @Autowired
    private GoodsService goodsService;

    @Override
    public boolean save(Sales entity) {
        Goods goods = this.goodsService.getById(entity.getGoodsId());
        if((goods.getNumber() - entity.getNumber()) >= 0){
            goods.setNumber(goods.getNumber() - entity.getNumber());
        }
        return super.save(entity);
    }
}
