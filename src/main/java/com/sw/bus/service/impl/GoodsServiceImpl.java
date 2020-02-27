package com.sw.bus.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sw.bus.dao.GoodsMapper;
import com.sw.bus.pojo.Goods;
import com.sw.bus.service.GoodsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author ：单威
 * @description： 商品 业务实现层
 * @date ：Created in 2020/2/20 10:35
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class GoodsServiceImpl extends ServiceImpl<GoodsMapper, Goods> implements GoodsService {
}
