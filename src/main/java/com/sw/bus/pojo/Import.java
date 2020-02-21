package com.sw.bus.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * @author ：单威
 * @description： 进货 实体类
 * @date ：Created in 2020/2/21 9:47
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("bus_import")
public class Import implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String payType;

    private Date importTime;

    private String operatePerson;

    private Integer number;

    private String remark;

    private Double importPrice;

    private Integer providerId;

    private Integer goodsId;

    @TableField(exist = false)
    private String providerName;
    @TableField(exist = false)
    private String goodsName;
    @TableField(exist = false)
    private String size;//规格
}
