package com.xzq.blog.admin.service;

import com.xzq.blog.admin.pojo.Admin;
import com.xzq.blog.admin.pojo.Permission;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
public class AutoService {
    @Autowired
    AdminService adminService;
    public boolean auth(HttpServletRequest request , Authentication authentication){
        String requestURI = request.getRequestURI();
        Object principal = authentication.getPrincipal();
        if (principal == null || "anonymousUser".equals(principal)){
            //未登录
            return false;
        }
        UserDetails userDetails = (UserDetails) principal;
        String username = userDetails.getUsername();
        Admin admin = adminService.findAdminByUsername(username);
        if (admin.getId()==1){
            return true;
        }
        if (admin == null){
            return false;
        }
        Long id = admin.getId();
        List<Permission> permissions = adminService.findPermissionByadminId(id);
        requestURI = StringUtils.split(requestURI,"?")[0];
        for (Permission permission:permissions){
            if (requestURI.equals(permission.getPath())){
                return true;
            }
        }
        return false;
    }
}
