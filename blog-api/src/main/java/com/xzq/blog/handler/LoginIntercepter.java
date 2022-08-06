package com.xzq.blog.handler;

import com.alibaba.fastjson.JSON;
import com.xzq.blog.pojo.SysUser;
import com.xzq.blog.service.LoginService;
import com.xzq.blog.service.SysUserService;
import com.xzq.blog.utils.UserThreadLocal;
import com.xzq.blog.vo.ErrorCode;
import com.xzq.blog.vo.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@Component
public class LoginIntercepter implements HandlerInterceptor {

    @Autowired
    private SysUserService sysUserService;
    /**
     * 判断请求的接口路径是否为HandlerMethod（Controller）方法
     * 判断token是否为空，若为空，则表示未登陆
     * 如果不为空，进行登录验证 ，通过checkToken使用token查询出redis中的用户信息 用户不存在则表示未登录
     * 如果成功就放行
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!(handler instanceof HandlerMethod)){
            //可能是资源handler
            return true;
        }
        String token = request.getHeader("Authorization");

        log.info("=================request start===========================");
        String requestURI = request.getRequestURI();
        log.info("request uri:{}",requestURI);
        log.info("request method:{}",request.getMethod());
        log.info("token:{}", token);
        log.info("=================request end===========================");

        //token为空
        if(StringUtils.isBlank(token)){
            Result result = Result.fail(ErrorCode.NO_LOGIN.getCode(), ErrorCode.NO_LOGIN.getMsg());
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().print(JSON.toJSONString(result));
            return false;
        }
//        如果不为空，进行登录验证 ，通过checkToken使用token查询出redis中的用户信息 用户不存在则表示未登录
        SysUser sysUser = sysUserService.checkUserByToken(token);
        if (sysUser == null){
            Result result = Result.fail(ErrorCode.NO_LOGIN.getCode(), ErrorCode.NO_LOGIN.getMsg());
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().print(JSON.toJSONString(result));
            return false;
        }
        UserThreadLocal.put(sysUser);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        UserThreadLocal.remove();
    }
}
