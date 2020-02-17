package com.sw.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sw.sys.pojo.Role;

import java.util.List;

/**
 * @description:
 * @author: sw
 * @time: 2020/2/16 14:57
 */
public interface RoleService extends IService<Role> {
    List<Integer> getRolePermissionByRid(Integer roleId);

    void saveRolePermission(Integer rid, Integer[] ids);
}
