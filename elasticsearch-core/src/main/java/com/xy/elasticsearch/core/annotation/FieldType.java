package com.xy.elasticsearch.core.annotation;

/**
 * @author zoubo
 * @version 1.0.0
 * @date 2019/12/28 14:33
 */
public enum FieldType {

    /**
     *
     */
    KEYWORD("keyword"),

    LONG("long"),

    TEXT("text"),

    ALIAS("alias"),

    FLOAT("float"),

    JOIN("join"),

    GEO_POINT("geo_point"),

    DATE("date"),

    BINARY("binary"),

    BOOLEAN("boolean"),

    DATE_NANOS("date_nanos"),

    DENSE_VECTOR("dense_vector"),

    FLATTENED("flattened"),

    GEO_SHAPE("geo_shape"),

    IP("ip"),

    NESTED("nested"),

    INTEGER("integer"),

    SHORT("short"),

    BYTE("byte"),

    DOUBLE("double"),

    HALF_FLOAT("halt_float"),

    SCALED_FLOAT("scaled_float"),

    PERCOLATOR("percolator"),

    INTEGER_RANGE("integer_range"),

    FLOAT_RANG("float_range"),

    LONG_RANGE("long_range"),

    DOUBLE_RANGE("double_range"),

    DATE_RANGE("date_range"),

    IP_RANGE("ip_range"),

    RANK_FEATURE("rank_feature"),

    RANK_FEATURES("rank_features"),

    SEARCH_AS_YOU_TYPE("search_as_you_type"),

    SPARSE_VECTOR("sparse_vector"),

    TOKEN_COUNT("token_count"),

    SHAPE("shape");


    private String type;

    FieldType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
