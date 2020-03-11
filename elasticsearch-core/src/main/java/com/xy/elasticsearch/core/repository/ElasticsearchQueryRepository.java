package com.xy.elasticsearch.core.repository;


import com.xy.elasticsearch.core.wrapper.Wrapper;

import java.util.Collection;
import java.util.List;

/**
 * @author zoubo
 * @version 1.0.0
 * @date 2020/1/9 16:08
 */
public interface ElasticsearchQueryRepository<T> {


    /**
     * 根据文档id查询
     * @param id 文档id
     * @return <T>
     */
    T selectById(String id);


    /**
     * 根据文档查询
     * @param ids 文档id
     * @return List<T></>
     */
    List<T> selectByIds(Collection<String> ids);


    /**
     * 根据 Wrapper 条件
     * @param queryWrapper 查询条件
     * @return List
     */
    List<T> selectList(Wrapper<T> queryWrapper);




}
