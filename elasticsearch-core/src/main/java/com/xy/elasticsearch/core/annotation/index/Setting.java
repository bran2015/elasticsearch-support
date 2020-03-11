package com.xy.elasticsearch.core.annotation.index;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 索引库settings设置
 * @author zoubo
 * @version 1.0.0
 * @date 2019/12/27 14:19
 */
@Target(value = {ElementType.TYPE})
@Retention(value = RetentionPolicy.RUNTIME)
public @interface Setting {

    /**
     * setting设置的json文件路径
     */
    String path();

}
