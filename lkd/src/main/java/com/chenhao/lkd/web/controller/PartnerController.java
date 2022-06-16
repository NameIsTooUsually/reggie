package com.chenhao.lkd.web.controller;

import com.chenhao.lkd.pojo.Partner;
import com.chenhao.lkd.pojo.dto.PartnerDto;
import com.chenhao.lkd.pojo.vo.PageVo;
import com.chenhao.lkd.service.PartnerService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author ChenHao
 * @version 1.0
 * @description:
 * @date 2022/6/13 17:15
 */
@RestController
@RequestMapping("/api/user-service/partner")
public class PartnerController {
    @Autowired
    PartnerService partnerService;

    //查询合作商
    @GetMapping("/search")
    public PageVo<PartnerDto> page(Integer pageIndex, Integer pageSize){
        //判断页面信息
        if (null==pageIndex){
            pageIndex=1;
        }
        if(null==pageSize){
            pageSize =10000;
        }

        PageVo<PartnerDto> partnerPage = partnerService.listByPage(pageIndex,pageSize);
         return partnerPage;
    }
    //添加合作商
    @PostMapping
    public boolean save(@RequestBody Partner partner){
        //添加合作商
        //设置密码
        boolean saveResult = partnerService.save(partner);

        if(saveResult){
            //添加成功
            return true;
        }
        return false;
    }

    //修改密码
    @PutMapping("/resetPwd/{id}")
    public boolean resetPwd(@PathVariable Integer id){
        //判断参数是否异常

        if(null==id){
            //用户id异常
            return false;
        }

       boolean resetResult = partnerService.resetPwd(id);

        if(resetResult){
            //密码修改成功
            return true;
        }
        return false;
    }

    //修改合作商信息
    @PutMapping("/{id}")
    public boolean updatePartner(@PathVariable Integer id,@RequestBody Partner partner){
        //判断参数
        if (null == id) {
            //id 参数异常
            return false;
        }
        //设置id
        partner.setId(id);
        boolean saveResult = partnerService.update(partner);
        if(saveResult){
            return true;
        }
        return false;
    }

    //根据id删除合作商
    @DeleteMapping("/{id}")
    public boolean deleteById(@PathVariable Integer id){
        //判断参数
        if(null==id){
            //id参数异常
            return false;
        }
        boolean deleteResult = partnerService.deleteById(id);
        if(deleteResult){
            //删除成功
            return true;
        }
        //删除失败
        return false;

    }
}
