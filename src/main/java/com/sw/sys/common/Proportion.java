package com.sw.sys.common;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * @author ：单威
 * @description：  进退货
 * @date ：Created in 2020/2/24 16：20
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ToString
public class Proportion {

    private Integer value;

    private String name;

}
