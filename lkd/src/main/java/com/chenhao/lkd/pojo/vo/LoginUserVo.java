package com.chenhao.lkd.pojo.vo;

import lombok.Data;

@Data
public class LoginUserVo {
   private Integer userId;
   private String userName;
   private String roleCode;
   private boolean success;
   private String msg;
   private String regionId;
   private boolean isRepair;
   private String token;
}
