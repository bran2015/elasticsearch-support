package com.xy.elasticsearch.core.repository;

import com.xy.elasticsearch.core.factory.EntityParse;

import java.io.IOException;

/**
 * @author zoubo
 * @version 1.0.0
 * @date 2020/1/9 16:08
 */
public interface ElasticsearchIndexRepository<T> {


    /**
     * 删除索引库
     */
    void deleteIndex();

    /**
     * 索引库是否存在
     */
    Boolean indexExist();

    /**
     * 创建索引
     */
    void createIndex(Class<T> tClass);


    void pathCreateIndex(EntityParse entityParse) throws IOException;

    void settingAndMappingCreateIndex(EntityParse entityParse) throws IOException;

    void settingCreateIndex(EntityParse entityParse) throws IOException;

    void mappingCreateIndex(EntityParse entityParse) throws IOException;

    void indexResourceCreateIndex(EntityParse entityParse) throws IOException;

}
