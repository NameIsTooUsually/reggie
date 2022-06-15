package com.chenhao.reggie.web.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chenhao.reggie.entity.Setmeal;
import com.chenhao.reggie.entity.dto.SetmealDto;
import com.chenhao.reggie.service.SetmealService;
import com.chenhao.reggie.web.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author ChenHao
 * @version 1.0
 * @description:
 * @date 2022/6/12 19:08
 */
@RestController
@RequestMapping("/setmeal")
@Slf4j
public class SetmealController {

    @Autowired
    SetmealService setmealService;

    /**
     * 查询套餐页面信息，包含所含菜品分类名称
     *
     * @param currentPage
     * @param pageSize
     * @param name
     * @return
     */
    @GetMapping("/page")
    public R<Page<SetmealDto>> page(@RequestParam("page") Integer currentPage, Integer pageSize, String name) {
        //判断分页参数是否为null
        if (null == currentPage) {
            currentPage = 1;
        }
        if (null == pageSize) {
            pageSize = 8;
        }

        //调用方法查询页面数据
        Page<SetmealDto> dtoPage = setmealService.findBypageWithName(currentPage, pageSize, name);

        if (null != dtoPage) {
            return R.success("查询成功", dtoPage);
        }

        return R.fail("查询失败");
    }

    //添加套餐信息
    @PostMapping
    public R<SetmealDto> add(@RequestBody SetmealDto setmealDto) {
        //判断套餐信息是否为null
        if(setmealDto!=null){
            boolean addResult = setmealService.addSetmeal(setmealDto);
            if(addResult){
                return R.success("添加成功");
            }
            return R.fail("添加失败");
        }
        return R.fail("参数异常");
    }

    //根据id查询套餐信息,包含选择菜品信息
    @GetMapping("/{id}")
    public R findById(@PathVariable Long id){
        log.info("根据id进行查询。id：{}",id);
        //判断id是否存在
        if(null!=id){
            //根据id进行查询
            SetmealDto setmealDto = setmealService.getByIdWithChoiseCategory(id);
            if(null!=setmealDto){
                return R.success("查询成功",setmealDto);
            }
            return R.fail("查询失败");
        }
        return R.fail("参数异常");
    }

    //修改套餐信息
    @PutMapping
    public R updateSetmeal(@RequestBody SetmealDto setmealDto){
        if (null != setmealDto.getId()){
            boolean saveResult = setmealService.updateSetmeal(setmealDto);
            if(saveResult){
                return R.success("修改成功");
            }
            return R.fail("修改失败");

        }

        return R.fail("参数有误！");
    }

    //批量修改套餐状态起售或停售
    @PostMapping("/status/{status}")
    public R updateSetmealStatus(@PathVariable Integer status,Long[] ids){
        //判断status是否异常
        if (status!=null&&(status==0||status==1)){
            boolean updateResult = setmealService.updateSetmealStatus(status,ids);
            if(updateResult){
                return R.success("修改成功");
            }
            return R.fail("修改失败");
        }
        return R.fail("参数异常");
    }

    //根据ids进行删除
    @DeleteMapping
    public R deleteByIds(Long[] ids){

        //判断参数是否有误
        if(null!=ids&&ids.length>0){
           boolean deleteResult =  setmealService.deleteByIds(ids);
            if (deleteResult) {
                return R.success("删除成功");
            }
            return R.fail("删除失败");

        }
        return R.fail("参数异常");
    }

    //根据分类id进行查询
    @GetMapping("/list")
    public R<List<Setmeal>> list(Long categoryId){
        //判断categoryId是否异常
        if (null!=categoryId) {
            List<Setmeal> setmeals = setmealService.listByCategoryId(categoryId);
            if(null!=setmeals&&setmeals.size()>0){
                return R.success("查询成功",setmeals);
            }
            return R.fail("空空如也");
        }
        return R.fail("参数异常");
    }

}
