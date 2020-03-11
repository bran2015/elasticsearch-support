package com.xy.elasticsearch.core.wrapper;

/**
 * @author zoubo
 * @version 1.0.0
 * @date 2020/1/30 12:16
 */
public enum  SymbolCondition implements AssemblyFragment{

    /**
     * and...
     */
    AND("and"),

    /**
     * or...
     */
    OR("or"),

    /**
     * is not ... 想等于sql的is not、 !=、not like
     */
    IS_NOT("is_not")
    ;


    private String symbol;

    SymbolCondition(String symbol) {
        this.symbol = symbol;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    @Override
    public Object getFragment() {
        return this.symbol;
    }
}
