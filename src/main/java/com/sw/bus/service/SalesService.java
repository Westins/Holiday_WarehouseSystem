package com.sw.bus.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sw.bus.pojo.Sales;
import com.sw.sys.common.EchartsView;
import com.sw.sys.common.Proportion;

import java.util.List;

/**
 * @author ：单威
 * @description： 销售 业务层
 * @date ：Created in 2020/2/28 10:35
 */
public interface SalesService extends IService<Sales> {
    /**
     * 近一年的各商品的销售情况
     * @return
     */
    List<EchartsView> loadGoodsSalesByYear();

    /**
     * 加载今年商品销售情况
     * @return
     */
    List<Proportion> loadSalesGoodsByMonth();
    /**
     * 查询本月销售额
     * @return
     */
    Integer loadSalesByMonth();
}
