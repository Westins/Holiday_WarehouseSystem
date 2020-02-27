package com.sw.sys.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sw.sys.common.DataView;
import com.sw.bus.service.ExportService;
import com.sw.bus.service.ImportService;
import com.sw.sys.common.DataGridView;
import com.sw.sys.common.Proportion;
import com.sw.sys.common.TimeUtil;
import com.sw.sys.pojo.LogInfo;
import com.sw.sys.pojo.Notice;
import com.sw.sys.service.LogInfoService;
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
     * 登录日志Service 注入
     */
    @Autowired
    private LogInfoService logInfoService;

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
     * 查询前三条登录信息
     *
     * @return
     */
    @RequestMapping(value = "/loadMainLogInfo")
    public DataGridView loadMainLogInfo() {
        QueryWrapper<LogInfo> wrapper = new QueryWrapper<>();
        // 时间降序
        wrapper.orderByDesc("loginTime");
        // 分页查询
        IPage<LogInfo> page = new Page<LogInfo>(1, 3);

        this.logInfoService.page(page, wrapper);
        return new DataGridView(page.getTotal(), page.getRecords());
    }

    @RequestMapping(value = "/loadMainNotice")
    public DataGridView loadMainNotice() {
        QueryWrapper<Notice> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("createTime");
        IPage<Notice> page = new Page<Notice>(1, 3);

        this.noticeService.page(page, wrapper);
        return new DataGridView(page.getTotal(), page.getRecords());
    }

    /**
     * 查询当日进货 退货数量
     *
     * @return
     */
    @RequestMapping(value = "/loadImportExportByNow")
    public Map<String, Object> loadImportExportByNow() {
        Map<String, Object> map = new HashMap<>(16);
        Integer importNumber = this.importService.loadImportByNow();
        Integer exportNumber = this.exportService.loadExportByNow();
        map.put("importNumber", importNumber);
        map.put("exportNumber", exportNumber);
        return map;
    }

    /**
     * 查询 近一年的进退货数量
     *
     * @return
     */
    @RequestMapping(value = "/loadImportExportByYear")
    public Map<String, Object> loadImportExportByYear() {

        Map<String, Object> map = new HashMap<>(16);

        // 查询出近一年的进货数据
        List<DataView> im = this.importService.loadImportByYear();
        // 查询出近一年的退货数据
        List<DataView> ex = this.exportService.loadExportByYear();

        List<DataView> importList = TimeUtil.getYearTimeByLate();
        List<DataView> exportList = TimeUtil.getYearTimeByLate();

        List<String> time = new ArrayList<>();
        List<Integer> importNumber = new ArrayList<>();
        List<Integer> exportNumber = new ArrayList<>();

        for (DataView dv : importList) {
            for (Integer i = 0; i < im.size(); i++) {
                if (im.get(i).getTimeByYear().equals(dv.getTimeByYear())) {
                    dv.setNumberByYear(im.get(i).getNumberByYear());
                }
            }
            importNumber.add(dv.getNumberByYear());
            time.add(dv.getTimeByYear());
        }
        for (DataView dv : exportList) {
            for (Integer i = 0; i < ex.size(); i++) {
                if (ex.get(i).getTimeByYear().equals(dv.getTimeByYear())) {
                    dv.setNumberByYear(ex.get(i).getNumberByYear());
                }
            }
            exportNumber.add(dv.getNumberByYear());
        }

        Collections.reverse(importNumber);
        Collections.reverse(exportNumber);
        Collections.reverse(time);
        map.put("importNumber", importNumber);
        map.put("exportNumber", exportNumber);
        map.put("time", time);
        return map;
    }

    /**
     * 查询 进退货
     * @return
     */
    @RequestMapping(value = "/loadExportImportByProportion")
    public Map<String,Object> loadExportImportByProportion(){
        Map<String, Object> map = new HashMap<>(16);

        List<Proportion> importList = this.importService.loadImportByGoods();
        List<Proportion> exportList = this.exportService.loadExportByGoods();

        map.put("importList",importList);
        map.put("exportList",exportList);
        return map;
    }
}
