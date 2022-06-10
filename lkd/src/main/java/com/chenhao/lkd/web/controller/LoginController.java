package com.chenhao.lkd.web.controller;

import com.chenhao.lkd.pojo.dto.LoginDto;
import com.chenhao.lkd.pojo.vo.LoginUserVo;
import com.chenhao.lkd.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ChenHao
 * @version 1.0
 * @description:
 * @date 2022/6/10 11:05
 */
@RestController
@RequestMapping("/api/user-service/user")
public class LoginController {

    @Autowired
    UserService userService;

    @PostMapping("/login")
    public LoginUserVo login(@RequestBody LoginDto loginDto){
        LoginUserVo loginUserVo =  userService.login(loginDto);
        return loginUserVo;
    }

}
