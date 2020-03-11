package com.xy.elasticsearch.spring.annotation;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author zoubo
 * @version 1.0.0
 * @date 2020/2/27 09:33
 */

@Target(value = {ElementType.TYPE})
@Retention(value = RetentionPolicy.RUNTIME)
@Import(ElasticsearchScannerRegistrar.class)
@Documented
public @interface ElasticsearchScan {

    String[] value() default {};

}
