package com.chenhao.lkd.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chenhao.lkd.mapper.SkuMapper;
import com.chenhao.lkd.pojo.Sku;
import com.chenhao.lkd.pojo.SkuClass;
import com.chenhao.lkd.pojo.vo.SkuPageVo;
import com.chenhao.lkd.service.SkuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author ChenHao
 * @version 1.0
 * @description:
 * @date 2022/6/10 15:37
 */
@Service
public class SkuServiceImpl implements SkuService {

    @Autowired
    SkuMapper skuMapper;
    @Override
    public SkuPageVo searchByPage(Integer pageIndex, Integer pageSize) {
        //判断页码和页面大小是否为null
        if (null == pageIndex) {
            pageIndex = 1;
        }
        if (null == pageSize) {
            pageSize = 10;
        }

        //查询总数
        Integer skuCount = skuMapper.selectCount(null);

        //开始分页查询，创建页面对象
        Page<Sku> page = new Page<>();
        page.setSize(pageSize);
        page.setCurrent(pageIndex);

        skuMapper.selectPage(page,null);

        //设置返回参数
        SkuPageVo<Sku> skuSkuPageVo = new SkuPageVo<>();
        skuSkuPageVo.setPageIndex(pageIndex);
        skuSkuPageVo.setPageSize(pageSize);
        skuSkuPageVo.setTotalCount(skuCount);
        if(skuCount<=pageSize){
            skuSkuPageVo.setTotalPage(1);
        }else {
            skuSkuPageVo.setTotalPage((skuCount/pageSize)+1);
        }
        List<Sku> skus = page.getRecords();
        for (int i = 0; i < skus.size(); i++) {
            Sku sku = skus.get(i);
            //查询SkuClass类
            SkuClass skuClass = skuMapper.findSkuClassById(sku.getClassId());
            sku.setSkuClass(skuClass);
        }
        skuSkuPageVo.setCurrentPageRecords(page.getRecords());

        return skuSkuPageVo;
    }
}
