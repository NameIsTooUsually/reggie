package com.chenhao.lkd.web.controller;

import com.chenhao.lkd.pojo.vo.PageVo;
import com.chenhao.lkd.service.NodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}