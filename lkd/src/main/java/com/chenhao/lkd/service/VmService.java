package com.chenhao.lkd.service;

import com.chenhao.lkd.pojo.Vm;
import com.chenhao.lkd.pojo.vo.PageVo;
import com.chenhao.lkd.pojo.dto.VmDto;

import java.util.List;

public interface VmService {
    //根据nodeid获取VM信息
    List<Vm> getListByNodeId(Long nodeId);

    //根据nodeID查询
    int findByNodeId(Long id);

    //查询设备信息
    PageVo<VmDto> page(Integer pageIndex, Integer pageSize);

}
