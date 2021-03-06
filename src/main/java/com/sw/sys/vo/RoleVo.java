package com.sw.sys.vo;

import com.sw.sys.pojo.Role;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @description: 角色Vo
 * @author: 单威
 * @time: 2020/2/16 14:58
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class RoleVo extends Role {

    private static final long serialVersionUID = 1L;


    private Integer page = 1;
    private Integer limit = 10;
}
