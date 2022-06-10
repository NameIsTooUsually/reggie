package com.chenhao.lkd.service;

import com.chenhao.lkd.pojo.vo.SkuPageVo;

/**
 * @author ChenHao
 * @version 1.0
 * @description:
 * @date 2022/6/10 15:36
 */
public interface SkuService {

    SkuPageVo searchByPage(Integer pageIndex, Integer pageSize);
}
