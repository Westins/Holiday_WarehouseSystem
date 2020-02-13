package com.sw.sys.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sw.sys.common.DataGridView;
import com.sw.sys.common.ResultObj;
import com.sw.sys.common.WebUtil;
import com.sw.sys.pojo.LogInfo;
import com.sw.sys.pojo.Notice;
import com.sw.sys.pojo.User;
import com.sw.sys.service.impl.LogInfoServiceImpl;
import com.sw.sys.service.impl.NoticeServiceImpl;
import com.sw.sys.vo.LogInfoVo;
import com.sw.sys.vo.NoticeVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

/**
 * @description: 系统公告 控制器
 * @author: sw
 * @time: 2020/2/11 16:57
 */
@RestController
@RequestMapping(value = "/notice")
public class NoticeController {

    @Autowired
    private NoticeServiceImpl noticeServiceImpl;

    /**
     * 加载所有 系统公告信息
     *
     * @param noticeVo
     * @return
     */
    @RequestMapping(value = "/loadAllNotice")
    public DataGridView loadAllLogInfo(NoticeVo noticeVo) {
        QueryWrapper<Notice> wrapper = new QueryWrapper<>();
        // 模糊查询 标题
        wrapper.like(StringUtils.isNotBlank(noticeVo.getTitle()), "title", noticeVo.getTitle());
        // 模糊查询 发布人
        wrapper.like(StringUtils.isNotBlank(noticeVo.getCreateName()), "createName", noticeVo.getCreateName());
        // 起初时间
        wrapper.ge(noticeVo.getStartTime() != null, "createTime", noticeVo.getStartTime());
        // 结束时间
        wrapper.le(noticeVo.getEndTime() != null, "createTime", noticeVo.getEndTime());
        // 时间倒序查询
        wrapper.orderByDesc("createTime");

        // 分页查询
        IPage<Notice> page = new Page<>(noticeVo.getPage(), noticeVo.getLimit());


        this.noticeServiceImpl.page(page, wrapper);
        return new DataGridView(page.getTotal(), page.getRecords());
    }

    /**
     * 添加公告
     *
     * @param noticeVo
     * @return
     */
    @RequestMapping(value = "/saveNotice")
    public ResultObj saveNotice(NoticeVo noticeVo) {
        try {
            User user = (User) WebUtil.getSession().getAttribute("user");
            noticeVo.setCreateName(user.getName());
            noticeVo.setCreateTime(new Date());

            this.noticeServiceImpl.save(noticeVo);
            return ResultObj.SAVE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.SAVE_ERROR;
        }

    }

    /**
     * 公告修改
     *
     * @param noticeVo
     * @return
     */
    @RequestMapping(value = "/updNotice")
    public ResultObj updNotice(NoticeVo noticeVo) {
        try {
            this.noticeServiceImpl.updateById(noticeVo);
            return ResultObj.UPDATE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.UPDATE_ERROR;
        }
    }

    /**
     * 系统公告 - 删除
     * 单个删除
     *
     * @param noticeVo
     * @return
     */
    @RequestMapping(value = "/delNotice")
    public ResultObj delNotice(NoticeVo noticeVo) {
        try {
            this.noticeServiceImpl.removeById(noticeVo);
            return ResultObj.DELETE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.DELETE_ERROR;
        }
    }

    /**
     * 登录日志 - 删除
     * 批量多删
     *
     * @param noticeVo
     * @return
     */
    @RequestMapping("/batchDeleteNotice")
    public ResultObj batchDeleteNotice(NoticeVo noticeVo) {
        try {
            Collection<Serializable> idList=new ArrayList<>();
            for (Integer id : noticeVo.getIds()) {
                idList.add(id);
            }
            this.noticeServiceImpl.removeByIds(idList);
            return ResultObj.DELETE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.DELETE_ERROR;
        }
    }
}
