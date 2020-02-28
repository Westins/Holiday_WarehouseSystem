package com.sw.bus.pojo;

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
 * @author ：单威
 * @description： 销售量 实体类
 * @date ：Created in 2020/2/28 10:22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("bus_sales")
@ToString
public class Sales implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Integer customerId;

    private String payType;

    private Date salesTime;

    private String operatePerson;

    private Integer number;

    private String remark;

    private Double salePrice;

    private Integer goodsId;

    @TableField(exist = false)
    private String customerName;

    @TableField(exist = false)
    private String goodsName;

    @TableField(exist = false)
    private String size;

    @TableField(exist = false)
    private Integer providerId;
}
