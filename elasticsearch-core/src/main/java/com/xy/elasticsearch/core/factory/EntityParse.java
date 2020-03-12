package com.xy.elasticsearch.core.factory;

import com.xy.elasticsearch.core.parse.endurance.IndexEndurance;
import org.elasticsearch.common.settings.Settings;

import java.util.Map;

/**
 * @author zoubo
 * @version 1.0.0
 * @date 2020/1/14 12:08
 */
public interface EntityParse {


    /**
     * 此处为一个扩展点，可以根据自己的喜好设置mapping并最终解析为map类型
     * mapping解析
     * @return Map<String, Object>
     */
    Map<String, Object> mappingParse();


    /**
     * 此处为setting扩展解析点，可以根据自己点喜好设置setting
     * setting解析
     * @return CreateIndexRequest
     */
    Settings settingParse();


    /**
     * 获取解析类的信息
     * @param clazz clazz
     * @return IndexEndurance
     */
    IndexEndurance getIndexEndurance(Class clazz);
}
