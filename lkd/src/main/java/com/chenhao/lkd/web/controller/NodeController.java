package com.chenhao.lkd.web.controller;

import com.chenhao.lkd.pojo.Node;
import com.chenhao.lkd.pojo.vo.PageVo;
import com.chenhao.lkd.service.NodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author ChenHao
 * @version 1.0
 * @description:
 * @date 2022/6/12 7:39
 */
@RestController
@RequestMapping("/api/vm-service/node")
public class NodeController {
    @Autowired
    NodeService nodeService;

    @GetMapping("/search")
    public PageVo searchPageByRegionId(Integer pageIndex, Integer pageSize, Integer regionId) {
        //调用方法，查询页面
        PageVo pageVo = nodeService.searchPageByRegionId(pageIndex,pageSize,regionId);
        return pageVo;
    }
    //添加node信息
    @PostMapping
    public boolean addNode(@RequestBody Node node){
        if(null!=node){
            boolean addResult = nodeService.addNode(node);
        }
        return false;
    }

}