package com.chenhao.lkd.pojo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author ChenHao
 * @version 1.0
 * @description:
 * @date 2022/6/12 7:30
 */
@Data
@TableName("tb_node")
public class Node {
    private Long id;
    private String name;
    private String addr;
    private String areaCode;
    private Integer createUserId;
    private Long regionId;
    private Integer businessId;
    private Integer ownerId;
    private String ownerName;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;



}
