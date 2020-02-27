package com.sw.bus.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sw.sys.common.DataView;
import com.sw.bus.pojo.Export;
import com.sw.sys.common.Proportion;

import java.util.List;

/**
 * @author ：单威
 * @description： 退货 数据访问层
 * @date ：Created in 2020/2/21 11:46
 */
public interface ExportMapper extends BaseMapper<Export> {

    /**
     *  查询近期一年退货数据
     * @return
     */
    List<DataView> loadExportByYear();

    /**
     * 查询今日退货数据
     * @return
     */
    Integer loadExportByNow();

    /**
     * 查询退货中的商品信息
     * @return
     */
    List<Proportion> loadExportByGoods();
}
