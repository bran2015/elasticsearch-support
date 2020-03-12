package com.xy.elasticsearch.core.repository;

import com.xy.elasticsearch.core.factory.EntityParse;
import com.xy.elasticsearch.core.parse.IndexPattern;

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

    /**
     * 创建索引库
     * @see IndexPattern#INDEX_FIELD_SET_UP 该模式下创建索引的方式
     * @param entityParse entityParse
     */
    void pathCreateIndex(EntityParse entityParse) ;

    /**
     * 创建索引库
     * @see IndexPattern#SETTING_MAPPING_RESOURCES 该模式下创建索引的方式
     * @param entityParse entityParse
     */
    void settingAndMappingCreateIndex(EntityParse entityParse);

    /**
     * 创建索引库
     * @see IndexPattern#SETTING_RESOURCE 该模式下创建索引的方式
     * @param entityParse entityParse
     */
    void settingCreateIndex(EntityParse entityParse);

    /**
     * 创建索引库
     * @see IndexPattern#MAPPING_RESOURCE 该模式下创建索引的方式
     * @param entityParse entityParse
     */
    void mappingCreateIndex(EntityParse entityParse);

    /**
     * 创建索引库
     * @see IndexPattern#INDEX_SOURCE 该模式下创建索引的方式
     * @param entityParse entityParse
     */
    void indexResourceCreateIndex(EntityParse entityParse);

}
