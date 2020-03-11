package com.xy.elasticsearch.core.annotation.document;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * join文档映射器，此注解使用在父文档的field上
 * @author zoubo
 * @version 1.0.0
 * @date 2020/2/6 15:55
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Join {

    String type() default "";

    String[] childType() default {};

    int level();

}
