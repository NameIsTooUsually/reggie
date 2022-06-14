package com.chenhao.lkd.pojo.dto;

import com.chenhao.lkd.pojo.Business;
import com.chenhao.lkd.pojo.Node;
import com.chenhao.lkd.pojo.Region;
import lombok.Data;

/**
 * @author ChenHao
 * @version 1.0
 * @description:
 * @date 2022/6/13 21:15
 */
@Data
public class NodeDto extends Node {

    private Region region;
    private Business businessType;
}
