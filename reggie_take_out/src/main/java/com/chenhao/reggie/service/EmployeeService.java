package com.chenhao.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.chenhao.reggie.entity.Employee;

/**
 * @author ChenHao
 * @version 1.0
 * @description:
 * @date 2022/6/8 12:04
 */
public interface EmployeeService extends IService<Employee> {
    boolean saveWithCheckUsername(Employee employee);
}
