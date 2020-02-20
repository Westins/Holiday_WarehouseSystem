package com.sw.bus.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @author ：单威
 * @description： 商品 实体类
 * @date ：Created in 2020/2/20 10:29
 */

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("bus_goods")
@ToString
public class Goods {
    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String goodsName;

    private String producePlace;

    private String size;

    private String goodsPackage;

    private String productCode;

    private String approvalCode;

    private String description;

    private Double price;

    private Integer number;

    private Integer dangerNum;

    private String goodsImg;

    private Integer available;

    private Integer providerId;

    private Date time;

    @TableField(exist = false)
    private String providerName;

}
