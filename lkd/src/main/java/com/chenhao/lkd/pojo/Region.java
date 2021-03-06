package com.chenhao.lkd.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author ChenHao
 * @version 1.0
 * @description:
 * @date 2022/6/11 13:26
 */
@Data
@TableName("tb_region")
public class Region {
    private Long id;
    private String name;
    private String remark;
    @TableField(exist = false)
    private Integer nodeCount;

}
