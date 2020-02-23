package com.sw.bus.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sw.bus.pojo.Export;

import java.util.List;

/**
 * @author ：单威
 * @description： 退货 数据访问层
 * @date ：Created in 2020/2/21 11:46
 */
public interface ExportMapper extends BaseMapper<Export> {

    List<Integer> loadExportByMonth();

    Integer loadExportByNow();
}
