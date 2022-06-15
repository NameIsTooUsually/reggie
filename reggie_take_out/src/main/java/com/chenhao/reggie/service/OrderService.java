package com.chenhao.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.chenhao.reggie.entity.Orders;

public interface OrderService extends IService<Orders> {

    //提交订单
    boolean submit(Orders orders);
}