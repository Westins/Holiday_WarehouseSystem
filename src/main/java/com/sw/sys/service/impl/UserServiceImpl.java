package com.sw.sys.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sw.sys.dao.RoleMapper;
import com.sw.sys.dao.UserMapper;
import com.sw.sys.pojo.User;
import com.sw.sys.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;

/**
 * @description: 用户 业务实现类
 * @author: 单威
 * @time: 2020/2/10 10:16
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public boolean removeById(Serializable id) {
        //根据用户ID删除用户角色中间表的数据
        roleMapper.deleteRoleUserByUid(id);
        return super.removeById(id);
    }

    @Override
    public void saveUserRole(Integer uid, Integer[] ids) {
        //根据用户ID删除sys_role_user里面的数据
        this.roleMapper.deleteRoleUserByUid(uid);
        if(null!=ids&&ids.length>0) {
            for (Integer rid : ids) {
                this.roleMapper.insertUserRole(uid,rid);
            }
        }
    }

    @Override
    public boolean updateById(User entity) {
        return super.updateById(entity);
    }

    @Override
    public boolean save(User entity) {
        return super.save(entity);
    }

    @Override
    public User getById(Serializable id) {
        return super.getById(id);
    }
}
