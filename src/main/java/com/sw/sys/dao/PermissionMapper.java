package com.sw.sys.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sw.sys.pojo.Permission;
import org.apache.ibatis.annotations.Param;

import java.io.Serializable;

/**
 * @description:
 * @author: sw
 * @time: 2020/2/11 11:27
 */
public interface PermissionMapper extends BaseMapper<Permission> {

    void deletePermissionRole(@Param("id") Serializable id);
}
