package com.sw.sys.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sw.sys.pojo.Role;
import com.sw.sys.vo.PermissionVo;
import org.apache.ibatis.annotations.Param;

import java.io.Serializable;
import java.util.List;

/**
 * @description:
 * @author: sw
 * @time: 2020/2/16 14:56
 */
public interface RoleMapper extends BaseMapper<Role> {
    void deleteRolePermissionByRid(Serializable id);

    void deleteRoleUserByRid(Serializable id);

    List<Integer> getRolePermissionByRid(Integer roleId);

    void saveRolePermission(@Param("rid") Integer rid, @Param("pid") Integer pid);
}
