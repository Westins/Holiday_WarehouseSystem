package com.sw.bus.pojo;

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

    private Integer id;

    private Integer customerId;

    private String payType;

    private Date salesBackTime;

    private Double salesBackPrice;

    private String operatePerson;

    private Integer number;

    private String remark;

    private Integer goodsId;
}
