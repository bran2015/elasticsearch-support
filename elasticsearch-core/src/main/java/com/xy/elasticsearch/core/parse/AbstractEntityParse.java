package com.xy.elasticsearch.core.parse;

import com.xy.elasticsearch.core.annotation.FieldType;
import com.xy.elasticsearch.core.annotation.document.Id;
import com.xy.elasticsearch.core.annotation.document.Join;
import com.xy.elasticsearch.core.annotation.document.JoinType;
import com.xy.elasticsearch.core.annotation.document.Parent;
import com.xy.elasticsearch.core.annotation.index.Index;
import com.xy.elasticsearch.core.annotation.index.Mapping;
import com.xy.elasticsearch.core.annotation.index.Setting;
import com.xy.elasticsearch.core.factory.AbstractIndexFactory;
import com.xy.elasticsearch.core.factory.EntityParse;
import com.xy.elasticsearch.core.parse.endurance.IndexEndurance;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.util.*;

/**
 * 索引库实体解析
 * @author zoubo
 * @version 1.0.0
 * @date 2019/12/30 10:59
 */
abstract class AbstractEntityParse extends AbstractIndexFactory implements EntityParse {


    private Class clazz;

    IndexEndurance indexEndurance = new IndexEndurance();


    AbstractEntityParse(Class clazz) {
        this.clazz = clazz;
        indexAnnotationParse();
        settingAnnotationParse();
        mappingAnnotationParse();
        documentAnnotationParse();
        if (StringUtils.isNotBlank(indexEndurance.getPath())) {
            indexEndurance.setIndexPattern(IndexPattern.INDEX_FIELD_SET_UP);
        } else if (StringUtils.isNotBlank(indexEndurance.getSettingPath()) && StringUtils.isNotBlank(indexEndurance.getMappingPath())) {
            indexEndurance.setIndexPattern(IndexPattern.SETTING_MAPPING_RESOURCES);
        } else if (StringUtils.isNotBlank(indexEndurance.getMappingPath())) {
            indexEndurance.setIndexPattern(IndexPattern.MAPPING_RESOURCE);
        } else if (StringUtils.isNotBlank(indexEndurance.getSettingPath())) {
            indexEndurance.setIndexPattern(IndexPattern.SETTING_RESOURCE);
        } else {
            indexEndurance.setIndexPattern(IndexPattern.INDEX_SOURCE);
        }
        addIndexEndurance(clazz, indexEndurance);
    }

    private void indexAnnotationParse(){
        Index indexAnnotation = (Index) clazz.getAnnotation(Index.class);
        indexEndurance.setName(indexAnnotation.name())
                .setType(indexAnnotation.type())
                .setAlias(indexAnnotation.alias())
                .setCreated(indexAnnotation.created())
                .setIncludeTypeName(indexAnnotation.includeTypeName())
                .setWaitForActiveShards(indexAnnotation.waitForActiveShards())
                .setTimeout(indexAnnotation.timeout())
                .setMasterTimeout(indexAnnotation.masterTimeout())
                .setNumberOfReplicas(indexAnnotation.numberOfReplicas())
                .setNumberOfShards(indexAnnotation.numberOfShards())
                .setPath(indexAnnotation.path());
    }

    private void settingAnnotationParse() {
        Setting annotation = (Setting) clazz.getAnnotation(Setting.class);
        if (null != annotation) {
            indexEndurance.setSettingPath(annotation.path());
        }
    }


    private void mappingAnnotationParse(){
        Mapping annotation = (Mapping) clazz.getAnnotation(Mapping.class);
        if (null != annotation) {
            indexEndurance.setMappingPath(annotation.path());
        }
    }


    private void documentAnnotationParse(){
        //TODO 此处待优化
        Map<String, Field> fieldMap = new HashMap<>(1);
        Field[] declaredFields = clazz.getDeclaredFields();
        for (Field field : declaredFields){
            Class<?> type = field.getType();
            com.xy.elasticsearch.core.annotation.document.Field fieldAnnotation = field.getAnnotation(com.xy.elasticsearch.core.annotation.document.Field.class);
            IndexEndurance.FieldProperties properties = new IndexEndurance.FieldProperties();
            //解析type为keyword、text字段
            if (type == String.class){
                if (null != fieldAnnotation && StringUtils.isNotBlank(fieldAnnotation.analyzers()[0])){
                    properties.setFieldType(fieldAnnotation.type());
                    properties.setAnalyze(Arrays.asList(fieldAnnotation.analyzers()));
                    properties.setNormalizer(fieldAnnotation.normalizer());
                } else {
                    properties.setFieldType(FieldType.KEYWORD);
                }
                indexEndurance.getFieldProperties().put(field, properties);
            } else {
                //解析type为非keyword、text字段
                if (fieldAnnotation != null) {
                    properties.setFieldType(fieldAnnotation.type());
                    indexEndurance.getFieldProperties().put(field, properties);
                    //解析type为join的字段
                    if (fieldAnnotation.type().equals(FieldType.JOIN) && fieldAnnotation.join().length > 1) {
                        SortedMap<Integer, Join> joinMap = new TreeMap<>();
                        Join[] joinArr = fieldAnnotation.join();
                        for (Join join : joinArr) {
                            joinMap.put(join.level(), join);
                        }
                        indexEndurance.getJoinField().getJoinMap().putAll(joinMap);
                        indexEndurance.getJoinField().setField(field);
                    }
                }
            }
            Id id = field.getAnnotation(Id.class);
            if (null != id){
                indexEndurance.setId(field.getName());
                String routingStr = id.routing();
                if (StringUtils.isNotBlank(routingStr)){
                    String[] routingArr = routingStr.split("#");
                    indexEndurance.setRouting(Arrays.asList(routingArr));
                }
            }
            JoinType joinType = field.getAnnotation(JoinType.class);
            if (null != joinType){
                indexEndurance.getJoinField().setJoinType(joinType.value());
            }
            Parent parent = field.getAnnotation(Parent.class);
            if (null != parent){
                String parentStr = parent.value();
                if (StringUtils.isNotBlank(parentStr)){
                    String[] parentArr = parentStr.split("#");
                    indexEndurance.getJoinField().setParent(Arrays.asList(parentArr));
                }
            }
            //存储文档的每个字段
            fieldMap.put(field.getName(), field);
        }
        indexEndurance.setFields(fieldMap);
    }
}
