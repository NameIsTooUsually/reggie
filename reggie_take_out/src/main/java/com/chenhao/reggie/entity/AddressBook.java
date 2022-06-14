package com.chenhao.reggie.entity;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author ChenHao
 * @version 1.0
 * @description:
 * @date 2022/6/14 19:40
 */
@Data
public class AddressBook {
    private Long id;
    private Long userId;
    private String consignee;
    private Integer sex;
    private String phone;
    private String provinceCode;
    private String provinceName;
    private String cityCode;
    private String cityName;
    private String districtCode;
    private String districtName;
    private String detail;
    private String label;
    private Integer isDefault;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private Long createUser;
    private Long updateUser;
    private Integer isDeleted;
}
