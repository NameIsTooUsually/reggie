package com.chenhao.lkd.service.impl;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.chenhao.lkd.mapper.RegionMapper;
import com.chenhao.lkd.pojo.Region;
import com.chenhao.lkd.pojo.dto.RegionDto;
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
        pageVo.setPageIndex(pageIndex+1);
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
    public boolean add(RegionDto regionDto) {
        //创建region对象
        Region region = new Region();
        //设置参数
        region.setId(IdWorker.getId());
        region.setName(regionDto.getRegionName());
        region.setRemark(regionDto.getRemark());
        //添加
        int result = regionMapper.insert(region);
        return result>0;
    }

    //根据id查询
    @Override
    public Region getById(Long regionId) {
        Region region = regionMapper.getById(regionId);
        return region;
    }

    //根据id更新
    @Override
    public boolean updateById(Long id, RegionDto regionDto) {
        //创建region对象
        Region region = new Region();
        //设置参数
        region.setName(regionDto.getRegionName());
        region.setRemark(regionDto.getRemark());
        //设置id
        region.setId(id);
        int i = regionMapper.updateById(region);

        return i>0;
    }
}
