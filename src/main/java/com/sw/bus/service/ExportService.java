package com.sw.bus.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sw.sys.common.DataView;
import com.sw.bus.pojo.Export;
import com.sw.sys.common.Proportion;

import java.util.List;

/**
 * @author ：单威
 * @description： 退货 业务层
 * @date ：Created in 2020/2/21 11:47
 */
public interface ExportService extends IService<Export> {
    /**
     * 退货
     *
     * @param id     进货单ID
     * @param number 退货数量
     * @param remark 备注
     */
    void addExPort(Integer id, Integer number, String remark);

    /**
     * 查询近期一年退货数据
     *
     * @return
     */
    Integer loadExportByNow();

    /**
     * 查询今日退货数据
     *
     * @return
     */
    List<DataView> loadExportByYear();

    /**
     * 查询退货中的商品信息
     *
     * @return
     */
    List<Proportion> loadExportByGoods();
}
