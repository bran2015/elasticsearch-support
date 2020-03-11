package com.xy.elasticsearch.core.repository;

import org.elasticsearch.client.RestHighLevelClient;

/**
 * @author zoubo
 * @version 1.0.0
 * @date 2020/1/9 14:36
 */
public class SimpleElasticsearchRepository<T> extends AbstractElasticsearchRepository<T> implements ElasticsearchRepository<T> {


    public SimpleElasticsearchRepository(Class<T> tClass, RestHighLevelClient client) {
        super(tClass, client);
    }
}
