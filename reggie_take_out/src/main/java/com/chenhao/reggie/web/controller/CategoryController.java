package com.chenhao.reggie.web.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chenhao.reggie.entity.Category;
import com.chenhao.reggie.service.CategoryService;
import com.chenhao.reggie.web.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author ChenHao
 * @version 1.0
 * @description:
 * @date 2022/6/11 9:55
 */
@Slf4j
@RestController
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    CategoryService categoryService;

    /**
     * 添加菜品或套餐分类
     * @param category
     * @return
     */
    @PostMapping
    public R add(@RequestBody Category category){
        log.info("新增分类，信息：{}", category);
        boolean result = categoryService.saveAndCheckName(category);
        if(result){
            return R.success("新增菜品成功");
        }
        return R.fail("新增菜品失败");
    }

    /**
     * 分页查询
     * @param currentPage
     * @param pageSize
     * @return
     */
    @GetMapping("/page")
    public R<Page<Category>> page(@RequestParam("page") Integer currentPage, Integer pageSize){
        log.info("所有分类分页查询，第{}页，每页{}条", currentPage, pageSize);
        Page<Category> page = categoryService.selectByPage(currentPage,pageSize);

        if(null!=page){
            return R.success("查询成功",page);
        }else{
           return  R.fail("查询失败");
        }

    }

    /**
     * 根据id进行删除，并判断是否被其他类关联了
     * @param id
     * @return
     */
    @DeleteMapping
    public R deleteById(@RequestParam Long id){
        log.info("根据id进行删除，ID为{}",id);
        //根据id进行删除，这里不用多验证id是否为空了，如果id为空，删除就会失败
        boolean deleteResult = categoryService.removeByIdWithoutUsered(id);
        //判断返回值
        if (deleteResult) {
            return R.success("分类删除成功");
        }
        return R.fail("分类删除失败");
    }
    //更新菜品类型
    public R update(@RequestBody Category category){
        if(null!=category.getId()){
            //id不为空，则进行修改
            boolean updateResult = categoryService.updateById(category);

            if(updateResult){
               return  R.success("修改成功");
            }
            return R.fail("修改失败");
        }

        return R.fail("参数异常");
    }

    //根据类型查询
    @GetMapping("/list")
    public R<List<Category>> listByType(@RequestParam Long type){
        log.info("根据类型查询，类型id为：{}",type);
        //判断参数是否为空
        if(null!=type){
            //创建查询条件
            LambdaQueryWrapper<Category> qw = new LambdaQueryWrapper<>();
            qw.eq(Category::getType,type);
            qw.orderByAsc(Category::getSort).orderByDesc(Category::getUpdateTime);
            //执行查询
            List<Category> categories = categoryService.list(qw);

            //判断
            if(null!=categories){
                return R.success("查询分类成功",categories);
            }
                return R.fail("查询分类失败");

        }
        return R.fail("参数异常");
    }
}
