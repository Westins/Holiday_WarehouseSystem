package com.sw.sys.common;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * @author ：单威
 * @description： 时间工具类
 * @date ：Created in 2020/2/24 10:43
 */
public class TimeUtil {

    /**
     * 获取近一年的时间
     */
    public static List<DataView> getYearTimeByLate() {
        List<DataView> list = new ArrayList<>();
        Calendar c = Calendar.getInstance();
        for (int i = 0; i < 12; i++) {
            DataView dv = new DataView();
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
            dv.setTimeByYear(date);
            dv.setNumberByYear(0);
            list.add(dv);
        }
        return list;
    }
}
