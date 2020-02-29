package com.sw.bus.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sw.bus.pojo.Export;
import com.sw.bus.pojo.Goods;
import com.sw.bus.pojo.Provider;
import com.sw.bus.service.ExportService;
import com.sw.bus.service.GoodsService;
import com.sw.bus.service.ProviderService;
import com.sw.bus.vo.ExportVo;
import com.sw.sys.common.DataGridView;
import com.sw.sys.common.ResultObj;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author ：单威
 * @description： 退货 控制器
 * @date ：Created in 2020/2/21 11:45
 */
@RestController
@RequestMapping(value = "/export")
public class ExportController {

    /**
     * 退货Service 注入
     */
    @Autowired
    private ExportService exportService;

    /**
     * 商品Service 注入
     */
    @Autowired
    private GoodsService goodsService;

    /**
     * 供应商Service 注入
     */
    @Autowired
    private ProviderService providerService;

    /**
     * 加载所有退货信息
     *
     * @param exportVo
     * @return
     */
    @RequestMapping(value = "/loadAllExport")
    public DataGridView loadAllExport(ExportVo exportVo) {
        IPage<Export> page = new Page<>(exportVo.getPage(), exportVo.getLimit());
        QueryWrapper<Export> wrapper = new QueryWrapper<>();
        wrapper.eq(exportVo.getProviderId() != null && exportVo.getProviderId() != 0, "providerId", exportVo.getProviderId());
        wrapper.eq(exportVo.getGoodsId() != null && exportVo.getGoodsId() != 0, "goodsId", exportVo.getGoodsId());
        wrapper.ge(exportVo.getStartTime() != null, "exportTime", exportVo.getStartTime());
        wrapper.le(exportVo.getEndTime() != null, "exportTime", exportVo.getEndTime());
        wrapper.like(StringUtils.isNotBlank(exportVo.getOperatePerson()), "operatePerson", exportVo.getOperatePerson());
        wrapper.like(StringUtils.isNotBlank(exportVo.getRemark()), "remark", exportVo.getRemark());
        wrapper.orderByDesc("exportTime");
        this.exportService.page(page, wrapper);
        List<Export> records = page.getRecords();
        for (Export ex : records) {
            Provider provider = this.providerService.getById(ex.getProviderId());
            if (null != provider) {
                ex.setProviderName(provider.getProviderName());
            }
            Goods goods = this.goodsService.getById(ex.getGoodsId());
            if (null != goods) {
                ex.setGoodsName(goods.getGoodsName());
                ex.setSize(goods.getSize());
                ex.setGoodsImg(goods.getGoodsImg());
            }
        }
        return new DataGridView(page.getTotal(), records);
    }

    /**
     * 添加退货信息
     *
     * @param id
     * @param number
     * @param remark
     * @return
     */
    @RequestMapping(value = "/saveExport")
    public ResultObj saveExport(Integer id, Integer number, String remark) {
        try {
            this.exportService.addExPort(id, number, remark);
            return ResultObj.SAVE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.SAVE_ERROR;
        }
    }
}
