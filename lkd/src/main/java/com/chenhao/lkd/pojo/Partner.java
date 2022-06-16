package com.chenhao.lkd.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author ChenHao
 * @version 1.0
 * @description:
 * @date 2022/6/13 17:06
 */
@Data
@TableName("tb_partner")
public class Partner {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String name;
    private String password;
    private String account;
    private String mobile;
    private String phone;
    private String email;
    private String province;
    private String city;
    private String county;
    private String addr;
    private String contact;
    private Integer ratio;
    private Integer status;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
