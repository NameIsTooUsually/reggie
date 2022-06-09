package com.chenhao.reggie.common;

import com.chenhao.reggie.web.R;
import com.chenhao.reggie.web.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author ChenHao
 * @version 1.0
 * @description:
 * @date 2022/6/9 10:06
 */
@Slf4j
@RestControllerAdvice(annotations = {RestController.class, Controller.class})
public class GlobalExceptionHandler {

    //处理业务异常
    @ExceptionHandler(BusinessException.class)
    public R businessExceptionHandler(BusinessException e){
        //打印日志
        log.warn(e.getMessage());

        return R.fail(e.getMessage());

    }
}
