package com.chenhao.lkd.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author ChenHao
 * @version 1.0
 * @description:
 * @date 2022/6/16 20:02
 */
@Data
@TableName("tb_vm_type")
public class VmType {
    private Integer typeId;
    private Integer vmRow;
    private Integer vmCol;
    private String name;
    private Integer channelMaxCapacity;
    private String model;
    private String image;

}
