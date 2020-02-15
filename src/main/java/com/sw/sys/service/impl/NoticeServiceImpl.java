package com.sw.sys.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sw.sys.dao.NoticeMapper;
import com.sw.sys.dao.UserMapper;
import com.sw.sys.pojo.Notice;
import com.sw.sys.pojo.User;
import com.sw.sys.service.NoticeService;
import com.sw.sys.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @description:
 * @author: sw
 * @time: 2020/2/10 10:16
 */
@Service
@Transactional
public class NoticeServiceImpl extends ServiceImpl<NoticeMapper, Notice> implements NoticeService {

}
