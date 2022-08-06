package com.xzq.blog.service;

import com.xzq.blog.pojo.SysUser;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xzq.blog.vo.Result;
import com.xzq.blog.vo.UserVo;
import jdk.nashorn.internal.parser.Token;

/**
* @author 94090
* @description 针对表【ms_sys_user】的数据库操作Service
* @createDate 2022-07-21 17:18:54
*/
public interface SysUserService{
    Result findUserByToken(String token);

    SysUser findUserById(Long id);

    SysUser findUser(String account, String password);

    SysUser findUserByAccount(String account);

    void save(SysUser sysUser);

    SysUser checkUserByToken(String token);

    UserVo findUserVoById(Long authorId);
}
