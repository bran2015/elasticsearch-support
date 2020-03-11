package com.xy.elasticsearch.core.wrapper;

import lombok.Getter;
import lombok.Setter;
import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;

import java.util.Arrays;
import java.util.List;

/**
 * @author zoubo
 * @version 1.0.0
 * @date 2020/1/16 17:06
 */
@SuppressWarnings("all")
@Setter
@Getter
public class QueryWheel {

    private BoolQueryBuilder booleanQuery = QueryBuilders.boolQuery();


    /**
     * 是否参与打分
     * @param scored
     * @param assemblyFragments
     */
    protected void add(boolean scored, AssemblyFragment ... assemblyFragments){
        List<AssemblyFragment> list = Arrays.asList(assemblyFragments);
        if (!(list.get(0).getFragment() instanceof QueryBuilder)){
            throw new ElasticsearchException("the is not a QueryBulider");
        }
        if (!scored){
            booleanQuery.filter((QueryBuilder) list.get(0).getFragment());
            return;
        }
        SymbolCondition symbolCondition = (SymbolCondition)list.get(1);
        switch (symbolCondition){
            case AND:
                booleanQuery.must((QueryBuilder) list.get(0).getFragment());
                break;
            case OR:
                booleanQuery.should((QueryBuilder) list.get(0).getFragment());
                break;
            case IS_NOT:
                booleanQuery.mustNot((QueryBuilder) list.get(0).getFragment());
            default:
                break;
        }
    }


}
