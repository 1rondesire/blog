package com.xzq.blog.service;

import com.xzq.blog.pojo.Category;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xzq.blog.vo.CategoryVo;
import com.xzq.blog.vo.Result;

import java.util.List;

/**
* @author 94090
* @description 针对表【ms_category】的数据库操作Service
* @createDate 2022-07-22 21:50:53
*/
public interface CategoryService extends IService<Category> {

    CategoryVo findCategoryById(Long categoryId);

    Result findAll();

    Result finddetailAll();

    Result categoriesdetailbyId(Long id);

}
