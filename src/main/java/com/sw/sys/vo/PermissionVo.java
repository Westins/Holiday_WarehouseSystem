package com.sw.sys.vo;

import com.sw.sys.pojo.Permission;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @description:
 * @author: sw
 * @time: 2020/2/11 12:00
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class PermissionVo extends Permission {
    private static final long serialVersionUID = 1L;
}
