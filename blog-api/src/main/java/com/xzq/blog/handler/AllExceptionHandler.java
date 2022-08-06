package com.xzq.blog.handler;

import com.xzq.blog.vo.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class AllExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result doException(Exception exception){
        System.out.println("========================================================");
        exception.printStackTrace();
        System.out.println("========================================================");
        return Result.fail(-999,"系统出错了");
    }
}
