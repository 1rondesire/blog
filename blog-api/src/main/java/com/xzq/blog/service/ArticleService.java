package com.xzq.blog.service;

import com.xzq.blog.pojo.Article;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xzq.blog.vo.ArticleVo;
import com.xzq.blog.vo.Result;
import com.xzq.blog.vo.params.ArticleParam;
import com.xzq.blog.vo.params.PageParams;

import java.util.List;

/**
* @author 94090
* @description 针对表【ms_article】的数据库操作Service
* @createDate 2022-07-21 17:10:48
*/
public interface ArticleService extends IService<Article> {
    public List<ArticleVo> listArticle(PageParams pageParams);

    Result hotArticle(int limit);

    Result newArticle(int limit);

    Result listArchives();

    Result findArticleById(Long id);

    Result publish(ArticleParam articleParam);

    Result search(String searchstr);
}
