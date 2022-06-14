package com.chenhao.lkd.web.controller;

import com.chenhao.lkd.pojo.SkuClass;
import com.chenhao.lkd.pojo.vo.PageVo;
import com.chenhao.lkd.service.SkuClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author ChenHao
 * @version 1.0
 * @description:
 * @date 2022/6/10 17:46
 */
@RestController
@RequestMapping("/api/vm-service/skuClass")
public class SkuClassController {

    @Autowired
    SkuClassService skuClassService;

    //查询商品类别列表
    @GetMapping("/search")
    public PageVo searchByPage(@RequestParam Integer pageIndex, Integer pageSize) {
        //查询页面
        PageVo pageVo =  skuClassService.searchByPage(pageIndex, pageSize);
        return pageVo;
    }
    //新增商品类别信息
    @PostMapping
    public boolean addSkuClass(@RequestBody SkuClass skuClass){
        //判断参数是否有问题
        if(null==skuClass){
            return false;
        }
        boolean addResult = skuClassService.addSkuClass(skuClass);

        return addResult;
    }
    //根据id进行修改
    @PutMapping("/{id}")
    public boolean updateSkuClass(@PathVariable Integer id,@RequestBody SkuClass skuClass){
        //验证id
        if(null!=id){
           boolean updateResult =  skuClassService.updateSkuClass(id,skuClass);
           return updateResult;
        }
        return false;
    }

}
