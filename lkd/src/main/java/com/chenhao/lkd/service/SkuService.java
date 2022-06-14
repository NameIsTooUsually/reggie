package com.chenhao.lkd.service;

import com.chenhao.lkd.pojo.Sku;
import com.chenhao.lkd.pojo.vo.PageVo;

/**
 * @author ChenHao
 * @version 1.0
 * @description:
 * @date 2022/6/10 15:36
 */
public interface SkuService {

    //分页查询
    PageVo searchByPage(Integer pageIndex, Integer pageSize);
    //添加sku
    boolean addSku(Sku sku);
    //修改sku
    boolean updateSkuById(Long skuId, Sku sku);

}
