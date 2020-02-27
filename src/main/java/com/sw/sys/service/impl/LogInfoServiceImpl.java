package com.sw.sys.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sw.sys.dao.LogInfoMapper;
import com.sw.sys.pojo.LogInfo;
import com.sw.sys.service.LogInfoService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @description: 登录日志 业务实现类
 * @author: 单威
 * @time: 2020/2/10 10:16
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class LogInfoServiceImpl extends ServiceImpl<LogInfoMapper, LogInfo> implements LogInfoService {
}
