package com.chenhao.lkd.web.controller;

import com.chenhao.lkd.pojo.vo.TaskVo;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author ChenHao
 * @version 1.0
 * @description:
 * @date 2022/6/10 15:03
 */
@RestController
@RequestMapping("/api/task-service/task")
public class TaskController {
    @RequestMapping("/taskReportInfo")
    public List<TaskVo> taskReportInfo(){

        return null;
    }
}
