package com.xy.elasticsearch.core.annotation;

import java.lang.annotation.*;

/**
 * @author zoubo
 * @version 1.0.0
 * @date 2020/2/27 12:03
 */
@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE, ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER })
public @interface Repository {
}
