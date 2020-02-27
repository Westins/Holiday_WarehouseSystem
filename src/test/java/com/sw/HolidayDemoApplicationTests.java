package com.sw;

import com.sw.sys.common.DataView;
import com.sw.bus.service.ExportService;
import com.sw.bus.service.ImportService;
import com.sw.sys.common.TimeUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@SpringBootTest
class HolidayDemoApplicationTests {

    @Test
    void contextLoads() {
        List<String> list = new ArrayList<String>();
        Calendar c = Calendar.getInstance();
        for (int i = 0; i < 12; i++) {
            int k = c.get(Calendar.YEAR);
            int j = c.get(Calendar.MONTH) + 1 - i;
            String date = "";
            if (j >= 1) {
                date = k + "-" + (j >= 10 ? "" : "0") + j;
            } else {
                int p = 11 - i;//剩余循环次数
                int m = c.get(Calendar.YEAR) - 1;
                int n = c.get(Calendar.MONTH) + 2 + p;
                date = m + "-" + (n >= 10 ? "" : "0") + n;
            }
            list.add(date);
        }
    }

    @Autowired
    private ExportService exportService;
    @Autowired
    private ImportService importService;

    @Test
    public void test() {

        // 查询出近一年的进货数据
        List<DataView> im = this.importService.loadImportByYear();
        // 查询出近一年的退货数据
        List<DataView> ex = this.exportService.loadExportByYear();

        /**
         * data: ['周一', '周二', '周三', '周四', '周五', '周六', '周日']
         *
         * {
         * 	name: '邮件营销',
         *  type: 'line',
         * 	data: [100, 200, 300, 400, 500, 600, 700]
         * },
         */
        List<DataView> importList = TimeUtil.getYearTimeByLate();
        List<DataView> exportList = TimeUtil.getYearTimeByLate();

        for (DataView dv : importList) {
            for (Integer i = 0; i < im.size(); i++) {
                if (im.get(i).getTimeByYear().equals(dv.getTimeByYear())) {
                    dv.setNumberByYear(im.get(i).getNumberByYear());
                }
            }
        }
        for (DataView dv : exportList) {
            for (Integer i = 0; i < ex.size(); i++) {
                if (ex.get(i).getTimeByYear().equals(dv.getTimeByYear())) {
                    dv.setNumberByYear(ex.get(i).getNumberByYear());
                }
            }
        }




    }
}
