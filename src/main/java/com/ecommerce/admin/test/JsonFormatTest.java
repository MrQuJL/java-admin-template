package com.ecommerce.admin.test;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.JSONWriter.Feature;

public class JsonFormatTest {
    public static void main(String[] args) {
        // 测试1：直接格式化JSON字符串（错误用法）
        System.out.println("=== 测试1：直接格式化JSON字符串（错误用法） ===");
        String jsonStr = "{\"name\":\"张三\",\"age\":18}";
        System.out.println("原始字符串：" + jsonStr);
        String formatted1 = JSON.toJSONString(jsonStr, Feature.PrettyFormat);
        System.out.println("格式化结果（错误）：" + formatted1);
        System.out.println();

        // 测试2：正确格式化JSON字符串（先解析为Java对象，再格式化）
        System.out.println("=== 测试2：正确格式化JSON字符串 ===");
        // 解析JSON字符串为JSONObject
        JSONObject jsonObject = JSON.parseObject(jsonStr);
        // 格式化输出
        String formatted2 = JSON.toJSONString(jsonObject, Feature.PrettyFormat);
        System.out.println("格式化结果（正确）：");
        System.out.println(formatted2);
        System.out.println();

        // 测试3：直接格式化JSON数组字符串（错误用法）
        System.out.println("=== 测试3：直接格式化JSON数组字符串（错误用法） ===");
        String jsonArrayStr = "[{\"name\":\"张三\",\"age\":18},{\"name\":\"李四\",\"age\":20}]";
        System.out.println("原始数组字符串：" + jsonArrayStr);
        String formatted3 = JSON.toJSONString(jsonArrayStr, Feature.PrettyFormat);
        System.out.println("格式化结果（错误）：" + formatted3);
        System.out.println();

        // 测试4：正确格式化JSON数组（先解析为JSONArray，再格式化）
        System.out.println("=== 测试4：正确格式化JSON数组 ===");
        // 解析JSON数组字符串为JSONArray
        JSONArray jsonArray = JSON.parseArray(jsonArrayStr);
        // 格式化输出
        String formatted4 = JSON.toJSONString(jsonArray, Feature.PrettyFormat);
        System.out.println("格式化结果（正确）：");
        System.out.println(formatted4);
        System.out.println();

        // 测试5：直接格式化Java对象
        System.out.println("=== 测试5：直接格式化Java对象 ===");
        // 创建Java对象
        User user = new User();
        user.setName("王五");
        user.setAge(22);
        user.setEmail("wangwu@example.com");
        // 默认格式化输出（4个空格缩进）
        String formatted5 = JSON.toJSONString(user, Feature.PrettyFormat);
        System.out.println("格式化结果（默认4个空格）：");
        System.out.println(formatted5);
        System.out.println();

        // 测试6：自定义缩进大小（使用替换方式）
        System.out.println("=== 测试6：自定义缩进大小（2个空格） ===");
        // 先获取默认格式化结果
        String defaultFormatted = JSON.toJSONString(user, Feature.PrettyFormat);
        // 将默认的4个空格替换为2个空格
        String formatted6 = defaultFormatted.replaceAll("    ", "  ");
        System.out.println("格式化结果（2个空格）：");
        System.out.println(formatted6);
        System.out.println();

        // 测试7：自定义缩进大小（1个空格）
        System.out.println("=== 测试7：自定义缩进大小（1个空格） ===");
        // 将默认的4个空格替换为1个空格
        String formatted7 = defaultFormatted.replaceAll("    ", " ");
        System.out.println("格式化结果（1个空格）：");
        System.out.println(formatted7);
        System.out.println();

        // 测试8：使用制表符缩进
        System.out.println("=== 测试8：使用制表符缩进 ===");
        // 将默认的4个空格替换为制表符
        String formatted8 = defaultFormatted.replaceAll("    ", "\t");
        System.out.println("格式化结果（制表符）：");
        System.out.println(formatted8);
        System.out.println();
    }

    // 测试用的Java类
    static class User {
        private String name;
        private int age;
        private String email;

        // Getters and setters
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        public int getAge() { return age; }
        public void setAge(int age) { this.age = age; }
        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }
    }
}