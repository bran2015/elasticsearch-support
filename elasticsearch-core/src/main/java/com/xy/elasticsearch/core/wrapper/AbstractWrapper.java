package com.xy.elasticsearch.core.wrapper;

import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;

/**
 * 条件封装
 * @author zoubo
 * @version 1.0.0
 * @date 2020/1/15 12:13
 */
@SuppressWarnings({"all"})
public abstract class AbstractWrapper<T, R, children extends AbstractWrapper<T, R, children>> extends Wrapper<T> implements Condition<children, R>, JoinCondition<children, R> {

    protected children thisWrapper = (children)this;

    protected T entity;

    protected Class<T> entityClass;

    protected QueryWheel queryWheel = new QueryWheel();

    @Override
    public T getEntity() {
        return entity;
    }

    public children setEntity(T entity){
        this.entity = entity;
        return (children)this;
    }

    @Override
    public children term(boolean scored, R column, Object val, SymbolCondition symbolCondition, Float boost) {
        return execute(scored, () -> QueryBuilders.termQuery(columnToString(column), val).boost(boost), symbolCondition);
    }

    @Override
    public children terms(boolean scored, SymbolCondition symbolCondition, Float boost, R column, Object ... val) {
        return execute(scored, () -> QueryBuilders.termsQuery(columnToString(column), val).boost(boost), symbolCondition);
    }

    @Override
    public children fuzzy(boolean scored, R column, Object val, SymbolCondition symbolCondition, Float boost) {
        return execute(scored, () -> QueryBuilders.fuzzyQuery(columnToString(column), val).boost(boost), symbolCondition);
    }

    @Override
    public children matchAll() {
        return execute(false, () -> QueryBuilders.matchAllQuery(), SymbolCondition.AND);
    }

    @Override
    public children prefix(boolean scored, R column, Object val, SymbolCondition symbolCondition, Float boost) {
        return execute(scored, () -> QueryBuilders.prefixQuery(columnToString(column), String.valueOf(val)).boost(boost), symbolCondition);
    }


    @Override
    public children range(boolean scored, R column, Object lte, Object gte, SymbolCondition symbolCondition, Float boost) {
        return execute(scored, () -> QueryBuilders.rangeQuery(columnToString(column)).gte(gte).lte(lte).boost(boost), symbolCondition);
    }


    @Override
    public children match(boolean scored, R column, Object val, SymbolCondition symbolCondition, Float boost) {
        return execute(scored, () -> QueryBuilders.matchQuery(columnToString(column), String.valueOf(val)).boost(boost), symbolCondition);
    }


    @Override
    public children matchPhrase(boolean scored, R column, Object val, SymbolCondition symbolCondition, Float boost, Integer slop) {
        return execute(scored, () -> QueryBuilders.matchPhraseQuery(columnToString(column), String.valueOf(val)).slop(slop).boost(boost), symbolCondition);
    }

    @Override
    public children matchPhrasePrefix(boolean scored, R column, Object val, SymbolCondition symbolCondition, Float boost, Integer slop) {
        return execute(scored, () -> QueryBuilders.matchPhrasePrefixQuery(columnToString(column), String.valueOf(val)).slop(slop).boost(boost), symbolCondition);
    }


    @Override
    public children multiMatch(boolean scored, R column, Object val, SymbolCondition symbolCondition, Float boost, String analyzer) {
        if (analyzer.equals(DEFAULT_ANALYZER)){
            return execute(scored, () -> QueryBuilders.multiMatchQuery(val, columnToString(column)).boost(boost), symbolCondition);
        }
        return execute(scored, () -> QueryBuilders.multiMatchQuery(val, columnToString(column)).analyzer(analyzer), symbolCondition);
    }

    protected children execute(boolean scored, AssemblyFragment ... assemblyFragments){
        queryWheel.add(scored, assemblyFragments);
        return thisWrapper;
    }


    @Override
    public BoolQueryBuilder getBoolQueryBuilder() {
        return queryWheel.getBooleanQuery();
    }

    protected String columnToString(R column){
        return (String)column;
    }
}
