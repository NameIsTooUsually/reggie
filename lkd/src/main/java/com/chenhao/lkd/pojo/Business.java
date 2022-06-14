package com.chenhao.lkd.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author ChenHao
 * @version 1.0
 * @description:
 * @date 2022/6/12 9:38
 */
@Data
@TableName("tb_business")
public class Business {
    	private Integer id;
	    private String name;
}
