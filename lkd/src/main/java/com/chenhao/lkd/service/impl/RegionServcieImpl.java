package com.chenhao.lkd.service.impl;

import com.chenhao.lkd.mapper.RegionMapper;
import com.chenhao.lkd.pojo.Region;
import com.chenhao.lkd.pojo.vo.PageVo;
import com.chenhao.lkd.service.RegionServcie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author ChenHao
 * @version 1.0
 * @description:
 * @date 2022/6/11 14:13
 */
@Service
public class RegionServcieImpl implements RegionServcie {

    @Autowired
    RegionMapper regionMapper;
    //根据页面和姓名查询
    @Override
    public PageVo searchByPageAndName(Integer pageIndex, Integer pageSize, String name) {
        //处理页面信息
        if (null==pageIndex||pageIndex<=0){
            pageIndex=0;
        }else {
            pageIndex = pageIndex-1;
        }

        //查询页面数据
        List<Region> list = regionMapper.searchByPageAndName(pageIndex,pageSize,name);
        System.out.println(list);
        //封装返回数据
        PageVo<Region> pageVo = new PageVo<>();
        pageVo.setCurrentPageRecords(list);
        pageVo.setPageSize(pageSize);
        pageVo.setPageIndex(pageIndex);
        int total = regionMapper.searchTotal();
        pageVo.setTotalPage(total);

        if(total<=pageSize){
            pageVo.setTotalPage(1);
        }else {
            pageVo.setTotalPage((total/pageSize)+1);
        }

        return pageVo;
    }

    @Override
    public boolean add(Region region) {

        int result = regionMapper.add(region);

        return result>0;
    }
}
