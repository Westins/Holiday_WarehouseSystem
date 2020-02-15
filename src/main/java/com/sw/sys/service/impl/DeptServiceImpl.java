package com.sw.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sw.sys.dao.DeptMapper;
import com.sw.sys.pojo.Dept;
import com.sw.sys.service.DeptService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;

/**
 * @description:
 * @author: sw
 * @time: 2020/2/14 10:23
 */
@Service
@Transactional
public class DeptServiceImpl extends ServiceImpl<DeptMapper, Dept> implements DeptService {

    @Override
    public Dept getOne(Wrapper<Dept> queryWrapper) {
        return super.getOne(queryWrapper);
    }

    @Override
    public boolean updateById(Dept entity) {return super.updateById(entity);}

    @Override
    public boolean removeById(Serializable id) {
        return super.removeById(id);
    }
}
