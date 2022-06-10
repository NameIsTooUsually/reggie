package com.chenhao.lkd.pojo.vo;

import lombok.Data;

import java.sql.Date;

/**
 * @author ChenHao
 * @version 1.0
 * @description:
 * @date 2022/6/10 15:07
 */
@Data
public class TaskVo {
    private Integer total;
    private Integer completedTotal;
    private Integer cancelTotal;
    private Integer progressTotal;
    private Integer workerCount;
    private boolean repair;
    private Date date;

}
