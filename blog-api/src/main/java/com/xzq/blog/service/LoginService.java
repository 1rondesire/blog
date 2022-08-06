package com.xzq.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xzq.blog.vo.Result;
import com.xzq.blog.vo.params.LoginParam;
import org.springframework.stereotype.Service;

public interface LoginService {
    Result register(LoginParam loginParam);

    Result login(LoginParam loginParam);

    Result logout(String token);
}
