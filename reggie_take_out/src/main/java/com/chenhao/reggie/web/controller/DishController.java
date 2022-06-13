package com.chenhao.reggie.web.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chenhao.reggie.entity.Dish;
import com.chenhao.reggie.entity.dto.DishDto;
import com.chenhao.reggie.service.DishService;
import com.chenhao.reggie.web.R;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/dish")
@Slf4j
public class DishController {
    @Autowired
    private DishService dishService;

    //保存商品信息
    @PostMapping
    public R saveDishAndFlavor(@RequestBody DishDto dishDto){
        if(dishDto==null){
            return R.fail("参数异常");
        }

        boolean saveResult = dishService.saveDishAndFlavor(dishDto);

        if(saveResult){
            return R.success("保存成功");
        }
        return R.fail("保存失败");
    }


    //分页查询商品信息，并显示菜品分类名称
    @GetMapping("/page")
    public R<Page<DishDto>> page(@RequestParam("page") Integer currentPage,Integer pageSize,String name){

        Page<DishDto> dishDtoPage = dishService.searchPageAndName(currentPage, pageSize, name);
        //设置响应内容
        return R.success("查询分类成功",dishDtoPage);
    }

    //根据id查询菜品信息
    @GetMapping("/{id}")
    public R<DishDto> findDishAndFlavor(@PathVariable Long id){
        //判断id是否为null
        if(null!=id){
            DishDto dishDto  = dishService.findDishAndFlavor(id);
            //判断返回值是否为null
            if(null!=dishDto){
                return R.success("查询成功",dishDto);
            }
            return R.fail("查询失败");
        }

        return R.fail("参数异常");
    }

    //根据id修改菜品信息
    @PutMapping
    public R update(@RequestBody DishDto dishDto){
        //判断id是否为null
        Long dishId = dishDto.getId();
        if(null!=dishId){
            boolean updateResult = dishService.saveByIdWithFlavors(dishDto);
            if(updateResult){
                return R.success("修改成功");
            }
            return R.fail("修改失败");
        }

        return R.fail("参数异常");
    }

    //批量修改菜品状态
    @PostMapping("/status/{status}")
    public R updateStatusByIds(@PathVariable Integer status,Long[] ids){
        //判断参数是否异常
        if(null!=status&&(status==0||status==1)){
            boolean updateResult = dishService.updateStatusByIds(status,ids);

            if(updateResult){
                return R.success("修改参数成功");
            }
            return R.fail("修改参数失败");
        }
        return R.fail("参数异常");
    }

    //根据菜品分类id查询菜品信息
    @GetMapping("/list")
    public R<List<Dish>> findByCategoryId(Long categoryId,String name){
        //判断id或name是否都为null
        if(null!=categoryId|| StringUtils.isNotBlank(name)){
            //设置查询条件
            LambdaQueryWrapper<Dish> qw = new LambdaQueryWrapper<>();
            qw.eq(null!=categoryId,Dish::getCategoryId,categoryId).like(null!=name,Dish::getName,name)
                    .orderByAsc(Dish::getSort);
            List<Dish> dishList = dishService.list(qw);

            if(null!=dishList){
                return R.success("查询成功",dishList);
            }
            return R.fail("查询失败");


        }
        return R.fail("参数异常");

    }

}