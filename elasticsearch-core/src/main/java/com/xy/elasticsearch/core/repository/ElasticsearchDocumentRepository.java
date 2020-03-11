package com.xy.elasticsearch.core.repository;

import lombok.NonNull;

import java.util.List;

/**
 * 对文档进行增删改，该接口的所有方法将在第三期实现类事务处理
 * @author zoubo
 * @version 1.0.0
 * @date 2020/1/9 16:07
 */
public interface ElasticsearchDocumentRepository<T> {

    /**
     * 插入
     * @param t t
     */
    void insert(@NonNull T t);

    /**
     * 批量插入
     * @param list list
     */
    void batchInsert(@NonNull List<T> list);

    /**
     * 修改
     * @param t t
     */
    void update(@NonNull T t);

    /**
     * 批量修改
     * @param list list
     */
    void batchUpdate(@NonNull List<T> list);

    /**
     * 修改，不存在则新增
     * @param t t
     */
    void upserts(@NonNull T t);


    /**
     * 批量修改,不存在则新增
     * @param list list
     */
    void batchUpserts(@NonNull List<T> list);


}
