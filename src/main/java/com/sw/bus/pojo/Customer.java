package com.sw.bus.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @description: 客户 实体类
 * @author: 单威
 * @time: 2020/2/20 9:28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("bus_customer")
@ToString
public class Customer implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String customerName;

    private String zip;

    private String address;

    private String telephone;

    private String connectionPerson;

    private String phone;

    private String bank;

    private String account;

    private String email;

    private String fax;

    private Integer available;
}
