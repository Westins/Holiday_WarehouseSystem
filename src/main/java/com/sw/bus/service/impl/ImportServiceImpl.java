package com.sw.bus.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sw.bus.dao.GoodsMapper;
import com.sw.bus.dao.ImportMapper;
import com.sw.sys.common.DataView;
import com.sw.bus.pojo.Goods;
import com.sw.bus.pojo.Import;
import com.sw.bus.service.ImportService;
import com.sw.sys.common.Proportion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;

/**
 * @author ：单威
 * @description： 进货 业务实现类
 * @date ：Created in 2020/2/21 9:50
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class ImportServiceImpl extends ServiceImpl<ImportMapper, Import> implements ImportService {

    /**
     * 商品数据访问 注入
     */
    @Autowired
    private GoodsMapper goodsMapper;

    @Override
    public boolean save(Import entity) {
        //根据商品编号查询商品
        Goods goods = goodsMapper.selectById(entity.getGoodsId());
        goods.setNumber(goods.getNumber() + entity.getNumber());
        goodsMapper.updateById(goods);
        //保存进货信息
        return super.save(entity);
    }

    @Override
    public boolean updateById(Import entity) {
        //根据进货ID查询进货
        Import im = this.baseMapper.selectById(entity.getId());
        //根据商品ID查询商品信息
        Goods goods = this.goodsMapper.selectById(entity.getGoodsId());
        //库存的算法  当前库存-进货单修改之前的数量+修改之后的数量
        goods.setNumber(goods.getNumber() - im.getNumber() + entity.getNumber());
        this.goodsMapper.updateById(goods);
        //更新进货单
        return super.updateById(entity);
    }


    @Override
    public boolean removeById(Serializable id) {
        //根据进货ID查询进货
        Import im = this.baseMapper.selectById(id);
        //根据商品ID查询商品信息
        Goods goods = this.goodsMapper.selectById(im.getGoodsId());
        //库存的算法  当前库存-进货单数量
        goods.setNumber(goods.getNumber() - im.getNumber());
        this.goodsMapper.updateById(goods);
        return super.removeById(id);
    }

    @Override
    public Integer loadImportByNow() {
        return this.baseMapper.loadImportByNow();
    }

    @Override
    public List<DataView> loadImportByYear() {
        return this.getBaseMapper().loadImportByYear();
    }

    @Override
    public List<Proportion> loadImportByGoods() {
        return this.baseMapper.loadImportByGoods();
    }
}
