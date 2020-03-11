package com.xy.elasticsearch.core.factory;


import com.xy.elasticsearch.core.parse.endurance.IndexEndurance;

/**
 * @author zoubo
 * @version 1.0.0
 * @date 2019/12/30 11:17
 */
public interface IndexFactory {


    /**
     * 获取索引库对象
     * @param path 路径
     * @return IndexEndurance
     */
    IndexEndurance getIndexEndurance(String path);


    /**
     * 获取索引库对象
     * @param clazz class
     * @return IndexEndurance
     */
    IndexEndurance getIndexEndurance(Class clazz);

}
