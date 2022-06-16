package com.chenhao.lkd.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chenhao.lkd.mapper.PartherMapper;
import com.chenhao.lkd.pojo.Node;
import com.chenhao.lkd.pojo.Partner;
import com.chenhao.lkd.pojo.dto.PartnerDto;
import com.chenhao.lkd.pojo.vo.PageVo;
import com.chenhao.lkd.service.NodeService;
import com.chenhao.lkd.service.PartnerService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.time.LocalDateTime;
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
    //添加合作商信息
    @Override
    public boolean save(Partner partner) {
        //判断该合作商是否存在
        String name = partner.getName();
        if(!StringUtils.isNotBlank(name)){
            //没有设置用户名，参数异常，添加失败
            return false;
        }
        LambdaQueryWrapper<Partner> qw = new LambdaQueryWrapper<>();
        qw.eq(Partner::getName,name);
        //将用户名设置成条件，判断该用户是否已经存在
        Partner partner1 = partherMapper.selectOne(qw);
        if(null!=partner1){
            //该用户名已经存在，添加失败
            return false;
        }

        //设置默认密码
        String pwd = DigestUtils.md5DigestAsHex(partner.getPassword().getBytes());
        partner.setPassword(pwd);
        //设置添加修改时间
        partner.setCreateTime(LocalDateTime.now());
        partner.setUpdateTime(LocalDateTime.now());

        //添加合作商
        int insert = partherMapper.insert(partner);
        return insert>0;
    }

    //重置合作商密码
    @Override
    public boolean resetPwd(Integer id) {
        //重置成默认密码
        String pwd = DigestUtils.md5DigestAsHex("123456".getBytes());
        //设置修改参数
        Partner partner = new Partner();
        partner.setPassword(pwd);
        partner.setId(id);
        int i = partherMapper.updateById(partner);

        return i>0;
    }
    //根据根据id修改合作商信息
    @Override
    public boolean update(Partner partner) {
        //根据id进行修改
        int i = partherMapper.updateById(partner);
        return i>0;
    }

    //根据id删除合作商
    @Override
    public boolean deleteById(Integer id) {
        //删除前，判断是否该合作商下是否有点位关联
        Integer nodeSum = nodeService.searchByPartnerId(id);
        if(nodeSum>0){
            //该合作商还有点位关联
            return false;
        }
        //删除合作商信息
        int i = partherMapper.deleteById(id);
        return i>0;
    }
}
