package com.sw.bus.vo;

import com.sw.bus.pojo.Import;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @author ：单威
 * @description： 进货Vo
 * @date ：Created in 2020/2/21 9:51
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class ImportVo extends Import {

    private static final long serialVersionUID = 1L;

    private Integer page = 1;
    private Integer limit = 10;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;
}
