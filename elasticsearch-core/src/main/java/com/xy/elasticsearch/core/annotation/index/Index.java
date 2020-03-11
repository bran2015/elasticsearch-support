package com.xy.elasticsearch.core.annotation.index;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 索引库，当name为null时，则会去检查analysisPath是否为null
 * @author zoubo
 * @version 1.0.0
 * @date 2019/12/27 14:04
 */
@Target(value = {ElementType.TYPE})
@Retention(value = RetentionPolicy.RUNTIME)
public @interface Index {

    /**
     * 索引库名称
     */
    String name();

    /**
     * 索引库类型
     */
    String type();

    /**
     * 是否创建索引库， 默认否
     */
    boolean created() default false;

    /**
     * 索引别名
     */
    String alias()  default "";

    /**
     * 如果为true，则映射主体中应为映射类型 默认为false
     */
    boolean includeTypeName() default false;

    /**
     * 每次操作处于活动状态的分片数，默认1
     */
    int waitForActiveShards() default 1;

    /**
     * 请求超时时间 默认30（秒）
     */
    int timeout() default 30;

    /**
     * 连接主节点超时时间 默认30（秒）
     */
    int masterTimeout() default 30;

    /**
     * 分片 默认5
     */
    int numberOfShards() default 5;

    /**
     * 副本 默认1
     */
    int numberOfReplicas() default 1;

    /**
     * 包含setting、mapping的全部json格式的文件路径
     * 包含五个key：Tokenizers、Token Filters、Character Filters、Normalizers，json格式
     */
    String path() default "";




}
