package com.sw.bus.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sw.bus.pojo.Goods;
import com.sw.bus.pojo.Import;
import com.sw.bus.pojo.Provider;
import com.sw.bus.service.GoodsService;
import com.sw.bus.service.ImportService;
import com.sw.bus.service.ProviderService;
import com.sw.bus.vo.ImportVo;
import com.sw.sys.common.DataGridView;
import com.sw.sys.common.ResultObj;
import com.sw.sys.common.WebUtil;
import com.sw.sys.pojo.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

/**
 * @author ：单威
 * @description： 进货 控制器
 * @date ：Created in 2020/2/21 9:40
 */
@RestController
@RequestMapping(value = "/import")
public class ImportController {

    /**
     * 进货Service 注入
     */
    @Autowired
    private ImportService importService;

    /**
     * 供应商Service 注入
     */
    @Autowired
    private ProviderService providerService;

    /**
     * 商品Service 注入
     */
    @Autowired
    private GoodsService goodsService;

    /**
     * 加载全部进货信息 -- 查询
     *
     * @param importVo
     * @return
     */
    @RequestMapping(value = "/loadAllImport")
    public DataGridView loadAllImport(ImportVo importVo) {
        IPage<Import> page = new Page<>(importVo.getPage(), importVo.getLimit());
        QueryWrapper<Import> wrapper = new QueryWrapper<>();
        wrapper.eq(importVo.getProviderId() != null && importVo.getProviderId() != 0, "providerId", importVo.getProviderId());
        wrapper.eq(importVo.getGoodsId() != null && importVo.getGoodsId() != 0, "goodsId", importVo.getGoodsId());
        wrapper.ge(importVo.getStartTime() != null, "importTime", importVo.getStartTime());
        wrapper.le(importVo.getEndTime() != null, "importTime", importVo.getEndTime());
        wrapper.like(StringUtils.isNotBlank(importVo.getOperatePerson()), "operatePerson", importVo.getOperatePerson());
        wrapper.like(StringUtils.isNotBlank(importVo.getRemark()), "remark", importVo.getRemark());
        wrapper.orderByDesc("importTime");
        this.importService.page(page, wrapper);
        List<Import> records = page.getRecords();
        for (Import im : records) {
            Provider provider = this.providerService.getById(im.getProviderId());
            if (null != provider) {
                im.setProviderName(provider.getProviderName());
            }
            Goods goods = this.goodsService.getById(im.getGoodsId());
            if (null != goods) {
                im.setGoodsName(goods.getGoodsName());
                im.setSize(goods.getSize());
            }
        }
        return new DataGridView(page.getTotal(), records);
    }

    /**
     * 添加进货信息
     *
     * @param importVo
     * @return
     */
    @RequestMapping(value = "/saveImport")
    public ResultObj saveImport(ImportVo importVo) {
        try {
            importVo.setImportTime(new Date());
            User user = (User) WebUtil.getSession().getAttribute("user");
            importVo.setOperatePerson(user.getName());
            this.importService.save(importVo);
            return ResultObj.SAVE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.SAVE_ERROR;
        }
    }

    /**
     * 修改进货信息
     *
     * @param importVo
     * @return
     */
    @RequestMapping(value = "/updImport")
    public ResultObj updImport(ImportVo importVo) {
        try {
            this.importService.updateById(importVo);
            return ResultObj.UPDATE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.UPDATE_ERROR;
        }
    }

    /**
     * 删除进货信息
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/delImport")
    public ResultObj delImport(Integer id) {
        try {
            this.importService.removeById(id);
            return ResultObj.DELETE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.DELETE_ERROR;
        }
    }
}
