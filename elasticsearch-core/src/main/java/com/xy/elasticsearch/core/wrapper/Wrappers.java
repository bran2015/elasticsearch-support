package com.xy.elasticsearch.core.wrapper;

/**
 * @author zoubo
 * @version 1.0.0
 * @date 2020/1/14 16:32
 */
public class Wrappers {

    private Wrappers(){

    }


    public static <T> ComplexQuery<T> complexQuery(){
        return new ComplexQuery<>();
    }

}
