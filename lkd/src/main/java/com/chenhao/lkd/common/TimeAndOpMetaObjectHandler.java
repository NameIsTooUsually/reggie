package com.chenhao.lkd.common;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Slf4j
@Component
public class TimeAndOpMetaObjectHandler implements MetaObjectHandler {


    @Override
    public void insertFill(MetaObject metaObject) {
        log.info("公共字段自动填充 [ insert ]...");
        metaObject.setValue("createTime", LocalDateTime.now());
        metaObject.setValue("updateTime", LocalDateTime.now());

        /*Long employee = BaseContextUtil.getCurrentId();
        metaObject.setValue("createUser",employee);
        metaObject.setValue("updateUser",employee);*/

    }

    @Override
    public void updateFill(MetaObject metaObject) {
        log.info("公共字段自动填充 [ update ]...");

        metaObject.setValue("updateTime", LocalDateTime.now());
      /*  Long employee = BaseContextUtil.getCurrentId();
        metaObject.setValue("updateUser",employee);*/
    }
}