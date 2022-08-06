package com.xzq.blog.controller;

import com.xzq.blog.pojo.Category;
import com.xzq.blog.service.CategoryService;
import com.xzq.blog.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("categorys")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public Result categories(){
        Result categoryList = categoryService.findAll();
        return categoryList;
    }

    @GetMapping("detail")
    public Result categoriesdetail(){
        Result categoryList = categoryService.finddetailAll();
        return categoryList;
    }
    @GetMapping("detail/{id}")
    public Result categoriesdetailbyId(@PathVariable("id") Long id){
        Result categoryList = categoryService.categoriesdetailbyId(id);
        return categoryList;
    }
}
