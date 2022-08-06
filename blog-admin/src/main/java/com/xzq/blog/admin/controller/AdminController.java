package com.xzq.blog.admin.controller;

import com.xzq.blog.admin.params.PageParam;
import com.xzq.blog.admin.pojo.Permission;
import com.xzq.blog.admin.service.PermissionService;
import com.xzq.blog.admin.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("admin")
public class AdminController {
    @Autowired
    private PermissionService permissionService;


    @PostMapping("permission/permissionList")
    public Result listpermission(@RequestBody PageParam pageParam){
        return permissionService.listpermission(pageParam);
    }
    @PostMapping("permission/add")
    public Result add(@RequestBody Permission permission){
        return permissionService.add(permission);
    }

    @PostMapping("permission/update")
    public Result update(@RequestBody Permission permission){
        return permissionService.update(permission);
    }

    @GetMapping("permission/delete/{id}")
    public Result delete(@PathVariable("id") Long id){
        return permissionService.delete(id);
    }
}
