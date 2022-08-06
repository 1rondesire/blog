package com.xzq.blog.service;

import com.xzq.blog.pojo.Comment;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xzq.blog.vo.Result;
import com.xzq.blog.vo.params.CommentParam;

/**
* @author 94090
* @description 针对表【ms_comment】的数据库操作Service
* @createDate 2022-07-23 21:02:07
*/
public interface CommentService extends IService<Comment> {
    /**
     * 根据文章id查询所有的评论列表
     * @param id
     * @return
     */
    Result commentsByArticleId(Long id);

    Result comment(CommentParam commentParam);
}
