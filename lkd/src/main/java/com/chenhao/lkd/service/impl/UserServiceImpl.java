package com.chenhao.lkd.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.chenhao.lkd.mapper.UserMapper;
import com.chenhao.lkd.pojo.User;
import com.chenhao.lkd.pojo.dto.LoginDto;
import com.chenhao.lkd.pojo.vo.LoginUserVo;
import com.chenhao.lkd.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * @author ChenHao
 * @version 1.0
 * @description:
 * @date 2022/6/10 11:10
 */
@Service
@Slf4j
public class UserServiceImpl implements UserService {
    @Autowired
    UserMapper userMapper;

    @Override
    public LoginUserVo login(LoginDto loginDto) {

        //判断参数是否有异常
        if(null==loginDto){
            LoginUserVo loginUserVo = new LoginUserVo();
            loginUserVo.setSuccess(false);
            loginUserVo.setMsg("参数异常");
            return loginUserVo;
        }
        //判断用户名是否存在
        String loginName = loginDto.getLoginName();
        //创建查询条件
        LambdaQueryWrapper<User> qw = new LambdaQueryWrapper<>();
        qw.eq(User::getLoginName,loginDto.getLoginName());
        User user = userMapper.selectOne(qw);
        if(null==user){
            //说明该用户不存在
            LoginUserVo loginUserVo = new LoginUserVo();
            loginUserVo.setSuccess(false);
            loginUserVo.setMsg("用户名不存在");
            return loginUserVo;
        }

        /*//验证密码
        if(!(StringUtils.isNotBlank(loginDto.getPassword())&&loginDto.getPassword().equals(user.getPassword()))){
            //密码不正确
            LoginUserVo loginUserVo = new LoginUserVo();
            loginUserVo.setSuccess(false);
            loginUserVo.setMsg("密码错误");
            return loginUserVo;
        }*/

        //密码相等
        LoginUserVo loginUserVo = new LoginUserVo();
        loginUserVo.setUserId(user.getId());
        loginUserVo.setUserName(user.getUserName());
        loginUserVo.setRoleCode(user.getRoleCode());
        loginUserVo.setSuccess(true);
        loginUserVo.setMsg("登录成功");
        loginUserVo.setToken(UUID.randomUUID().toString());
        return loginUserVo;

    }
}
