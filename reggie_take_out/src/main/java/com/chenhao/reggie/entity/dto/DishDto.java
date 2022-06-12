package com.chenhao.reggie.entity.dto;

import com.chenhao.reggie.entity.Dish;
import com.chenhao.reggie.entity.DishFlavor;
import lombok.Data;

import java.util.List;

/**
 * @author ChenHao
 * @version 1.0
 * @description:
 * @date 2022/6/12 10:05
 */
@Data
public class DishDto extends Dish {

    private List<DishFlavor> flavors;

    private String categoryName;
}
