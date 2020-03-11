package com.xy.elasticsearch.core.factory;


import com.xy.elasticsearch.core.parse.endurance.IndexEndurance;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zoubo
 * @version 1.0.0
 * @date 2019/12/30 11:14
 */
public abstract class AbstractIndexFactory implements IndexFactory {


    private static final Map<String, IndexEndurance> INDEX_ENDURANCE_MAP = new HashMap<>(1);



    public void addIndexEndurance(String path, IndexEndurance indexEndurance){
        INDEX_ENDURANCE_MAP.put(path, indexEndurance);
    }


    protected void addIndexEndurance(Class clazz, IndexEndurance indexEndurance){
        INDEX_ENDURANCE_MAP.put(clazz.getTypeName(), indexEndurance);
    }

    protected List<IndexEndurance> getAll(){
        List<IndexEndurance> list = new ArrayList<>();
        INDEX_ENDURANCE_MAP.forEach((key, value) -> list.add(value));
        return list;
    }

    @Override
    public IndexEndurance getIndexEndurance(Class clazz) {
        return INDEX_ENDURANCE_MAP.get(clazz.getTypeName());
    }

    @Override
    public IndexEndurance getIndexEndurance(String path) {
        return INDEX_ENDURANCE_MAP.get(path);
    }

}
