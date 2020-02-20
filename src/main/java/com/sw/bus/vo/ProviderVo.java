package com.sw.bus.vo;

import com.sw.bus.pojo.Provider;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author ：单威
 * @description： 供应商Vo
 * @date ：Created in 2020/2/20 10:15
 */

@Data
@EqualsAndHashCode(callSuper = false)
public class ProviderVo extends Provider {

    private static final long serialVersionUID = 1L;

    private Integer page = 1;
    private Integer limit = 10;

    private Integer[] ids;
}
