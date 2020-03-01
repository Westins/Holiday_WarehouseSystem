package com.sw.sys.common;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author ：单威
 * @description： Echarts 视图数据
 * @date ：Created in 2020/2/29 12:08
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ToString
public class EchartsView {

    private String name;

    private String type = "line";

    private List<String> nameData;

    private List<Integer> Data;

    private List<String> timeData;
}
