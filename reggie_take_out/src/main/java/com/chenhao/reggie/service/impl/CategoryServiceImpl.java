package com.chenhao.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chenhao.reggie.entity.Category;
import com.chenhao.reggie.entity.Dish;
import com.chenhao.reggie.entity.Setmeal;
import com.chenhao.reggie.mapper.CategoryMapper;
import com.chenhao.reggie.service.CategoryService;
import com.chenhao.reggie.service.DishService;
import com.chenhao.reggie.service.SetmealService;
import com.chenhao.reggie.web.exception.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    @Autowired
    CategoryMapper categoryMapper;

    @Autowired
    DishService dishService;

    @Autowired
    SetmealService setmealService;


    /**
     * //添加分类，并检查名称是否重复
     * @param category
     * @return
     */
    @Override
    public boolean saveAndCheckName(Category category) {
        //查询该分类名称是否存在
        //创建查询对象
        LambdaQueryWrapper<Category> qw = new LambdaQueryWrapper<>();
        //添加查询条件
        qw.eq(Category::getName,category.getName());
        //查询
        Category category1 = categoryMapper.selectOne(qw);
        if(null!=category1){
            throw new BusinessException("分类:"+category1.getName()+"已存在了");
        }

        int insert = categoryMapper.insert(category);
        return insert>0;
    }

    @Override
    public Page<Category> selectByPage(Integer currentPage, Integer pageSize) {
        //判断页面信息
        if(null==currentPage){
            currentPage = 1;
        }
        if(null==pageSize){
            pageSize = 5;
        }

        //创建分页查询对象
        Page<Category> page = new Page<>();
        page.setCurrent(currentPage);
        page.setSize(pageSize);

        //创建分页查询条件
        LambdaQueryWrapper<Category> qw = new LambdaQueryWrapper<>();
        qw.orderByAsc(Category::getSort).orderByDesc(Category::getUpdateTime);
        //查询
        categoryMapper.selectPage(page,qw);

        return page;
    }

    @Override
    public boolean removeByIdWithoutUsered(Long id) {
        //判断id是否在被dish和Setmeal表关联
        //id是否在dish表中

        //创建查询条件对象
        LambdaQueryWrapper<Dish> qwD = new LambdaQueryWrapper<>();
        qwD.eq(id!=null,Dish::getCategoryId,id);
        //查询
        List<Dish> dishes = dishService.list(qwD);
        if(null!=dishes&&dishes.size()>0){
            //有关联数据
            throw new BusinessException("菜品分类已经使用");
        }

        //创建查询条件对象
        LambdaQueryWrapper<Setmeal> qwS = new LambdaQueryWrapper<>();
        qwS.eq(id!=null,Setmeal::getCategoryId,id);
        //查询
        List<Setmeal> setmeals = setmealService.list(qwS);
        if(null!=setmeals&&setmeals.size()>0){
            //有关联数据
            throw new BusinessException("套餐分类已经使用");
        }

        //到这里说明没有被关联，可以直接删除了
        int i = categoryMapper.deleteById(id);
        return i>0;
    }
}