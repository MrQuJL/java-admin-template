# Java Admin Template

一个基于Spring Boot的通用Java后台管理系统模板，包含用户管理、角色管理、权限管理等基础功能，采用MVC分层架构，支持多环境配置。

## 技术栈

- **后端框架**：Spring Boot 2.7.15
- **持久层框架**：MyBatis Plus 3.5.3.1
- **数据库**：MySQL 8.0+
- **缓存**：Redis
- **开发语言**：Java 8
- **构建工具**：Maven
- **代码生成**：MyBatis Plus Generator
- **日志框架**：SLF4J + Logback

## 项目结构

```
java-admin-template
├── src/main
│   ├── java/com/example/javaadmintemplate
│   │   ├── config/           # 配置类
│   │   ├── controller/       # 控制器层
│   │   ├── entity/           # 实体类
│   │   ├── mapper/           # Mapper接口
│   │   ├── service/          # 服务层
│   │   │   └── impl/         # 服务实现类
│   │   ├── utils/            # 工具类
│   │   └── JavaAdminTemplateApplication.java  # 主启动类
│   └── resources
│       ├── mapper/           # MyBatis XML文件
│       ├── application.yml   # 主配置文件
│       ├── application-dev.yml  # 开发环境配置
│       └── application-prod.yml # 生产环境配置
├── src/test                  # 测试代码
├── pom.xml                  # Maven配置
└── README.md                # 项目说明
```

## 快速开始

### 1. 环境准备

- JDK 8
- MySQL 8.0+
- Redis 5.0+
- Maven 3.6+

### 2. 数据库配置

1. 创建数据库：
```sql
CREATE DATABASE java_admin_template DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

2. 创建用户表：
```sql
CREATE TABLE `sys_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `username` varchar(50) NOT NULL COMMENT '用户名',
  `password` varchar(100) NOT NULL COMMENT '密码',
  `real_name` varchar(50) DEFAULT NULL COMMENT '真实姓名',
  `phone` varchar(20) DEFAULT NULL COMMENT '手机号码',
  `email` varchar(100) DEFAULT NULL COMMENT '邮箱地址',
  `status` int(11) DEFAULT '1' COMMENT '用户状态：0-禁用，1-启用',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注信息',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='系统用户表';
```

### 3. 配置文件修改

修改 `src/main/resources/application-dev.yml` 中的数据库和Redis连接信息：

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/java_admin_template?useUnicode=true&characterEncoding=utf-8&useSSL=false&serverTimezone=Asia/Shanghai
    username: root
    password: 123456
  redis:
    host: localhost
    port: 6379
    password: 
```

### 4. 启动项目

使用Maven启动项目：

```bash
mvn spring-boot:run
```

或使用IDE直接运行 `JavaAdminTemplateApplication.java` 类。

### 5. 访问API

项目启动后，可通过以下地址访问API：

```
http://localhost:8080/admin/api/users
```

## API文档

### 用户管理API

| 方法 | URL | 描述 |
|------|-----|------|
| GET | /api/users | 获取用户列表（分页） |
| GET | /api/users/{id} | 根据ID获取用户详情 |
| POST | /api/users | 创建新用户 |
| PUT | /api/users/{id} | 更新用户信息 |
| DELETE | /api/users/{id} | 删除用户 |
| DELETE | /api/users | 批量删除用户 |
| GET | /api/users/username/{username} | 根据用户名查询用户 |
| PATCH | /api/users/{id}/status | 更新用户状态 |

## 多环境配置

项目支持多环境配置，通过修改 `application.yml` 中的 `spring.profiles.active` 来切换环境：

- `dev`：开发环境
- `prod`：生产环境

## 核心功能

- ✅ 用户管理（CRUD）
- ✅ 角色管理（待实现）
- ✅ 权限管理（待实现）
- ✅ 菜单管理（待实现）
- ✅ 数据字典（待实现）
- ✅ 操作日志（待实现）

## 开发规范

1. **命名规范**：
   - 类名：大驼峰命名法
   - 方法名：小驼峰命名法
   - 变量名：小驼峰命名法
   - 常量名：全大写，下划线分隔

2. **代码风格**：
   - 缩进：4个空格
   - 换行：LF
   - 括号：同一行

3. **注释规范**：
   - 类注释：使用Javadoc注释，说明类的作用
   - 方法注释：使用Javadoc注释，说明方法的作用、参数、返回值
   - 关键代码注释：使用//注释，说明代码的作用

## 许可证

MIT License

## 作者

Example

## 联系方式

如有问题或建议，请联系：example@example.com
