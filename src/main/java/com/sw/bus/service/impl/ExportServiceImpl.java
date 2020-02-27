package com.sw.bus.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sw.bus.dao.ExportMapper;
import com.sw.bus.dao.GoodsMapper;
import com.sw.bus.dao.ImportMapper;
import com.sw.sys.common.DataView;
import com.sw.bus.pojo.Export;
import com.sw.bus.pojo.Goods;
import com.sw.bus.pojo.Import;
import com.sw.bus.service.ExportService;
import com.sw.sys.common.Proportion;
import com.sw.sys.common.WebUtil;
import com.sw.sys.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @author ：单威
 * @description： 业务 实现层
 * @date ：Created in 2020/2/21 11:48
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class ExportServiceImpl extends ServiceImpl<ExportMapper, Export> implements ExportService {

    @Autowired
    private ImportMapper importMapper;

    @Autowired
    private GoodsMapper goodsMapper;

    @Override
    public void addExPort(Integer id, Integer number, String remark) {
        //1,根据进货单ID查询进货单信息
        Import im = this.importMapper.selectById(id);
        //2,根据商品ID查询商品信息
        Goods goods = this.goodsMapper.selectById(im.getGoodsId());
        goods.setNumber(goods.getNumber() - number);
        this.goodsMapper.updateById(goods);
        //3,添加退货单信息
        Export entity = new Export();
        entity.setGoodsId(im.getGoodsId());
        entity.setNumber(number);
        User user = (User) WebUtil.getSession().getAttribute("user");
        entity.setOperatePerson(user.getName());
        entity.setExportPrice(im.getImportPrice());
        entity.setExportTime(new Date());
        entity.setPayType(im.getPayType());
        entity.setProviderId(im.getProviderId());
        entity.setRemark(remark);
        this.getBaseMapper().insert(entity);
    }

    @Override
    public Integer loadExportByNow() {
        return this.baseMapper.loadExportByNow();
    }

    @Override
    public List<DataView> loadExportByYear() {
       return this.baseMapper.loadExportByYear();
    }

    @Override
    public List<Proportion> loadExportByGoods() {
        return this.baseMapper.loadExportByGoods();
    }
}
