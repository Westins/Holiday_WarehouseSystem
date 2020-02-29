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
 * @description： 销售退货 实体类
 * @date ：Created in 2020/2/28 10:22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("bus_salesBack")
@ToString
public class SalesBack implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Integer customerId;

    private String payType;

    private Date salesBackTime;

    private Double salesBackPrice;

    private String operatePerson;

    private Integer number;

    private String remark;

    private Integer goodsId;

    @TableField(exist = false)
    private String goodsImg;

    @TableField(exist = false)
    private String goodsName;

    @TableField(exist = false)
    private String size;

    @TableField(exist = false)
    private String customerName;
}
