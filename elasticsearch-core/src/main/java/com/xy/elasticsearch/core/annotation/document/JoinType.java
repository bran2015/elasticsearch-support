package com.xy.elasticsearch.core.annotation.document;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * join类文档的type
 * @author zoubo
 * @version 1.0.0
 * @date 2020/2/6 16:54
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface JoinType {

    String value() default "";
}
