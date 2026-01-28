package com.ecommerce.admin.common.config;

import com.ecommerce.admin.common.filter.ContentCachingFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * 过滤器统一配置类
 * 用于集中管理系统中的所有过滤器及其执行顺序
 */
@Configuration
public class FilterConfig {

    /**
     * 1. 跨域过滤器 (优先级最高)
     * 解决前后端分离架构下的跨域请求问题
     */
    @Bean
    public FilterRegistrationBean<CorsFilter> corsFilter() {
        // 创建CORS配置对象
        CorsConfiguration config = new CorsConfiguration();
        config.addAllowedOriginPattern("*");
        config.setAllowCredentials(true);
        config.addAllowedMethod("*");
        config.addAllowedHeader("*");
        config.addExposedHeader("Authorization");
        config.addExposedHeader("Content-Disposition");
        
        // 创建URL映射源
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        
        // 使用 FilterRegistrationBean 包装 CorsFilter
        FilterRegistrationBean<CorsFilter> bean = new FilterRegistrationBean<>(new CorsFilter(source));
        // 设置最高优先级: Integer.MIN_VALUE
        bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
        
        return bean;
    }

    /**
     * 2. 内容缓存过滤器 (优先级次之)
     * 用于包装请求和响应，支持多次读取Body (供日志拦截器使用)
     */
    @Bean
    public FilterRegistrationBean<ContentCachingFilter> contentCachingFilter() {
        FilterRegistrationBean<ContentCachingFilter> bean = new FilterRegistrationBean<>();
        bean.setFilter(new ContentCachingFilter());
        bean.addUrlPatterns("/*");
        // 设置优先级: Integer.MIN_VALUE + 1，确保在 CorsFilter 之后执行
        bean.setOrder(Ordered.HIGHEST_PRECEDENCE + 1);
        return bean;
    }
}
