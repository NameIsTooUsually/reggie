package com.chenhao.lkd.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author ChenHao
 * @version 1.0
 * @description:
 * @date 2022/6/10 15:28
 */
@Data
@TableName("tb_sku")
public class Sku {
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private Long skuId;
    private String skuName;
    private String skuImage;
    private Integer price;
    private Integer classId;
    private boolean isDiscount;
    private String unit;
    private String brandName;

    @TableField(exist = false)
    private SkuClass skuClass;
    @TableField(exist = false)
    private String capacity;
    @TableField(exist = false)
    private String realPrice;
}
