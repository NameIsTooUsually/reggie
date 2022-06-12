package com.chenhao.lkd.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chenhao.lkd.mapper.SkuMapper;
import com.chenhao.lkd.pojo.Sku;
import com.chenhao.lkd.pojo.SkuClass;
import com.chenhao.lkd.pojo.vo.PageVo;
import com.chenhao.lkd.service.SkuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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

    /**
     * 分页查询
     * @param pageIndex
     * @param pageSize
     * @return
     */
    @Override
    public PageVo searchByPage(Integer pageIndex, Integer pageSize) {
        //判断页码和页面大小是否为null
        if (null == pageIndex) {
            pageIndex = 1;
        }
        if (null == pageSize) {
            pageSize = 5;
        }

        //查询总数
        Integer skuCount = skuMapper.selectCount(null);

        //开始分页查询，创建页面对象
        Page<Sku> page = new Page<>();
        page.setSize(pageSize);
        page.setCurrent(pageIndex);

        skuMapper.selectPage(page,null);

        //设置返回参数
        PageVo<Sku> skuSkuPageVo = new PageVo<>();
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

    /**
     * 添加sku
     * @param sku
     * @return
     */
    @Override
    public boolean addSku(Sku sku) {
        if(null==sku){
            return false;
        }
        sku.setCreateTime(LocalDateTime.now());
        sku.setUpdateTime(LocalDateTime.now());
        //添加sku
        int insert = skuMapper.insert(sku);
        return insert>0;
    }

    /**
     * 根据id修改sku
     * @param skuId
     * @param sku
     * @return
     */
    @Override
    public boolean updateSkuById(Integer skuId, Sku sku) {
        //判断id是否为空
        if(null!=skuId){
            //id不为空，调用方法修改
            //创建修改条件
            LambdaQueryWrapper<Sku> qw = new LambdaQueryWrapper<>();
            qw.eq(Sku::getSkuId,skuId);
            int updateResult = skuMapper.update(sku, qw);

            return updateResult>0;
        }
        return false;
    }
}
