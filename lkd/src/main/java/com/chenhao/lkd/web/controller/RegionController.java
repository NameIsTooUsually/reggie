package com.chenhao.lkd.web.controller;

import com.chenhao.lkd.pojo.Region;
import com.chenhao.lkd.pojo.dto.RegionDto;
import com.chenhao.lkd.pojo.vo.PageVo;
import com.chenhao.lkd.service.RegionServcie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author ChenHao
 * @version 1.0
 * @description:
 * @date 2022/6/11 13:28
 */
@RestController
@RequestMapping("/api/vm-service/region")
public class RegionController {

    @Autowired
    RegionServcie regionServcie;
    //分页查询区域信息，并统计该区域的点位数
    @GetMapping("/search")
    public PageVo searchByPageAndName(Integer pageIndex, Integer pageSize, String name) {
        //调用方法，获取页面信息
        PageVo pageVo = regionServcie.searchByPageAndName(pageIndex,pageSize,name);
        return pageVo;
    }

    //新增区域信息
    @PostMapping
    public boolean add(@RequestBody RegionDto region){

        if(null==region){
            return false;
        }
        //调用方法添加区域
        return regionServcie.add(region);


    }

    //修改区域信息
    @PutMapping("/{id}")
    public boolean update(@PathVariable Long id,@RequestBody RegionDto  region){
        if(null!=id){
            boolean updateResult = regionServcie.updateById(id,region);
            return updateResult;
        }
        return false;
    }
}
