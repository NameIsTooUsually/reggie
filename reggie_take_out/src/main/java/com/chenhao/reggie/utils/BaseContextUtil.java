package com.chenhao.reggie.utils;

/**
 * @author ChenHao
 * @version 1.0
 * @description:
 * @date 2022/6/9 17:14
 */
public class BaseContextUtil {
    private static ThreadLocal<Long> threadLocal = new ThreadLocal<>();
    /**
     * 设置值
     * @param id
     */
    public static void setCurrentId(Long id){
        threadLocal.set(id);
    }
    /**
     * 获取值
     * @return
     */
    public static Long getCurrentId(){
        return threadLocal.get();
    }
}
