package com.ecommerce.admin.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * 跨域配置类
 * 解决前后端分离架构下的跨域请求问题
 */
@Configuration
public class CorsConfig {

    /**
     * 配置跨域过滤器
     * @return CorsFilter
     */
    @Bean
    public CorsFilter corsFilter() {
        // 创建CORS配置对象
        CorsConfiguration config = new CorsConfiguration();
        
        // 允许所有来源访问，生产环境中建议配置具体的域名
        config.addAllowedOriginPattern("*");
        
        // 允许发送Cookie
        config.setAllowCredentials(true);
        
        // 允许所有请求方法（GET, POST, PUT, DELETE等）
        config.addAllowedMethod("*");
        
        // 允许所有请求头
        config.addAllowedHeader("*");
        
        // 暴露一些自定义响应头，如JWT令牌等
        config.addExposedHeader("Authorization");
        config.addExposedHeader("Content-Disposition");
        
        // 创建URL映射源
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        
        // 对所有URL应用CORS配置
        source.registerCorsConfiguration("/**", config);
        
        // 返回CORS过滤器
        return new CorsFilter(source);
    }
}
