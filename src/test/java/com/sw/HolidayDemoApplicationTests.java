package com.sw;

import com.sw.bus.service.SalesService;
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
                int p = 11 - i;//Ê£ÓàÑ­»·´ÎÊý
                int m = c.get(Calendar.YEAR) - 1;
                int n = c.get(Calendar.MONTH) + 2 + p;
                date = m + "-" + (n >= 10 ? "" : "0") + n;
            }
            list.add(date);
        }
    }

    @Autowired
    private SalesService salesService;

    @Test
    public void test() {
        salesService.loadGoodsSalesByYear();
    }
}
