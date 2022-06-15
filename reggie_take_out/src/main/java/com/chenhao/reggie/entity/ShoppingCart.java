package com.chenhao.reggie.entity;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class ShoppingCart implements Serializable {
    private static final long serialVersionUID = 1L;

    // 购物车中某一类商品的id
    private Long id;

    // 某个菜品/套餐名称
    private String name;

    // 用户id
    private Long userId;

    // 菜品id
    private Long dishId;

    // 套餐id
    private Long setmealId;

    // 菜品的口味
    private String dishFlavor;

    // 某个菜品/套餐的数量
    private Integer number;

    // 单个菜品/套餐的金额
    private Integer amount;

    // 菜品或套餐的图片
    private String image;

    private LocalDateTime createTime;
}