package com.xy.elasticsearch.core.wrapper;

import java.io.Serializable;

import static org.elasticsearch.index.query.AbstractQueryBuilder.DEFAULT_BOOST;

/**
 * @author zoubo
 * @version 1.0.0
 * @date 2020/1/14 17:08
 */
public interface Condition<children, R> extends Serializable {

    /**
     * true-参与打分 false-不参与打分
     */
    boolean DEFAULT_SCORED = true;

    /**
     * 默认偏移量
     */
    int DEFAULT_SLOP = 0;

    /**
     * 默认分词器
     */
    String DEFAULT_ANALYZER = "";


    /**
     * = '值' 过滤
     * @param column    字段
     * @param val       值
     * @return children
     */
    default children term(R column, Object val){
        return term(DEFAULT_SCORED, column, val, SymbolCondition.AND, DEFAULT_BOOST);
    }


    /**
     * = '值'
     * @param scored 执行条件 是否打分
     * @param column    字段
     * @param val       值
     * @return children
     */
    default children term(boolean scored, R column, Object val) {
        return term(scored, column, val, SymbolCondition.AND, DEFAULT_BOOST);
    }


    /**
     * = '值'
     * @param column    字段
     * @param val       值
     * @param boost 权重
     * @return children
     */
    default children term(R column, Object val, Float boost){
        return term(DEFAULT_SCORED, column, val, SymbolCondition.AND, boost);
    }


    /**
     * = '值'
     * @param scored 执行条件 是否打分
     * @param column    字段
     * @param val       值
     * @param boost 权重
     * @return children
     */
    default children term(boolean scored, R column, Object val, Float boost) {
        return term(scored, column, val, SymbolCondition.AND, boost);
    }


    /**
     * = '值'
     * @param column    字段
     * @param val       值
     * @param symbolCondition 执行条件 符号
     * @return children
     */
    default children term(R column, Object val, SymbolCondition symbolCondition) {
        return term(DEFAULT_SCORED, column, val, symbolCondition, DEFAULT_BOOST);
    }


    /**
     * = '值'
     * @param scored 执行条件 是否打分
     * @param column    字段
     * @param val       值
     * @param symbolCondition 执行条件 符号
     * @return children
     */
    default children term(boolean scored, R column, Object val, SymbolCondition symbolCondition) {
        return term(scored, column, val, symbolCondition, DEFAULT_BOOST);
    }


    /**
     * = '值'
     * @param column    字段
     * @param val       值
     * @param symbolCondition 执行条件 符号
     * @param boost 权重
     * @return children
     */
    default children term(R column, Object val, SymbolCondition symbolCondition, Float boost) {
        return term(DEFAULT_SCORED, column, val, symbolCondition, boost);
    }


    /**
     * = '值'
     * @param scored 执行条件 是否打分
     * @param column    字段
     * @param val       值
     * @param symbolCondition 执行条件 符号
     * @param boost 权重
     * @return children
     */
    children term(boolean scored, R column, Object val, SymbolCondition symbolCondition, Float boost);


    /**
     * in (值) 过滤
     * @param column    字段
     * @param val       值
     * @return children
     */
    default children terms(R column, Object... val){
        return terms(DEFAULT_SCORED, SymbolCondition.AND, DEFAULT_BOOST, column, val);
    }


    /**
     * in (值) 过滤
     * @param scored 执行条件 是否打分
     * @param column    字段
     * @param val       值
     * @return children
     */
    default children terms(boolean scored, R column, Object... val) {
        return terms(scored, SymbolCondition.AND, DEFAULT_BOOST, column, val);
    }


    /**
     * in (值) 过滤
     * @param column    字段
     * @param val       值
     * @param boost 权重
     * @return children
     */
    default children terms(Float boost, R column, Object... val){
        return terms(DEFAULT_SCORED, SymbolCondition.AND, boost, column, val);
    }


    /**
     * in (值) 过滤
     * @param scored 执行条件 是否打分
     * @param column    字段
     * @param val       值
     * @param boost 权重
     * @return children
     */
    default children terms(boolean scored, Float boost, R column, Object... val) {
        return terms(scored, SymbolCondition.AND, boost, column, val);
    }


    /**
     * in (值) 过滤
     * @param column    字段
     * @param val       值
     * @param symbolCondition 执行条件 符号
     * @return children
     */
    default children terms(R column, SymbolCondition symbolCondition, Object... val) {
        return terms(DEFAULT_SCORED, symbolCondition, DEFAULT_BOOST, column, val);
    }


    /**
     *in (值) 过滤
     * @param scored 执行条件 是否打分
     * @param column    字段
     * @param val       值
     * @param symbolCondition 执行条件 符号
     * @return children
     */
    default children terms(boolean scored, SymbolCondition symbolCondition, R column, Object... val) {
        return terms(scored, symbolCondition, DEFAULT_BOOST, column, val);
    }


    /**
     * in (值) 过滤
     * @param column    字段
     * @param val       值
     * @param symbolCondition 执行条件 符号
     * @param boost 权重
     * @return children
     */
    default children terms(SymbolCondition symbolCondition, Float boost, R column, Object... val) {
        return terms(DEFAULT_SCORED, symbolCondition, boost, column, val);
    }


    /**
     * in (值) 过滤
     * @param scored 执行条件 是否打分
     * @param column    字段
     * @param val       值
     * @param symbolCondition 执行条件 符号
     * @param boost 权重
     * @return children
     */
    children terms(boolean scored, SymbolCondition symbolCondition, Float boost, R column, Object... val);


    /**
     * like '值' 过滤
     * @param column    字段
     * @param val       值
     * @return children
     */
    default children fuzzy(R column, Object val){
        return fuzzy(DEFAULT_SCORED, column, val, SymbolCondition.AND, DEFAULT_BOOST);
    }


    /**
     * like '值'
     * @param scored 执行条件 是否打分
     * @param column    字段
     * @param val       值
     * @return children
     */
    default children fuzzy(boolean scored, R column, Object val) {
        return fuzzy(scored, column, val, SymbolCondition.AND, DEFAULT_BOOST);
    }


    /**
     * like '值'
     * @param column    字段
     * @param val       值
     * @param boost 权重
     * @return children
     */
    default children fuzzy(R column, Object val, Float boost){
        return fuzzy(DEFAULT_SCORED, column, val, SymbolCondition.AND, boost);
    }


    /**
     * like '值'
     * @param scored 执行条件 是否打分
     * @param column    字段
     * @param val       值
     * @param boost 权重
     * @return children
     */
    default children fuzzy(boolean scored, R column, Object val, Float boost) {
        return fuzzy(scored, column, val, SymbolCondition.AND, boost);
    }


    /**
     * like '值'
     * @param column    字段
     * @param val       值
     * @param symbolCondition 执行条件 符号
     * @return children
     */
    default children fuzzy(R column, Object val, SymbolCondition symbolCondition) {
        return fuzzy(DEFAULT_SCORED, column, val, symbolCondition, DEFAULT_BOOST);
    }


    /**
     * like '值'
     * @param scored 执行条件 是否打分
     * @param column    字段
     * @param val       值
     * @param symbolCondition 执行条件 符号
     * @return children
     */
    default children fuzzy(boolean scored, R column, Object val, SymbolCondition symbolCondition) {
        return fuzzy(scored, column, val, symbolCondition, DEFAULT_BOOST);
    }


    /**
     * like '值'
     * @param column    字段
     * @param val       值
     * @param symbolCondition 执行条件 符号
     * @param boost 权重
     * @return children
     */
    default children fuzzy(R column, Object val, SymbolCondition symbolCondition, Float boost) {
        return fuzzy(DEFAULT_SCORED, column, val, symbolCondition, boost);
    }


    /**
     * like '值'
     * @param scored 执行条件 是否打分
     * @param column    字段
     * @param val       值
     * @param symbolCondition 执行条件 符号
     * @param boost 权重
     * @return children
     */
    children fuzzy(boolean scored, R column, Object val, SymbolCondition symbolCondition, Float boost);


    /**
     * 全文检索
     * @return children
     */
    children matchAll();


    /**
     * 前缀配置 '值%'
     * @param column    字段
     * @param val       值
     * @return children
     */
    default children prefix(R column, Object val){
        return prefix(DEFAULT_SCORED, column, val, SymbolCondition.AND, DEFAULT_BOOST);
    }


    /**
     * 前缀配置 '值%'
     * @param scored 执行条件 是否打分
     * @param column    字段
     * @param val       值
     * @return children
     */
    default children prefix(boolean scored, R column, Object val) {
        return prefix(scored, column, val, SymbolCondition.AND, DEFAULT_BOOST);
    }


    /**
     * 前缀配置 '值%'
     * @param column    字段
     * @param val       值
     * @param boost 权重
     * @return children
     */
    default children prefix(R column, Object val, Float boost){
        return prefix(DEFAULT_SCORED, column, val, SymbolCondition.AND, boost);
    }


    /**
     * 前缀配置 '值%'
     * @param scored 执行条件 是否打分
     * @param column    字段
     * @param val       值
     * @param boost 权重
     * @return children
     */
    default children prefix(boolean scored, R column, Object val, Float boost) {
        return prefix(scored, column, val, SymbolCondition.AND, boost);
    }


    /**
     * 前缀配置 '值%'
     * @param column    字段
     * @param val       值
     * @param symbolCondition 执行条件 符号
     * @return children
     */
    default children prefix(R column, Object val, SymbolCondition symbolCondition) {
        return prefix(DEFAULT_SCORED, column, val, symbolCondition, DEFAULT_BOOST);
    }


    /**
     * 前缀配置 '值%'
     * @param scored 执行条件 是否打分
     * @param column    字段
     * @param val       值
     * @param symbolCondition 执行条件 符号
     * @return children
     */
    default children prefix(boolean scored, R column, Object val, SymbolCondition symbolCondition) {
        return prefix(scored, column, val, symbolCondition, DEFAULT_BOOST);
    }


    /**
     * 前缀配置 '值%'
     * @param column    字段
     * @param val       值
     * @param symbolCondition 执行条件 符号
     * @param boost 权重
     * @return children
     */
    default children prefix(R column, Object val, SymbolCondition symbolCondition, Float boost) {
        return prefix(DEFAULT_SCORED, column, val, symbolCondition, boost);
    }


    /**
     * 前缀配置 '值%'
     * @param scored 执行条件 是否打分
     * @param column    字段
     * @param val       值
     * @param symbolCondition 执行条件 符号
     * @param boost 权重
     * @return children
     */
    children prefix(boolean scored, R column, Object val, SymbolCondition symbolCondition, Float boost);
    

    /**
     * 范围查询 ' 值 <= column <= 值 '
     * @param column    字段
     * @param gte       值 大于等于
     * @param lte       值 小于等于
     * @return children
     */
    default children range(R column, Object lte, Object gte){
        return range(DEFAULT_SCORED, column, lte, gte, SymbolCondition.AND, DEFAULT_BOOST);
    }


    /**
     * 范围查询 ' 值 <= column <= 值 '
     * @param column    字段
     * @param gte       值 大于等于
     * @param lte       值 小于等于
     * @param scored    执行条件 是否打分
     * @return children
     */
    default children range(boolean scored, R column, Object lte, Object gte) {
        return range(scored, column, lte, gte, SymbolCondition.AND, DEFAULT_BOOST);
    }
    

    /**
     * 范围查询 ' 值 <= column <= 值 '
     * @param column    字段
     * @param gte       值 大于等于
     * @param lte       值 小于等于
     * @param boost     权重
     * @return children
     */
    default children range(R column, Object lte, Object gte, Float boost){
        return range(DEFAULT_SCORED, column, lte, gte, SymbolCondition.AND, boost);
    }



    /**
     * 范围查询 ' 值 <= column <= 值 '
     * @param column    字段
     * @param gte       值 大于等于
     * @param lte       值 小于等于
     * @param scored    执行条件 是否打分
     * @param boost     权重
     * @return children
     */
    default children range(boolean scored, R column, Object lte, Object gte, Float boost) {
        return range(scored, column, lte, gte, SymbolCondition.AND, boost);
    }



    /**
     * 范围查询 ' 值 <= column <= 值 '
     * @param column    字段
     * @param gte       值 大于等于
     * @param lte       值 小于等于
     * @param symbolCondition 执行条件
     * @return children
     */
    default children range(R column, Object lte, Object gte, SymbolCondition symbolCondition) {
        return range(DEFAULT_SCORED, column, lte, gte, symbolCondition, DEFAULT_BOOST);
    }



    /**
     * 范围查询 ' 值 <= column <= 值 '
     * @param column    字段
     * @param gte       值 大于等于
     * @param lte       值 小于等于
     * @param scored    执行条件 是否打分
     * @param symbolCondition 执行条件
     * @return children
     */
    default children range(boolean scored, R column, Object lte, Object gte, SymbolCondition symbolCondition) {
        return range(scored, column, lte, gte, symbolCondition, DEFAULT_BOOST);
    }



    /**
     * 范围查询 ' 值 <= column <= 值 '
     * @param column    字段
     * @param gte       值 大于等于
     * @param lte       值 小于等于
     * @param symbolCondition 执行条件
     * @param boost     权重
     * @return children
     */
    default children range(R column, Object lte, Object gte, SymbolCondition symbolCondition, Float boost) {
        return range(DEFAULT_SCORED, column, lte, gte, symbolCondition, boost);
    }



    /**
     * 范围查询 ' 值 <= column <= 值 '
     * @param column    字段
     * @param gte       值 大于等于
     * @param lte       值 小于等于
     * @param scored    执行条件 是否打分
     * @param symbolCondition 执行条件
     * @param boost     权重
     * @return children
     */
    children range(boolean scored, R column, Object lte, Object gte, SymbolCondition symbolCondition, Float boost);


    /**
     * 匹配过滤 '%值%'   如：中华人民 分词后的筛选为 '中'、'华'、'人'、'民'， 未分词时为'中华人民'
     * @param column    字段
     * @param val       值
     * @return children
     */
    default children match(R column, Object val){
        return match(DEFAULT_SCORED, column, val, SymbolCondition.AND, DEFAULT_BOOST);
    }


    /**
     * 匹配过滤 '%值%'   如：中华人民 分词后的筛选为 '中'、'华'、'人'、'民'， 未分词时为'中华人民'
     * @param scored 执行条件 是否打分
     * @param column    字段
     * @param val       值
     * @return children
     */
    default children match(boolean scored, R column, Object val) {
        return match(scored, column, val, SymbolCondition.AND, DEFAULT_BOOST);
    }


    /**
     * 匹配过滤 '%值%'   如：中华人民 分词后的筛选为 '中'、'华'、'人'、'民'， 未分词时为'中华人民'
     * @param column    字段
     * @param val       值
     * @param boost 权重
     * @return children
     */
    default children match(R column, Object val, Float boost){
        return match(DEFAULT_SCORED, column, val, SymbolCondition.AND, boost);
    }


    /**
     * 匹配过滤 '%值%'   如：中华人民 分词后的筛选为 '中'、'华'、'人'、'民'， 未分词时为'中华人民'
     * @param scored 执行条件 是否打分
     * @param column    字段
     * @param val       值
     * @param boost 权重
     * @return children
     */
    default children match(boolean scored, R column, Object val, Float boost) {
        return match(scored, column, val, SymbolCondition.AND, boost);
    }


    /**
     * 匹配过滤 '%值%'   如：中华人民 分词后的筛选为 '中'、'华'、'人'、'民'， 未分词时为'中华人民'
     * @param column    字段
     * @param val       值
     * @param symbolCondition 执行条件 符号
     * @return children
     */
    default children match(R column, Object val, SymbolCondition symbolCondition) {
        return match(DEFAULT_SCORED, column, val, symbolCondition, DEFAULT_BOOST);
    }


    /**
     * 匹配过滤 '%值%'   如：中华人民 分词后的筛选为 '中'、'华'、'人'、'民'， 未分词时为'中华人民'
     * @param scored 执行条件 是否打分
     * @param column    字段
     * @param val       值
     * @param symbolCondition 执行条件 符号
     * @return children
     */
    default children match(boolean scored, R column, Object val, SymbolCondition symbolCondition) {
        return match(scored, column, val, symbolCondition, DEFAULT_BOOST);
    }


    /**
     * 匹配过滤 '%值%'   如：中华人民 分词后的筛选为 '中'、'华'、'人'、'民'， 未分词时为'中华人民'
     * @param column    字段
     * @param val       值
     * @param symbolCondition 执行条件 符号
     * @param boost 权重
     * @return children
     */
    default children match(R column, Object val, SymbolCondition symbolCondition, Float boost) {
        return match(DEFAULT_SCORED, column, val, symbolCondition, boost);
    }


    /**
     * 匹配过滤 '%值%'   如：中华人民 分词后的筛选为 '中'、'华'、'人'、'民'， 未分词时为'中华人民'
     * @param scored 执行条件 是否打分
     * @param column    字段
     * @param val       值
     * @param symbolCondition 执行条件 符号
     * @param boost 权重
     * @return children
     */
    children match(boolean scored, R column, Object val, SymbolCondition symbolCondition, Float boost);


    /**
     * 短语匹配过滤 '%值%'   如：中华人民 分词后的筛选为'中华人民'
     * @param column    字段
     * @param val       值
     * @return children
     */
    default children matchPhrase(R column, Object val){
        return matchPhrase(DEFAULT_SCORED, column, val, SymbolCondition.AND, DEFAULT_BOOST);
    }

    /**
     * 短语匹配过滤 '%值%'   如：中华人民 匹配为'中华人民'，'中华人民'这个词不是连着的doc搜索不出来，但是，
     * 如果设置slop，如slop为1时，含有'中国人的民'的短语亦可筛选出来
     * @param column    字段
     * @param val       值
     * @param slop      偏移量 默认为0
     * @return children
     */
    default children matchPhrase(R column, Object val, Integer slop){
        return matchPhrase(DEFAULT_SCORED, column, val, SymbolCondition.AND, DEFAULT_BOOST, slop);
    }


    /**
     * 短语匹配过滤 '%值%'   如：中华人民 筛选为'中华人民'
     * @param scored 执行条件 是否打分
     * @param column    字段
     * @param val       值
     * @return children
     */
    default children matchPhrase(boolean scored, R column, Object val) {
        return matchPhrase(scored, column, val, SymbolCondition.AND, DEFAULT_BOOST);
    }


    /**
     * 短语匹配过滤 '%值%'   如：中华人民 匹配为'中华人民'，'中华人民'这个词不是连着的doc搜索不出来，但是，
     * 如果设置slop，如slop为1时，含有'中国人的民'的短语亦可筛选出来
     * @param scored    执行条件 是否打分
     * @param column    字段
     * @param val       值
     * @param slop      偏移量
     * @return children
     */
    default children matchPhrase(boolean scored, R column, Object val, Integer slop) {
        return matchPhrase(scored, column, val, SymbolCondition.AND, DEFAULT_BOOST, slop);
    }

    /**
     * 短语匹配过滤 '%值%'   如：中华人民 筛选为'中华人民'
     * @param column    字段
     * @param val       值
     * @param boost     权重
     * @return children
     */
    default children matchPhrase(R column, Object val, Float boost){
        return matchPhrase(DEFAULT_SCORED, column, val, SymbolCondition.AND, boost);
    }

    /**
     * 短语匹配过滤 '%值%'   如：中华人民 匹配为'中华人民'，'中华人民'这个词不是连着的doc搜索不出来，但是，
     * 如果设置slop，如slop为1时，含有'中国人的民'的短语亦可筛选出来
     * @param column    字段
     * @param val       值
     * @param boost     权重
     * @param slop      偏移量
     * @return children
     */
    default children matchPhrase(R column, Object val, Float boost, Integer slop){
        return matchPhrase(DEFAULT_SCORED, column, val, SymbolCondition.AND, boost, slop);
    }


    /**
     * 短语匹配过滤 '%值%'   如：中华人民 筛选为'中华人民'
     * @param scored 执行条件 是否打分
     * @param column    字段
     * @param val       值
     * @param boost     权重
     * @return children
     */
    default children matchPhrase(boolean scored, R column, Object val, Float boost) {
        return matchPhrase(scored, column, val, SymbolCondition.AND, boost);
    }

    /**
     * 短语匹配过滤 '%值%'   如：中华人民 匹配为'中华人民'，'中华人民'这个词不是连着的doc搜索不出来，但是，
     * 如果设置slop，如slop为1时，含有'中国人的民'的短语亦可筛选出来
     * @param scored 执行条件 是否打分
     * @param column    字段
     * @param val       值
     * @param boost     权重
     * @param slop      偏移量
     * @return children
     */
    default children matchPhrase(boolean scored, R column, Object val, Float boost, Integer slop) {
        return matchPhrase(scored, column, val, SymbolCondition.AND, boost, slop);
    }


    /**
     * 短语匹配过滤 '%值%'   如：中华人民 筛选为'中华人民'
     * @param column    字段
     * @param val       值
     * @param symbolCondition 执行条件 符号
     * @return children
     */
    default children matchPhrase(R column, Object val, SymbolCondition symbolCondition) {
        return matchPhrase(DEFAULT_SCORED, column, val, symbolCondition, DEFAULT_BOOST);
    }

    /**
     * 短语匹配过滤 '%值%'   如：中华人民 匹配为'中华人民'，'中华人民'这个词不是连着的doc搜索不出来，但是，
     * 如果设置slop，如slop为1时，含有'中国人的民'的短语亦可筛选出来
     * @param column    字段
     * @param val       值
     * @param symbolCondition 执行条件 符号
     * @param slop      偏移量
     * @return children
     */
    default children matchPhrase(R column, Object val, SymbolCondition symbolCondition, Integer slop) {
        return matchPhrase(DEFAULT_SCORED, column,val, symbolCondition, DEFAULT_BOOST, slop);
    }


    /**
     * 短语匹配过滤 '%值%'   如：中华人民 筛选为'中华人民'
     * @param scored 执行条件 是否打分
     * @param column    字段
     * @param val       值
     * @param symbolCondition 执行条件 符号
     * @return children
     */
    default children matchPhrase(boolean scored, R column, Object val, SymbolCondition symbolCondition) {
        return matchPhrase(scored, column,val, symbolCondition, DEFAULT_BOOST, DEFAULT_SLOP);
    }

    /**
     * 短语匹配过滤 '%值%'   如：中华人民 匹配为'中华人民'，'中华人民'这个词不是连着的doc搜索不出来，但是，
     * 如果设置slop，如slop为1时，含有'中国人的民'的短语亦可筛选出来
     * @param scored 执行条件 是否打分
     * @param column    字段
     * @param val       值
     * @param symbolCondition 执行条件 符号
     * @param slop      偏移量
     * @return children
     */
    default children matchPhrase(boolean scored, R column, Object val, SymbolCondition symbolCondition, Integer slop) {
        return matchPhrase(scored, column,val, symbolCondition, DEFAULT_BOOST, slop);
    }


    /**
     * 短语匹配过滤 '%值%'   如：中华人民 筛选为'中华人民'
     * @param column    字段
     * @param val       值
     * @param symbolCondition 执行条件 符号
     * @param boost 权重
     * @return children
     */
    default children matchPhrase(R column, Object val, SymbolCondition symbolCondition, Float boost) {
        return matchPhrase(DEFAULT_SCORED, column,val, symbolCondition, boost, DEFAULT_SLOP);
    }

    /**
     * 短语匹配过滤 '%值%'   如：中华人民 匹配为'中华人民'，'中华人民'这个词不是连着的doc搜索不出来，但是，
     * 如果设置slop，如slop为1时，含有'中国人的民'的短语亦可筛选出来
     * @param column    字段
     * @param val       值
     * @param symbolCondition 执行条件 符号
     * @param boost     权重
     * @param slop      偏移量
     * @return children
     */
    default children matchPhrase(R column, Object val, SymbolCondition symbolCondition, Float boost, Integer slop) {
        return matchPhrase(DEFAULT_SCORED, column,val, symbolCondition, boost, slop);
    }


    /**
     * 短语匹配过滤 '%值%'   如：中华人民 筛选为'中华人民'
     * @param scored 执行条件 是否打分
     * @param column    字段
     * @param val       值
     * @param symbolCondition 执行条件 符号
     * @param boost 权重
     * @return children
     */
    default children matchPhrase(boolean scored, R column, Object val, SymbolCondition symbolCondition, Float boost){
        return matchPhrase(scored, column,val, symbolCondition, boost, DEFAULT_SLOP);
    }


    /**
     * 短语匹配过滤 '%值%'   如：中华人民 匹配为'中华人民'，'中华人民'这个词不是连着的doc搜索不出来，但是，
     * 如果设置slop，如slop为1时，含有'中国人的民'的短语亦可筛选出来
     * @param scored 执行条件 是否打分
     * @param column    字段
     * @param val       值
     * @param symbolCondition 执行条件 符号
     * @param boost     权重
     * @param slop      偏移量
     * @return children
     */
    children matchPhrase(boolean scored, R column, Object val, SymbolCondition symbolCondition, Float boost, Integer slop);


    /**
     * 短语前缀匹配过滤 '%值%'   如：中华人民 分词后的筛选为'中华人民'
     * @param column    字段
     * @param val       值
     * @return children
     */
    default children matchPhrasePrefix(R column, Object val){
        return matchPhrasePrefix(DEFAULT_SCORED, column, val, SymbolCondition.AND, DEFAULT_BOOST);
    }

    /**
     * 短语前缀匹配过滤 '%值%'   如：中华人民 匹配为'中华人民'，'中华人民'这个词不是连着的doc搜索不出来，但是，
     * 如果设置slop，如slop为1时，含有'中国人的民'的短语亦可筛选出来
     * @param column    字段
     * @param val       值
     * @param slop      偏移量 默认为0
     * @return children
     */
    default children matchPhrasePrefix(R column, Object val, Integer slop){
        return matchPhrasePrefix(DEFAULT_SCORED, column, val, SymbolCondition.AND, DEFAULT_BOOST, slop);
    }


    /**
     * 短语前缀匹配过滤 '%值%'   如：中华人民 筛选为'中华人民'
     * @param scored 执行条件 是否打分
     * @param column    字段
     * @param val       值
     * @return children
     */
    default children matchPhrasePrefix(boolean scored, R column, Object val) {
        return matchPhrasePrefix(scored, column, val, SymbolCondition.AND, DEFAULT_BOOST);
    }


    /**
     * 短语前缀匹配过滤 '%值%'   如：中华人民 匹配为'中华人民'，'中华人民'这个词不是连着的doc搜索不出来，但是，
     * 如果设置slop，如slop为1时，含有'中国人的民'的短语亦可筛选出来
     * @param scored    执行条件 是否打分
     * @param column    字段
     * @param val       值
     * @param slop      偏移量
     * @return children
     */
    default children matchPhrasePrefix(boolean scored, R column, Object val, Integer slop) {
        return matchPhrasePrefix(scored, column, val, SymbolCondition.AND, DEFAULT_BOOST, slop);
    }

    /**
     * 短语前缀匹配过滤 '%值%'   如：中华人民 筛选为'中华人民'
     * @param column    字段
     * @param val       值
     * @param boost     权重
     * @return children
     */
    default children matchPhrasePrefix(R column, Object val, Float boost){
        return matchPhrasePrefix(DEFAULT_SCORED, column, val, SymbolCondition.AND, boost);
    }

    /**
     * 短语前缀匹配过滤 '%值%'   如：中华人民 匹配为'中华人民'，'中华人民'这个词不是连着的doc搜索不出来，但是，
     * 如果设置slop，如slop为1时，含有'中国人的民'的短语亦可筛选出来
     * @param column    字段
     * @param val       值
     * @param boost     权重
     * @param slop      偏移量
     * @return children
     */
    default children matchPhrasePrefix(R column, Object val, Float boost, Integer slop){
        return matchPhrasePrefix(DEFAULT_SCORED, column, val, SymbolCondition.AND, boost, slop);
    }

    /**
     * 短语前缀匹配过滤 '%值%'   如：中华人民 筛选为'中华人民'
     * @param scored 执行条件 是否打分
     * @param column    字段
     * @param val       值
     * @param boost     权重
     * @return children
     */
    default children matchPhrasePrefix(boolean scored, R column, Object val, Float boost) {
        return matchPhrasePrefix(scored, column, val, SymbolCondition.AND, boost);
    }

    /**
     * 短语前缀匹配过滤 '%值%'   如：中华人民 匹配为'中华人民'，'中华人民'这个词不是连着的doc搜索不出来，但是，
     * 如果设置slop，如slop为1时，含有'中国人的民'的短语亦可筛选出来
     * @param scored 执行条件 是否打分
     * @param column    字段
     * @param val       值
     * @param boost     权重
     * @param slop      偏移量
     * @return children
     */
    default children matchPhrasePrefix(boolean scored, R column, Object val, Float boost, Integer slop) {
        return matchPhrasePrefix(scored, column, val, SymbolCondition.AND, boost, slop);
    }


    /**
     * 短语前缀匹配过滤 '%值%'   如：中华人民 筛选为'中华人民'
     * @param column    字段
     * @param val       值
     * @param symbolCondition 执行条件 符号
     * @return children
     */
    default children matchPhrasePrefix(R column, Object val, SymbolCondition symbolCondition) {
        return matchPhrasePrefix(DEFAULT_SCORED, column, val, symbolCondition, DEFAULT_BOOST);
    }

    /**
     * 短语前缀匹配过滤 '%值%'   如：中华人民 匹配为'中华人民'，'中华人民'这个词不是连着的doc搜索不出来，但是，
     * 如果设置slop，如slop为1时，含有'中国人的民'的短语亦可筛选出来
     * @param column    字段
     * @param val       值
     * @param symbolCondition 执行条件 符号
     * @param slop      偏移量
     * @return children
     */
    default children matchPhrasePrefix(R column, Object val, SymbolCondition symbolCondition, Integer slop) {
        return matchPhrasePrefix(DEFAULT_SCORED, column,val, symbolCondition, DEFAULT_BOOST, slop);
    }

    /**
     * 短语前缀匹配过滤 '%值%'   如：中华人民 筛选为'中华人民'
     * @param scored 执行条件 是否打分
     * @param column    字段
     * @param val       值
     * @param symbolCondition 执行条件 符号
     * @return children
     */
    default children matchPhrasePrefix(boolean scored, R column, Object val, SymbolCondition symbolCondition) {
        return matchPhrasePrefix(scored, column,val, symbolCondition, DEFAULT_BOOST, DEFAULT_SLOP);
    }

    /**
     * 短语前缀匹配过滤 '%值%'   如：中华人民 匹配为'中华人民'，'中华人民'这个词不是连着的doc搜索不出来，但是，
     * 如果设置slop，如slop为1时，含有'中国人的民'的短语亦可筛选出来
     * @param scored 执行条件 是否打分
     * @param column    字段
     * @param val       值
     * @param symbolCondition 执行条件 符号
     * @param slop      偏移量
     * @return children
     */
    default children matchPhrasePrefix(boolean scored, R column, Object val, SymbolCondition symbolCondition, Integer slop) {
        return matchPhrasePrefix(scored, column,val, symbolCondition, DEFAULT_BOOST, slop);
    }


    /**
     * 短语前缀匹配过滤 '%值%'   如：中华人民 筛选为'中华人民'
     * @param column    字段
     * @param val       值
     * @param symbolCondition 执行条件 符号
     * @param boost 权重
     * @return children
     */
    default children matchPhrasePrefix(R column, Object val, SymbolCondition symbolCondition, Float boost) {
        return matchPhrasePrefix(DEFAULT_SCORED, column,val, symbolCondition, boost, DEFAULT_SLOP);
    }

    /**
     * 短语前缀匹配过滤 '%值%'   如：中华人民 匹配为'中华人民'，'中华人民'这个词不是连着的doc搜索不出来，但是，
     * 如果设置slop，如slop为1时，含有'中国人的民'的短语亦可筛选出来
     * @param column    字段
     * @param val       值
     * @param symbolCondition 执行条件 符号
     * @param boost     权重
     * @param slop      偏移量
     * @return children
     */
    default children matchPhrasePrefix(R column, Object val, SymbolCondition symbolCondition, Float boost, Integer slop) {
        return matchPhrasePrefix(DEFAULT_SCORED, column,val, symbolCondition, boost, slop);
    }


    /**
     * 短语前缀匹配过滤 '%值%'   如：中华人民 筛选为'中华人民'
     * @param scored 执行条件 是否打分
     * @param column    字段
     * @param val       值
     * @param symbolCondition 执行条件 符号
     * @param boost 权重
     * @return children
     */
    default children matchPhrasePrefix(boolean scored, R column, Object val, SymbolCondition symbolCondition, Float boost){
        return matchPhrasePrefix(scored, column,val, symbolCondition, boost, DEFAULT_SLOP);
    }


    /**
     * 短语前缀匹配过滤 '%值%'   如：中华人民 匹配为'中华人民'，'中华人民'这个词不是连着的doc搜索不出来，但是，
     * 如果设置slop，如slop为1时，含有'中国人的民'的短语亦可筛选出来
     * @param scored 执行条件 是否打分
     * @param column    字段
     * @param val       值
     * @param symbolCondition 执行条件 符号
     * @param boost     权重
     * @param slop      偏移量
     * @return children
     */
    children matchPhrasePrefix(boolean scored, R column, Object val, SymbolCondition symbolCondition, Float boost, Integer slop);


    /**
     * 短语匹配过滤 '%值%'   如：中华人民 分词后的筛选为'中华人民'
     * @param column    字段
     * @param val       值
     * @return children
     */
    default children multiMatch(R column, Object val){
        return multiMatch(DEFAULT_SCORED, column, val, SymbolCondition.AND, DEFAULT_BOOST);
    }

    /**
     * 多重匹配查询
     * @param column    字段
     * @param val       值
     * @param analyzer  分词器
     * @return children
     */
    default children multiMatch(R column, Object val, String analyzer){
        return multiMatch(DEFAULT_SCORED, column, val, SymbolCondition.AND, DEFAULT_BOOST, analyzer);
    }


    /**
     * 多重匹配查询
     * @param scored 执行条件 是否打分
     * @param column    字段
     * @param val       值
     * @return children
     */
    default children multiMatch(boolean scored, R column, Object val) {
        return multiMatch(scored, column, val, SymbolCondition.AND, DEFAULT_BOOST);
    }


    /**
     * 多重匹配查询
     * @param scored    执行条件 是否打分
     * @param column    字段
     * @param val       值
     * @param analyzer  分词器
     * @return children
     */
    default children multiMatch(boolean scored, R column, Object val, String analyzer) {
        return multiMatch(scored, column, val, SymbolCondition.AND, DEFAULT_BOOST, analyzer);
    }

    /**
     * 多重匹配查询
     * @param column    字段
     * @param val       值
     * @param boost     权重
     * @return children
     */
    default children multiMatch(R column, Object val, Float boost){
        return multiMatch(DEFAULT_SCORED, column, val, SymbolCondition.AND, boost);
    }

    /**
     * 多重匹配查询
     * @param column    字段
     * @param val       值
     * @param boost     权重
     * @param analyzer  分词器
     * @return children
     */
    default children multiMatch(R column, Object val, Float boost, String analyzer){
        return multiMatch(DEFAULT_SCORED, column, val, SymbolCondition.AND, boost, analyzer);
    }


    /**
     * 多重匹配查询
     * @param scored 执行条件 是否打分
     * @param column    字段
     * @param val       值
     * @param boost     权重
     * @return children
     */
    default children multiMatch(boolean scored, R column, Object val, Float boost) {
        return multiMatch(scored, column, val, SymbolCondition.AND, boost);
    }

    /**
     * 多重匹配查询
     * @param scored 执行条件 是否打分
     * @param column    字段
     * @param val       值
     * @param boost     权重
     * @param analyzer  分词器
     * @return children
     */
    default children multiMatch(boolean scored, R column, Object val, Float boost, String analyzer) {
        return multiMatch(scored, column, val, SymbolCondition.AND, boost, analyzer);
    }


    /**
     * 多重匹配查询
     * @param column    字段
     * @param val       值
     * @param symbolCondition 执行条件 符号
     * @return children
     */
    default children multiMatch(R column, Object val, SymbolCondition symbolCondition) {
        return multiMatch(DEFAULT_SCORED, column, val, symbolCondition, DEFAULT_BOOST);
    }

    /**
     * 多重匹配查询
     * @param column    字段
     * @param val       值
     * @param symbolCondition 执行条件 符号
     * @param analyzer  分词器
     * @return children
     */
    default children multiMatch(R column, Object val, SymbolCondition symbolCondition, String analyzer) {
        return multiMatch(DEFAULT_SCORED, column,val, symbolCondition, DEFAULT_BOOST, analyzer);
    }


    /**
     * 多重匹配查询
     * @param scored 执行条件 是否打分
     * @param column    字段
     * @param val       值
     * @param symbolCondition 执行条件 符号
     * @return children
     */
    default children multiMatch(boolean scored, R column, Object val, SymbolCondition symbolCondition) {
        return multiMatch(scored, column,val, symbolCondition, DEFAULT_BOOST, DEFAULT_ANALYZER);
    }

    /**
     * 多重匹配查询
     * @param scored 执行条件 是否打分
     * @param column    字段
     * @param val       值
     * @param symbolCondition 执行条件 符号
     * @param analyzer  分词器
     * @return children
     */
    default children multiMatch(boolean scored, R column, Object val, SymbolCondition symbolCondition, String analyzer) {
        return multiMatch(scored, column,val, symbolCondition, DEFAULT_BOOST, analyzer);
    }


    /**
     * 多重匹配查询
     * @param column    字段
     * @param val       值
     * @param symbolCondition 执行条件 符号
     * @param boost 权重
     * @return children
     */
    default children multiMatch(R column, Object val, SymbolCondition symbolCondition, Float boost) {
        return multiMatch(DEFAULT_SCORED, column,val, symbolCondition, boost, DEFAULT_ANALYZER);
    }

    /**
     * 多重匹配查询
     * @param column    字段
     * @param val       值
     * @param symbolCondition 执行条件 符号
     * @param boost     权重
     * @param analyzer  分词器
     * @return children
     */
    default children multiMatch(R column, Object val, SymbolCondition symbolCondition, Float boost, String analyzer) {
        return multiMatch(DEFAULT_SCORED, column,val, symbolCondition, boost, analyzer);
    }


    /**
     * 多重匹配查询
     * @param scored 执行条件 是否打分
     * @param column    字段
     * @param val       值
     * @param symbolCondition 执行条件 符号
     * @param boost 权重
     * @return children
     */
    default children multiMatch(boolean scored, R column, Object val, SymbolCondition symbolCondition, Float boost){
        return multiMatch(scored, column,val, symbolCondition, boost, DEFAULT_ANALYZER);
    }


    /**
     * 多重匹配查询
     * @param scored 执行条件 是否打分
     * @param column    字段
     * @param val       值
     * @param symbolCondition 执行条件 符号
     * @param boost     权重
     * @param analyzer  分词器
     * @return children
     */
    children multiMatch(boolean scored, R column, Object val, SymbolCondition symbolCondition, Float boost, String analyzer);
    
    
    
    

}
