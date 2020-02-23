package com.sw.bus.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sw.bus.pojo.Import;

import java.util.List;

/**
 * @author ：单威
 * @description： 进货 数据访问层
 * @date ：Created in 2020/2/21 9:45
 */
public interface ImportMapper extends BaseMapper<Import> {

    List<Integer> loadImportByMonth();

    Integer loadImportByNow();
}
