package com.xzq.blog.controller;

import com.xzq.blog.service.TagService;
import com.xzq.blog.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("tags")
public class TagsController {
    @Autowired
    private TagService tagService;

    /**
     * 获取首页的最热标签
     * limit定义最热的x个标签
     * @return
     */
    @GetMapping("hot")
    public Result hot(){
        int limit = 6;
        return tagService.hots(limit);
    }

    @GetMapping()
    public Result tags(){
        return tagService.findAll();
    }
    @GetMapping("detail")
    public Result tagsdetail(){
        return tagService.finddetailAll();
    }

    @GetMapping("detail/{id}")
    public Result tagsdetailById(@PathVariable("id") Long id){
        return tagService.finddetailById(id);
    }
}
