package com.example.javaadmintemplate.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 页面控制器
 * 处理页面导航请求
 *
 * @author example
 */
@Controller
public class PageController {

    /**
     * 个人中心页面
     */
    @GetMapping("/user/profile")
    public String profile() {
        return "user/profile";
    }
}
