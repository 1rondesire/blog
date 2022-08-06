package com.xzq.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xzq.blog.dos.Archives;
import com.xzq.blog.mapper.ArticleBodyMapper;
import com.xzq.blog.mapper.ArticleTagMapper;
import com.xzq.blog.mapper.TagMapper;
import com.xzq.blog.pojo.Article;
import com.xzq.blog.pojo.ArticleBody;
import com.xzq.blog.pojo.ArticleTag;
import com.xzq.blog.pojo.SysUser;
import com.xzq.blog.service.*;
import com.xzq.blog.mapper.ArticleMapper;
import com.xzq.blog.utils.UserThreadLocal;
import com.xzq.blog.vo.*;
import com.xzq.blog.vo.params.ArticleParam;
import com.xzq.blog.vo.params.PageParams;
import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
* @author 94090
* @description 针对表【ms_article】的数据库操作Service实现
* @createDate 2022-07-21 17:10:48
*/
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article>
    implements ArticleService{
    @Autowired
    private ArticleMapper articleMapper;
    @Autowired
    private TagService tagService;
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private ArticleBodyMapper articleBodyMapper;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private ThreadService threadService;
    @Autowired
    private ArticleTagMapper articleTagMapper;

    @Override
    public List<ArticleVo> listArticle(PageParams pageParams) {
        Page<Article> articlePage = new Page<>(pageParams.getPage(), pageParams.getPageSize());
        IPage<Article> articleIPage = this.articleMapper.listArticle(
                 articlePage
                ,pageParams.getCategoryId()
                ,pageParams.getTagId()
                ,pageParams.getYear()
                ,pageParams.getMonth());
        List<Article> records = articleIPage.getRecords();
//        不能直接返回
        List<ArticleVo> articleVoList = copyList(records,true,true);
        return articleVoList;
    }

//    @Override
//    public List<ArticleVo> listArticle(PageParams pageParams) {
//        Page<Article> articlePage = new Page<>(pageParams.getPage(), pageParams.getPageSize());
//        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
//        //加入分类的条件查询
//        if (pageParams.getCategoryId() != null){
//            queryWrapper.eq(Article::getCategoryId,pageParams.getCategoryId());
//        }
//        //加入标签的条件查询
//        List<Long> articleIdList = new ArrayList<>();
//        //article表没用tag字段，需要查询articletag关联表获得该tag对应的articleId（一个文章可以有多个标签）
//        if (pageParams.getTagId() != null){
//            LambdaQueryWrapper<ArticleTag> lambdaQueryWrapper = new LambdaQueryWrapper<>();
//            lambdaQueryWrapper.eq(ArticleTag::getTagId,pageParams.getTagId());
//            List<ArticleTag> articleTags = articleTagMapper.selectList(lambdaQueryWrapper);
//            for (ArticleTag articleTag : articleTags){
//                articleIdList.add(articleTag.getArticleId());
//            }
//            if (articleIdList.size() > 0){
//                // and Id in (xx,x,x,x,x)
//                queryWrapper.in(Article::getId,articleIdList);
//            }
//
//        }
//        按照创建时间倒序排序
//        queryWrapper.orderByDesc(Article::getWeight).orderByDesc(Article::getCreateDate);
//        Page<Article> page = articleMapper.selectPage(articlePage, queryWrapper);
//        List<Article> records = page.getRecords();
////        不能直接返回
//        List<ArticleVo> articleVoList = copyList(records,true,true);
//        return articleVoList;
//    }

    @Override
    public Result hotArticle(int limit) {
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        //select id,title from ms_article order by view_count desc limit 5
        queryWrapper.orderByDesc(Article::getViewCounts).select(Article::getId,Article::getTitle).last("limit "+limit);
        List<Article> articles = articleMapper.selectList(queryWrapper);
        List<ArticleVo> articleVoList = copyList(articles,false,false);
        return Result.success(articleVoList);
    }

    @Override
    public Result newArticle(int limit) {
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        //select id,title from ms_article order by view_count desc limit 5
        queryWrapper.orderByDesc(Article::getCreateDate).select(Article::getId,Article::getTitle).last("limit "+limit);
        List<Article> articles = articleMapper.selectList(queryWrapper);
        List<ArticleVo> articleVoList = copyList(articles,false,false);
        return Result.success(articleVoList);
    }

    @Override
    public Result listArchives() {
        List<Archives> archivesList = articleMapper.listArchives();
        return Result.success(archivesList);
    }

    /**
     * 根据id查询文章信息
     * @param id
     * @return
     */
    @Override
    public Result findArticleById(Long id) {
        Article article = articleMapper.selectById(id);
        ArticleVo articleVo = copy(article, true, true,true,true);
        threadService.updateArticleViewCount(articleMapper,article);
        return Result.success(articleVo);
    }

    /**
     * 文章发布
     * 作者id需要从threadlocal中获取，所以该请求需要加入登陆拦截器中
     * 标签需要加入关联表中
     * 讲文章内容加入articlebody表中，并得到bodyid插入article表中
     * @param articleParam
     * @return
     */
    @Override
    public Result publish(ArticleParam articleParam) {
        Article article;
        boolean isEdit = false;
        if (articleParam.getId() != null){
            article = new Article();
            article.setId(articleParam.getId());
            article.setTitle(articleParam.getTitle());
            article.setSummary(articleParam.getSummary());
            article.setCategoryId(articleParam.getCategory().getId());
            articleMapper.updateById(article);
            isEdit = true;
        }else{
            article = new Article();
            article.setAuthorId(UserThreadLocal.get().getId());
            article.setWeight(Article.Article_Common);
            article.setViewCounts(0);
            article.setTitle(articleParam.getTitle());
            article.setSummary(articleParam.getSummary());
            article.setCommentCounts(0);
            article.setCreateDate(System.currentTimeMillis());
            article.setCategoryId(articleParam.getCategory().getId());
            //先将可以加入的数据加入article表中，得到articleId，以便为关联表进行插入
            articleMapper.insert(article);
        }

        List<TagVo> tags = articleParam.getTags();
        Long articleId = article.getId();
        //将标签加入标签文章关联表中
        if (!isEdit){
            if (tags != null){
                for (TagVo tag : tags){
                    ArticleTag articleTag = new ArticleTag();
                    articleTag.setArticleId(articleId);
                    articleTag.setTagId(tag.getId());
                    articleTagMapper.insert(articleTag);
                }
            }
        }else{
            LambdaUpdateWrapper<ArticleTag> updateWrapper = new LambdaUpdateWrapper<>();
            updateWrapper.eq(ArticleTag::getArticleId,articleId);
            articleTagMapper.delete(updateWrapper);
            if (tags != null){
                for (TagVo tag : tags){
                    ArticleTag articleTag = new ArticleTag();
                    articleTag.setArticleId(articleId);
                    articleTag.setTagId(tag.getId());
                    articleTagMapper.insert(articleTag);
                }
            }
        }
        ArticleBody articleBody = new ArticleBody();
        //将文章内容加入articlebody表中
        articleBody.setArticleId(articleId);
        articleBody.setContent(articleParam.getBody().getContent());
        articleBody.setContentHtml(articleParam.getBody().getContentHtml());
        if (!isEdit){
            articleBodyMapper.insert(articleBody);
        }else {
            LambdaQueryWrapper<ArticleBody> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(ArticleBody::getArticleId,articleId);
            ArticleBody body = articleBodyMapper.selectOne(queryWrapper);
            Long id = body.getId();
            articleBody.setId(id);
            articleBodyMapper.updateById(articleBody);
        }
        //得到bodyid，插入article表中
        Long articleBodyId = articleBody.getId();
        article.setBodyId(articleBodyId);
        //更新还未插入aiticle表中的数据
        articleMapper.updateById(article);
        //返回值需要一个k:y对象 id:xxxxx
        Map<String, String> map = new HashMap<>();
        //不使用tostring可能会造成精度损失
        map.put("id",article.getId().toString());
        return Result.success(map);
    }

    @Override
    public Result search(String searchstr) {
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(Article::getTitle,searchstr).select(Article::getId,Article::getTitle);
        List<Article> articles = articleMapper.selectList(queryWrapper);
        List<ArticleVo> articleVoList = copyList(articles,false,false);
        return Result.success(articleVoList);
    }

    //    将List<Article>变为List<ArticleVo>
    private List<ArticleVo> copyList(List<Article> records,boolean isTag,boolean isAuthor) {
        List<ArticleVo> articleVoList = new ArrayList<>();
        for (Article article : records){
            ArticleVo articleVo = copy(article,isTag,isAuthor,false,false);
            articleVoList.add(articleVo);
        }
        return articleVoList;
    }
    //重载copyList
    private List<ArticleVo> copyList(List<Article> records,boolean isTag,boolean isAuthor,boolean isBody,boolean isCategory) {
        List<ArticleVo> articleVoList = new ArrayList<>();
        for (Article article : records){
            ArticleVo articleVo = copy(article,isTag,isAuthor,isBody,isCategory);
            articleVoList.add(articleVo);
        }
        return articleVoList;
    }
//    将List<Article>中的一个个Article变为ArticleVo
    private ArticleVo copy(Article article,boolean isTag,boolean isAuthor,boolean isBody,boolean isCategory){
        ArticleVo articleVo = new ArticleVo();
        BeanUtils.copyProperties(article,articleVo);
        articleVo.setCreateDate(new DateTime(article.getCreateDate()).toString("yyyy-MM-dd HH:mm"));
        if (isTag){
            Long articleId = article.getId();
            articleVo.setTags(tagService.findTagsByArticle(articleId));
        }
        if (isAuthor){
            Long authorId = article.getAuthorId();
            SysUser sysUser = sysUserService.findUserById(authorId);
            UserVo userVo = new UserVo();
            BeanUtils.copyProperties(sysUser,userVo);
            articleVo.setAuthor(userVo);
        }
        if (isBody){
            Long bodyId = article.getBodyId();
            articleVo.setBody(findArticleBodyById(bodyId));
        }
        if (isCategory){
            Long categoryId = article.getCategoryId();
            articleVo.setCategory(categoryService.findCategoryById(categoryId));
        }
        return articleVo;
    }

    private ArticleBodyVo findArticleBodyById(Long bodyId) {
        ArticleBody articleBody = articleBodyMapper.selectById(bodyId);
        ArticleBodyVo articleBodyVo = new ArticleBodyVo();
        articleBodyVo.setContent(articleBody.getContent());
        return articleBodyVo;
    }
}




