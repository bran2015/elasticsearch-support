package com.xy.elasticsearch.core.exception;

import com.alibaba.fastjson.JSONObject;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * @author zoubo
 * @version 1.0.0
 * @date 2019/12/30 14:12
 */
@Getter
@Setter
@Accessors(chain = true)
public class ElasticsearchException extends RuntimeException {

    protected String code;

    protected Object[] param;

    public ElasticsearchException(String msg, String code, Object... param){
        super(msg);
        this.param = param;
        this.code = code;
    }

    public ElasticsearchException(String msg, Object ... param){
        super(msg);
        this.param = param;
    }

    @Override
    public String toString() {
        return JSONObject.toJSONString(this);
    }
}
