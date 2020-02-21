package com.sw.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sw.sys.pojo.User;

/**
 * @description: 用户 业务层
 * @author: 单威
 * @time: 2020/2/10 10:16
 */
public interface UserService extends IService<User> {
    void saveUserRole(Integer uid, Integer[] ids);
}
