package com.ecommerce.admin.common.config;

import com.ecommerce.admin.common.interceptor.RequestLogInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * WebMvc配置类
 * 用于配置拦截器、资源处理等Web相关配置
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Autowired
    private RequestLogInterceptor requestLogInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 注册请求日志拦截器，对所有请求生效
        registry.addInterceptor(requestLogInterceptor)
                .addPathPatterns("/**")
                // 排除静态资源请求
                .excludePathPatterns("/static/**", "/error", "/favicon.ico");
    }
}
