package com.sw.sys.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sw.sys.dao.LogInfoMapper;
import com.sw.sys.dao.UserMapper;
import com.sw.sys.pojo.LogInfo;
import com.sw.sys.pojo.User;
import com.sw.sys.service.LogInfoService;
import com.sw.sys.service.UserService;
import org.springframework.stereotype.Service;

/**
 * @description:
 * @author: sw
 * @time: 2020/2/10 10:16
 */
@Service
public class LogInfoServiceImpl extends ServiceImpl<LogInfoMapper, LogInfo> implements LogInfoService {

}
