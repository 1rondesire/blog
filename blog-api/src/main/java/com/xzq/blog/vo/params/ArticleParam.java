package com.xzq.blog.vo.params;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.xzq.blog.vo.CategoryVo;
import com.xzq.blog.vo.TagVo;
import lombok.Data;

import java.util.List;
@Data
public class ArticleParam {
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    private ArticleBodyParam body;

    private CategoryVo category;

    private String summary;

    private List<TagVo> tags;

    private String title;

    private String search;
}
