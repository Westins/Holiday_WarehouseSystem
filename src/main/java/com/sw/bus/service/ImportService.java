package com.sw.bus.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sw.sys.common.DataView;
import com.sw.bus.pojo.Import;
import com.sw.sys.common.Proportion;

import java.util.List;

/**
 * @author ：单威
 * @description： 进货 业务层
 * @date ：Created in 2020/2/21 9:50
 */
public interface ImportService extends IService<Import> {
    /**
     * 查询今日的进货信息
     *
     * @return
     */
    Integer loadImportByNow();

    /**
     * 查询近期一年的进货数据
     *
     * @return
     */
    List<DataView> loadImportByYear();

    /**
     * 查询进货的商品信息
     *
     * @return
     */
    List<Proportion> loadImportByGoods();

    /**
     * 查询当年进货商品信息
     *
     * @return
     */
    List<Proportion> loadImportGoodsByMonth();
}
