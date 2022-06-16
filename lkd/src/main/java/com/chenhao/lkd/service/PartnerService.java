package com.chenhao.lkd.service;

import com.chenhao.lkd.pojo.Partner;
import com.chenhao.lkd.pojo.dto.PartnerDto;
import com.chenhao.lkd.pojo.vo.PageVo;

import java.util.List;

public interface PartnerService {
    //查询parther信息
    PageVo<PartnerDto> listByPage(Integer pageIndex, Integer pageSize);

    //添加合作商信息
    boolean save(Partner partner);

    //重置密码
    boolean resetPwd(Integer id);
    //修改合作商信息
    boolean update(Partner partner);

    //根据id进行删除
    boolean deleteById(Integer id);
}
