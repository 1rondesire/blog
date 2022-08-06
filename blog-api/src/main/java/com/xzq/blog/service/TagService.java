package com.xzq.blog.service;

import com.xzq.blog.pojo.Tag;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xzq.blog.vo.Result;
import com.xzq.blog.vo.TagVo;

import java.util.List;

/**
* @author 94090
* @description 针对表【ms_tag】的数据库操作Service
* @createDate 2022-07-21 17:19:34
*/
public interface TagService extends IService<Tag> {
    List<TagVo> findTagsByArticle(Long articleId);
    Result hots(int limit);

    Result findAll();

    Result finddetailAll();

    Result finddetailById(Long id);
}
