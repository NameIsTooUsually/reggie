package com.chenhao.lkd.pojo.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class LoginDto implements Serializable {
    private static final long serialVersionUID = 1L;
    private String loginName;
    private String password;
    private String mobile;
    private String code;
    private String clientToken;
    private Integer loginType;
    private String account;
}
