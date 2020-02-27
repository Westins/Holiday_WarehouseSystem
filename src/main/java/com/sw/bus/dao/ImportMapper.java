package com.sw.bus.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sw.sys.common.DataView;
import com.sw.bus.pojo.Import;
import com.sw.sys.common.Proportion;

import java.util.List;

/**
 * @author ：单威
 * @description： 进货 数据访问层
 * @date ：Created in 2020/2/21 9:45
 */
public interface ImportMapper extends BaseMapper<Import> {

    /**
     * 查询近期一年的进货数据
     *
     * @return
     */
    List<DataView> loadImportByYear();

    /**
     * 查询今日的进货信息
     *
     * @return
     */
    Integer loadImportByNow();

    /**
     * 查询进货的商品信息
     *
     * @return
     */
    List<Proportion> loadImportByGoods();
}
