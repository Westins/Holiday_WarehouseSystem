package com.sw.bus.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sw.bus.dao.ProviderMapper;
import com.sw.bus.pojo.Provider;
import com.sw.bus.service.ProviderService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author ：单威
 * @description： 供应商 业务实现层
 * @date ：Created in 2020/2/20 10:14
 */
@Service
@Transactional
public class ProviderServiceImpl extends ServiceImpl<ProviderMapper, Provider> implements ProviderService {
}
