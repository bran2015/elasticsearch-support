package com.xy.elasticsearch.core.utils;

import com.xy.elasticsearch.core.exception.ElasticsearchException;
import org.apache.commons.lang3.StringUtils;

import java.util.Collection;
import java.util.function.Predicate;

/**
 * @author zoubo
 * @version 1.0.0
 * @date 2020/1/13 16:03
 */
public class ValidateUtils {

    public static void isBlank(String source) {
        if (StringUtils.isBlank(source)){
            throw new ElasticsearchException("", "");
        }
    }


    public static <T> void isEmpty(Collection<T> collection){
        if (collection == null || collection.size() == 0){
            throw new ElasticsearchException("", "");
        }
    }


    public static void isNull(Object object){
        if (null == object){
            throw new ElasticsearchException("", "");
        }
    }


    /**
     * 验证
     * @param predicate Predicate
     */
    public static <T> void action(Predicate<T> predicate, T t) {
        boolean result = predicate.test(t);
        if (result){
            throw new ElasticsearchException("", "");
        }
    }


}
