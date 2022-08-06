package com.xzq.blog.admin.service;

import com.xzq.blog.admin.params.PageParam;
import com.xzq.blog.admin.pojo.Permission;
import com.xzq.blog.admin.vo.Result;

public interface PermissionService {
    Result listpermission(PageParam pageParam);

    Result add(Permission permission);

    Result update(Permission permission);

    Result delete(Long id);
}
