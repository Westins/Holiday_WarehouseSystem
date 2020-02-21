package com.sw.sys.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * @description: 用户实体类
 * @author: 单威
 * @time: 2020/2/10 10:32
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sys_user")
@ToString
public class User implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String name;

    private String loginName;

    private String address;

    private Integer sex;

    private String remark;

    private String pwd;

    private Integer deptId;

    private Date hireDate;

    private Integer mgr;

    private Integer available;

    private Integer orderNum;

    /**
     * 用户类型[0超级管理员1，管理员，2普通用户]
     */
    private Integer type;

    /**
     * 头像地址
     */
    private String imgPath;

    private String salt;
    
    
    /**
     * 领导名称
     */
    @TableField(exist=false)
    private String leaderName;
    /**
     * 部门名称
     */
    @TableField(exist=false)
    private String deptName;

}
