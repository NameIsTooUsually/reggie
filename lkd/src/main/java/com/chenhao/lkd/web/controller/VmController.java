package com.chenhao.lkd.web.controller;

import com.chenhao.lkd.pojo.vo.PageVo;
import com.chenhao.lkd.pojo.dto.VmDto;
import com.chenhao.lkd.service.VmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ChenHao
 * @version 1.0
 * @description:
 * @date 2022/6/13 21:46
 */
@RestController
@RequestMapping("/api/vm-service/vm")
public class VmController {
    @Autowired
    VmService vmService;



    //查询设备信息
    @GetMapping("/search")
    public PageVo<VmDto> page(Integer pageIndex, Integer pageSize){
        //判断页面信息
        if(null==pageIndex){
            pageIndex=1;
        }
        if(null==pageSize){
            pageSize=8;
        }

        PageVo<VmDto> pageVo = vmService.page(pageIndex,pageSize);
        return pageVo;
    }
}
