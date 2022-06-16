package com.chenhao.lkd.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.chenhao.lkd.pojo.Region;
import com.chenhao.lkd.pojo.dto.RegionDto;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface RegionMapper extends BaseMapper<Region> {
    List<Region> searchByPageAndName(@Param("pageIndex") Integer pageIndex, @Param("pageSize")Integer pageSize, @Param("name")String name);

    int searchTotal();

    //根据id进行查询
    Region getById(Long id);

   /* //添加区域信息
    @Insert("insert into tb_region (name,remark) values(#{name},#{remark})")
    int add(Region region);*/
}

