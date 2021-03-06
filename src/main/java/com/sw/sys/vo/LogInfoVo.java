package com.sw.sys.vo;

import com.sw.sys.pojo.LogInfo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @description: 登录日志Vo
 * @author: 单威
 * @time: 2020/2/11 12:00
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class LogInfoVo extends LogInfo {
    private static final long serialVersionUID = 1L;

    private Integer page = 1;
    private Integer limit = 10;

    /**
     * 用户接受多个ID
     */
    private Integer[] ids;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;
}
