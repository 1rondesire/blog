package com.xzq.blog.admin.service;

import com.xzq.blog.admin.pojo.Admin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class SecurityUserService implements UserDetailsService {
    @Autowired
    AdminService adminService;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Admin adminByUsername = adminService.findAdminByUsername(username);
        if (adminByUsername == null){
            return null;
        }
        UserDetails userDetails = new User(username,adminByUsername.getPassword(),new ArrayList<>());
        return userDetails;
    }
}
