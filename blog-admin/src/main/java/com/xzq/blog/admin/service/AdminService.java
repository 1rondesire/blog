package com.xzq.blog.admin.service;

import com.xzq.blog.admin.mapper.AdminMapper;
import com.xzq.blog.admin.pojo.Admin;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xzq.blog.admin.pojo.Permission;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
* @author 94090
* @description 针对表【ms_admin】的数据库操作Service
* @createDate 2022-07-24 18:44:51
*/
public interface AdminService extends IService<Admin> {
    public Admin findAdminByUsername(String username);

    List<Permission> findPermissionByadminId(Long id);
}
