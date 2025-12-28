# Java后台管理系统模板

基于Spring Boot + MyBatis-Plus的后台管理系统模板项目，开箱即用。

## 技术栈

- **Java**: 1.8
- **Spring Boot**: 2.7.14
- **MyBatis-Plus**: 3.5.3.1
- **MySQL**: 8.0.33
- **Redis**: 无密码配置
- **Fastjson**: 2.0.25
- **Lombok**: 简化代码
- **SpringDoc**: Swagger API文档

## 核心功能

1. 用户管理完整CRUD（分页查询、增删改查）
2. 统一响应格式（Result包装类）
3. 全局异常处理（@RestControllerAdvice）
4. 跨域支持
5. 标准MVC分层架构

## 项目结构

```
src/main/
├── java/
│   └── com/example/admin/
│       ├── common/                    # 公共类目录（工具类、常量、异常等）
│       │   ├── BusinessException.java # 业务异常类
│       │   ├── GlobalExceptionHandler.java # 全局异常处理器
│       │   └── Result.java            # 统一响应类
│       ├── config/                    # 配置类目录
│       │   ├── MyBatisPlusConfig.java # MyBatis-Plus配置
│       │   ├── RedisConfig.java       # Redis配置
│       │   └── WebMvcConfig.java      # Web MVC配置（跨域）
│       ├── controller/                # 控制器层
│       │   └── UserController.java    # 用户控制器
│       ├── entity/                    # 实体类目录
│       │   └── User.java              # 用户实体类
│       ├── mapper/                    # MyBatis Mapper接口目录
│       │   └── UserMapper.java        # 用户Mapper接口
│       ├── service/                   # 业务逻辑层目录
│       │   ├── UserService.java       # 用户Service接口
│       │   └── impl/
│       │       └── UserServiceImpl.java # 用户Service实现类
│       └── AdminApplication.java      # Spring Boot启动类
└── resources/
    ├── mapper/                        # MyBatis XML映射文件目录
    │   └── UserMapper.xml             # 用户Mapper XML
    ├── sql/                           # SQL脚本文件目录
    │   └── init.sql                   # 数据库初始化脚本
    ├── application.yml                # 主配置文件
    ├── application-dev.yml            # 开发环境配置文件
    └── application-prod.yml           # 生产环境配置文件
```

## 快速开始

### 1. 环境要求

- JDK 1.8+
- Maven 3.6+
- MySQL 8.0+
- Redis

### 2. 数据库配置

创建数据库并执行初始化脚本：

```bash
mysql -uroot -p123456 < src/main/resources/sql/init.sql
```

### 3. 修改配置

根据实际情况修改配置文件中的数据库和Redis连接信息：

- 开发环境：`application-dev.yml`
- 生产环境：`application-prod.yml`

### 4. 启动项目

```bash
mvn clean package
java -jar target/java-admin-template-1.0.0.jar
```

或直接在IDE中运行 `AdminApplication.java`

### 5. 访问Swagger文档

启动成功后，访问：http://localhost:8080/swagger-ui.html

## API接口

### 用户管理

| 接口 | 方法 | 说明 |
|------|------|------|
| /api/user/list | GET | 分页查询用户列表 |
| /api/user/get | GET | 根据ID查询用户 |
| /api/user/add | POST | 新增用户 |
| /api/user/update | POST | 修改用户 |
| /api/user/delete | POST | 删除用户 |

## 编码规范

### 1. 接口请求类型

- **GET**: 仅用于查询操作
- **POST**: 用于新增、修改、删除操作

### 2. 方法命名规范

- **新增**: `add` 前缀（如：addUser）
- **删除**: `delete` 前缀（如：deleteUser）
- **修改**: `update` 前缀（如：updateUser）
- **查询**: `list`（列表）、`get`（详情）前缀

### 3. 分层架构规范

- **Controller层**: 只负责接收请求、参数校验、返回响应，不包含业务逻辑
- **Service层**: 编写业务逻辑，通过MyBatis-Plus访问数据

### 4. MyBatis-Plus使用规范

- **查询数据**: 使用 `this.lambdaQuery()` 链式调用
- **修改数据**: 使用 `this.lambdaUpdate()` 链式调用

## 配置说明

### 数据库配置

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/java_admin?useUnicode=true&characterEncoding=utf8&useSSL=false&serverTimezone=Asia/Shanghai
    username: root
    password: 123456
    driver-class-name: com.mysql.cj.jdbc.Driver
```

### Redis配置

```yaml
spring:
  redis:
    host: localhost
    port: 6379
    database: 0
```

### 多环境配置

- 开发环境：`application-dev.yml`（默认）
- 生产环境：`application-prod.yml`

在 `application.yml` 中通过 `spring.profiles.active` 切换环境。

## 常见问题

### 1. 数据库连接失败

检查MySQL服务是否启动，用户名密码是否正确。

### 2. Redis连接失败

检查Redis服务是否启动，端口是否正确。

### 3. Swagger文档无法访问

检查 `springdoc.api-docs.path` 配置，确保路径正确。

## 开发建议

1. 遵循RESTful API设计规范
2. 使用统一的响应格式（Result）
3. 合理使用异常处理机制
4. 编写清晰的注释和文档
5. 定期进行代码审查

## 许可证

MIT License

## 作者

admin

## 更新日志

### v1.0.0 (2024-01-01)

- 初始版本发布
- 实现用户管理CRUD功能
- 集成Swagger API文档
- 完善异常处理机制
