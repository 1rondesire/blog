package com.xzq.blog.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xzq.blog.pojo.SysUser;
import com.xzq.blog.service.SysUserService;
import com.xzq.blog.mapper.SysUserMapper;
import com.xzq.blog.utils.JWTUtils;
import com.xzq.blog.vo.ErrorCode;
import com.xzq.blog.vo.LoginUserVo;
import com.xzq.blog.vo.Result;
import com.xzq.blog.vo.UserVo;
import jdk.nashorn.internal.parser.Token;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
* @author 94090
* @description 针对表【ms_sys_user】的数据库操作Service实现
* @createDate 2022-07-21 17:18:54
*/
@Service
public class SysUserServiceImpl implements SysUserService{

    @Autowired
    private SysUserMapper sysUserMapper;
    @Autowired
    private RedisTemplate<String,String> redisTemplate;
    @Override
    /**
     * token合法性校验
     * 是否为空，解析是否成功，redis中是否存在
     * 成功则返回对应前段所需的实体LoginUserVo
     * 失败则返回错误
     */
    public Result findUserByToken(String token) {
//        if(StringUtils.isBlank(token)){
//            return Result.fail(ErrorCode.TOKEN_ERROR.getCode(),ErrorCode.TOKEN_ERROR.getMsg());
//        }
//        if (JWTUtils.checkToken(token)==null){
//            return Result.fail(ErrorCode.TOKEN_ERROR.getCode(),ErrorCode.TOKEN_ERROR.getMsg());
//        }
//        String userJson = redisTemplate.opsForValue().get("TOKEN_" + token);
//        if (StringUtils.isBlank(userJson)){
//            return Result.fail(ErrorCode.TOKEN_ERROR.getCode(),ErrorCode.TOKEN_ERROR.getMsg());
//        }
        //        SysUser sysUser = JSON.parseObject(userJson, SysUser.class);
        SysUser sysUser = checkUserByToken(token);
        if (sysUser==null){
            return Result.fail(ErrorCode.TOKEN_ERROR.getCode(),ErrorCode.TOKEN_ERROR.getMsg());
        }
        LoginUserVo loginUserVo = new LoginUserVo();
        loginUserVo.setNickname(sysUser.getNickname());
        loginUserVo.setAccount(sysUser.getAccount());
        loginUserVo.setAvatar(sysUser.getAvatar());
        loginUserVo.setId(sysUser.getId());
        return Result.success(loginUserVo);
    }

    @Override
    public SysUser checkUserByToken(String token) {
        if(StringUtils.isBlank(token)){
            return null;
        }
        if (JWTUtils.checkToken(token)==null){
            return null;
        }
        String userJson = redisTemplate.opsForValue().get("TOKEN_" + token);
        if (StringUtils.isBlank(userJson)){
            return null;
        }
        SysUser sysUser = JSON.parseObject(userJson, SysUser.class);
        return sysUser;
    }

    @Override
    public UserVo findUserVoById(Long authorId) {
        SysUser sysUser = sysUserMapper.selectById(authorId);
        if (sysUser == null){
            sysUser = new SysUser();
            sysUser.setNickname("无名氏");
            sysUser.setAvatar("/static/img/logo.b3a48c0.png");
            sysUser.setNickname("码神之路");
        }
        UserVo userVo = new UserVo();
        BeanUtils.copyProperties(sysUser,userVo);
        return userVo;
    }

    @Override
    public SysUser findUserById(Long id) {
        SysUser sysUser = sysUserMapper.selectById(id);
        if (sysUser == null){
            sysUser = new SysUser();
            sysUser.setNickname("无名氏");
        }
        return sysUser;
    }

    @Override
    public SysUser findUser(String account, String password) {
        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUser::getAccount,account).eq(SysUser::getPassword,password).
                select(SysUser::getAccount,SysUser::getId,SysUser::getAvatar,SysUser::getNickname).last("limit 1");
        SysUser sysUser = sysUserMapper.selectOne(queryWrapper);
        System.out.println(sysUser);
        return sysUser;
    }

    /**
     * 根据账户查找用户信息
     * @param account
     * @return
     */
    @Override
    public SysUser findUserByAccount(String account) {
        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<SysUser>();
        queryWrapper.eq(SysUser::getAccount,account).last("limit 1");
        SysUser sysUser = sysUserMapper.selectOne(queryWrapper);
        return sysUser;
    }

    /**
     * 新增sysUser中的用户信息
     * @param sysUser
     */
    @Override
    public void save(SysUser sysUser) {
        sysUserMapper.insert(sysUser);
    }


}




