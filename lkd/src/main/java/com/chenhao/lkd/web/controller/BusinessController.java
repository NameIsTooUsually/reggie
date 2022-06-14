package com.chenhao.lkd.web.controller;

import com.chenhao.lkd.pojo.Business;
import com.chenhao.lkd.pojo.vo.PageVo;
import com.chenhao.lkd.service.BusinessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author ChenHao
 * @version 1.0
 * @description:
 * @date 2022/6/13 16:54
 */
@RestController
@RequestMapping("//api/vm-service/businessType")
public class BusinessController {
    @Autowired
    BusinessService businessService;


    //查询商圈信息
    @GetMapping
    public List<Business> page() {
        //查询商圈信息
        List<Business> page = businessService.page();

        return page;
    }
}
