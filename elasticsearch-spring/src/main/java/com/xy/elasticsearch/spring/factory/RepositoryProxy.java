package com.xy.elasticsearch.spring.factory;

import com.xy.elasticsearch.core.repository.SimpleElasticsearchRepository;
import org.elasticsearch.client.RestHighLevelClient;

import java.io.Serializable;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * @author zoubo
 * @version 1.0.0
 * @date 2020/1/9 15:27
 */
public class RepositoryProxy<T> implements InvocationHandler, Serializable {

    private final SimpleElasticsearchRepository simpleElasticsearchRepository;

    public RepositoryProxy(Class<T> repositoryInterface, RestHighLevelClient restHighLevelClient) {
        Type[] genericInterfaces = repositoryInterface.getGenericInterfaces();
        ParameterizedType parameterized = (ParameterizedType) genericInterfaces[0];
        this.simpleElasticsearchRepository = new SimpleElasticsearchRepository((Class<?>) parameterized.getActualTypeArguments()[0], restHighLevelClient);
    }


    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (Object.class.equals(method.getDeclaringClass())){
            return method.invoke(this, args);
        }
        return method.invoke(simpleElasticsearchRepository, args);
    }

}
