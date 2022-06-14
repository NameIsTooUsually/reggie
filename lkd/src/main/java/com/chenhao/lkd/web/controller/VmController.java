package com.chenhao.lkd.web.controller;

import com.chenhao.lkd.pojo.Vm;
import com.chenhao.lkd.service.VmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author ChenHao
 * @version 1.0
 * @description:
 * @date 2022/6/13 21:46
 */
@RestController
@RequestMapping("/api/vm-service/node")
public class VmController {
    @Autowired
    VmService vmService;

    //根据nodeid获取VM信息
    @GetMapping("/vmList/{nodeId}")
    public List<Vm> list(@PathVariable Long nodeId){
        //判断nodeIde是否为null
        if(null!=nodeId){
            List<Vm> vms =  vmService.getListByNodeId(nodeId);
            return vms;
        }

        return null;
    }
}
