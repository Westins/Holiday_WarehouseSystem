package com.sw.sys.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sw.bus.service.SalesBackService;
import com.sw.bus.service.SalesService;
import com.sw.sys.common.*;
import com.sw.bus.service.ExportService;
import com.sw.bus.service.ImportService;
import com.sw.sys.pojo.Notice;
import com.sw.sys.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 * @author ：单威
 * @description： 工作台数据
 * @date ：Created in 2020/2/23 13:11
 */
@RestController
@RequestMapping(value = "/main")
public class MainController {

    /**
     * 系统公告Service注入
     */
    @Autowired
    private NoticeService noticeService;

    /**
     * 进货service 注入
     */
    @Autowired
    private ImportService importService;

    /**
     * 退货Service 注入
     */
    @Autowired
    private ExportService exportService;

    /**
     * 商品销售Service 注入
     */
    @Autowired
    private SalesService salesService;

    /**
     * 商品销售退货Service 注入
     */
    @Autowired
    private SalesBackService salesBackService;

    /**
     * 加载 最新三条系统公告
     *
     * @return
     */
    @RequestMapping(value = "/loadMainNotice")
    public DataGridView loadMainNotice() {
        QueryWrapper<Notice> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("createTime");
        IPage<Notice> page = new Page<>(1, 3);

        this.noticeService.page(page, wrapper);
        return new DataGridView(page.getTotal(), page.getRecords());
    }

    /**
     * 查询当月进货 退货数量
     *
     * @return
     */
    @RequestMapping(value = "/loadImportExportByNow")
    public Map<String, Object> loadImportExportByNow() {
        Map<String, Object> map = new HashMap<>(16);
        Integer importNumber = this.importService.loadImportByNow();
        Integer exportNumber = this.exportService.loadExportByNow();
        map.put("importNumber", importNumber == null ? 0 : importNumber);
        map.put("exportNumber", exportNumber == null ? 0 : exportNumber);
        return map;
    }

    /**
     * 查询 近期一年各商品的销售情况
     * 并返回Examples 需要的数据格式
     *
     * @return
     */
    @RequestMapping(value = "loadSalesGoodsByYear")
    public DataGridView loadSalesGoodsByYear() {
        List<EchartsView> list = this.salesService.loadGoodsSalesByYear();
        return new DataGridView(list);
    }

    /**
     * 加载今月商品进货占比
     *
     * @return
     */
    @RequestMapping(value = "loadImportGoodsByMonth")
    public DataGridView loadImportGoodsByMonth() {
        List<Proportion> importList = this.importService.loadImportGoodsByMonth();
        return new DataGridView(importList);
    }

    /**
     * 加载今月商品出售占比
     *
     * @return
     */
    @RequestMapping(value = "loadSalesGoodsByMonth")
    public DataGridView loadSalesGoodsByMonth() {
        List<Proportion> salesList = this.salesService.loadSalesGoodsByMonth();
        return new DataGridView(salesList);
    }

    /**
     * 加载本月商品销售和退货数量
     *
     * @return
     */
    @RequestMapping(value = "loadSalesAndBackNumberByMonth")
    public Map<String, Object> loadSalesAndBackNumberByMonth() {
        Map<String, Object> map = new HashMap<>(16);

        Integer salesNumber = this.salesService.loadSalesByMonth();
        Integer salesBackNumber = this.salesBackService.loadSalesBackGoodsByMonth();

        map.put("salesNumber", salesNumber == null ? 0 : salesNumber);
        map.put("salesBackNumber", salesBackNumber == null ? 0 : salesBackNumber);
        return map;
    }
}
