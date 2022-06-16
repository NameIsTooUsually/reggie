package com.chenhao.lkd.service.impl;

import com.chenhao.lkd.mapper.VmTypeMapper;
import com.chenhao.lkd.pojo.VmType;
import com.chenhao.lkd.service.VmtypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author ChenHao
 * @version 1.0
 * @description:
 * @date 2022/6/16 20:51
 */
@Service
public class VmtypeServiceImpl implements VmtypeService {
    @Autowired
    VmTypeMapper vmTypeMapper;

    //根据id获取vmType信息
    @Override
    public VmType getById(Integer vmType) {
        VmType vmType1 = vmTypeMapper.selectById(vmType);
        return vmType1;
    }
}
