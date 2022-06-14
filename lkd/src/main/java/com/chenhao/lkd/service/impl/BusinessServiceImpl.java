package com.chenhao.lkd.service.impl;

import com.chenhao.lkd.mapper.BusinessMapper;
import com.chenhao.lkd.pojo.Business;
import com.chenhao.lkd.service.BusinessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author ChenHao
 * @version 1.0
 * @description:
 * @date 2022/6/13 16:56
 */
@Service
public class BusinessServiceImpl implements BusinessService {
    @Autowired
    BusinessMapper businessMapper;
    //查询商圈信息
    @Override
    public List<Business> page() {
        List<Business> businesses = businessMapper.selectList(null);

        return businesses;
    }

    //根据id获取商圈信息
    @Override
    public Business getById(Integer businessId) {
        Business business = businessMapper.selectById(businessId);
        return business;
    }
}
