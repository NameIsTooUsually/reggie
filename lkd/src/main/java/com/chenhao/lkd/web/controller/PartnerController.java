package com.chenhao.lkd.web.controller;

import com.chenhao.lkd.pojo.Partner;
import com.chenhao.lkd.pojo.dto.PartnerDto;
import com.chenhao.lkd.pojo.vo.PageVo;
import com.chenhao.lkd.service.PartnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author ChenHao
 * @version 1.0
 * @description:
 * @date 2022/6/13 17:15
 */
@RestController
@RequestMapping("/api/user-service/partner")
public class PartnerController {
    @Autowired
    PartnerService partnerService;
    @GetMapping("/search")
    public PageVo<PartnerDto> page(Integer pageIndex, Integer pageSize){
        //判断页面信息
        if (null==pageIndex){
            pageIndex=1;
        }
        if(null==pageSize){
            pageSize =10000;
        }

        PageVo<PartnerDto> partnerPage = partnerService.listByPage(pageIndex,pageSize);
         return partnerPage;
    }
}
