package com.xy.elasticsearch.core.annotation.index;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author zoubo
 * @version 1.0.0
 * @date 2019/12/30 15:51
 */
@Target(value = {ElementType.TYPE})
@Retention(value = RetentionPolicy.RUNTIME)
public @interface Mapping {

    /**
     * mapping映射的json文件路径
     */
    String path();

}
