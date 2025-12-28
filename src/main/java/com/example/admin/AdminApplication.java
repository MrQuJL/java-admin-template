package com.example.admin;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Spring Boot启动类
 * 
 * @author admin
 */
@SpringBootApplication
@MapperScan("com.example.admin.mapper")
public class AdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(AdminApplication.class, args);
        System.out.println("========================================");
        System.out.println("后台管理系统启动成功！");
        System.out.println("Swagger文档地址: http://localhost:8080/swagger-ui.html");
        System.out.println("========================================");
    }
}