package com.chenhao.lkd.service;

import com.chenhao.lkd.pojo.Node;
import com.chenhao.lkd.pojo.vo.PageVo;

import java.util.List;

public interface NodeService {
    PageVo searchPageByRegionId(Integer pageIndex, Integer pageSize, Integer regionId);
    //根据名称查询
    List<Node> searchByName(String partnerName);

    //添加node信息
    boolean addNode(Node node);
}
