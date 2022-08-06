package com.xzq.blog.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

//返回值模板
@Data
@AllArgsConstructor
public class Result {
    private boolean success;
    private int code;
    private String msg;
    private Object data;

    public static Result success(Object data){
        return new Result(true,200,"success",data);
    }
    public static Result fail(int code,String msg){
        return new Result(true,code,msg,null);
    }
}
