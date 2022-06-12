package com.chenhao.lkd.service;

import com.chenhao.lkd.pojo.Region;
import com.chenhao.lkd.pojo.vo.PageVo;

/**
 * @author ChenHao
 * @version 1.0
 * @description:
 * @date 2022/6/11 14:13
 */
public interface RegionServcie {
    PageVo searchByPageAndName(Integer pageIndex, Integer pageSize, String name);

    boolean add(Region region);
}
