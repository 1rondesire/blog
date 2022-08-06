package com.xzq.blog.controller;

import com.xzq.blog.service.CommentService;
import com.xzq.blog.vo.Result;
import com.xzq.blog.vo.params.CommentParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RequestMapping("comments")
@RestController
public class CommentsController {

    @Autowired
    private CommentService commentService;


    @GetMapping("article/{id}")
    public Result comments(@PathVariable Long id){
        return commentService.commentsByArticleId(id);
    }

    @PostMapping("create/change")
    public Result comment(@RequestBody CommentParam commentParam){
        return commentService.comment(commentParam);
    }
}
