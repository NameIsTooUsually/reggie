package com.chenhao.lkd.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.chenhao.lkd.pojo.Sku;
import com.chenhao.lkd.pojo.SkuClass;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * @author ChenHao
 * @version 1.0
 * @description:
 * @date 2022/6/10 15:46
 */
@Mapper
public interface SkuMapper extends BaseMapper<Sku> {

    @Select("select * from tb_sku_class where class_id = #{classId}")
    SkuClass findSkuClassById(Integer classId);

}
