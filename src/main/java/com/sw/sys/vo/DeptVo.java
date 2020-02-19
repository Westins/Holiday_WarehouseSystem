package com.sw.sys.vo;

import com.sw.sys.pojo.Dept;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @description: 部门Vo
 * @author: 单威
 * @time: 2020/2/14 11:16
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class DeptVo extends Dept {

    private static final long serialVersionUID = 1L;

    private Integer page = 1;
    private Integer limit = 10;
}
