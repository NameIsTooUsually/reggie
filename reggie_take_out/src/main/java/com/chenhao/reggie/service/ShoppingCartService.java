package com.chenhao.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.chenhao.reggie.entity.ShoppingCart;

import java.util.List;

public interface ShoppingCartService extends IService<ShoppingCart> {
    //添加商品到购物车
    ShoppingCart add(ShoppingCart shoppingCart);
    //查询用户购物车信息
    List<ShoppingCart> listByUserId();

    //清空用户购物车信息
    boolean cleanByUesrId();
    //减少购物车信息
    ShoppingCart sub(ShoppingCart shoppingCart);
}