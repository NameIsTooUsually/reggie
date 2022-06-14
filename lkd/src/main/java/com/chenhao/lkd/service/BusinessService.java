package com.chenhao.lkd.service;

import com.chenhao.lkd.pojo.Business;

import java.util.List;

/**
 * @author ChenHao
 * @version 1.0
 * @description:
 * @date 2022/6/13 16:56
 */
public interface BusinessService {
    //查询商圈信息
    List<Business> page();

    Business getById(Integer businessId);
}
