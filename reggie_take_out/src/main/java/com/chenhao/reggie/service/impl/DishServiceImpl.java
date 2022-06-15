package com.chenhao.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chenhao.reggie.entity.Category;
import com.chenhao.reggie.entity.Dish;
import com.chenhao.reggie.entity.DishFlavor;
import com.chenhao.reggie.entity.dto.DishDto;
import com.chenhao.reggie.mapper.DishMapper;
import com.chenhao.reggie.service.CategoryService;
import com.chenhao.reggie.service.DishFlavorService;
import com.chenhao.reggie.service.DishService;
import com.chenhao.reggie.web.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class DishServiceImpl extends ServiceImpl<DishMapper, Dish> implements DishService {
    @Autowired
    DishFlavorService dishFlavorService;

    @Autowired
    CategoryService categoryService;

    @Autowired
    DishMapper dishMapper;

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public boolean saveDishAndFlavor(DishDto dishDto) {

        //判断菜品名称是否存在
        String dishName = dishDto.getName();

        //根据name查询，判断是否重复
        LambdaQueryWrapper<Dish> qw = new LambdaQueryWrapper<>();
        qw.eq(Dish::getName, dishName);
        Dish dish = this.getOne(qw);

        if (dish != null) {
            throw new BusinessException("菜品名称已经存在了。。。");
        }

        //添加dish信息
        boolean save = this.save(dishDto);
        //获取dish的id
        Long dishDtoId = dishDto.getId();

        //添加dishDTOID到dishFlavor中
        List<DishFlavor> flavors = dishDto.getFlavors();
        for (DishFlavor flavor : flavors) {
            flavor.setDishId(dishDtoId);
        }

        //throw new SQLIntegrityConstraintViolationException();

        //添加dishFlavor信息
        dishFlavorService.saveBatch(flavors);

        return true;
    }

    //查询菜品页面信息，并且显示菜品分类信息
    @Override
    public Page<DishDto> searchPageAndName(Integer currentPage, Integer pageSize, String name) {

        //判断分页参数是否为null
        if (null == currentPage) {
            currentPage = 1;
        }
        if (null == pageSize) {
            pageSize = 7;
        }
        //创建page对象
        Page<Dish> page = new Page<>(currentPage, pageSize);
        //创建查询条件对象
        LambdaQueryWrapper<Dish> qw = new LambdaQueryWrapper<>();
        qw.like(null != name, Dish::getName, name);
        //获取页面信息
        this.page(page, qw);

        //创建新的page对象，泛型为DishDto
        Page<DishDto> dishDtoPage = new Page<>();

        //复制page信息到dishDtoPage
        BeanUtils.copyProperties(page, dishDtoPage, "records");

        //获取所有分类信息
        List<Category> categoryList = categoryService.list();

        //创建集合存储disDto对象
        List<DishDto> dishDtoList = new ArrayList<>();
        //给返回值赋值
        List<Dish> dishes = page.getRecords();
        for (Dish dish : dishes) {
            //创建disDto对象
            DishDto dishDto = new DishDto();
            //给dishDto赋值
            BeanUtils.copyProperties(dish, dishDto);
            //给dishDto对象设置categoryName
            for (Category category : categoryList) {
                if (dish.getCategoryId().equals(category.getId())) {
                    dishDto.setCategoryName(category.getName());
                }
            }
            dishDtoList.add(dishDto);
        }

        dishDtoPage.setRecords(dishDtoList);
        return dishDtoPage;
    }

    //根据id查询dish和dishFlavor信息
    @Override
    public DishDto findDishAndFlavor(Long id) {
        //查询dish基本信息
        Dish dish = this.getById(id);
        if (null == dish) {
            return null;
        }
        //查询口味信息
        LambdaQueryWrapper<DishFlavor> qw = new LambdaQueryWrapper<>();
        qw.eq(DishFlavor::getDishId, id);
        List<DishFlavor> flavors = dishFlavorService.list(qw);

        //创建dishDto对象
        DishDto dishDto = new DishDto();
        BeanUtils.copyProperties(dish, dishDto);

        //添加口味信息到dishDto
        dishDto.setFlavors(flavors);

        return dishDto;

    }

    @Override
    public boolean saveByIdWithFlavors(DishDto dishDto) {
        //添加基本信息
        boolean saveResult = this.updateById(dishDto);
        //判断是否修改成功
        if (!saveResult) {
            return false;
        }

        //获取dishid
        Long dishId = dishDto.getId();

        //删除dishFlavor信息
        LambdaQueryWrapper<DishFlavor> qw = new LambdaQueryWrapper<>();
        qw.eq(DishFlavor::getDishId, dishId);
        //如果删除不成功，则表示之前没有dish的Flavor
        boolean remove = dishFlavorService.remove(qw);

        //添加flavor信息
        List<DishFlavor> flavors = dishDto.getFlavors();
        //给flavor添加dishID
        for (DishFlavor flavor : flavors) {
            flavor.setDishId(dishId);
        }
        //添加
        boolean saveFlavorResult = dishFlavorService.saveBatch(flavors);

        return saveFlavorResult;

    }

    //批量修改菜品状态
    @Override
    public boolean updateStatusByIds(Integer status, Long[] ids) {

        boolean updateResult = dishMapper.updateStatusByIds(status, ids);

        return updateResult;
    }

    //根据菜品分类id查询，并且返回菜品口味
    @Override
    public List<DishDto> listWithFlavor(Long categoryId, String name) {
        //设置查询条件
        LambdaQueryWrapper<Dish> qw = new LambdaQueryWrapper<>();
        qw.eq(null != categoryId, Dish::getCategoryId, categoryId).like(StringUtils.isNotBlank(name), Dish::getName, name)
                .orderByAsc(Dish::getSort);
        //添加隐含条件
        qw.eq(Dish::getStatus,1);

        List<Dish> dishList = this.list(qw);
        if(dishList.size()==0){
            return null;
        }
        //获取所有查询到的菜品id
        //创建集合存储id
        List<Long> dishIds = new ArrayList<>();
        for (Dish dish : dishList) {
            dishIds.add(dish.getId());
        }
        //根据菜品id，查询口味
        LambdaQueryWrapper<DishFlavor> qwD = new LambdaQueryWrapper<>();
        qwD.in(DishFlavor::getDishId, dishIds);
        List<DishFlavor> flavors = dishFlavorService.list(qwD);

        //创建集合接收dishDto
        List<DishDto> dishDtoList = new ArrayList<>();
        //拼装返回参数
        for (Dish dish : dishList) {
            //创建dishDto
            DishDto dishDto = new DishDto();
            BeanUtils.copyProperties(dish, dishDto);
            //创建集合存储菜品口味信息
            List<DishFlavor> dishFlavors = new ArrayList<>();
            for (int i = 0; i < flavors.size(); i++) {
                //获取菜品id
                Long dishId = dish.getId();
                //判断该菜品和该口味是否对应
                if (dishId.equals(flavors.get(i).getDishId())) {
                    dishFlavors.add(flavors.get(i));
                    //该口味已经匹配到了，所以从列表中移除
                    flavors.remove(i);
                    //防止遗漏数据
                    i--;
                }
            }
            //口味循环结束，添加到dishDto中
            dishDto.setFlavors(dishFlavors);
            //添加到返回参数的dishDto中去
            dishDtoList.add(dishDto);
        }
        return dishDtoList;
    }


}