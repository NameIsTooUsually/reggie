package com.chenhao.reggie.web.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chenhao.reggie.entity.Employee;
import com.chenhao.reggie.entity.dto.LoginDTO;
import com.chenhao.reggie.service.EmployeeService;
import com.chenhao.reggie.web.R;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.lang.model.element.VariableElement;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;


/**
 * @author ChenHao
 * @version 1.0
 * @description:
 * @date 2022/6/8 12:08
 */
@Slf4j
@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    EmployeeService employeeService;
    @Autowired
    HttpServletRequest request;

    //登录功能
    @PostMapping("/login")
    public R login(@RequestBody LoginDTO loginDTO) {
        //拿密码转成密文
        String password = loginDTO.getPassword();
        if (StringUtils.isNotBlank(password)) {
            //密码不为空,获取密文
            String pws = DigestUtils.md5DigestAsHex(password.getBytes());

            //获取用户名，根据用户名获取用户，判断用户是否存在
            String username = loginDTO.getUsername();
            LambdaQueryWrapper<Employee> qw = new LambdaQueryWrapper<>();
            qw.eq(null != username, Employee::getUsername, loginDTO.getUsername());
            Employee employee = employeeService.getOne(qw);
            if (null == employee) {
                return R.fail("用户名不存在");
            }

            //验证密码是否正确
            if (!pws.equals(employee.getPassword())) {
                return R.fail("密码错误");
            }
            //验证该用户是否被禁用
            if (0 == employee.getStatus()) {
                return R.fail("都离职了就别登录了");
            }
            //用户名密码正确。登录成功，将用户id存入session
            request.getSession().setAttribute("employee", employee.getId());
            log.info("{} 登录成功", loginDTO.getUsername());
            //提示登录成功
            return R.success("登录成功", employee);
        }
        return R.fail("密码错误");
    }

    //退出功能
    @PostMapping("/logout")
    public R logout() {
        log.info("退出登录");

        request.getSession().removeAttribute("employee");

        return R.success("退出成功");
    }


    //添加员工
    @PostMapping
    public R save(@RequestBody Employee employee) {
        log.info("登录的员工信息为{}",employee);
        //添加员工
        boolean saveResult = employeeService.saveWithCheckUsername(employee);

        if (saveResult) {
            return R.success("添加员工成功");
        }
        return R.fail("添加员工失败");

    }

    //分页查询
    @GetMapping("/page")
    public R<Page<Employee>> page(@RequestParam("page") Integer currentPage, Integer pageSize, String name) {
        log.info("当前页码为{},页面大小为{}，员工姓名为{}",currentPage,pageSize,name);
        //判断页码是否为空
        if (null == currentPage) {
            currentPage = 1;
        }
        //判断页面大小是否为空
        if (null == pageSize) {
            pageSize = 2;
        }

        //创建分页对象
        Page<Employee> page = new Page<>();
        page.setCurrent(currentPage);
        page.setSize(pageSize);

        //进行查询
        //创建条件
        LambdaQueryWrapper<Employee> qw = new LambdaQueryWrapper<>();
        qw.like(StringUtils.isNotBlank(name), Employee::getName, name);
        qw.orderByDesc(Employee::getUpdateTime);

        employeeService.page(page, qw);

        //数据要返回前端页面，需要将密码影藏
        List<Employee> records = page.getRecords();
        for (Employee emp : records) {
            //去除密码
            emp.setPassword(null);
        }

        return R.success("查询成功", page);

    }

    //更新员工信息
    @PutMapping
    public R update(@RequestBody Employee employee) {
        log.info("更新的员工信息为{}",employee);
        if (null != employee.getId()) {
             //  //获取并设置操作人员id
             //  Long employeeId = (Long) request.getSession().getAttribute("employee");
             //  employee.setUpdateUser(employeeId);
            //设置更改时间
            //employee.setUpdateTime(LocalDateTime.now());

            if (employee.getStatus() != null && (employee.getStatus() != 0 && employee.getStatus() != 1)) {
                //员工状态给的有问题，给出提示
                return R.fail("状态错误");
            }

            //执行更新程序
            boolean updateResult = employeeService.updateById(employee);

            if (updateResult) {
                return R.success("更新成功");
            }
            return R.fail("更新失败");
        }
        return R.fail("参数错误");

    }


    //通过id查询员工信息
    @GetMapping("/{id}")
    public R findById(@PathVariable Long id) {
        log.info("根据id<{}>查询",id);

        //判断id是否为null
        if (null != id) {
            //根据id进行查询
            Employee employee = employeeService.getById(id);
            if (null != employee) {
                employee.setPassword(null);
                return R.success("查询成功", employee);
            } else {
                return R.fail("查询失败");
            }

        }
        return R.fail("参数错误");
    }
}
