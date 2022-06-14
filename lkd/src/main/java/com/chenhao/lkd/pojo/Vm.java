package com.chenhao.lkd.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author ChenHao
 * @version 1.0
 * @description:
 * @date 2022/6/13 21:40
 */
@Data
@TableName("tb_vending_machine")
public class Vm {
    private Long id;
    private Integer vmType;
    private String innerCode;
    private Long nodeId;
    private Integer vmStatus;
    private LocalDateTime lastSupplyTime;
    private String cityCode;
    private Integer areaId;
    private Long createUserId;
    private String createUserName;
    private Integer businessId;
    private Long regionId;
    private Integer ownerId;
    private String ownerName;
    private String clientId;
    private Double longitudes;
    private Double latitude;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
