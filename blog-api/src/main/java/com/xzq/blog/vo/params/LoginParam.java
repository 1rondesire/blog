package com.xzq.blog.vo.params;

import lombok.Data;
import org.springframework.data.redis.connection.ReactiveSetCommands;


@Data
public class LoginParam {
    private String account;
    private String password;
    private String nickname;
}
