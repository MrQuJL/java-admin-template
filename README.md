# Java后台管理系统模板

基于Spring Boot 2.7.14 + MyBatis-Plus 3.5.3.1的后台管理系统模板项目，开箱即用。

## 技术栈

- Java 1.8
- Spring Boot 2.7.14
- MyBatis-Plus 3.5.3.1
- MySQL 8.0.33
- Redis
- Fastjson 2.0.25
- Lombok

## 项目结构

```
src/main/
├── java/
│   └── com/example/admin/
│       ├── common/                                # 公共类目录
│       │   ├── Result.java                       # 统一响应结果类
│       │   └── BusinessException.java            # 自定义异常类
│       ├── config/                                # 配置类目录
│       │   ├── GlobalExceptionHandler.java       # 全局异常处理
│       │   └── CorsConfig.java                   # 跨域配置
│       ├── controller/                            # 控制器层
│       │   └── UserController.java               # 用户控制器
│       ├── entity/                                # 实体类目录
│       │   └── User.java                         # 用户实体类
│       ├── mapper/                                # MyBatis Mapper接口目录
│       │   └── UserMapper.java                   # 用户Mapper
│       ├── service/                               # 业务逻辑层目录
│       │   ├── UserService.java                  # 用户Service接口
│       │   └── impl/
│       │       └── UserServiceImpl.java         # 用户Service实现
│       └── AdminApplication.java                  # Spring Boot启动类
└── resources/
    ├── mapper/                                    # MyBatis XML映射文件目录
    ├── sql/                                       # SQL脚本文件目录
    │   └── init.sql                              # 数据库初始化脚本
    ├── application.yml                            # 主配置文件
    ├── application-dev.yml                        # 开发环境配置文件
    └── application-prod.yml                       # 生产环境配置文件
```

## 快速开始

### 1. 环境要求

- JDK 1.8+
- Maven 3.6+
- MySQL 8.0+
- Redis 5.0+

### 2. 数据库配置

创建数据库并执行初始化脚本：

```bash
mysql -u root -p < src/main/resources/sql/init.sql
```

默认数据库配置：
- 数据库名：admin_db
- 用户名：root
- 密码：123456

### 3. Redis配置

确保Redis服务已启动，默认配置：
- 主机：localhost
- 端口：6379
- 密码：无

### 4. 启动项目

```bash
# 开发环境启动
mvn spring-boot:run

# 或者打包后启动
mvn clean package
java -jar target/java-admin-template-1.0.0.jar

# 生产环境启动
java -jar target/java-admin-template-1.0.0.jar --spring.profiles.active=prod
```

### 5. 访问接口

项目启动后，访问地址：`http://localhost:8080/api`

## 核心功能

### 用户管理接口

| 接口 | 方法 | 路径 | 说明 |
|------|------|------|------|
| 查询单个用户 | GET | /api/user/get/{id} | 根据ID查询用户 |
| 分页查询用户 | GET | /api/user/list | 分页查询用户列表 |
| 新增用户 | POST | /api/user/add | 新增用户 |
| 修改用户 | POST | /api/user/update | 修改用户 |
| 删除用户 | POST | /api/user/delete/{id} | 删除用户 |

### 接口示例

#### 查询单个用户
```bash
GET http://localhost:8080/api/user/get/1
```

响应：
```json
{
  "code": 200,
  "message": "操作成功",
  "data": {
    "id": 1,
    "username": "admin",
    "password": "123456",
    "nickname": "管理员",
    "email": "admin@example.com",
    "phone": "13800138000",
    "status": 1,
    "createTime": "2024-01-01 00:00:00",
    "updateTime": "2024-01-01 00:00:00",
    "deleted": 0
  }
}
```

#### 分页查询用户
```bash
GET http://localhost:8080/api/user/list?current=1&size=10&username=admin
```

#### 新增用户
```bash
POST http://localhost:8080/api/user/add
Content-Type: application/json

{
  "username": "test",
  "password": "123456",
  "nickname": "测试用户",
  "email": "test@example.com",
  "phone": "13800138004",
  "status": 1
}
```

## 编码规范

### 1. 接口请求类型规范

- **GET**：仅用于查询操作
- **POST**：用于新增/修改/删除操作

### 2. 方法命名规范

| 操作 | 方法前缀 | 示例 |
|------|----------|------|
| 新增 | add | addUser |
| 删除 | delete | deleteUser |
| 修改 | update | updateUser |
| 单条查询 | get | getUserById |
| 列表查询 | list | listUser |
| 分页查询 | page | pageUser |

### 3. 分层职责规范

#### Controller层
- 只负责请求转发、参数校验和响应封装
- 禁止包含任何业务逻辑
- 使用@Valid进行参数校验

#### Service层
- 数据查询必须使用`this.lambdaQuery().eq().list()`链式调用
- 数据修改必须使用`this.lambdaUpdate().set().eq().update()`链式调用
- 更新操作必须包含where条件

### 4. 统一响应格式

所有接口返回统一的Result格式：

```json
{
  "code": 200,
  "message": "操作成功",
  "data": {}
}
```

## 配置说明

### 多环境配置

- 开发环境：`application-dev.yml`
- 生产环境：`application-prod.yml`

切换环境：
```yaml
spring:
  profiles:
    active: dev  # 或 prod
```

### MyBatis-Plus配置

- 主键策略：自增
- 逻辑删除字段：deleted
- 驼峰命名转换：已启用
- SQL日志：开发环境已启用

## 注意事项

1. 确保MySQL和Redis服务已启动
2. 首次运行前需执行数据库初始化脚本
3. 生产环境请修改默认密码
4. 建议使用IDEA或Eclipse等IDE进行开发

## 许可证

MIT License
