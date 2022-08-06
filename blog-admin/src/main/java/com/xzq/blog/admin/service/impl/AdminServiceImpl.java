package com.xzq.blog.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xzq.blog.admin.pojo.Admin;
import com.xzq.blog.admin.pojo.Permission;
import com.xzq.blog.admin.service.AdminService;
import com.xzq.blog.admin.mapper.AdminMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author 94090
* @description 针对表【ms_admin】的数据库操作Service实现
* @createDate 2022-07-24 18:44:51
*/
@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin>
    implements AdminService{
    @Autowired
    AdminMapper adminMapper;

    public Admin findAdminByUsername(String username){
        LambdaQueryWrapper<Admin> adminLambdaQueryWrapper = new LambdaQueryWrapper<>();
        adminLambdaQueryWrapper.eq(Admin::getUsername,username).last("limit 1");
        Admin admin = adminMapper.selectOne(adminLambdaQueryWrapper);
        return admin;
    }

    @Override
    public List<Permission> findPermissionByadminId(Long id) {
        return adminMapper.findPermissionByadminId(id);
    }
}




