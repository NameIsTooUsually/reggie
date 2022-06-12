package com.chenhao.reggie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.chenhao.reggie.entity.Dish;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface DishMapper extends BaseMapper<Dish> {
    boolean updateStatusByIds(@Param("status") Integer status, @Param("ids") Long[] ids);
}