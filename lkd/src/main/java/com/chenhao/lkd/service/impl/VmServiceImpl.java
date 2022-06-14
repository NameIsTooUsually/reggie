package com.chenhao.lkd.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.chenhao.lkd.mapper.VmMappper;
import com.chenhao.lkd.pojo.Vm;
import com.chenhao.lkd.service.VmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author ChenHao
 * @version 1.0
 * @description:
 * @date 2022/6/13 21:52
 */
@Service
public class VmServiceImpl implements VmService {
    @Autowired
    VmMappper vmMappper;

    //根据nodeid获取VM信息
    @Override
    public List<Vm> getListByNodeId(Long nodeId) {
        //创建查询对象
        LambdaQueryWrapper<Vm> qw = new LambdaQueryWrapper<>();
        qw.eq(Vm::getNodeId,nodeId);
        List<Vm> vms = vmMappper.selectList(qw);
        return vms;
    }
}
