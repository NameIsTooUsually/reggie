package com.chenhao.lkd.service;

import com.chenhao.lkd.pojo.SkuClass;
import com.chenhao.lkd.pojo.vo.PageVo;

/**
 * @author ChenHao
 * @version 1.0
 * @description:
 * @date 2022/6/10 15:36
 */
public interface SkuClassService {

    //分页查询商品分类信息
    PageVo searchByPage(Integer pageIndex, Integer pageSize);
    //添加商品分类信息
    boolean addSkuClass(SkuClass skuClass);
    //根据id修改商品分类信息
    boolean updateSkuClass(Integer id, SkuClass skuClass);
}
