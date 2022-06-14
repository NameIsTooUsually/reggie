package com.chenhao.lkd.service;

import com.chenhao.lkd.pojo.Vm;

import java.util.List;

public interface VmService {
    //根据nodeid获取VM信息
    List<Vm> getListByNodeId(Long nodeId);
}
