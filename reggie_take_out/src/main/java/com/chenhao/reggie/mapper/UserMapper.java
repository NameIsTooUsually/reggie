package com.chenhao.reggie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.chenhao.reggie.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {
}