package com.ecommerce.admin.common.interceptor;

import com.alibaba.fastjson2.JSON;
import com.ecommerce.admin.common.constants.DateConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * 请求日志拦截器
 * 用于记录每次请求的详细信息
 */
@Slf4j
@Component
public class RequestLogInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 记录请求开始时间
        request.setAttribute("startTime", System.currentTimeMillis());
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        // postHandle只在请求成功时执行，这里不需要处理
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // 只处理HandlerMethod类型的处理器
        if (handler instanceof HandlerMethod) {
            // 获取请求基本信息
            String url = request.getRequestURI();
            String ip = request.getRemoteAddr();
            String method = request.getMethod();
            String requestTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern(DateConstants.DATE_TIME_FORMAT));
            
            // 获取Controller信息
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            String className = handlerMethod.getBeanType().getSimpleName();
            String methodName = handlerMethod.getMethod().getName();
            
            // 获取请求类型
            String contentType = request.getContentType();
            String requestType = contentType != null ? contentType : "unknown";
            
            // 获取请求参数
            Map<String, Object> params = new HashMap<>();
            
            // 获取GET请求参数
            Map<String, String[]> parameterMap = request.getParameterMap();
            for (Map.Entry<String, String[]> entry : parameterMap.entrySet()) {
                params.put(entry.getKey(), Arrays.asList(entry.getValue()));
            }
            
            // 获取JSON请求体
            String requestBody = null;
            // 尝试从ContentCachingRequestWrapper获取请求体（Spring Boot会自动包装）
            ContentCachingRequestWrapper cachingRequest = WebUtils.getNativeRequest(request, ContentCachingRequestWrapper.class);
            if (cachingRequest != null) {
                byte[] contentAsByteArray = cachingRequest.getContentAsByteArray();
                if (contentAsByteArray.length > 0) {
                    requestBody = new String(contentAsByteArray, StandardCharsets.UTF_8);
                }
            }
            
            // 如果没有获取到请求体，尝试从request属性中获取（兼容旧版方式）
            if (requestBody == null || requestBody.isEmpty()) {
                requestBody = (String) request.getAttribute("requestBody");
            }
            
            // 将JSON请求体添加到params中
            if (requestBody != null && !requestBody.isEmpty()) {
                params.put("body", requestBody);
            }
            
            // 计算请求处理时间
            long startTime = (long) request.getAttribute("startTime");
            long endTime = System.currentTimeMillis();
            long duration = endTime - startTime;
            
            // 构建日志信息
            StringBuilder logMsg = new StringBuilder();
            logMsg.append("\n=== 请求日志 ===\n");
            logMsg.append("URL: ").append(url).append("\n");
            logMsg.append("IP: ").append(ip).append("\n");
            logMsg.append("Method: ").append(method).append("\n");
            logMsg.append("Content-Type: ").append(requestType).append("\n");
            logMsg.append("Controller: ").append(className).append(".").append(methodName).append("\n");
            logMsg.append("Params: ").append(JSON.toJSONString(params)).append("\n");
            logMsg.append("Request Time: ").append(requestTime).append("\n");
            logMsg.append("Processing Time: ").append(duration).append("ms\n");
            logMsg.append("Response Status: ").append(response.getStatus()).append("\n");
            
            if (ex != null) {
                logMsg.append("Exception: ").append(ex.getClass().getSimpleName()).append(": ").append(ex.getMessage()).append("\n");
            }
            
            logMsg.append("================\n");
            
            // 打印日志
            log.info(logMsg.toString());
        }
    }
}
