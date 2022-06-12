package com.chenhao.lkd.service;

import com.chenhao.lkd.pojo.vo.PageVo;

/**
 * @author ChenHao
 * @version 1.0
 * @description:
 * @date 2022/6/10 15:36
 */
public interface SkuClassService {

    PageVo searchByPage(Integer pageIndex, Integer pageSize);
}
