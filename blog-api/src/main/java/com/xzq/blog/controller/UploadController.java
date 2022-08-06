package com.xzq.blog.controller;

import com.xzq.blog.utils.QiniuUtils;
import com.xzq.blog.vo.Result;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;


/**
 * 上传文件
 */
@RestController
@RequestMapping("upload")
public class UploadController {
    @Autowired
    private QiniuUtils qiniuUtils;

    @PostMapping
    public Result upload(@RequestParam("image") MultipartFile file){
        //获取原始文件名称
        String originalFilename = file.getOriginalFilename();
        //将原始后缀加上唯一的uuid得到不重复的文件名
        String fileName = UUID.randomUUID().toString() + "." + StringUtils.substringAfterLast(originalFilename,".");
        //上传文件到七牛云
        boolean upload = qiniuUtils.upload(file, fileName);
        if (upload){
            return Result.success(qiniuUtils.url + fileName);
        }
        return Result.fail(20001,"上传失败");
    }
}
