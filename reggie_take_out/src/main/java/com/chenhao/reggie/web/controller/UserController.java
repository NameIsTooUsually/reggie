package com.chenhao.reggie.web.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.chenhao.reggie.entity.User;
import com.chenhao.reggie.service.UserService;
import com.chenhao.reggie.utils.SMSUtils;
import com.chenhao.reggie.utils.ValidateCodeUtils;
import com.chenhao.reggie.web.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.Map;

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
    UserService userService;

    @Autowired
    HttpSession session;

    @PostMapping("/sendMsg")
    public R sendMsg(@RequestBody User user) {
        //判断手机号是否有误
        String phone = user.getPhone();
        if (null != phone && phone.length() == 11) {
            //生成验证码
            //Integer code = ValidateCodeUtils.generateValidateCode(6);
            Integer code=1234;
            //添加验证码到session
            session.setAttribute(phone, code);
            System.out.println(code);
            //发送验证码
            SMSUtils.sendMessage(phone, code.toString());
            return R.success("发送成功");
        }
        return R.fail("手机号码有误");
    }

    //登录
    @PostMapping("/login")
    public R login(@RequestBody Map<String, String> map) {
        //获取code和phone参数
        String code = map.get("code");
        String phone = map.get("phone");


        //判断
        if (null != code && null != phone && phone.length() == 11) {
            //判断验证码是否正确,获取存在session中验证码
            Object sessionCode = session.getAttribute(phone);

            //判断验证码是否存在，如果没有获取到验证，则说明手机号不对，或验证码过期了
            if(null==sessionCode)
            {
                return R.fail("请检查手机号码是否正确，或验证码已经过期了");
            }
            if(code.equals(sessionCode.toString())){
                //验证码正确，判断手机号是否注册过
                LambdaQueryWrapper<User> qw = new LambdaQueryWrapper<>();
                qw.eq(User::getPhone, phone);
                User user = userService.getOne(qw);
                //判断user
                if (null == user) {
                    //说明该用户还未注册
                    user = new User();
                    //设置参数
                    user.setPhone(phone);
                    user.setStatus(1);
                    //注册
                    userService.save(user);
                }
                //登录成功，将用户id存入session
                session.setAttribute("user",user.getId());

                //登录成功，清除验证码session
                session.removeAttribute(phone);
                return R.success("登录成功");
            }else{
                return R.fail("验证码错误");
            }
        }


        return R.fail("参数异常");
    }
}
