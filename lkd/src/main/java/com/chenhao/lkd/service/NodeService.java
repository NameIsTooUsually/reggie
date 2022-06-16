package com.chenhao.lkd.service;

import com.chenhao.lkd.pojo.Node;
import com.chenhao.lkd.pojo.Region;
import com.chenhao.lkd.pojo.vo.PageVo;

import java.util.List;

public interface NodeService {
    PageVo searchPageByRegionId(Integer pageIndex, Integer pageSize, Long regionId, Node node);
    //根据名称查询
    List<Node> searchByName(String partnerName);

    //添加node信息
    boolean addNode(Node node);
    //根据id进行修改
    boolean updateNodeById(Long id, Node node);

    //根据id进行删除
    boolean deleteById(Long id);

    //根据合作商id查询点位信息
    Integer searchByPartnerId(Integer id);

    //根据id获取node信息
    Node getById(Long nodeId);
}
