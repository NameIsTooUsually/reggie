package com.chenhao.lkd.pojo.dto;

import com.chenhao.lkd.pojo.Node;
import com.chenhao.lkd.pojo.Region;
import com.chenhao.lkd.pojo.Vm;
import com.chenhao.lkd.pojo.VmType;
import lombok.Data;

/**
 * @author ChenHao
 * @version 1.0
 * @description:
 * @date 2022/6/16 19:40
 */
@Data
public class VmDto extends Vm {
    //private Business businessType;
    private Region region;
    private VmType type;
    private Node node;

}
