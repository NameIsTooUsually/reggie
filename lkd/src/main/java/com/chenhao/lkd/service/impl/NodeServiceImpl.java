package com.chenhao.lkd.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chenhao.lkd.mapper.NodeMapper;
import com.chenhao.lkd.pojo.Business;
import com.chenhao.lkd.pojo.Node;
import com.chenhao.lkd.pojo.Region;
import com.chenhao.lkd.pojo.dto.NodeDto;
import com.chenhao.lkd.pojo.vo.PageVo;
import com.chenhao.lkd.service.BusinessService;
import com.chenhao.lkd.service.NodeService;
import com.chenhao.lkd.service.RegionServcie;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
    @Autowired
    RegionServcie regionServcie;
    @Autowired
    BusinessService businessService;
    @Override
    public PageVo searchPageByRegionId(Integer pageIndex, Integer pageSize, Integer regionId) {
        //判断regionId是否为null
//        if(null==regionId){
//            return null;
//        }
        //处理页面信息
        if (null==pageIndex){
            pageIndex=1;
        }
        if (null==pageSize){
            pageSize=8;
        }

        //创建查询调对象
        LambdaQueryWrapper<Node> qw = new LambdaQueryWrapper<>();
        qw.eq(regionId!=null,Node::getRegionId,regionId);

        //创建分页对象
        Page<Node> page = new Page<>();
        page.setSize(pageSize);
        page.setCurrent(pageIndex);

        //查询页面信息
        nodeMapper.selectPage(page,qw);
        //查询页面总数
        List<Node> nodes = nodeMapper.selectList(null);

        //添加返回参数
        PageVo<NodeDto> nodePageVo = new PageVo<>();
        nodePageVo.setPageIndex(pageIndex);
        nodePageVo.setPageSize(pageSize);
        // 创建一个集合用于接收nodedto
        List<NodeDto> list = new ArrayList<>();
        //页面数据
        List<Node> records = page.getRecords();
        for (Node node : records) {
            //创建noteDto 对象用于返回
            NodeDto nodeDto = new NodeDto();
            //复制
            BeanUtils.copyProperties(node,nodeDto);
            //查询region信息,并设置
           Region region =  regionServcie.getById(node.getRegionId());
           nodeDto.setRegion(region);
           //查询Business信息，并记录
            Business business = businessService.getById(node.getBusinessId());
            nodeDto.setBusinessType(business);

            //添加进集合中
            list.add(nodeDto);

        }

        nodePageVo.setCurrentPageRecords(list);
        nodePageVo.setTotalCount(nodes.size());

        if (nodes.size()<=pageSize){
            nodePageVo.setTotalPage(1);
        }else{
            nodePageVo.setTotalPage((nodes.size()/pageSize)+1);
        }

        return nodePageVo;
    }

    @Override
    public List<Node> searchByName(String partnerName) {
        LambdaQueryWrapper<Node> qw = new LambdaQueryWrapper<>();
        qw.eq(Node::getOwnerName,partnerName);
        List<Node> nodes = nodeMapper.selectList(qw);
        return nodes;
    }

    @Override
    public boolean addNode(Node node) {

        int insert = nodeMapper.insert(node);

        return insert>0;
    }
}
