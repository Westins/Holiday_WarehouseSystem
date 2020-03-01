package com.sw.bus.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sw.bus.dao.SalesMapper;
import com.sw.bus.pojo.Goods;
import com.sw.bus.pojo.Sales;
import com.sw.bus.service.GoodsService;
import com.sw.bus.service.SalesService;
import com.sw.sys.common.DataView;
import com.sw.sys.common.EchartsView;
import com.sw.sys.common.Proportion;
import com.sw.sys.common.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
        if ((goods.getNumber() - entity.getNumber()) >= 0) {
            goods.setNumber(goods.getNumber() - entity.getNumber());
        }
        return super.save(entity);
    }

    @Override
    public boolean updateById(Sales entity) {

        Sales sales = this.baseMapper.selectById(entity.getId());
        Goods goods = this.goodsService.getById(entity.getGoodsId());
        if (sales.getNumber() - entity.getNumber() > 0) {
            goods.setNumber(goods.getNumber() - (sales.getNumber() - entity.getNumber()));
        } else if (sales.getNumber() - entity.getNumber() < 0) {
            goods.setNumber(goods.getNumber() + (sales.getNumber() - entity.getNumber()));
        }
        this.goodsService.updateById(goods);

        return super.updateById(entity);
    }

    @Override
    public List<EchartsView> loadGoodsSalesByYear() {
        // 查询出售的商品ID
        List<Integer> goodsIdsList = this.baseMapper.loadSalesGoodsIds();
        // 根据goodsId 找出近一年的数据
        List<EchartsView> echartsViewList = new ArrayList<>();
        List<String> nameList = new ArrayList<>();
        for (Integer goodsId : goodsIdsList) {
            EchartsView echartsView = new EchartsView();
            List<DataView> timeList = TimeUtil.getYearTimeByLate();

            List<String> time = new ArrayList<>();
            List<Integer> numberList = new ArrayList<>();
            List<DataView> salesList = this.baseMapper.loadSalesYearByGoodsId(goodsId);
            Goods goods = this.goodsService.getById(goodsId);

            for (DataView dv : timeList) {
                for (int i = 0; i < salesList.size(); i++) {
                    if (salesList.get(i).getTimeByYear().equals(dv.getTimeByYear())) {
                        dv.setNumberByYear(salesList.get(i).getNumberByYear());
                    }
                }
                numberList.add(dv.getNumberByYear());
                time.add(dv.getTimeByYear());
            }

            nameList.add(goods.getGoodsName());
            echartsView.setName(goods.getGoodsName());
            echartsView.setNameData(nameList);
            Collections.reverse(numberList);
            Collections.reverse(time);
            echartsView.setData(numberList);
            echartsView.setTimeData(time);
            echartsViewList.add(echartsView);
        }
        return echartsViewList;
    }

    @Override
    public List<Proportion> loadSalesGoodsByMonth() {
        return this.baseMapper.loadSalesGoodsByMonth();
    }

    @Override
    public Integer loadSalesByMonth() {
        return this.baseMapper.loadSalesByMonth();
    }
}
