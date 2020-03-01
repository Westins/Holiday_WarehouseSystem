package com.sw.bus.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sw.bus.pojo.Sales;
import com.sw.sys.common.DataView;
import com.sw.sys.common.Proportion;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author ：单威
 * @description： 销售 数据访问层
 * @date ：Created in 2020/2/28 10:34
 */
public interface SalesMapper extends BaseMapper<Sales> {
    /**
     * 查询出售商品ID
     *
     * @return
     */
    List<Integer> loadSalesGoodsIds();


    /**
     * 根据商品ID 查询近一年该商品销售情况
     *
     * @param goodsId
     * @return
     */
    List<DataView> loadSalesYearByGoodsId(@Param("goodsId") Integer goodsId);

    /**
     * 加载今年商品出售占比
     *
     * @return
     */
    List<Proportion> loadSalesGoodsByMonth();

    /**
     * 查询本月销售额
     * @return
     */
    Integer loadSalesByMonth();
}
