package com.xy.elasticsearch.spring.factory;

import org.elasticsearch.client.RestHighLevelClient;

import java.lang.reflect.Proxy;

/**
 * @author zoubo
 * @version 1.0.0
 * @date 2020/1/9 15:24
 */
public class RepositoryProxyFactory<T> {


    private final Class<T> repositoryInterface;

    public RepositoryProxyFactory(Class<T> repositoryInterface) {
        this.repositoryInterface = repositoryInterface;
    }

    public Class<T> getRepositoryInterface() {
        return repositoryInterface;
    }

    private T newInstance(RepositoryProxy<T> repositoryProxy){
        return (T)Proxy.newProxyInstance(repositoryInterface.getClassLoader(), new Class[]{repositoryInterface}, repositoryProxy);
    }

    public T newInstance(final RestHighLevelClient restHighLevelClient){
        final RepositoryProxy<T> repositoryProxy = new RepositoryProxy<>(repositoryInterface, restHighLevelClient);
        return newInstance(repositoryProxy);
    }

    public T newInstance(){
        final RepositoryProxy<T> repositoryProxy = new RepositoryProxy<>(repositoryInterface, null);
        return newInstance(repositoryProxy);
    }

}
