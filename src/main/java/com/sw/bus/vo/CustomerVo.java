package com.sw.bus.vo;

import com.sw.bus.pojo.Customer;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @description: 客户Vo
 * @author: 单威
 * @time: 2020/2/20 9:24
 */

@Data
@EqualsAndHashCode(callSuper = false)
public class CustomerVo extends Customer {
    
    private static final long serialVersionUID = 1L;

    private Integer page = 1;
    private Integer limit = 10;

    private Integer[] ids;

}
