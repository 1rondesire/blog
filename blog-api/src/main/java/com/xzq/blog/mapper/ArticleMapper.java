package com.xzq.blog.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xzq.blog.dos.Archives;
import com.xzq.blog.pojo.Article;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
* @author 94090
* @description 针对表【ms_article】的数据库操作Mapper
* @createDate 2022-07-21 17:10:48
* @Entity com.xzq.blog.pojo.Article
*/
public interface ArticleMapper extends BaseMapper<Article> {

    List<Archives> listArchives();

    IPage<Article> listArticle(Page<Article> articlePage, Long categoryId, Long tagId, String year, String month);
}




