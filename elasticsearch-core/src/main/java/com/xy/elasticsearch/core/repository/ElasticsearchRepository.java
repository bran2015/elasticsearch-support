package com.xy.elasticsearch.core.repository;

/**
 * @author zoubo
 * @version 1.0.0
 * @date 2020/1/9 14:30
 */
public interface ElasticsearchRepository<T> extends ElasticsearchDocumentRepository<T>, ElasticsearchIndexRepository<T>, ElasticsearchQueryRepository<T> {



}
