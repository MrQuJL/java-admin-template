# Java 项目通用规则

## 代码规范

### 1. 命名规范
- **类名**: 使用 PascalCase，如 `UserService`, `OrderController`
- **方法名**: 使用 camelCase，如 `getUserById()`, `calculateTotalAmount()`
- **常量**: 使用 UPPER_SNAKE_CASE，如 `MAX_RETRY_COUNT`, `DEFAULT_TIMEOUT`
- **包名**: 全部小写，使用反向域名格式，如 `com.company.project.module`
- **变量名**: 使用 camelCase，避免使用单字符名称（除循环变量外）

### 2. 代码结构
```java
// 正确的导入顺序
import java.io.*;
import java.util.*;

import javax.*;

import org.*;

import com.company.*;

// 类定义
public class UserService {
    // 常量
    private static final int MAX_USERS = 100;
    
    // 静态变量
    private static volatile UserService instance;
    
    // 实例变量
    private final UserRepository userRepository;
    
    // 构造函数
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    
    // 方法按访问修饰符排序：public -> protected -> private
}
```

### 3. 注释规范
- **类注释**: 使用 JavaDoc，描述类的用途和责任
- **方法注释**: 说明方法功能、参数、返回值和异常
- **复杂逻辑**: 添加行内注释解释复杂的业务逻辑
- **TODO/FIXME**: 标记需要后续处理的问题

```java
/**
 * 用户服务类，负责用户相关的业务逻辑处理
 * 
 * @author developer
 * @since 1.0.0
 */
public class UserService {
    
    /**
     * 根据用户ID获取用户信息
     * 
     * @param userId 用户ID，不能为null
     * @return 用户对象，如果未找到返回null
     * @throws IllegalArgumentException 如果userId为null
     */
    public User getUserById(Long userId) {
        // 参数验证
        if (userId == null) {
            throw new IllegalArgumentException("用户ID不能为null");
        }
        
        return userRepository.findById(userId);
    }
}
```

## 异常处理

### 1. 异常分类处理
```java
// 业务异常 - 自定义异常类
public class BusinessException extends RuntimeException {
    private final String errorCode;
    
    public BusinessException(String errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }
}

// 系统异常 - 记录日志并友好提示
@ControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse> handleBusinessException(BusinessException e) {
        log.warn("业务异常: {}", e.getMessage());
        return ResponseEntity.badRequest()
            .body(new ErrorResponse(e.getErrorCode(), e.getMessage()));
    }
    
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleSystemException(Exception e) {
        log.error("系统异常", e);
        return ResponseEntity.internalServerError()
            .body(new ErrorResponse("SYSTEM_ERROR", "系统繁忙，请稍后重试"));
    }
}
```

### 2. 异常处理原则
- 不要捕获异常后不做任何处理
- 不要忽略异常信息
- 使用合适的异常类型
- 提供有意义的错误信息

## 日志规范

### 1. 日志级别使用
- **ERROR**: 系统错误，需要立即处理
- **WARN**: 潜在问题，需要关注
- **INFO**: 重要业务流程
- **DEBUG**: 调试信息，开发环境使用
- **TRACE**: 详细跟踪信息

### 2. 日志格式
```java
@Slf4j
@Service
public class UserService {
    
    public User createUser(CreateUserRequest request) {
        log.info("开始创建用户，用户名: {}", request.getUsername());
        
        try {
            // 业务逻辑
            User user = userRepository.save(request.toEntity());
            log.info("用户创建成功，用户ID: {}", user.getId());
            return user;
        } catch (DataIntegrityViolationException e) {
            log.warn("用户名已存在: {}", request.getUsername());
            throw new BusinessException("USERNAME_EXISTS", "用户名已存在");
        } catch (Exception e) {
            log.error("创建用户失败，用户名: {}", request.getUsername(), e);
            throw new BusinessException("CREATE_USER_FAILED", "创建用户失败");
        }
    }
}
```

### 3. 日志最佳实践
- 使用 SLF4J 作为日志门面
- 使用占位符而不是字符串拼接
- 记录关键业务操作
- 避免记录敏感信息

## 单元测试

### 1. 测试命名规范
```java
@Test
void shouldReturnUserWhenUserExists() {
    // Given
    Long userId = 1L;
    User expectedUser = new User(userId, "testuser");
    when(userRepository.findById(userId)).thenReturn(Optional.of(expectedUser));
    
    // When
    User actualUser = userService.getUserById(userId);
    
    // Then
    assertThat(actualUser).isNotNull();
    assertThat(actualUser.getId()).isEqualTo(userId);
    assertThat(actualUser.getUsername()).isEqualTo("testuser");
}

@Test
void shouldThrowExceptionWhenUserNotFound() {
    // Given
    Long userId = 999L;
    when(userRepository.findById(userId)).thenReturn(Optional.empty());
    
    // When & Then
    assertThatThrownBy(() -> userService.getUserById(userId))
        .isInstanceOf(ResourceNotFoundException.class)
        .hasMessage("用户不存在");
}
```

### 2. 测试覆盖率要求
- 核心业务逻辑覆盖率 > 80%
- 数据访问层覆盖率 > 70%
- 工具类覆盖率 > 90%
- 避免无意义的测试

## 数据库规范

### 1. JPA/Hibernate 规范
```java
@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "username", nullable = false, unique = true, length = 50)
    private String username;
    
    @Column(name = "email", nullable = false, unique = true)
    private String email;
    
    @Column(name = "created_at", nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    @UpdateTimestamp
    private LocalDateTime updatedAt;
    
    @Version
    private Long version;
}
```

### 2. SQL 规范
- 使用参数化查询，防止 SQL 注入
- 表名使用复数形式，如 `users`, `orders`
- 列名使用小写加下划线，如 `created_at`
- 添加必要的索引
- 使用数据库迁移工具管理 schema 变更

## API 设计规范

### 1. RESTful API 设计
```java
@RestController
@RequestMapping("/api/v1/users")ublic class UserController {
    
    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUser(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }
    
    @PostMapping
    public ResponseEntity<UserResponse> createUser(@Valid @RequestBody CreateUserRequest request) {
        UserResponse user = userService.createUser(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> updateUser(@PathVariable Long id, 
                                                   @Valid @RequestBody UpdateUserRequest request) {
        UserResponse user = userService.updateUser(id, request);
        return ResponseEntity.ok(user);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
```

### 2. 响应格式统一
```java
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse<T> {
    private String code;
    private String message;
    private T data;
    private LocalDateTime timestamp;
    
    public static <T> ApiResponse<T> success(T data) {
        return ApiResponse.<T>builder()
            .code("SUCCESS")
            .message("操作成功")
            .data(data)
            .timestamp(LocalDateTime.now())
            .build();
    }
    
    public static <T> ApiResponse<T> error(String code, String message) {
        return ApiResponse.<T>builder()
            .code(code)
            .message(message)
            .timestamp(LocalDateTime.now())
            .build();
    }
}
```

## 性能优化

### 1. 缓存使用
```java
@Service
public class UserService {
    
    @Cacheable(value = "users", key = "#userId")
    public User getUserById(Long userId) {
        return userRepository.findById(userId)
            .orElseThrow(() -> new ResourceNotFoundException("用户不存在"));
    }
    
    @CacheEvict(value = "users", key = "#userId")
    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }
}
```

### 2. 数据库优化
- 使用连接池
- 批量操作
- 合理使用索引
- 避免 N+1 查询问题
- 使用分页查询

## 安全规范

### 1. 输入验证
```java
@RestController
@Validated
public class UserController {
    
    @PostMapping("/users")
    public ResponseEntity<UserResponse> createUser(@Valid @RequestBody CreateUserRequest request) {
        // 验证逻辑
        return ResponseEntity.ok(userService.createUser(request));
    }
}

@Data
public class CreateUserRequest {
    
    @NotBlank(message = "用户名不能为空")
    @Size(min = 3, max = 20, message = "用户名长度必须在3-20个字符之间")
    @Pattern(regexp = "^[a-zA-Z0-9_]+$", message = "用户名只能包含字母、数字和下划线")
    private String username;
    
    @NotBlank(message = "邮箱不能为空")
    @Email(message = "邮箱格式不正确")
    private String email;
    
    @NotBlank(message = "密码不能为空")
    @Size(min = 8, message = "密码长度至少为8位")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).*$", 
             message = "密码必须包含大小写字母和数字")
    private String password;
}
```

### 2. 敏感信息处理
- 密码必须加密存储
- 敏感信息脱敏显示
- 使用 HTTPS 传输敏感数据
- 定期更新安全策略

## 常用工具类

### 1. 字符串工具
```java
public class StringUtils {
    
    public static boolean isBlank(String str) {
        return str == null || str.trim().isEmpty();
    }
    
    public static boolean isNotBlank(String str) {
        return !isBlank(str);
    }
    
    public static String mask(String str, int start, int end) {
        if (isBlank(str) || start >= end || start >= str.length()) {
            return str;
        }
        StringBuilder sb = new StringBuilder(str);
        for (int i = start; i < Math.min(end, str.length()); i++) {
            sb.setCharAt(i, '*');
        }
        return sb.toString();
    }
}
```

### 2. 日期工具
```java
public class DateUtils {
    
    public static final String DEFAULT_DATE_PATTERN = "yyyy-MM-dd";
    public static final String DEFAULT_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
    
    public static String format(LocalDateTime dateTime, String pattern) {
        if (dateTime == null || isBlank(pattern)) {
            return null;
        }
        return dateTime.format(DateTimeFormatter.ofPattern(pattern));
    }
    
    public static LocalDateTime parse(String dateTimeStr, String pattern) {
        if (isBlank(dateTimeStr) || isBlank(pattern)) {
            return null;
        }
        return LocalDateTime.parse(dateTimeStr, DateTimeFormatter.ofPattern(pattern));
    }
}
```

## 项目结构建议

```
src/
├── main/
│   ├── java/
│   │   └── com/company/project/
│   │       ├── config/          # 配置类
│   │       ├── controller/      # 控制器
│   │       ├── service/         # 服务层
│   │       │   ├── impl/       # 服务实现
│   │       │   └── dto/        # 数据传输对象
│   │       ├── repository/      # 数据访问层
│   │       ├── entity/          # 实体类
│   │       ├── exception/       # 异常处理
│   │       ├── util/            # 工具类
│   │       └── Application.java # 启动类
│   └── resources/
│       ├── application.yml
│       ├── application-dev.yml
│       ├── application-prod.yml
│       └── logback-spring.xml
└── test/
    └── java/
        └── com/company/project/
            ├── controller/
            ├── service/
            └── repository/
```

## 依赖管理

### 1. 常用依赖版本管理
```xml
<properties>
    <maven.compiler.source>11</maven.compiler.source>
    <maven.compiler.target>11</maven.compiler.target>
    <spring.boot.version>2.7.0</spring.boot.version>
    <mysql.connector.version>8.0.33</mysql.connector.version>
    <mybatis.plus.version>3.5.3.1</mybatis.plus.version>
    <druid.version>1.2.18</druid.version>
    <hutool.version>5.8.20</hutool.version>
    <commons.lang3.version>3.12.0</commons.lang3.version>
</properties>
```

### 2. 依赖使用原则
- 优先使用 Spring Boot Starter
- 避免重复依赖
- 定期更新依赖版本
- 移除未使用的依赖