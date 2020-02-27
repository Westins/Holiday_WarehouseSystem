package com.sw.sys.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sw.sys.dao.RoleMapper;
import com.sw.sys.pojo.Role;
import com.sw.sys.service.RoleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;

/**
 * @description: 角色 业务实现类
 * @author: 单威
 * @time: 2020/2/16 14:57
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {


    @Override
    public boolean removeById(Serializable id) {
        //根据角色ID删除sys_role_permission
        this.baseMapper.deleteRolePermissionByRid(id);
        //根据角色ID删除sys_role_user
        this.baseMapper.deleteRoleUserByRid(id);
        return super.removeById(id);
    }

    /**
     * 根据角色ID查询当前角色拥有的所有的权限或菜单ID
     */
    @Override
    public List<Integer> getRolePermissionByRid(Integer roleId) {
        return this.baseMapper.getRolePermissionByRid(roleId);
    }

    /**
     * 保存角色和菜单权限之间的关系
     */
    @Override
    public void saveRolePermission(Integer rid, Integer[] ids) {
        RoleMapper roleMapper = this.getBaseMapper();
        roleMapper.deleteRolePermissionByRid(rid);
        if (ids != null && ids.length > 0) {
            for (Integer pid : ids) {
                roleMapper.saveRolePermission(rid, pid);
            }
        }
    }

    /**
     * 查询当前用户拥有的角色ID集合
     */
    @Override
    public List<Integer> queryUserRoleIdsByUid(Integer id) {
        return this.baseMapper.queryUserRoleIdsByUid(id);
    }
}
