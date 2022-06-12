package com.chenhao.lkd.service;

import com.chenhao.lkd.pojo.vo.PageVo;

public interface NodeService {
    PageVo searchPageByRegionId(Integer pageIndex, Integer pageSize, Integer regionId);
}
