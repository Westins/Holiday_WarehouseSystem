package com.sw.sys.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sw.sys.dao.PermissionMapper;
import com.sw.sys.pojo.Permission;
import com.sw.sys.service.PermissionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;

/**
 * @description: 菜单 业务实现类
 * @author: 单威
 * @time: 2020/2/14 16:37
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements PermissionService {

    @Override
    public boolean removeById(Serializable id) {
        //根据权限或菜单ID删除权限表各和角色的关系表里面的数据
        this.getBaseMapper().deletePermissionRole(id);
        return super.removeById(id);
    }
}
