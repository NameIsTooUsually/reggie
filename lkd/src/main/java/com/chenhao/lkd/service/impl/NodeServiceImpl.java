package com.chenhao.lkd.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
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
import com.chenhao.lkd.service.VmService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.naming.Name;
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
    @Autowired
    VmService vmService;

    //根据regionId进行查询
    @Override
    public PageVo searchPageByRegionId(Integer pageIndex, Integer pageSize, Long regionId, Node node1) {
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
        qw.eq(regionId!=null,Node::getRegionId,regionId)
        .like(node1.getName()!=null,Node::getName, node1.getName());

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
            //查询该点位下机器数量
            int vmSum = vmService.findByNodeId(node.getId());
            nodeDto.setVmCount(vmSum);

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
    //根据名称进行查询
    @Override
    public List<Node> searchByName(String partnerName) {
        LambdaQueryWrapper<Node> qw = new LambdaQueryWrapper<>();
        qw.eq(Node::getOwnerName,partnerName);
        List<Node> nodes = nodeMapper.selectList(qw);
        return nodes;
    }
    //添加node
    @Override
    public boolean addNode(Node node) {

        int insert = nodeMapper.insert(node);

        return insert>0;
    }

    //根据id进行修改
    @Override
    public boolean updateNodeById(Long id, Node node) {
        //创建修改条件对象
        LambdaQueryWrapper<Node> qw = new LambdaQueryWrapper<>();
        qw.eq(Node::getId,id);
        int update = nodeMapper.update(node, qw);
        return update>0;
    }

    //根据id进行删除，并判断是否有机器关联
    @Override
    public boolean deleteById(Long id) {
        //判断该id是否有机器关联
        int count = vmService.findByNodeId(id);

        if(count>0){
            //返回值大于0，说明有机器被关联
            return false;
        }

        //执行删除
        LambdaQueryWrapper<Node> qw = new LambdaQueryWrapper<>();
        qw.eq(Node::getId,id);
        int delete = nodeMapper.delete(qw);
        return delete>0;
    }

    //根据合作商id查询节点
    @Override
    public Integer searchByPartnerId(Integer id) {
        //创建查询条件
        LambdaQueryWrapper<Node> qw = new LambdaQueryWrapper<>();
        qw.eq(Node::getOwnerId,id);
        Integer count = nodeMapper.selectCount(qw);
        return count;
    }

    @Override
    public Node getById(Long nodeId) {
        Node node = nodeMapper.selectById(nodeId);
        return node;
    }
}
