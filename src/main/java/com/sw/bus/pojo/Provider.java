package com.sw.bus.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * @author ：单威
 * @description： 供应商 实体类
 * @date ：Created in 2020/2/20 10:12
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("bus_provider")
@ToString
public class Provider {
    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String providerName;

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
