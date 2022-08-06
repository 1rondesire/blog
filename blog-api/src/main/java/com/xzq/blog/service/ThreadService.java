package com.xzq.blog.service;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.xzq.blog.mapper.ArticleMapper;
import com.xzq.blog.pojo.Article;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class ThreadService {

    @Async("taskExcutor")
    public void updateArticleViewCount(ArticleMapper articleMapper, Article article){
        Integer viewCounts = article.getViewCounts();
        Article articleupdate = new Article();
        articleupdate.setViewCounts(viewCounts+1);
        LambdaUpdateWrapper<Article> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(Article::getId,article.getId()).eq(Article::getViewCounts,viewCounts);
        articleMapper.update(articleupdate,updateWrapper);
    }
}
