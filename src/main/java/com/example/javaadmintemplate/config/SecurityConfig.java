package com.example.javaadmintemplate.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * 安全配置类
 * 配置密码加密器和安全相关设置
 *
 * @author example
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * 配置BCrypt密码加密器
     * BCrypt是一种安全的密码哈希算法，支持自动加盐
     *
     * @return PasswordEncoder 密码加密器实例
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                // 禁用CSRF保护（仅用于开发环境，生产环境建议启用）
                .csrf().disable()
                // 允许所有请求访问
                .authorizeRequests()
                .anyRequest().permitAll()
                .and()
                // 启用表单登录（默认提供登录页面）
                .formLogin()
                .and()
                // 配置注销
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/")
                .permitAll();
    }

}
