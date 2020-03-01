package com.sw.sys.common;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * @author ：单威
 * @description：  进退货一年数据
 * @date ：Created in 2020/2/24 11:19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ToString
public class DataView {

    private Integer numberByYear;

    private String timeByYear;

    private Integer goodsId;
}
