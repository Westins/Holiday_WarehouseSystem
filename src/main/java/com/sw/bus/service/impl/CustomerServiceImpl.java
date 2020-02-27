package com.sw.bus.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sw.bus.dao.CustomerMapper;
import com.sw.bus.pojo.Customer;
import com.sw.bus.service.CustomerService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @description: 客户 业务实现层
 * @author: 单威
 * @time: 2020/2/20 9:23
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class CustomerServiceImpl extends ServiceImpl<CustomerMapper, Customer> implements CustomerService {

}
