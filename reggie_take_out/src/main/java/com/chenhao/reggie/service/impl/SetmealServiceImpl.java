package com.chenhao.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chenhao.reggie.entity.Category;
import com.chenhao.reggie.entity.Setmeal;
import com.chenhao.reggie.entity.SetmealDish;
import com.chenhao.reggie.entity.dto.SetmealDto;
import com.chenhao.reggie.mapper.SetmealMapper;
import com.chenhao.reggie.service.CategoryService;
import com.chenhao.reggie.service.SetmealDishService;
import com.chenhao.reggie.service.SetmealService;
import com.chenhao.reggie.web.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class SetmealServiceImpl extends ServiceImpl<SetmealMapper, Setmeal> implements SetmealService {
    @Autowired
    CategoryService categoryService;

   @Autowired
    SetmealDishService setmealDishService;
   @Autowired
   SetmealMapper setmealMapper;

    /**
     * 查询套餐页面信息，包含所含菜品分类名称
     * @param currentPage
     * @param pageSize
     * @param name
     * @return
     */
    @Override
    public Page<SetmealDto> findBypageWithName(Integer currentPage, Integer pageSize, String name) {
        //查询seteaml基础信息
        //创建查询条件对象
        LambdaQueryWrapper<Setmeal> qw = new LambdaQueryWrapper<>();
        qw.like(name != null, Setmeal::getName, name).orderByDesc(Setmeal::getUpdateTime);
        //创建分页查询条件
        Page<Setmeal> page = new Page<>(currentPage, pageSize);
        //查询基础信息
        Page<Setmeal> pageS = this.page(page, qw);
        //查询菜品分类
        List<Category> list = categoryService.list();

        //添加返回信息
        //创建返回setmealDto page
        Page<SetmealDto> dtoPage = new Page<>();
        //将基础信息赋值过来
        BeanUtils.copyProperties(pageS, dtoPage, "records");

        //创建集合接收封装好的setmealDto
        List<SetmealDto> dtoList = new ArrayList<>();
        //添加菜品分类名称
        //获取基础信息表，循环遍历每一个setmeal,并获取id
        List<Setmeal> records = pageS.getRecords();
        for (Setmeal s : records) {
            Long categoryId = s.getCategoryId();
            //菜品分类id和菜品分类集合进行比对
            for (Category category : list) {
                //如果菜品分类的id和集合中的分类比对上了
                if (categoryId.equals(category.getId())) {
                    //创建setmealDto 对象
                    SetmealDto setmealDto = new SetmealDto();
                    //复制基础信息
                    BeanUtils.copyProperties(s, setmealDto);
                    //添加名称信息
                    setmealDto.setCategoryName(category.getName());
                    //设置setmealDto到集合
                    dtoList.add(setmealDto);
                    //结束本次循环
                    break;
                }
            }
        }
        //循环结束，整合返回参数
        dtoPage.setRecords(dtoList);


        return dtoPage;
    }

    @Override
    public boolean addSetmeal(SetmealDto setmealDto) {
        //添加信息
        //创建Setmeal对象
        Setmeal setmeal = new Setmeal();
        //赋值基础信息
        BeanUtils.copyProperties(setmealDto, setmeal, "categoryName", "setmealDishes");
        //添加基础信息
        boolean saveResult = this.save(setmeal);
        if(!saveResult){
            return false;
        }
        //获取套餐id
        Long setmealId = setmeal.getId();
        //获取中间表信息
        List<SetmealDish> setmealDishes = setmealDto.getSetmealDishes();
        for (SetmealDish setmealDish : setmealDishes) {
            setmealDish.setSetmealId(setmealId.toString());
        }
        //添加中间表信息
        boolean saveBatchResult = setmealDishService.saveBatch(setmealDishes);
        return saveBatchResult;
    }

    //通过id进行查询，并返回已经选择的菜品信息
    @Override
    public SetmealDto getByIdWithChoiseCategory(Long id) {
        //查询基础信息
        Setmeal setmeal = this.getById(id);

        //根据套餐查询已被选择的菜品
        //创建菜品查询条件
        LambdaQueryWrapper<SetmealDish> qw = new LambdaQueryWrapper<>();
        qw.eq(SetmealDish::getSetmealId,id);
        List<SetmealDish> setmealDishes = setmealDishService.list(qw);

        if(null==setmealDishes){
            throw new BusinessException("没有这个套餐啊！！");
        }
        //组装返回信息
        //创建SetmealDto对象
        SetmealDto setmealDto = new SetmealDto();
        //赋值信息
        BeanUtils.copyProperties(setmeal,setmealDto);
        //添加菜品信息
        setmealDto.setSetmealDishes(setmealDishes);

        return setmealDto;
    }

    @Override
    public boolean updateSetmeal(SetmealDto setmealDto) {
        //添加Setmeal基本信息
        Setmeal setmeal = new Setmeal();

        //复制信息
        BeanUtils.copyProperties(setmealDto,setmeal,"categoryName", "setmealDishes");

        //修改基本信息
        boolean updateResult = this.updateById(setmeal);
        if(!updateResult){
            //基础信息添加失败
            return false;
        }

        //根据id删除原有SetmealDish内容
        //获取Setmealid
        Long setmealDtoId = setmealDto.getId();
        LambdaQueryWrapper<SetmealDish> qw = new LambdaQueryWrapper<>();
        qw.eq(SetmealDish::getSetmealId,setmealDtoId);
        boolean remove = setmealDishService.remove(qw);

        //添加菜品信息
        //获取新添加的菜品信息
        List<SetmealDish> setmealDishes = setmealDto.getSetmealDishes();
        //添加Setmealid
        for (SetmealDish setmealDish : setmealDishes) {
            setmealDish.setSetmealId(setmealDtoId.toString());
        }

        //添加菜品
        boolean saveBatch = setmealDishService.saveBatch(setmealDishes);
        if(!saveBatch){
            return false;
        }

        return true;
    }

    @Override
    public boolean updateSetmealStatus(Integer status, Long[] ids) {
        boolean uadateResult = setmealMapper.updateSetmealStatus(status,ids);

        return uadateResult;
    }

    @Override
    public boolean deleteByIds(Long[] ids) {
        //先删除中间表中关联的菜品信息
        for (Long id : ids) {
            //创建删除条件对象
            LambdaQueryWrapper<SetmealDish> qw = new LambdaQueryWrapper<>();
            qw.eq(SetmealDish::getSetmealId,id);
            setmealDishService.remove(qw);
        }

        //再删除套餐信息
        for (Long id : ids) {
            boolean removeResult = this.removeById(id);
            if(!removeResult){
                return false;
            }
        }

        return true;
    }
}