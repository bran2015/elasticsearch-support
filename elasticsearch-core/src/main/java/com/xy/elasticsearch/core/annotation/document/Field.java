package com.xy.elasticsearch.core.annotation.document;


import com.xy.elasticsearch.core.annotation.FieldType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 索引库字段，索引库对mapping设置
 * @author zoubo
 * @version 1.0.0
 * @date 2019/12/28 14:29
 */
@Target(value = {ElementType.FIELD})
@Retention(value = RetentionPolicy.RUNTIME)
public @interface Field {

    /**
     * 字段类型
     * <note>如果该字段需要进行前缀查询，type设置为keyword，否则搜索结果将受影响</note>
     */
    FieldType type();


    /**
     * 分词器
     */
    String[] analyzers() default "";

    /**
     * 特殊的过滤器，此部分功能也可集成在analyzer的filter、char_filter、tokenizer
     * 注意，设置此过滤器的字段，其type必须为keyword类型，否则会报映射错误
     */
    String normalizer() default "";

    /**
     * join类型的type
     */
    Join[] join() default {};


}
