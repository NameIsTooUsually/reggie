package com.chenhao.lkd.service;

import com.chenhao.lkd.pojo.dto.LoginDto;
import com.chenhao.lkd.pojo.vo.LoginUserVo;

public interface UserService {
    LoginUserVo login(LoginDto loginDto);
}
