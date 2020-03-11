package com.xy.elasticsearch.core.index;

import com.xy.elasticsearch.core.exception.ElasticsearchException;
import com.xy.elasticsearch.core.model.Demo;
import com.xy.elasticsearch.core.parse.EntityIndexParse;
import com.xy.elasticsearch.core.parse.IndexPattern;
import com.xy.elasticsearch.core.parse.endurance.IndexEndurance;
import com.xy.elasticsearch.core.utils.ValidateUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.elasticsearch.action.admin.indices.alias.Alias;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.support.ActiveShardCount;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

/**
 * @author zoubo
 * @version 1.0.0
 * @date 2020/1/14 11:52
 */
class CreateIndexTest {

    private IndexEndurance indexEndurance;

    private EntityIndexParse parse;

    private RestHighLevelClient client;

    @BeforeEach
    void before(){
        parse = new EntityIndexParse(Demo.class);
        indexEndurance = parse.getIndexEndurance(Demo.class);
        RestClientBuilder builder = RestClient.builder(new HttpHost("nexus.mc-health.cn", 9200, "http"));
        builder.setHttpClientConfigCallback(httpAsyncClientBuilder -> {
            RequestConfig.Builder custom = RequestConfig.custom()
                    //超时时间5分钟
                    .setConnectTimeout(5 * 60 * 1000)
                    //Socket超时时长
                    .setSocketTimeout(5 * 60 * 1000).setConnectionRequestTimeout(5 * 60 * 1000);
            httpAsyncClientBuilder.setDefaultRequestConfig(custom.build());
            return httpAsyncClientBuilder;
        });
        client = new RestHighLevelClient(builder);
    }


    /**
     * @see IndexPattern#INDEX_FIELD_SET_UP 该模式下创建索引的方式
     */
    @Test
    void pathCreateIndex() throws IOException {
        CreateIndexRequest request = new CreateIndexRequest(indexEndurance.getName());
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(indexEndurance.getPath());
        ValidateUtils.isNull(inputStream);
        String indexScheme = new BufferedReader(new InputStreamReader(inputStream))
                .lines().collect(Collectors.joining(System.lineSeparator()));
        request.source(indexScheme, XContentType.JSON);
        request.timeout(TimeValue.timeValueMinutes(indexEndurance.getTimeout()));
        request.masterNodeTimeout(TimeValue.timeValueSeconds(indexEndurance.getMasterTimeout()));
        request.waitForActiveShards(ActiveShardCount.ONE);
        client.indices().create(request, RequestOptions.DEFAULT);
    }




    /**
     * @see IndexPattern#SETTING_MAPPING_RESOURCES 该模式下创建索引的方式
     */
    @Test
    void settingAndMappingCreateIndex() throws IOException {
        CreateIndexRequest request = new CreateIndexRequest(indexEndurance.getName());
        InputStream settingInputStream = this.getClass().getClassLoader().getResourceAsStream(indexEndurance.getSettingPath());
        if (null == settingInputStream){
            throw new ElasticsearchException("");
        }
        InputStream mappingInputStream = this.getClass().getClassLoader().getResourceAsStream(indexEndurance.getMappingPath());
        if (null == mappingInputStream){
            throw new ElasticsearchException("");
        }
        String mapping = new BufferedReader(new InputStreamReader(mappingInputStream))
                .lines().collect(Collectors.joining(System.lineSeparator()));
        request.mapping(indexEndurance.getType(), mapping, XContentType.JSON);
        String setting = new BufferedReader(new InputStreamReader(settingInputStream))
                .lines().collect(Collectors.joining(System.lineSeparator()));
        request.settings(setting, XContentType.JSON);
        request.timeout(TimeValue.timeValueMinutes(indexEndurance.getTimeout()));
        request.masterNodeTimeout(TimeValue.timeValueSeconds(indexEndurance.getMasterTimeout()));
        request.waitForActiveShards(ActiveShardCount.ONE);
        client.indices().create(request, RequestOptions.DEFAULT);
    }


    /**
     * @see IndexPattern#SETTING_RESOURCE 该模式下创建索引的方式
     */
    @Test
    void settingCreateIndex() throws IOException {
        CreateIndexRequest request = new CreateIndexRequest(indexEndurance.getName());
        InputStream settingInputStream = this.getClass().getClassLoader().getResourceAsStream(indexEndurance.getSettingPath());
        if (null == settingInputStream){
            throw new ElasticsearchException("");
        }
        request.settings(new BufferedReader(new InputStreamReader(settingInputStream))
                .lines().collect(Collectors.joining(System.lineSeparator())), XContentType.JSON);
        request.mapping(indexEndurance.getType(), parse.mappingParse());
        request.timeout(TimeValue.timeValueMinutes(indexEndurance.getTimeout()));
        request.masterNodeTimeout(TimeValue.timeValueSeconds(indexEndurance.getMasterTimeout()));
        request.waitForActiveShards(ActiveShardCount.ONE);
        client.indices().create(request, RequestOptions.DEFAULT);
    }


    /**
     * @see IndexPattern#MAPPING_RESOURCE 该模式下创建索引的方式
     */
    @Test
    void mappingCreateIndex() throws IOException {
        CreateIndexRequest request = new CreateIndexRequest(indexEndurance.getName());
        InputStream mappingInputStream = this.getClass().getClassLoader().getResourceAsStream(indexEndurance.getMappingPath());
        if (null == mappingInputStream){
            throw new ElasticsearchException("");
        }
        request.mapping(indexEndurance.getType(),  new BufferedReader(new InputStreamReader(mappingInputStream))
                .lines().collect(Collectors.joining(System.lineSeparator())), XContentType.JSON);
        request.timeout(TimeValue.timeValueMinutes(indexEndurance.getTimeout()));
        request.masterNodeTimeout(TimeValue.timeValueSeconds(indexEndurance.getMasterTimeout()));
        request.waitForActiveShards(ActiveShardCount.ONE);
        client.indices().create(request, RequestOptions.DEFAULT);
    }

    /**
     * @see IndexPattern#INDEX_SOURCE 该模式下创建索引的方式
     */
    @Test
    void indexResourceCreateIndex() throws IOException {
        CreateIndexRequest request = new CreateIndexRequest(indexEndurance.getName());
        request.timeout(TimeValue.timeValueMinutes(indexEndurance.getTimeout()));
        request.masterNodeTimeout(TimeValue.timeValueSeconds(indexEndurance.getMasterTimeout()));
        request.waitForActiveShards(ActiveShardCount.ONE);
        request.settings(parse.settingParse());
        request.mapping(indexEndurance.getType(), parse.mappingParse());
        if (StringUtils.isNotBlank(indexEndurance.getAlias())) {
            request.alias(new Alias(indexEndurance.getAlias()));
        }
        client.indices().create(request, RequestOptions.DEFAULT);
    }



}
