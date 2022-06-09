package com.chenhao.reggie.web.exception;

/**
 * @author ChenHao
 * @version 1.0
 * @description:
 * @date 2022/6/9 10:04
 */
public class BusinessException extends RuntimeException {
    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(String message, Throwable cause) {
        super(message, cause);
    }
}
