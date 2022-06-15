package com.chenhao.reggie.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.chenhao.reggie.entity.Dish;
import com.chenhao.reggie.entity.dto.DishDto;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface DishService extends IService<Dish> {
    boolean saveDishAndFlavor(DishDto dishDto);
    //查询菜品页面信息，并且显示菜品分类信息
    Page<DishDto> searchPageAndName(Integer currentPage,Integer pageSize,String name);

    //根据id查询dish和dishFlavor信息
    DishDto findDishAndFlavor(Long id);

    //根据id修改菜品信息和口味信息
    boolean saveByIdWithFlavors(DishDto dishDto);
    //批量修改菜品状态
    boolean updateStatusByIds(Integer status, Long[] ids);

    //根据分类id进行查询，并返回菜品口味
    List<DishDto> listWithFlavor(Long categoryId, String name);
}