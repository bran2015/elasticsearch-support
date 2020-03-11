package com.xy.elasticsearch.core.wrapper;

import org.elasticsearch.index.query.BoolQueryBuilder;

/**
 * @author zoubo
 * @version 1.0.0
 * @date 2020/1/23 12:10
 */
@SuppressWarnings("all")
public abstract class Wrapper<T> {


    /**
     * 实体对象（子类实现）
     *
     * @return 泛型 T
     */
    public abstract T getEntity();


    public abstract BoolQueryBuilder getBoolQueryBuilder();




}




