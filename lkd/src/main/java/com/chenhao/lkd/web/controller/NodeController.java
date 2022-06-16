package com.chenhao.lkd.web.controller;

import com.chenhao.lkd.pojo.Node;
import com.chenhao.lkd.pojo.Vm;
import com.chenhao.lkd.pojo.vo.PageVo;
import com.chenhao.lkd.service.NodeService;
import com.chenhao.lkd.service.VmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    @Autowired
    VmService vmService;
    @GetMapping("/search")
    //todo 需要修改接收参数，修改成Node
    public PageVo searchPageByRegionId(Integer pageIndex, Integer pageSize, Long regionId,Node node) {
        //调用方法，查询页面
        PageVo pageVo = nodeService.searchPageByRegionId(pageIndex,pageSize,regionId,node);
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

    //根据id修改node信息
    @PutMapping("/{id}")
    public boolean updateNode(@PathVariable Long id,@RequestBody Node node){
        //判断参数
        if(null!=id){
            boolean updateRest = nodeService.updateNodeById(id,node);
            if(updateRest){
                return true;
            }
            return false;

        }

        return false;
    }

    //根据id进行删除，如果点位下有机器就不能删除
    @DeleteMapping("/{id}")
    public String deleteById(@PathVariable Long id){
        //判断id是否为空
        if(null!=id){
            boolean deleteResult = nodeService.deleteById(id);
            if(deleteResult){
                return "删除成功";
            }
            return "点位下有售货机，不可删除";
        }
        return "参数异常";
    }
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