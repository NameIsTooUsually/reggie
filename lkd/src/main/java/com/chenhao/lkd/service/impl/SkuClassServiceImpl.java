package com.chenhao.lkd.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chenhao.lkd.mapper.SkuClassMapper;
import com.chenhao.lkd.mapper.SkuMapper;
import com.chenhao.lkd.pojo.Sku;
import com.chenhao.lkd.pojo.SkuClass;
import com.chenhao.lkd.pojo.vo.SkuPageVo;
import com.chenhao.lkd.service.SkuClassService;
import com.chenhao.lkd.service.SkuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author ChenHao
 * @version 1.0
 * @description:
 * @date 2022/6/10 15:37
 */
@Service
public class SkuClassServiceImpl implements SkuClassService {

    @Autowired
    SkuClassMapper skuClassMapper;
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
        Integer skuCount = skuClassMapper.selectCount(null);

        //开始分页查询，创建页面对象
        Page<SkuClass> page = new Page<>();
        page.setSize(pageSize);
        page.setCurrent(pageIndex);

        skuClassMapper.selectPage(page,null);

        //设置返回参数
        SkuPageVo<SkuClass> skuSkuPageVo = new SkuPageVo<>();
        skuSkuPageVo.setPageIndex(pageIndex);
        skuSkuPageVo.setPageSize(pageSize);
        skuSkuPageVo.setTotalCount(skuCount);
        if(skuCount<=pageSize){
            skuSkuPageVo.setTotalPage(1);
        }else {
            skuSkuPageVo.setTotalPage((skuCount/pageSize)+1);
        }
        skuSkuPageVo.setCurrentPageRecords(page.getRecords());

        return skuSkuPageVo;
    }
}
