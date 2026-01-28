package com.ecommerce.admin.common.config;

import cn.dev33.satoken.interceptor.SaInterceptor;
import cn.dev33.satoken.router.SaRouter;
import cn.dev33.satoken.stp.StpUtil;
import com.ecommerce.admin.common.interceptor.RequestLogInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.File;

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
        // 1. 注册请求日志拦截器，对所有请求生效 (建议放在最前面)
        registry.addInterceptor(requestLogInterceptor)
                .addPathPatterns("/**")
                // 排除静态资源请求
                .excludePathPatterns("/static/**", "/error", "/favicon.ico", "/file/**");

        // 2. 注册 Sa-Token 鉴权拦截器
        registry.addInterceptor(new SaInterceptor(handle -> {
            // 使用 SaRouter 路由匹配进行更精确的路径排除
            SaRouter.match("/**")
                    // 排除登录接口和验证码接口
                    .notMatch("/api/auth/login", "/api/auth/captcha")
                    // 排除错误页面 (非常重要，否则会掩盖404)
                    .notMatch("/error")
                    // 排除文件访问路径
                    .notMatch("/file/**")
                    // 排除静态资源
                    .notMatch("/", "/*.html", "/**/*.html", "/**/*.css", "/**/*.js", "/favicon.ico")
                    // 排除 Swagger 文档
                    .notMatch("/doc.html*", "/webjars/**", "/swagger-resources/**", "/v2/api-docs/**")
                    // 排除 WebSocket
                    .notMatch("/ws/**")
                    // 对剩余路径进行登录校验
                    .check(r -> StpUtil.checkLogin());
        })).addPathPatterns("/**");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 配置本地上传目录的静态资源映射
        String path = System.getProperty("user.dir") + File.separator + "uploads" + File.separator;
        registry.addResourceHandler("/file/**")
                .addResourceLocations("file:" + path);
    }
}
