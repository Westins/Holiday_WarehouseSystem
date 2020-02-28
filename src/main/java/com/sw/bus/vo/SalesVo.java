package com.sw.bus.vo;

import com.sw.bus.pojo.Sales;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @author ：单威
 * @description： 销售Vo
 * @date ：Created in 2020/2/28 10:36
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SalesVo extends Sales {
    private static final long serialVersionUID = 1L;

    private Integer page = 1;
    private Integer limit = 10;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;
}
