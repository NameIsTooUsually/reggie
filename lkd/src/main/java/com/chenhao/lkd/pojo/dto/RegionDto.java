package com.chenhao.lkd.pojo.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

/**
 * @author ChenHao
 * @version 1.0
 * @description:
 * @date 2022/6/13 15:55
 */
@Data
public class RegionDto {
    private Long id;
    @TableField("name")
    private String regionName;
    private String remark;
    @TableField(exist = false)
    private Integer nodeCount;
}
