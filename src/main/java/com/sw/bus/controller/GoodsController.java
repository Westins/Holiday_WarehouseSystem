package com.sw.bus.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sw.bus.pojo.Goods;
import com.sw.bus.pojo.Provider;
import com.sw.bus.service.GoodsService;
import com.sw.bus.service.ProviderService;
import com.sw.bus.vo.CustomerVo;
import com.sw.bus.vo.GoodsVo;
import com.sw.sys.common.DataGridView;
import com.sw.sys.common.ResultObj;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * @author ：单威
 * @description： 商品管理 控制器
 * @date ：Created in 2020/2/20 10:28
 */
@RestController
@RequestMapping(value = "/goods")
public class GoodsController {

    /**
     * 商品Service 注入
     */
    @Autowired
    private GoodsService goodsService;

    /**
     * 供应商Service 注入
     */
    @Autowired
    private ProviderService providerService;

    /**
     * 加载商品信息 -- 查询
     *
     * @param goodsVo
     * @return
     */
    @RequestMapping(value = "/loadAllGoods")
    public DataGridView loadAllGoods(GoodsVo goodsVo) {
        QueryWrapper<Goods> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(goodsVo.getProviderId() != null && goodsVo.getProviderId() != 0, "providerId", goodsVo.getProviderId());
        queryWrapper.like(StringUtils.isNotBlank(goodsVo.getGoodsName()), "goodsName", goodsVo.getGoodsName());
        queryWrapper.like(StringUtils.isNotBlank(goodsVo.getProductCode()), "productCode", goodsVo.getProductCode());
        queryWrapper.like(StringUtils.isNotBlank(goodsVo.getApprovalCode()), "approvalCode", goodsVo.getApprovalCode());
        queryWrapper.like(StringUtils.isNotBlank(goodsVo.getDescription()), "description", goodsVo.getDescription());
        queryWrapper.like(StringUtils.isNotBlank(goodsVo.getSize()), "size", goodsVo.getSize());
        IPage<Goods> page = new Page<>(goodsVo.getPage(), goodsVo.getLimit());

        this.goodsService.page(page, queryWrapper);
        List<Goods> records = page.getRecords();

        for (Goods goods : records) {
            Provider provider = this.providerService.getById(goods.getProviderId());
            if (null != provider) {
                goods.setProviderName(provider.getProviderName());
            }
        }

        return new DataGridView(page.getTotal(), records);
    }

    /**
     * 添加商品信息
     *
     * @param goodsVo
     * @return
     */
    @RequestMapping(value = "/saveGoods")
    public ResultObj saveGoods(GoodsVo goodsVo) {
        try {
            goodsVo.setTime(new Date());
            this.goodsService.save(goodsVo);
            return ResultObj.SAVE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.SAVE_ERROR;
        }
    }

    /**
     * 修改商品信息
     *
     * @param goodsVo
     * @return
     */
    @RequestMapping(value = "/updGoods")
    public ResultObj updGoods(GoodsVo goodsVo) {
        try {
            this.goodsService.updateById(goodsVo);
            return ResultObj.UPDATE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.UPDATE_ERROR;
        }
    }

    /**
     * 删除商品信息
     * 单删
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/delGoods")
    public ResultObj delGoods(Integer id) {
        try {
            this.goodsService.removeById(id);
            return ResultObj.DELETE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.DELETE_ERROR;
        }
    }

    /**
     * 删除商品信息
     * 多删
     *
     * @param goodsVo
     * @return
     */
    @RequestMapping("/batchDeleteGoods")
    public ResultObj batchDeleteGoods(GoodsVo goodsVo) {
        try {
            Collection<Serializable> idList = new ArrayList<Serializable>();
            for (Integer id : goodsVo.getIds()) {
                idList.add(id);
            }
            this.goodsService.removeByIds(idList);
            return ResultObj.DELETE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.DELETE_ERROR;
        }
    }
}
