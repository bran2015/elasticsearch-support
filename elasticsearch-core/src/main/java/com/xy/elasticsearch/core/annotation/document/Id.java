package com.xy.elasticsearch.core.annotation.document;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * doc的文档
 * @author zoubo
 * @version 1.0.0
 * @date 2019/12/28 15:26
 */
@Target(value = {ElementType.FIELD})
@Retention(value = RetentionPolicy.RUNTIME)
public @interface Id {

    /**
     * 文档路由，匹配规则为：#xx#xx，如#id#code，此路由为字段id及code的值按顺序以'_'连接，如无此字段，则当字符串处理
     */
    String routing() default "";
}
