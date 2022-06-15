package com.chenhao.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chenhao.reggie.entity.*;
import com.chenhao.reggie.mapper.OrderMapper;
import com.chenhao.reggie.service.*;
import com.chenhao.reggie.utils.BaseContextUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Orders> implements OrderService {

    @Autowired
    UserService userService;
    @Autowired
    AddressBookService addressBookService;
    @Autowired
    ShoppingCartService shoppingCartService;
    @Autowired
    OrderDetailService orderDetailService;

    //提交订单
    @Override
    public boolean submit(Orders orders) {
        //获取用户id
        Long userId = BaseContextUtil.getCurrentId();
        //查询用户数据
        LambdaQueryWrapper<User> qw = new LambdaQueryWrapper<>();
        qw.eq(User::getId,userId);
        User user = userService.getOne(qw);
        //查询地址数据
        AddressBook addressBook = addressBookService.getById(orders.getAddressBookId());

        //设置订单相关参数
        orders.setNumber(UUID.randomUUID().toString());
        orders.setUserId(userId);
        orders.setUserName(user.getName());
        orders.setPhone(user.getPhone());
        orders.setAddress(addressBook.getDetail());
        orders.setStatus(2);
        orders.setPayMethod(1);
        orders.setOrderTime(LocalDateTime.now());
        orders.setCheckoutTime(LocalDateTime.now());
        orders.setConsignee(addressBook.getConsignee());

        //计算订单总金额，购物车
        LambdaQueryWrapper<ShoppingCart> qwS = new LambdaQueryWrapper<>();
        qwS.eq(ShoppingCart::getUserId,userId);
        List<ShoppingCart> shoppingCarts = shoppingCartService.list(qwS);
        Integer amount = 0;
        for (ShoppingCart shoppingCart : shoppingCarts) {
            //获取数量
            Integer number = shoppingCart.getNumber();
            //获取价格
            Integer amount1 = shoppingCart.getAmount();
            amount+=number*amount1;
        }
        orders.setAmount(amount);

        //保存基本订单信息
        boolean saveResult = save(orders);
        if(!saveResult){
            return false;
        }
        //创建集合存储订单详情
        List<OrderDetail> odList = new ArrayList<>();
        //设置订单详情类
        for (ShoppingCart shoppingCart : shoppingCarts) {
            //创建订单详情对象
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setAmount(shoppingCart.getAmount());
            orderDetail.setImage(shoppingCart.getImage());
            orderDetail.setDishFlavor(shoppingCart.getDishFlavor());
            orderDetail.setSetmealId(shoppingCart.getSetmealId());
            orderDetail.setOrderId(orders.getId());
            orderDetail.setDishId(shoppingCart.getDishId());
            orderDetail.setNumber(shoppingCart.getNumber());
            orderDetail.setName(shoppingCart.getName());
            odList.add(orderDetail);
        }
        //设置订单详情信息
        boolean saveBatchResult = orderDetailService.saveBatch(odList);
        if(saveBatchResult){
            //清除购物车信息
            LambdaQueryWrapper<ShoppingCart> qwShop = new LambdaQueryWrapper<>();
            qwShop.eq(ShoppingCart::getUserId,userId);
            return true;
        }
        return false;
    }
}
