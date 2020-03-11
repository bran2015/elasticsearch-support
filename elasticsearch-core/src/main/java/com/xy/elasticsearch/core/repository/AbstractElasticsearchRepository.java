package com.xy.elasticsearch.core.repository;

import com.alibaba.fastjson.JSONObject;
import com.xy.elasticsearch.core.exception.ElasticsearchException;
import com.xy.elasticsearch.core.factory.AbstractIndexFactory;
import com.xy.elasticsearch.core.factory.EntityParse;
import com.xy.elasticsearch.core.parse.EntityIndexParse;
import com.xy.elasticsearch.core.parse.IndexPattern;
import com.xy.elasticsearch.core.parse.endurance.IndexEndurance;
import com.xy.elasticsearch.core.utils.MapUtils;
import com.xy.elasticsearch.core.utils.ValidateUtils;
import com.xy.elasticsearch.core.wrapper.Wrapper;
import lombok.NonNull;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.admin.indices.alias.Alias;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.admin.indices.get.GetIndexRequest;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.ActiveShardCount;
import org.elasticsearch.action.support.WriteRequest;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author zoubo
 * @version 1.0.0
 * @date 2020/1/9 16:15
 */
public class AbstractElasticsearchRepository<T> extends AbstractIndexFactory implements ElasticsearchRepository<T>{

    private final RestHighLevelClient client;

    private final Class<T> entityClass;

    /**
     * parentId
     */
    private static String VALUE_PARENT = "parent";

    /**
     * 路由值
     */
    private static String VALUE_ROUTING = "routing";

    AbstractElasticsearchRepository(Class<T> entityClass, RestHighLevelClient client){
        this.client = client;
        this.entityClass = entityClass;
        createIndex(entityClass);
    }

    @Override
    public void insert(T t) {
        try {
            IndexRequest indexRequest = add(t);
            client.index(indexRequest, RequestOptions.DEFAULT);
        }catch (IOException ex){
            throw new ElasticsearchException("", "", ex);
        }
    }

    @Override
    public void batchInsert(List<T> list) {
        ValidateUtils.isEmpty(list);
        try {
            BulkRequest bulkRequest = new BulkRequest();
            for (T t : list) {
                IndexRequest indexRequest = add(t);
                bulkRequest.add(indexRequest);
            }
            bulkRequest.setRefreshPolicy(WriteRequest.RefreshPolicy.IMMEDIATE);
            client.bulk(bulkRequest, RequestOptions.DEFAULT);
        }catch (Exception e){
            throw new ElasticsearchException("", "");
        }
    }

    @Override
    public void update(T t) {
        try {
            //TODO join类型、location类型等待处理
            Map<String, Object> map = MapUtils.transformObject(t, true);
            if (org.apache.commons.collections4.MapUtils.isEmpty(map)){
                throw new ElasticsearchException("[Elasticsearch support]: The {} is a null object", entityClass.getProtectionDomain().getCodeSource().getLocation().getPath());
            }
            IndexEndurance indexEndurance = getIndexEndurance(entityClass);
            String idFromField = indexEndurance.getId();
            Field idField = entityClass.getDeclaredField(idFromField);
            idField.setAccessible(true);
            Object id = idField.get(entityClass);
            UpdateRequest updateRequest = new UpdateRequest(indexEndurance.getName(), indexEndurance.getType(), id.toString());
            updateRequest.doc(map);
            updateRequest.routing(getRouting(t, indexEndurance));
            updateRequest.setRefreshPolicy(WriteRequest.RefreshPolicy.IMMEDIATE);
            client.update(updateRequest, RequestOptions.DEFAULT);
        }catch (Exception e){
            throw new ElasticsearchException("", "");
        }
    }

    @Override
    public void batchUpdate(List<T> list) {
        //TODO join类型、location类型等待处理
        ValidateUtils.isEmpty(list);
        try {
            Class<?> clazz = list.get(0).getClass();
            IndexEndurance indexEndurance = getIndexEndurance(clazz);
            String idFromField = indexEndurance.getId();
            Field idField = clazz.getDeclaredField(idFromField);
            idField.setAccessible(true);
            Object id = idField.get(clazz);
            BulkRequest bulkRequest = new BulkRequest();
            for (T t : list) {
                JSONObject jsonObject = JSONObject.parseObject(JSONObject.toJSONString(t));
                UpdateRequest updateRequest = new UpdateRequest(indexEndurance.getName(), indexEndurance.getType(), id.toString());
                updateRequest.routing(getRouting(t, indexEndurance));
                updateRequest.doc(jsonObject);
                bulkRequest.add(updateRequest);
                bulkRequest.setRefreshPolicy(WriteRequest.RefreshPolicy.IMMEDIATE);
                client.bulk(bulkRequest, RequestOptions.DEFAULT);
            }
        }catch (Exception ex){
            throw new ElasticsearchException("", "");
        }

    }

    @Override
    public void upserts(@NonNull T t) {

    }

    @Override
    public void batchUpserts(@NonNull List<T> list) {

    }

    private IndexRequest add(T t) {
        try {
            IndexEndurance indexEndurance = getIndexEndurance(entityClass);
            String idFromField = indexEndurance.getId();
            Field idField = entityClass.getDeclaredField(idFromField);
            idField.setAccessible(true);
            Object id = idField.get(t);
            IndexRequest indexRequest = new IndexRequest(indexEndurance.getName(), indexEndurance.getType(), id.toString());
            JSONObject jsonObject = JSONObject.parseObject(JSONObject.toJSONString(t));
            //子文档
            if (StringUtils.isNotBlank(indexEndurance.getJoinField().getJoinType()) && CollectionUtils.isNotEmpty(indexEndurance.getJoinField().getParent())){
                Map<String, Object> join = new HashMap<>(1);
                join.put("parent", getParent(t, indexEndurance));
                join.put("name", indexEndurance.getJoinField().getJoinType());
                jsonObject.put(indexEndurance.getJoinField().getField().getName(), join);
            }
            //父文档
            if (StringUtils.isNotBlank(indexEndurance.getJoinField().getJoinType()) && CollectionUtils.isEmpty(indexEndurance.getJoinField().getParent())){
                Map<String, Object> join = new HashMap<>(1);
                join.put("name", indexEndurance.getJoinField().getJoinType());
                jsonObject.put(indexEndurance.getJoinField().getField().getName(), join);
            }
            indexRequest.source(jsonObject.toJSONString(), XContentType.JSON);
            indexRequest.routing(getRouting(t, indexEndurance));
            indexRequest.timeout(TimeValue.timeValueSeconds(60));
            indexRequest.setRefreshPolicy(WriteRequest.RefreshPolicy.IMMEDIATE);
            return indexRequest;
        }catch (NoSuchFieldException | IllegalAccessException ex){
            throw new ElasticsearchException("");
        }
    }

    /**
     * 获取文档路由
     */
    private String getRouting(T t, IndexEndurance indexEndurance) throws IllegalAccessException {
       return expression(t, indexEndurance, VALUE_ROUTING);
    }

    /**
     * 获取父文档id
     */
    private String getParent(T t, IndexEndurance indexEndurance) throws IllegalAccessException {
        return expression(t, indexEndurance, VALUE_PARENT);
    }


    private String expression(T t, IndexEndurance indexEndurance, String mappingValue) throws IllegalAccessException {
        List<String> valueList = null;
        if (mappingValue.equals(VALUE_PARENT)){
            valueList = indexEndurance.getJoinField().getParent();
        } else if (mappingValue.equals(VALUE_ROUTING)){
            valueList = indexEndurance.getRouting();
        }
        if (CollectionUtils.isEmpty(valueList)){
            return null;
        }
        StringBuilder val = new StringBuilder();
        for (String value : valueList){
            Field valueField = indexEndurance.getFields().get(value);
            //如果为null，当字符串处理
            if (null == valueField){
                val.append(value).append("_");
            } else {
                valueField.setAccessible(true);
                Object valueFragment = valueField.get(t);
                val.append(null != valueFragment ? valueFragment : value).append("_");
            }
        }
        return val.deleteCharAt(val.length()-1).toString();
    }


    @Override
    public void deleteIndex() {
        IndexEndurance indexEndurance = getIndexEndurance(entityClass);
        String indexName = indexEndurance.getName();
        ValidateUtils.isBlank(indexName);
        try {
            DeleteIndexRequest request = new DeleteIndexRequest();
            request.indices(indexName);
            client.indices().delete(request, RequestOptions.DEFAULT);
        }catch (IOException ex){
            throw new ElasticsearchException("", "");
        }
    }

    @Override
    public Boolean indexExist() {
        try {
            IndexEndurance indexEndurance = getIndexEndurance(entityClass);
            GetIndexRequest request = new GetIndexRequest();
            request.indices(indexEndurance.getName());
            request.masterNodeTimeout(TimeValue.timeValueSeconds(indexEndurance.getMasterTimeout()));
            return client.indices().exists(request, RequestOptions.DEFAULT);
        }catch (IOException ex){
            throw new org.elasticsearch.ElasticsearchException(ex);
        }
    }

    @Override
    public void createIndex(Class<T> tClass) {
        EntityIndexParse entityParse = new EntityIndexParse(entityClass);
        IndexEndurance indexEndurance = entityParse.getIndexEndurance(entityClass);
        if (!indexExist() && indexEndurance != null && indexEndurance.getCreated()) {
            switch (indexEndurance.getIndexPattern()) {
                case INDEX_SOURCE:
                    indexResourceCreateIndex(entityParse);
                    break;
                case INDEX_FIELD_SET_UP:
                    pathCreateIndex(entityParse);
                    break;
                case SETTING_MAPPING_RESOURCES:
                    settingAndMappingCreateIndex(entityParse);
                    break;
                case SETTING_RESOURCE:
                    settingCreateIndex(entityParse);
                    break;
                case MAPPING_RESOURCE:
                    mappingCreateIndex(entityParse);
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public T selectById(String id) {
        SearchResponse response;
        try {
            IndexEndurance indexEndurance = getIndexEndurance(entityClass);
            SearchRequest request = new SearchRequest(indexEndurance.getName());
            SearchSourceBuilder builder = new SearchSourceBuilder();
            builder.query(QueryBuilders.idsQuery(indexEndurance.getType()).addIds(id));
            request.source(builder);
            response = client.search(request, RequestOptions.DEFAULT);
        }catch (IOException ex){
            throw new ElasticsearchException("", "", ex);
        }
        return response.getHits().getTotalHits().value > 0 ? JSONObject.parseObject(response.getHits().getHits()[0].getSourceAsString(), entityClass) : null;
    }

    @Override
    public List<T> selectByIds(Collection<String> ids) {
        SearchResponse response;
        try {
            IndexEndurance indexEndurance = getIndexEndurance(entityClass);
            SearchRequest request = new SearchRequest(indexEndurance.getName());
            SearchSourceBuilder builder = new SearchSourceBuilder();
            builder.query(QueryBuilders.idsQuery(indexEndurance.getType()).addIds(ids.toArray(new String[]{})));
            request.source(builder);
            response = client.search(request, RequestOptions.DEFAULT);
        }catch (IOException ex){
            throw new ElasticsearchException("", "", ex);
        }
        //TODO 待改善，不用foreach方法
        List<T> result = new ArrayList<>();
        response.getHits().forEach(item -> result.add(JSONObject.parseObject(item.getSourceAsString(), entityClass)));
        return result;
    }

    @Override
    public List<T> selectList(Wrapper<T> queryWrapper) {
        SearchResponse response;
        try {
            IndexEndurance indexEndurance = getIndexEndurance(entityClass);
            BoolQueryBuilder boolQueryBuilder = queryWrapper.getBoolQueryBuilder();
            SearchRequest request = new SearchRequest(indexEndurance.getName(), indexEndurance.getType());
            SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
            sourceBuilder.query(boolQueryBuilder);
            request.source(sourceBuilder);
            response = client.search(request, RequestOptions.DEFAULT);
        }catch (IOException ex){
            throw new ElasticsearchException("", "", ex);
        }
        return Arrays.stream(response.getHits().getHits()).map(item -> JSONObject.parseObject(item.getSourceAsString(), entityClass)).collect(Collectors.toList());
    }


    /**
     * @see IndexPattern#INDEX_FIELD_SET_UP 该模式下创建索引的方式
     */
    @Override
    public void pathCreateIndex(EntityParse entityParse) {
        try {
        IndexEndurance indexEndurance = entityParse.getIndexEndurance(entityClass);
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

        }catch (IOException ex){
            throw new ElasticsearchException("");
        }
    }


    /**
     * @see IndexPattern#SETTING_MAPPING_RESOURCES 该模式下创建索引的方式
     */
    @Override
    public void settingAndMappingCreateIndex(EntityParse entityParse) {
        try {
        IndexEndurance indexEndurance = entityParse.getIndexEndurance(entityClass);
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

        }catch (IOException ex){
            throw new ElasticsearchException("");
        }
    }


    /**
     * @see IndexPattern#SETTING_RESOURCE 该模式下创建索引的方式
     */
    @Override
    public void settingCreateIndex(EntityParse entityParse) {
        try {
        IndexEndurance indexEndurance = entityParse.getIndexEndurance(entityClass);
        CreateIndexRequest request = new CreateIndexRequest(indexEndurance.getName());
        InputStream settingInputStream = this.getClass().getClassLoader().getResourceAsStream(indexEndurance.getSettingPath());
        if (null == settingInputStream){
            throw new ElasticsearchException("");
        }
        request.settings(new BufferedReader(new InputStreamReader(settingInputStream))
                .lines().collect(Collectors.joining(System.lineSeparator())), XContentType.JSON);
        request.mapping(indexEndurance.getType(), entityParse.mappingParse());
        request.timeout(TimeValue.timeValueMinutes(indexEndurance.getTimeout()));
        request.masterNodeTimeout(TimeValue.timeValueSeconds(indexEndurance.getMasterTimeout()));
        request.waitForActiveShards(ActiveShardCount.ONE);
        client.indices().create(request, RequestOptions.DEFAULT);

        }catch (IOException ex){
            throw new ElasticsearchException("");
        }
    }


    /**
     * @see IndexPattern#MAPPING_RESOURCE 该模式下创建索引的方式
     */
    @Override
    public void mappingCreateIndex(EntityParse entityParse) {
        try {
            IndexEndurance indexEndurance = entityParse.getIndexEndurance(entityClass);
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
        }catch (IOException ex){
            throw new ElasticsearchException("");
        }
    }

    /**
     * @see IndexPattern#INDEX_SOURCE 该模式下创建索引的方式
     */
    @Override
    public void indexResourceCreateIndex(EntityParse entityParse) {
        try {
            IndexEndurance indexEndurance = entityParse.getIndexEndurance(entityClass);
            CreateIndexRequest request = new CreateIndexRequest(indexEndurance.getName());
            request.timeout(TimeValue.timeValueMinutes(indexEndurance.getTimeout()));
            request.masterNodeTimeout(TimeValue.timeValueSeconds(indexEndurance.getMasterTimeout()));
            request.waitForActiveShards(ActiveShardCount.ONE);
            request.settings(entityParse.settingParse());
            request.mapping(indexEndurance.getType(), entityParse.mappingParse());
            if (StringUtils.isNotBlank(indexEndurance.getAlias())) {
                request.alias(new Alias(indexEndurance.getAlias()));
            }
            client.indices().create(request, RequestOptions.DEFAULT);
        } catch (IOException ex){
            throw new ElasticsearchException("");
        }
    }
}
