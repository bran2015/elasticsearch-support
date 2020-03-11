package com.xy.elasticsearch.core.parse;

/**
 * 创建索引库的模式,值越大，优先级越高
 * @author zoubo
 * @version 1.0.0
 * @date 2019/12/30 16:06
 */
public enum IndexPattern {

    /**
     * index模式，@Index指定name、type，@Field，所有属性
     */
    INDEX_SOURCE(10),

    /**
     * 单独的setting模式，@Index指定name、type属性及@Setting
     */
    SETTING_RESOURCE(20),

    /**
     * 单独的mapping模式，@Index指定name、type属性以及@Mapping
     */
    MAPPING_RESOURCE(30),


    /**
     * setting和mapping双模式，@Index指定name、type属性，@Setting、@Mapping,如需要别名，需加上@Index的alias属性
     */
    SETTING_MAPPING_RESOURCES(40),

    /**
     * index模式，@Index指定name、type、path属性
     */
    INDEX_FIELD_SET_UP(50);




    private Integer pattern;

    IndexPattern(Integer pattern) {
        this.pattern = pattern;
    }

    public Integer getPattern() {
        return pattern;
    }

    public void setPattern(Integer pattern) {
        this.pattern = pattern;
    }
}
