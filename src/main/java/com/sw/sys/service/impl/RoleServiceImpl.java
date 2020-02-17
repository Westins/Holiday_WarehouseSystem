package com.sw.sys.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sw.sys.dao.RoleMapper;
import com.sw.sys.pojo.Role;
import com.sw.sys.service.RoleService;
import com.sw.sys.vo.PermissionVo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;

/**
 * @description:
 * @author: sw
 * @time: 2020/2/16 14:57
 */
@Service
@Transactional
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    @Override
    public boolean removeById(Serializable id) {
        this.baseMapper.deleteRolePermissionByRid(id);
        this.baseMapper.deleteRoleUserByRid(id);
        return super.removeById(id);
    }

    public List<Integer> getRolePermissionByRid(Integer roleId) {
        return this.baseMapper.getRolePermissionByRid(roleId);
    }

    @Override
    public void saveRolePermission(Integer rid, Integer[] ids) {
        RoleMapper roleMapper = this.getBaseMapper();
        roleMapper.deleteRolePermissionByRid(rid);
        if(ids != null || ids.length > 0){
            for (Integer pid : ids) {
                roleMapper.saveRolePermission(rid, pid);
            }
        }
    }
}
