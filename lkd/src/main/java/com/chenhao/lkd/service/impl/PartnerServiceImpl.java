package com.chenhao.lkd.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chenhao.lkd.mapper.PartherMapper;
import com.chenhao.lkd.pojo.Node;
import com.chenhao.lkd.pojo.Partner;
import com.chenhao.lkd.pojo.dto.PartnerDto;
import com.chenhao.lkd.pojo.vo.PageVo;
import com.chenhao.lkd.service.NodeService;
import com.chenhao.lkd.service.PartnerService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ChenHao
 * @version 1.0
 * @description:
 * @date 2022/6/13 20:58
 */
@Service
public class PartnerServiceImpl implements PartnerService {
    @Autowired
    PartherMapper partherMapper;
    @Autowired
    NodeService nodeService;
    //查询parther信息
    @Override
    public PageVo<PartnerDto> listByPage(Integer pageIndex, Integer pageSize) {
        //创建页面对象
        Page<Partner> page = new Page<>();
        page.setCurrent(pageIndex);
        page.setSize(pageSize);

        //查询页面信息
        partherMapper.selectPage(page,null);
        //查询列表总条目数
        List<Partner> partners1 = partherMapper.selectList(null);
        //合作商列表
        List<Partner> partners = page.getRecords();

        //创建返回对象
        PageVo<PartnerDto> pageVo = new PageVo<>();
        //创建接收PartnerDto的集合
        List<PartnerDto> partnerDtos = new ArrayList<>();
        //设置返回值
        pageVo.setPageIndex(pageIndex);
        pageVo.setPageSize(pageSize);
        pageVo.setTotalCount(partners1.size());
        if(partners1.size()<=pageSize){
            pageVo.setTotalPage(1);
        }else{
            pageVo.setTotalPage((partners1.size()/pageSize)+1);
        }
        for (Partner partner : partners) {
            //创建partnerDto对象
            PartnerDto partnerDto = new PartnerDto();
            //复制并去除密码
            BeanUtils.copyProperties(partner,partnerDto,"password");
            //获取合作商名称
            String partnerName = partner.getName();
            //根据合作商名称查询名下node
            List<Node> nodes = nodeService.searchByName(partnerName);
            partnerDto.setVmCount(nodes.size());
            //设置至集合
            partnerDtos.add(partnerDto);
        }

        //设置集合到返回参数
        pageVo.setCurrentPageRecords(partnerDtos);
        return pageVo;
    }
}
