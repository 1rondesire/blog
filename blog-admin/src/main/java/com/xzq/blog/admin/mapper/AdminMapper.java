package com.xzq.blog.admin.mapper;

import com.xzq.blog.admin.pojo.Admin;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xzq.blog.admin.pojo.Permission;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
* @author 94090
* @description 针对表【ms_admin】的数据库操作Mapper
* @createDate 2022-07-24 18:44:51
* @Entity com.xzq.blog.admin.pojo.Admin
*/
public interface AdminMapper extends BaseMapper<Admin> {
    @Select("select * from ms_permission where id in (select permission_id from ms_admin_permission where admin_id = #{id} )")
    List<Permission> findPermissionByadminId(Long id);
}




