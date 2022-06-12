package com.chenhao.lkd.web.controller;

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

    @GetMapping("/search")
    public PageVo searchByPage(@RequestParam Integer pageIndex, Integer pageSize) {
        //查询页面
        PageVo pageVo =  skuClassService.searchByPage(pageIndex, pageSize);

        return pageVo;

    }

}
