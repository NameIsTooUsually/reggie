package com.chenhao.lkd.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("tb_user")
public class User {
    private Integer id;

    private Integer roleId;

    private String userName;

    private String loginName;

    private String password;

    //private Integer areaId;

    private String secret;

    private String roleCode;

    private String mobile;

    //private Integer companyId;
}
