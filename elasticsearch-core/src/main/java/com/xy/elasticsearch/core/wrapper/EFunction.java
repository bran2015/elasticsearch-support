package com.xy.elasticsearch.core.wrapper;

import java.io.Serializable;
import java.lang.invoke.SerializedLambda;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.function.Function;

/**
 * @author zoubo
 * @version 1.0.0
 * @date 2020/1/21 14:35
 */
@FunctionalInterface
public interface EFunction <T, R> extends Function<T, R>, Serializable {


    /**
     * 序列化
     * @return SerializedLambda
     */
    default SerializedLambda serializedLambda(){
        try {
            Method method = this.getClass().getDeclaredMethod("writeReplace");
            method.setAccessible(true);
            return (SerializedLambda) method.invoke(this);
        }catch (NoSuchMethodException | IllegalAccessException |InvocationTargetException e) {
            throw new RuntimeException("");
        }
    }

}
