package com.xzq.blog.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xzq.blog.admin.params.PageParam;
import com.xzq.blog.admin.pojo.Permission;
import com.xzq.blog.admin.mapper.PermissionMapper;
import com.xzq.blog.admin.service.PermissionService;
import com.xzq.blog.admin.vo.PageResult;
import com.xzq.blog.admin.vo.Result;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author 94090
* @description 针对表【ms_permission】的数据库操作Service实现
* @createDate 2022-07-24 18:45:15
*/
@Service
public class PermissionServiceImpl implements PermissionService {
    @Autowired
    PermissionMapper permissionMapper;

    @Override
    public Result listpermission(PageParam pageParam) {

        Page<Permission> permissionPage = new Page<>(pageParam.getCurrentPage(),pageParam.getPageSize());
        LambdaQueryWrapper<Permission> queryWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNotEmpty(pageParam.getQueryString())){
            queryWrapper.eq(Permission::getName,pageParam.getQueryString());
        }
        Page<Permission> page = permissionMapper.selectPage(permissionPage, queryWrapper);
        List<Permission> records = page.getRecords();
        PageResult<Permission> pageResult = new PageResult<>();
        pageResult.setList(records);
        pageResult.setTotal(page.getTotal());
        return Result.success(pageResult);
    }

    @Override
    public Result add(Permission permission) {
        permissionMapper.insert(permission);
        return Result.success(null);
    }

    @Override
    public Result update(Permission permission) {
        permissionMapper.updateById(permission);
        return Result.success(null);
    }

    @Override
    public Result delete(Long id) {
        permissionMapper.deleteById(id);
        return Result.success(null);
    }
}




