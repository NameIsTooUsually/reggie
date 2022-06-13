package com.chenhao.reggie.entity.dto;

import com.chenhao.reggie.entity.Setmeal;
import com.chenhao.reggie.entity.SetmealDish;
import lombok.Data;

import java.util.List;

/**
 * @author ChenHao
 * @version 1.0
 * @description:
 * @date 2022/6/12 19:14
 */
@Data
public class SetmealDto extends Setmeal {
    private  String categoryName;
    private List<SetmealDish> setmealDishes;
}
