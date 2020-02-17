package com.sw.sys.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sw.sys.dao.PermissionMapper;
import com.sw.sys.pojo.Permission;
import com.sw.sys.service.PermissionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;

/**
 * @description:
 * @author: sw
 * @time: 2020/2/14 16:37
 */
@Service
@Transactional
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements PermissionService {

    @Override
    public boolean removeById(Serializable id) {

        PermissionMapper permissionMapper  = this.getBaseMapper();
        permissionMapper.deletePermissionRole(id);

        return super.removeById(id);
    }
}
