package com.chenhao.reggie.web.controller;

import com.chenhao.reggie.entity.Orders;
import com.chenhao.reggie.service.OrderService;
import com.chenhao.reggie.web.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 订单
 */
@Slf4j
@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    //提交订单
    @PostMapping("/submit")
    public R submit(@RequestBody Orders orders){
        boolean submitResult = orderService.submit(orders);

        if(submitResult){
           return R.success("提交订单成功");
        }

        return R.fail("提交订单失败");
    }
}