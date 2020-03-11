package com.xy.elasticsearch.core.utils;


import com.xy.elasticsearch.core.exception.ElasticsearchException;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zoubo
 * @version 1.0.0
 * @date 2020/1/9 19:09
 */
public class MapUtils{


    /**
     * 过滤nul值
     */
    private static Map<String, Object> filterNullValue(Map map){
        Map<String, Object> newMap = new HashMap<>(1);
        map.forEach((key, value) -> {
            if (value != null){
                newMap.put(key.toString(), value);
            }
        });
        return newMap;
    }


    /**
     * 对象转map,null值被过滤
     * @param object object
     * @param filterNull 是否过滤null值
     */
    public static Map<String, Object> transformObject(Object object, boolean filterNull){
        Map<String, Object> map = new HashMap<>(1);
        try {
            Class<?> clazz = object.getClass();
            Field[] declaredFields = clazz.getDeclaredFields();
            for (Field field : declaredFields){
                field.setAccessible(true);
                if (field.get(object) == null && filterNull){
                    continue;
                }
                map.put(field.getName(), field.get(object));
            }
        }catch (Exception e){
            e.printStackTrace();
            throw new ElasticsearchException("", "", e);
        }
        return map;
    }

}
