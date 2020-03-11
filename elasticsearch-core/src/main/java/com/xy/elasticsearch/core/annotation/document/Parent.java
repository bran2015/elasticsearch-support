package com.xy.elasticsearch.core.annotation.document;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 父文档id
 * @author zoubo
 * @version 1.0.0
 * @date 2020/2/6 11:14
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Parent {

    /**
     * 父文档id，匹配规则为：#xx#xx，如#id#code，此路由为字段id及code的值按顺序以'_'连接，如无此字段，则当字符串处理
     */
    String value() default "";


}
