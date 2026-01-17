package com.ecommerce.admin.common.filter;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 内容缓存过滤器
 * 用于包装请求，支持多次读取请求体
 */
@Component
public class ContentCachingFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 将请求包装为ContentCachingRequestWrapper，支持多次读取请求体
        ContentCachingRequestWrapper wrappedRequest = new ContentCachingRequestWrapper(request);
        // 继续执行过滤链
        filterChain.doFilter(wrappedRequest, response);
    }
}