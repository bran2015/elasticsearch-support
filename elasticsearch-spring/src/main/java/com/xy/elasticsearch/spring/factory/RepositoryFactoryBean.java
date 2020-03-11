package com.xy.elasticsearch.spring.factory;

import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * @author zoubo
 * @version 1.0.0
 * @date 2020/2/26 18:24
 */
public class RepositoryFactoryBean<T> implements FactoryBean<T>, ApplicationContextAware{

    private Class<T> repositoryInterface;

    private ApplicationContext applicationContext;

    public RepositoryFactoryBean(){}

    public RepositoryFactoryBean(Class<T> repositoryInterface){
        this.repositoryInterface = repositoryInterface;
    }

    @Override
    public T getObject() throws Exception {
        return new RepositoryProxyFactory<>(this.repositoryInterface).newInstance(applicationContext.getBean("restHighLevelClient", RestHighLevelClient.class));
    }

    @Override
    public Class<?> getObjectType() {
        return null;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
