package com.xzq.blog.mapper;

import com.xzq.blog.pojo.Tag;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
* @author 94090
* @description 针对表【ms_tag】的数据库操作Mapper
* @createDate 2022-07-21 17:19:34
* @Entity com.xzq.blog.pojo.Tag
*/
public interface TagMapper extends BaseMapper<Tag> {
    List<Tag> findTagsByArticle(Long articleId);

    List<Long> findHotsTagIds(int limit);

    List<Tag> findTagsByTagIds(List<Long> tagIds);
}




