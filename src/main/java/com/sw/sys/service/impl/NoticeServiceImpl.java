package com.sw.sys.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sw.sys.dao.NoticeMapper;
import com.sw.sys.pojo.Notice;
import com.sw.sys.service.NoticeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @description: 系统公告  业务实现类
 * @author: 单威
 * @time: 2020/2/10 10:16
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class NoticeServiceImpl extends ServiceImpl<NoticeMapper, Notice> implements NoticeService {

}
