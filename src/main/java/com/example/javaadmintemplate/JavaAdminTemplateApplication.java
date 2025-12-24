package com.example.javaadmintemplate;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Spring Boot 主启动类
 * 
 * @author example
 */
@SpringBootApplication
@MapperScan("com.example.javaadmintemplate.mapper") // 扫描Mapper接口
public class JavaAdminTemplateApplication {

    public static void main(String[] args) {
        SpringApplication.run(JavaAdminTemplateApplication.class, args);
    }

}