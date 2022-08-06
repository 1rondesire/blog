package com.xzq.blog.common.aop;

import org.aspectj.lang.annotation.Aspect;

import java.io.PrintWriter;
import java.lang.annotation.*;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LogAnnotation {

    String module() default "";
    String operator() default "";
}
