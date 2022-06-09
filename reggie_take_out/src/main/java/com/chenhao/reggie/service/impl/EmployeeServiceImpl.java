package com.chenhao.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chenhao.reggie.entity.Employee;
import com.chenhao.reggie.mapper.EmployeeMapper;
import com.chenhao.reggie.service.EmployeeService;
import com.chenhao.reggie.utils.BaseContextUtil;
import com.chenhao.reggie.web.controller.EmployeeController;
import com.chenhao.reggie.web.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

/**
 * @author ChenHao
 * @version 1.0
 * @description:
 * @date 2022/6/8 12:06
 */
@Service
@Slf4j
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements EmployeeService {
//    @Autowired
//    HttpServletRequest request;

    @Override
    public boolean saveWithCheckUsername(Employee employee) {

        Long employeeId = BaseContextUtil.getCurrentId();
        //判断用户名是否已经存在
        String username = employee.getUsername();
        //设置查询条件
        LambdaQueryWrapper<Employee> qw = new LambdaQueryWrapper<>();
        qw.eq(StringUtils.isNotBlank(username),Employee::getUsername,username);
        //根据条件查询用户名
        Employee emp = this.getOne(qw);

        //判断
        if(null!=emp){
            //用户名已经存在
            throw new BusinessException("用户名"+username+"已存在");
        }

        //设置默认密码
        String pwd = DigestUtils.md5DigestAsHex("123456".getBytes());
        employee.setPassword(pwd);

        //设置默认状态
        employee.setStatus(1);

        /*//设置时间
        employee.setCreateTime(LocalDateTime.now());
        employee.setUpdateTime(LocalDateTime.now());*/

        //设置操作用户
        //获取操作用户id
        //Long employeeId = (Long) request.getSession().getAttribute("employee");

       /* employee.setCreateUser(employeeId);
        employee.setUpdateUser(employeeId);*/

        //添加用户
        return this.save(employee);
    }
}
