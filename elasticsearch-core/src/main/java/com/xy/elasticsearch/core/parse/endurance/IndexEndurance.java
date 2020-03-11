package com.xy.elasticsearch.core.parse.endurance;

import com.xy.elasticsearch.core.annotation.FieldType;
import com.xy.elasticsearch.core.annotation.document.Join;
import com.xy.elasticsearch.core.annotation.document.JoinType;
import com.xy.elasticsearch.core.parse.IndexPattern;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 索引库解析数据
 * @author zoubo
 * @version 1.0.0
 * @date 2019/12/28 20:53
 */
@Getter
@Setter
@Accessors(chain = true)
public class IndexEndurance {

    /**
     * 索引库名称
     */
    private String name;

    /**
     * 索引库别名
     */
    private String alias;

    /**
     * 索引库type
     */
    private String type;

    /**
     * 是否创建索引库
     */
    private Boolean created;

    /**
     * 如果为true，则映射主体中应为映射类型 默认为false
     */
    private Boolean includeTypeName;

    /**
     * 每次操作处于活动状态的分片数，默认1
     */
    private Integer waitForActiveShards;

    /**
     * 请求超时时间 默认30（秒）
     */
    private Integer timeout;

    /**
     * 连接主节点超时时间 默认30（秒）
     */
    private Integer masterTimeout;

    /**
     * 分片 默认5
     */
    private Integer numberOfShards;

    /**
     * 副本 默认1
     */
    private Integer numberOfReplicas;

    /**
     * 包含setting、mapping的全部json格式的文件路径
     * 包含五个key：Tokenizers、Token Filters、Character Filters、Normalizers，json格式
     */
    private String path;

    /**
     * mapping的json文件路径
     */
    private String mappingPath;

    /**
     * setting的json文件路径
     */
    private String settingPath;

    /**
     * 索引库string类型字段，key为{@link Field} value为字段对属性
     */
    private Map<Field, FieldProperties> fieldProperties = new HashMap<>(1);

    /**
     * 索引字段映射
     */
    private Map<String, Field> fields;

    /**
     * 创建索引库的方式
     * @see IndexPattern
     */
    protected IndexPattern indexPattern;

    /**
     * 文档路由
     */
    private List<String> routing;

    /**
     * 文档id
     */
    private String id;

    /**
     * join类型
     */
    private JoinField joinField = new JoinField();


    @Data
    public static class FieldProperties {

        /**
         * 字段type
         */
        private FieldType fieldType;

        /**
         * 字段分词器
         */
        private List<String> analyze;

        /**
         * 特殊的过滤器，此部分功能也可集成在analyzer的filter、char_filter、tokenizer
         * 注意，设置此过滤器的字段，其type必须为keyword类型，否则会报映射错误
         */
        private String normalizer;
    }

    /**
     * join类型
     */
    @Data
    public static class JoinField {

        /**
         * join文档的连接type
         * @see JoinType#value()
         */
        private String joinType;

        /**
         * 父文档id
         */
        private List<String> parent;

        /**
         * join类型文档解析类
         * key为文档层级，value为type及子type
         */
        private Map<Integer, Join> joinMap = new HashMap<>();

        /**
         * 连接字段
         */
        private Field field;

    }

}
