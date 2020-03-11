package com.xy.elasticsearch.autoconfigure;

import com.xy.elasticsearch.autoconfigure.config.ElasticSearchProperties;
import com.xy.elasticsearch.core.annotation.Repository;
import com.xy.elasticsearch.spring.factory.RepositoryFactoryBean;
import com.xy.elasticsearch.spring.repository.ClassPathRepositoryScanner;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.boot.autoconfigure.AutoConfigurationPackages;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * @author zoubo
 * @version 1.0.0
 * @date 2020/2/26 10:13
 */
@Configuration
@EnableConfigurationProperties(ElasticSearchProperties.class)
public class ElasticsearchConfiguration {

    private static final Logger logger = LoggerFactory.getLogger(ElasticsearchConfiguration.class);

    private final ElasticSearchProperties properties;

    public ElasticsearchConfiguration(ElasticSearchProperties properties) {
        this.properties = properties;
    }

    @Bean()
    public RestHighLevelClient restHighLevelClient() {
        RestClientBuilder builder = RestClient.builder(new HttpHost(properties.getHostname(), properties.getPort(), properties.getScheme()));
        builder.setHttpClientConfigCallback(httpAsyncClientBuilder -> {
                    RequestConfig.Builder custom = RequestConfig.custom()
                            //超时时间
                            .setConnectTimeout(properties.getCollectionTimeout())
                            //Socket超时时长
                            .setSocketTimeout(properties.getSocketTimeout())
                            .setConnectionRequestTimeout(properties.getConnectionRequestTimeout());
                    httpAsyncClientBuilder.setDefaultRequestConfig(custom.build());
                    if (org.apache.commons.lang3.StringUtils.isNotBlank(properties.getSecure().getUsername())
                            && org.apache.commons.lang3.StringUtils.isNotBlank(properties.getSecure().getPassword())) {
                        CredentialsProvider provider = new BasicCredentialsProvider();
                        provider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(properties.getSecure().getUsername(),
                                properties.getSecure().getPassword()));
                        httpAsyncClientBuilder.setDefaultCredentialsProvider(provider);
                    }
                    return httpAsyncClientBuilder;
                });

        return new RestHighLevelClient(builder);
    }


    public static class AutoConfiguredRepositoryScannerRegistrar
            implements BeanFactoryAware, ImportBeanDefinitionRegistrar, ResourceLoaderAware {

        private BeanFactory beanFactory;

        private ResourceLoader resourceLoader;

        @Override
        public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
            this.beanFactory = beanFactory;
        }

        @Override
        public void setResourceLoader(ResourceLoader resourceLoader) {
            this.resourceLoader = resourceLoader;
        }

        @Override
        public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
            if (!AutoConfigurationPackages.has(this.beanFactory)) {
                logger.debug("Could not determine auto-configuration package, automatic repository scanning disabled.");
                return;
            }

            logger.debug("Searching for repository annotated with @Repository");

            List<String> packages = AutoConfigurationPackages.get(this.beanFactory);
            if (logger.isDebugEnabled()) {
                packages.forEach(pkg -> logger.debug("Using auto-configuration base package '{}'", pkg));
            }

            ClassPathRepositoryScanner scanner = new ClassPathRepositoryScanner(registry);
            if (this.resourceLoader != null) {
                scanner.setResourceLoader(this.resourceLoader);
            }
            scanner.setAnnotationClass(Repository.class);
            scanner.registerFilters();
            scanner.doScan(StringUtils.toStringArray(packages));
        }
    }

    @Configuration
    @ConditionalOnMissingBean(RepositoryFactoryBean.class)
    public static class RepositoryScannerRegistrarNotFoundConfiguration implements InitializingBean {

        @Override
        public void afterPropertiesSet() {
            logger.debug("No {} found.", RepositoryFactoryBean.class.getName());
        }
    }


}
