package com.sw.sys.vo;

import com.sw.sys.pojo.User;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @description: 用户Vo
 * @author: sw
 * @time: 2020/2/18 9:37
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class UserVo extends User {
    private static final long serialVersionUID = 1L;

    private Integer page = 1;
    private Integer limit = 10;
}
