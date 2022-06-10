package com.chenhao.lkd.pojo.vo;

import lombok.Data;

import java.util.List;

/**
 * @author ChenHao
 * @version 1.0
 * @description:
 * @date 2022/6/10 15:23
 */
@Data
public class SkuPageVo<T> {
    private Integer pageIndex;
    private Integer pageSize;
    private Integer totalPage;
    private Integer totalCount;
    private List<T> currentPageRecords;
}
