package com.chenhao.reggie.web.controller;

import com.chenhao.reggie.entity.User;
import com.chenhao.reggie.utils.SMSUtils;
import com.chenhao.reggie.utils.ValidateCodeUtils;
import com.chenhao.reggie.web.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 * @author ChenHao
 * @version 1.0
 * @description:
 * @date 2022/6/14 15:43
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    HttpSession session;

    @PostMapping("/sendMsg")
    public R sendMsg(@RequestBody User user){
        //判断手机号是否有误
        String phone = user.getPhone();

        if(null!=phone&&phone.length()==11){
            //生成验证码
            Integer code = ValidateCodeUtils.generateValidateCode(6);
            //添加验证码到session
            session.setAttribute("checkCode",code);
            System.out.println(code);
            //发送验证码
            SMSUtils.sendMessage("18726159095",code.toString());
            return R.success("发送成功");
        }
        return R.fail("手机号码有误");

    }
}
