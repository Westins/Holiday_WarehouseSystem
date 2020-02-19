package com.sw.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sw.sys.pojo.Role;

import java.io.Serializable;
import java.util.List;

/**
 * @description: 角色 业务层
 * @author: 单威
 * @time: 2020/2/16 14:57
 */
public interface RoleService extends IService<Role> {

    /**
     * 根据角色ID查询当前角色拥有的所有的权限或菜单ID
     * @param roleId
     * @return
     */
    List<Integer> getRolePermissionByRid(Integer roleId);

    /**
     * 保存角色和菜单权限之间的关系
     * @param rid
     * @param ids
     */
    void saveRolePermission(Integer rid, Integer[] ids);

    /**
     * 查询当前用户拥有的角色ID集合
     * @param id
     * @return
     */
    List<Integer> queryUserRoleIdsByUid(Integer id);
}
