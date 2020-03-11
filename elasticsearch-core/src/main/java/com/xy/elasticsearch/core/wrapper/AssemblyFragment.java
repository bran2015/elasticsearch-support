package com.xy.elasticsearch.core.wrapper;

import java.io.Serializable;

/**
 * 查询片段组装
 * @author zoubo
 * @version 1.0.0
 * @date 2020/1/17 18:33
 */
@FunctionalInterface
public interface AssemblyFragment extends Serializable {

    /**
     * 查询片段
     * @return String
     */
    Object getFragment();

}
