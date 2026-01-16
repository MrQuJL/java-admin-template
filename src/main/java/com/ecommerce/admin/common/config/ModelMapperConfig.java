package com.ecommerce.admin.common.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * ModelMapper配置类
 * 用于对象之间的转换
 */
@Configuration
public class ModelMapperConfig {

    /**
     * 配置ModelMapper
     * @return ModelMapper
     */
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}