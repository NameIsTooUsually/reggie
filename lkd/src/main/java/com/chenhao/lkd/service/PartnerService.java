package com.chenhao.lkd.service;

import com.chenhao.lkd.pojo.Partner;
import com.chenhao.lkd.pojo.dto.PartnerDto;
import com.chenhao.lkd.pojo.vo.PageVo;

import java.util.List;

public interface PartnerService {
    //查询parther信息
    PageVo<PartnerDto> listByPage(Integer pageIndex, Integer pageSize);
}
