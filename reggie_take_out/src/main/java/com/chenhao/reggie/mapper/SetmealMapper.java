package com.chenhao.reggie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.chenhao.reggie.entity.Setmeal;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface SetmealMapper extends BaseMapper<Setmeal> {

    boolean updateSetmealStatus(@Param("status") Integer status,@Param("ids") Long[] ids);
}