package com.sw.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sw.sys.pojo.User;

/**
 * @description:
 * @author: sw
 * @time: 2020/2/10 10:16
 */
public interface UserService extends IService<User> {
    void saveUserRole(Integer uid, Integer[] ids);
}
