package com.xy.elasticsearch.core.wrapper;

import com.xy.elasticsearch.core.exception.ElasticsearchException;

import java.lang.invoke.SerializedLambda;
import java.util.Locale;

/**
 * @author zoubo
 * @version 1.0.0
 * @date 2020/1/15 12:12
 */
public abstract class AbstractComplexQuery<T, children extends AbstractComplexQuery<T, children>>
        extends AbstractWrapper<T, EFunction<T, ?>, children>{




    @Override
    protected String columnToString(EFunction<T, ?> function) {
        SerializedLambda lambda = function.serializedLambda();
        String implMethodName = lambda.getImplMethodName();
        return methodToProperty(implMethodName);
    }


    private String methodToProperty(String method){
        String symbolSet = "set";
        String symbolGet = "get";
        if (method.startsWith(symbolGet) || method.startsWith(symbolSet)){
            String name = method.substring(3);
            if (name.length() == 1 || (name.length() > 1 && !Character.isUpperCase(name.charAt(1)))) {
                name = name.substring(0, 1).toLowerCase(Locale.ENGLISH) + name.substring(1);
            }
            return name;
        }
        throw new ElasticsearchException("Parse method name " + method + " failure. The name is not start with 'get' or 'set' ");
    }



}
