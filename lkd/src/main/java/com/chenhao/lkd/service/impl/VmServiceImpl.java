package com.chenhao.lkd.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chenhao.lkd.mapper.VmMappper;
import com.chenhao.lkd.pojo.Node;
import com.chenhao.lkd.pojo.Region;
import com.chenhao.lkd.pojo.Vm;
import com.chenhao.lkd.pojo.VmType;
import com.chenhao.lkd.pojo.vo.PageVo;
import com.chenhao.lkd.pojo.dto.VmDto;
import com.chenhao.lkd.service.NodeService;
import com.chenhao.lkd.service.RegionServcie;
import com.chenhao.lkd.service.VmService;
import com.chenhao.lkd.service.VmtypeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
    @Autowired
    RegionServcie regionServcie;
    @Autowired
    VmtypeService vmtypeService;
    @Autowired
    NodeService nodeService;

    //根据nodeid获取VM信息
    @Override
    public List<Vm> getListByNodeId(Long nodeId) {
        //创建查询对象
        LambdaQueryWrapper<Vm> qw = new LambdaQueryWrapper<>();
        qw.eq(Vm::getNodeId,nodeId);
        List<Vm> vms = vmMappper.selectList(qw);
        return vms;
    }

    //根据nodeID进行查询
    @Override
    public int findByNodeId(Long id) {
        //创建查询参数
        LambdaQueryWrapper<Vm> qw = new LambdaQueryWrapper<>();
        qw.eq(Vm::getNodeId,id);
        Integer count = vmMappper.selectCount(qw);
        return count;
    }

    //查询设备信息
    @Override
    public PageVo<VmDto> page(Integer pageIndex, Integer pageSize) {
        Page<Vm> page = new Page<>();
        page.setCurrent(pageIndex);
        page.setSize(pageSize);
        //查询机器基本信息
        vmMappper.selectPage(page, null);

        //获取基本信息
        List<Vm> vms = page.getRecords();
        //获取总数
        Integer count = vmMappper.selectCount(null);

        //组装返回信息
        PageVo<VmDto> vmVoPageVo = new PageVo<>();
        vmVoPageVo.setPageIndex(pageIndex);
        vmVoPageVo.setPageSize(pageSize);
        vmVoPageVo.setTotalCount(count);
        if(count<=pageSize){
            vmVoPageVo.setTotalPage(1);
        }else{
            vmVoPageVo.setTotalPage((count/pageSize)+1);
        }

        //创建集合接收Vmdto对象
        List<VmDto> vmDtos = new ArrayList<>();

        for (Vm vm : vms) {
            //创建vmdto对象
            VmDto vmDto = new VmDto();
            BeanUtils.copyProperties(vm,vmDto);
            //获取regionDto
            Region region = regionServcie.getById(vm.getRegionId());
            vmDto.setRegion(region);
            //获取VmType
            VmType vmType = vmtypeService.getById(vm.getVmType());
            vmDto.setType(vmType);
            //获取node
            Node node = nodeService.getById(vm.getNodeId());
            vmDto.setNode(node);
            vmDtos.add(vmDto);
        }

        vmVoPageVo.setCurrentPageRecords(vmDtos);

        return vmVoPageVo;
    }
}
