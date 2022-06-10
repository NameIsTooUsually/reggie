package com.chenhao.lkd.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author ChenHao
 * @version 1.0
 * @description:
 * @date 2022/6/10 17:49
 */
@Data
@TableName("tb_sku_class")
public class SkuClass {
    private Integer classId;
    private String className;
    private Integer parentId;
}
