package com.chenhao.reggie.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.chenhao.reggie.entity.Setmeal;
import com.chenhao.reggie.entity.dto.SetmealDto;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface SetmealService extends IService<Setmeal> {
    //查询套餐页面信息，包含所含菜品分类名称
    Page<SetmealDto> findBypageWithName(Integer currentPage, Integer pageSize, String name);
    //添加套餐信息
    boolean addSetmeal(SetmealDto setmealDto);
    //根据Setmealid获取Setmeal信息
    SetmealDto getByIdWithChoiseCategory(Long id);
    //修改Setmeal信息
    boolean updateSetmeal(SetmealDto setmealDto);
    //批量修改套餐状态起售或停售
    boolean updateSetmealStatus(Integer status, Long[] ids);

    //根据ids进行删除
    boolean deleteByIds(Long[] ids);
}