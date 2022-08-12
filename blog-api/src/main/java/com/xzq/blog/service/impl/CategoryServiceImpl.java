package com.xzq.blog.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xzq.blog.pojo.Category;
import com.xzq.blog.service.CategoryService;
import com.xzq.blog.mapper.CategoryMapper;
import com.xzq.blog.vo.CategoryVo;
import com.xzq.blog.vo.Result;
import org.apache.logging.log4j.core.util.JsonUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
* @author 94090
* @description 针对表【ms_category】的数据库操作Service实现
* @createDate 2022-07-22 21:50:53
*/
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category>
    implements CategoryService{

    @Autowired
    private CategoryMapper categoryMapper;
    @Override
    public CategoryVo findCategoryById(Long categoryId) {
        Category category = categoryMapper.selectById(categoryId);
        CategoryVo categoryVo = new CategoryVo();
        BeanUtils.copyProperties(category,categoryVo);
        return categoryVo;
    }

    @Override
    public Result findAll() {
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.select(Category::getId,Category::getCategoryName);
        List<Category> categories = categoryMapper.selectList(queryWrapper);
        List<CategoryVo> categoryVos = copyList(categories);
        return Result.success(categoryVos);
    }

    @Override
    public Result finddetailAll() {
        List<Category> categories = categoryMapper.selectList(null);
        List<CategoryVo> categoryVos = copyList(categories);
        return Result.success(categoryVos);
    }

    @Override
    public Result categoriesdetailbyId(Long id) {
        Category category = categoryMapper.selectById(id);
        CategoryVo categoryVo = copy(category);
        return Result.success(categoryVo);
    }

    public List<CategoryVo> copyList(List<Category> categoryList){
        List<CategoryVo> categoryVoList = new ArrayList<>();
        for (Category category : categoryList) {
            categoryVoList.add(copy(category));
        }
        return categoryVoList;
    }
    public CategoryVo copy(Category category){
        CategoryVo categoryVo = new CategoryVo();
        BeanUtils.copyProperties(category,categoryVo);
        return categoryVo;
    }
}




