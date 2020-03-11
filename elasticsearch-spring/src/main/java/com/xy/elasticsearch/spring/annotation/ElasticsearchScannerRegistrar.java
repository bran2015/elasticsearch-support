package com.xy.elasticsearch.spring.annotation;

import com.xy.elasticsearch.spring.repository.RepositoryScannerConfigurer;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author zoubo
 * @version 1.0.0
 * @date 2020/2/27 09:40
 */
public class ElasticsearchScannerRegistrar implements ImportBeanDefinitionRegistrar {



    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        AnnotationAttributes repositoryScanAttrs = AnnotationAttributes
                .fromMap(importingClassMetadata.getAnnotationAttributes(ElasticsearchScan.class.getName()));
        if (repositoryScanAttrs != null) {
            registerBeanDefinitions(repositoryScanAttrs, registry, generateBaseBeanName(importingClassMetadata, 0));
        }
    }


    private void registerBeanDefinitions(AnnotationAttributes attributes, BeanDefinitionRegistry registry, String beanName){
        BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(RepositoryScannerConfigurer.class);
        builder.addPropertyValue("processPropertyPlaceHolders", true);
        List<String> basePackages = Arrays.stream(attributes.getStringArray("value")).filter(StringUtils::hasText).collect(Collectors.toList());
        builder.addPropertyValue("basePackage", StringUtils.collectionToCommaDelimitedString(basePackages));
        registry.registerBeanDefinition(beanName, builder.getBeanDefinition());

    }


    private static String generateBaseBeanName(AnnotationMetadata importingClassMetadata, int index) {
        return importingClassMetadata.getClassName() + "#" + ElasticsearchScannerRegistrar.class.getSimpleName() + "#" + index;
    }
}
