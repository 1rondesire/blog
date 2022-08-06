package com.xzq.blog.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xzq.blog.common.aop.LogAnnotation;
import com.xzq.blog.common.cache.Cache;
import com.xzq.blog.pojo.Article;
import com.xzq.blog.service.ArticleService;
import com.xzq.blog.vo.ArticleVo;
import com.xzq.blog.vo.Result;
import com.xzq.blog.vo.params.ArticleParam;
import com.xzq.blog.vo.params.PageParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("articles")
public class ArticleController {
    @Autowired
    ArticleService articleService;


    @PostMapping("search")
    public Result search(@RequestBody ArticleParam articleParam){
        String search = articleParam.getSearch();
        return articleService.search(search);
    }


    @PostMapping("{id}")
    public Result Articleedit(@PathVariable("id") Long id){
        return articleService.findArticleById(id);
    }

    /**
     * 首页 文章列表
     * @param pageParams
     * @return
     */
    @PostMapping
    //自己定义的注解，代表要对这个接口记录日志
    @LogAnnotation (module="文章", operator="获取文章列表")
    public Result listArticle(@RequestBody PageParams pageParams){
        List<ArticleVo> article = articleService.listArticle(pageParams);
        return Result.success(article);
    }

    /**
     * 获取最热文章
     * @return
     */
    @PostMapping("hot")
    @Cache(expire = 5 * 60 * 1000,name = "hot_article")
    public Result hotArticle(){
        int limit = 5;
        return articleService.hotArticle(limit);
    }

    /**
     * 获取最新文章
     * @return
     */
    @Cache(expire = 5 * 60 * 1000,name = "new_article")
    @PostMapping("new")
    public Result newArticle(){
        int limit = 5;
        return articleService.newArticle(limit);
    }
    /**
     * 文章归档
     * xx年xx月有xx篇文章
     * @return
     */
    @PostMapping("listArchives")
    public Result listArchives(){
        return articleService.listArchives();
    }

    /**
     * 查询文章内容主体
     * @param id
     * @return
     */
    @PostMapping("view/{id}")
    public Result findArticleById(@PathVariable("id") Long id) {
        return articleService.findArticleById(id);
    }

    @PostMapping("publish")
    public Result publish(@RequestBody ArticleParam articleParam){
        return articleService.publish(articleParam);
    }
}
