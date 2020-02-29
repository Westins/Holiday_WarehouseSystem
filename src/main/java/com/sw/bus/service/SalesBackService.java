package com.sw.bus.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sw.bus.pojo.SalesBack;

/**
 * @author ：单威
 * @description： 销售退货 业务层
 * @date ：Created in 2020/2/29 9:20
 */
public interface SalesBackService extends IService<SalesBack> {
    /**
     * 添加 销售退货信息
     * @param id 退货的出售订单ID
     * @param number 退货数量
     * @param remark 拖货原因
     */
    void saveSalesBack(Integer id, Integer number, String remark);
}