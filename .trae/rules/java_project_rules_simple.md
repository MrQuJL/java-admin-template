# Java 项目开发规范

## 一、项目结构

```
src/main/java/com/company/
├── config/       # 配置类
├── controller/   # 控制器
├── service/      # 服务层
│   └── impl/    # 实现
├── repository/   # 数据访问
├── entity/       # 实体
├── dto/          # 传输对象
├── exception/    # 异常
└── util/         # 工具

src/main/resources/
├── application.yml
├── application-dev.yml
└── logback.xml
```

## 二、依赖管理

```xml
<properties>
    <java.version>11</java.version>
    <spring.boot.version>2.7.0</spring.boot.version>
</properties>

<dependencies>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
    <dependency>
        <groupId>org.projectlombok</groupId>
        <artifactId>lombok</artifactId>
    </dependency>
</dependencies>
```

## 三、代码规范

### 命名规则
- 类：PascalCase（UserService）
- 方法：camelCase（getUserById）
- 常量：UPPER_SNAKE_CASE（MAX_SIZE）
- 包：全小写（com.company.project）

### 代码示例
```java
@Service
public class UserService {
    private final UserRepository repo;
    
    public UserDTO getById(Long id) {
        return repo.findById(id)
            .map(this::toDTO)
            .orElseThrow(() -> new NotFoundException("用户不存在"));
    }
    
    private UserDTO toDTO(User user) {
        return UserDTO.builder()
            .id(user.getId())
            .name(user.getName())
            .build();
    }
}
```

## 四、注释规范

### 类注释
```java
/**
 * 用户服务
 * @author xxx
 * @since 1.0
 */
public class UserService {}
```

### 方法注释
```java
/**
 * 获取用户信息
 * @param id 用户ID
 * @return 用户DTO
 */
public UserDTO getById(Long id) {}
```

### 行内注释
```java
// 验证参数
if (id == null) throw new IllegalArgumentException();
```

## 五、日志规范

### 日志级别
- ERROR：系统错误
- WARN：警告信息
- INFO：重要流程
- DEBUG：调试信息

### 使用示例
```java
@Slf4j
@Service
public class OrderService {
    public void create(OrderDTO dto) {
        log.info("创建订单，用户ID:{}", dto.getUserId());
        try {
            // 业务逻辑
            log.info("订单创建成功，ID:{}", order.getId());
        } catch (Exception e) {
            log.error("订单创建失败", e);
            throw e;
        }
    }
}
```

### 注意事项
- 使用占位符：`log.info("用户:{}", name)`
- 不记录敏感信息：密码、身份证等

## 六、API设计规范

### RESTful风格
```
GET    /api/v1/users/{id}    # 查询
POST   /api/v1/users         # 创建
PUT    /api/v1/users/{id}    # 更新
DELETE /api/v1/users/{id}    # 删除
```

### 统一响应
```java
@Data
public class Result<T> {
    private String code;
    private String message;
    private T data;
    
    public static <T> Result<T> ok(T data) {
        return Result.<T>builder()
            .code("200")
            .message("成功")
            .data(data)
            .build();
    }
}
```

### 参数验证
```java
@Data
public class UserRequest {
    @NotBlank(message = "用户名不能为空")
    @Size(min=3, max=20)
    private String username;
    
    @Email(message = "邮箱格式错误")
    private String email;
}
```

### 异常处理
```java
@RestControllerAdvice
public class ExceptionHandler {
    @ExceptionHandler(NotFoundException.class)
    public Result<Void> handle(NotFoundException e) {
        return Result.error("404", e.getMessage());
    }
}
```

### 接口文档
```java
@RestController
@RequestMapping("/api/v1/users")
@Tag(name = "用户管理")
public class UserController {
    @GetMapping("/{id}")
    @Operation(summary = "获取用户")
    public Result<UserDTO> get(@PathVariable Long id) {}
}
```