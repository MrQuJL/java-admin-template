package com.ecommerce.admin.common.interceptor;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONWriter.Feature;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 请求日志拦截器
 * 用于记录每次请求的详细信息
 */
@Slf4j
@Component
public class RequestLogInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        request.setAttribute("startTime", System.currentTimeMillis());
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        if (!(handler instanceof HandlerMethod)) {
            return;
        }

        try {
            long startTime = (long) request.getAttribute("startTime");
            long duration = System.currentTimeMillis() - startTime;

            // 1. 提取请求/响应信息
            String url = request.getRequestURI();
            String method = request.getMethod();
            String params = JSON.toJSONString(getSimplifiedParams(request));
            String reqContentType = request.getContentType();
            String resContentType = response.getContentType();
            String requestBody = getRequestBody(request);
            String responseBody = getResponseBody(response);
            
            // 2. 构造格式化日志
            // 使用简化的分隔符，重点突出 URL 和 耗时
            StringBuilder sb = new StringBuilder();
            sb.append("\n[Request Access] >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>\n")
              .append(String.format("URL    : %s [%s]\n", url, method))
              .append(String.format("Handle : %s#%s\n", ((HandlerMethod) handler).getBeanType().getSimpleName(), ((HandlerMethod) handler).getMethod().getName()))
              .append(String.format("Type   : Req[%s] | Res[%s]\n", 
                  reqContentType == null ? "none" : reqContentType, 
                  resContentType == null ? "none" : resContentType))
              .append(String.format("Time   : %sms\n", duration))
              .append(String.format("Status : %s\n", response.getStatus()));

            if (!"{}".equals(params)) {
                sb.append("Params : ").append(params).append("\n");
            }
            
            if (requestBody != null) {
                sb.append("ReqBody: ").append(formatJson(requestBody)).append("\n");
            }

            if (responseBody != null) {
                sb.append("ResBody: ").append(formatJson(responseBody)).append("\n");
            }
            
            sb.append("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");

            log.info(sb.toString());

        } catch (Exception e) {
            log.error("RequestLogInterceptor 记录日志失败", e);
        }
    }

    private String getRequestBody(HttpServletRequest request) {
        ContentCachingRequestWrapper wrapper = WebUtils.getNativeRequest(request, ContentCachingRequestWrapper.class);
        return wrapper != null ? new String(wrapper.getContentAsByteArray(), StandardCharsets.UTF_8) : null;
    }

    private String getResponseBody(HttpServletResponse response) {
        ContentCachingResponseWrapper wrapper = WebUtils.getNativeResponse(response, ContentCachingResponseWrapper.class);
        return wrapper != null ? new String(wrapper.getContentAsByteArray(), StandardCharsets.UTF_8) : null;
    }

    private Map<String, String> getSimplifiedParams(HttpServletRequest request) {
        return request.getParameterMap().entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue().length > 0 ? e.getValue()[0] : ""));
    }

    private String formatJson(String json) {
        if (!JSON.isValid(json)) return json;
        // 生产环境建议不使用 PrettyFormat 以节省磁盘，开发环境可开启
        return JSON.toJSONString(JSON.parse(json), Feature.PrettyFormat, Feature.WriteNulls).replace("\t", "    ");
    }
}