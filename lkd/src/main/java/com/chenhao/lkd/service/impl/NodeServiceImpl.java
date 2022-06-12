package com.chenhao.lkd.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chenhao.lkd.mapper.NodeMapper;
import com.chenhao.lkd.pojo.Node;
import com.chenhao.lkd.pojo.vo.PageVo;
import com.chenhao.lkd.service.NodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author ChenHao
 * @version 1.0
 * @description:
 * @date 2022/6/12 7:37
 */
@Service
public class NodeServiceImpl implements NodeService {
    @Autowired
    NodeMapper nodeMapper;
    @Override
    public PageVo searchPageByRegionId(Integer pageIndex, Integer pageSize, Integer regionId) {
        //判断regionId是否为null
        if(null==regionId){
            return null;
        }
        //处理页面信息
        if (null==pageIndex){
            pageIndex=1;
        }

        //创建查询调对象
        LambdaQueryWrapper<Node> qw = new LambdaQueryWrapper<>();
        qw.eq(Node::getRegionId,regionId);

        //创建分页对象
        Page<Node> page = new Page<>();
        page.setSize(pageSize);
        page.setCurrent(pageIndex);

        //查询页面信息
        nodeMapper.selectPage(page,qw);
        //查询页面总数
        List<Node> nodes = nodeMapper.selectList(null);

        //添加返回参数
        PageVo<Node> nodePageVo = new PageVo<>();
        nodePageVo.setPageIndex(pageIndex);
        nodePageVo.setPageSize(pageSize);
        nodePageVo.setCurrentPageRecords(page.getRecords());
        nodePageVo.setTotalCount(nodes.size());

        if (nodes.size()<=pageSize){
            nodePageVo.setTotalPage(1);
        }else{
            nodePageVo.setTotalPage((nodes.size()/pageSize)+1);
        }

        return nodePageVo;
    }
}
