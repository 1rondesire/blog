package com.xzq.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xzq.blog.pojo.Tag;
import com.xzq.blog.service.TagService;
import com.xzq.blog.mapper.TagMapper;
import com.xzq.blog.vo.Result;
import com.xzq.blog.vo.TagVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
* @author 94090
* @description 针对表【ms_tag】的数据库操作Service实现
* @createDate 2022-07-21 17:19:34
*/
@Service
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag>
    implements TagService{
    @Autowired
    private TagMapper tagMapper;

    @Override
    public Result hots(int limit) {
        List<Long> tagIds = tagMapper.findHotsTagIds(limit);
        if (CollectionUtils.isEmpty(tagIds)){
            return Result.success(Collections.emptyList());
        }
        List<Tag> tagList = tagMapper.findTagsByTagIds(tagIds);
        return Result.success(tagList);
    }

    @Override
    public Result findAll() {
        LambdaQueryWrapper<Tag> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.select(Tag::getId,Tag::getTagName);
        List<Tag> tags = tagMapper.selectList(queryWrapper);
        List<TagVo> tagVos = copyList(tags);
        return Result.success(tagVos);
    }

    @Override
    public Result finddetailAll() {
        List<Tag> tags = tagMapper.selectList(null);
        List<TagVo> tagVos = copyList(tags);
        return Result.success(tagVos);
    }

    @Override
    public Result finddetailById(Long id) {
        Tag tag = tagMapper.selectById(id);
        TagVo tagVo = copy(tag);
        return Result.success(tagVo);
    }


    @Override
    public List<TagVo> findTagsByArticle(Long articleId) {
        List<Tag> tags = tagMapper.findTagsByArticle(articleId);
        return copyList(tags);
    }
    public List<TagVo> copyList(List<Tag> tagList){
        List<TagVo> tagVoList = new ArrayList<>();
        for (Tag tag : tagList) {
            tagVoList.add(copy(tag));
        }
        return tagVoList;
    }
    public TagVo copy(Tag tag){
        TagVo tagVo = new TagVo();
        BeanUtils.copyProperties(tag,tagVo);
        return tagVo;
    }

}




