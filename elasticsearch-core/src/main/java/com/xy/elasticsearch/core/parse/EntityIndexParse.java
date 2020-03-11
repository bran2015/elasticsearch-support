package com.xy.elasticsearch.core.parse;

import com.xy.elasticsearch.core.annotation.FieldType;
import com.xy.elasticsearch.core.annotation.document.Field;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zoubo
 * @version 1.0.0
 * @date 2019/12/28 15:37
 */
public class EntityIndexParse extends AbstractEntityParse {

    public EntityIndexParse(Class clazz) {
        super(clazz);
    }


    @Override
    public Map<String, Object> mappingParse(){
        Map<String, Object> mapping = new HashMap<>(1);
        Map<String, Object> properties = new HashMap<>(1);
        mappingParseForKeywordOrText(properties);
        mappingParseJoin(properties);
        mapping.put("properties", properties);
        return mapping;
    }

    /**
     * 字段type为join的field的mapping解析
     */
    private void mappingParseJoin(Map<String, Object> properties){
        if (indexEndurance.getJoinField().getJoinMap().size() > 0) {
            Map<String, Object> join = new HashMap<>(1);
            join.put("type", "join");
            Map<String, Object> item = new HashMap<>(1);
            indexEndurance.getJoinField().getJoinMap().forEach((key, value) -> item.put(value.type(), value.childType()));
            join.put("relations", item);
            properties.put(indexEndurance.getJoinField().getField().getName(), join);
        }
    }


    /**
     * 字段type为keyword、text的field的mapping解析
     */
    private void mappingParseForKeywordOrText(Map<String, Object> properties){
        indexEndurance.getFieldProperties().forEach((key, value) -> {
            Map<String, Object> item = new HashMap<>(1);
            if (value.getFieldType().equals(FieldType.KEYWORD) || value.getFieldType().equals(FieldType.TEXT)){
                item.put("type", value.getFieldType().getType());
                Map<String, Object> fields = new HashMap<>(1);
                if (value.getAnalyze() != null){
                    value.getAnalyze().forEach(analyzer -> {
                        Map<String, Object> analysis = new HashMap<>(1);
                        analysis.put("type", value.getFieldType().getType());
                        analysis.put("analyzer", analyzer);
                        fields.put(analyzer, analysis);
                    });
                } else {
                    Map<String, Object> analysis = new HashMap<>(1);
                    analysis.put("type", value.getFieldType().getType());
                    fields.put(FieldType.KEYWORD.getType(), analysis);
                }
                item.put("fields", fields);
                if (StringUtils.isNotBlank(value.getNormalizer())){
                    item.put("normalizer", value.getNormalizer());
                    /**
                     * 注意，设置此过滤器的字段，其type必须为keyword类型，否则会报映射错误，所以此处的type要重新设置，
                     * 那么{@link Field#type()}设置的类型将会失效
                     */
                    item.put("type", FieldType.KEYWORD.getType());
                }
            } else {
                item.put("type", value.getFieldType().getType());
            }
            properties.put(key.getName(), item);
        });
    }


    @Override
    public org.elasticsearch.common.settings.Settings settingParse() {
        return org.elasticsearch.common.settings.Settings.builder().put("number_of_shards", indexEndurance.getNumberOfShards()).put("number_of_replicas", indexEndurance.getNumberOfReplicas()).build();
    }

}
