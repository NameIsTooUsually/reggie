package com.chenhao.reggie.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author ChenHao
 * @version 1.0
 * @description:
 * @date 2022/6/12 20:30
 */
@Data
public class SetmealDish {
    private Long id;
    private String setmealId;
    private String dishId;
    private String name;
    private BigDecimal price;
    private Integer copies;
    private Integer sort;
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
    @TableField(fill = FieldFill.INSERT)
    private Long createUser;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long updateUser;
    @TableLogic(value = "0",delval = "1")
    private Integer isDeleted;
}
