# Java 项目开发规范

## 1. 项目结构规范

```
src/
├── main/
│   ├── java/com/company/
│   │   ├── config/       # 配置类
│   │   ├── controller/   # API层
│   │   ├── service/      # 业务层
│   │   ├── mapper/       # 数据层（MyBatis-Plus Mapper）
│   │   ├── entity/       # 实体
│   │   ├── dto/          # 数据传输对象
│   │   ├── exception/    # 自定义异常
│   │   └── util/         # 工具类
│   └── resources/
│       ├── application.yml
│       └── application-dev.yml
└── test/                 # 测试代码
```

## 2. 依赖规范

```xml
<properties>
    <java.version>17</java.version>
    <spring.boot.version>3.2.0</spring.boot.version>
</properties>

<dependencies>
    <!-- Spring Boot 核心 -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
    <!-- MyBatis-Plus 核心依赖 -->
    <dependency>
        <groupId>com.baomidou</groupId>
        <artifactId>mybatis-plus-boot-starter</artifactId>
        <version>3.5.5</version>
    </dependency>
    <!-- 数据库驱动 -->
    <dependency>
        <groupId>com.mysql</groupId>
        <artifactId>mysql-connector-j</artifactId>
        <scope>runtime</scope>
    </dependency>
    <!-- Lombok 简化代码 -->
    <dependency>
        <groupId>org.projectlombok</groupId>
        <artifactId>lombok</artifactId>
        <scope>provided</scope>
    </dependency>
    <!-- 测试框架 -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-test</artifactId>
        <scope>test</scope>
    </dependency>
</dependencies>
```

## 3. 代码规范

### 命名规则
- **类**：PascalCase（`UserService`）
- **方法**：camelCase（`getUserById`）
- **常量**：UPPER_SNAKE_CASE（`MAX_RETRY`）
- **包**：全小写（`com.company.project`）
- **变量**：camelCase，见名知意（`userId` 而非 `uid`）

### 实体类设计（MyBatis-Plus）
```java
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("sys_user")  // 指定数据库表名
public class User {
    @TableId(type = IdType.AUTO)  // 主键策略：自增
    private Long id;
    
    @TableField("user_name")  // 字段映射（驼峰转下划线可省略）
    private String username;
    
    private String email;
    
    @TableField(fill = FieldFill.INSERT)  // 自动填充：插入时填充
    private LocalDateTime createTime;
    
    @TableField(fill = FieldFill.INSERT_UPDATE)  // 自动填充：插入和更新时填充
    private LocalDateTime updateTime;
    
    @TableLogic  // 逻辑删除标记
    private Integer deleted;
}
```

### Mapper 接口设计（MyBatis-Plus）
```java
/**
 * 用户Mapper接口
 * 继承BaseMapper即可获得CRUD功能
 */
public interface UserMapper extends BaseMapper<User> {
    // 可在此添加自定义SQL方法
    User selectByUsername(String username);
}
```

### Service 层设计（MyBatis-Plus）
```java
/**
 * 用户服务实现
 * 继承ServiceImpl获得高级CRUD功能
 */
@Service
public class UserService extends ServiceImpl<UserMapper, User> {
    
    public UserDTO findById(Long id) {
        User user = getById(id); // 继承自ServiceImpl的方法
        if (user == null) {
            throw new ResourceNotFoundException("用户不存在");
        }
        return toDTO(user);
    }
    
    private UserDTO toDTO(User user) {
        return UserDTO.builder()
            .id(user.getId())
            .username(user.getUsername())
            .email(user.getEmail())
            .build();
    }
}
```

## 4. 注释规范

### 类注释
```java
/**
 * 用户服务类
 * 处理用户相关业务逻辑
 * @author 开发者
 * @since 2026-01
 */
@Service
public class UserService {}
```

### 方法注释
```java
/**
 * 根据ID获取用户信息
 * @param id 用户ID
 * @return 用户DTO对象
 */
public UserDTO findById(Long id) {}
```

### 重要逻辑注释
```java
// 校验用户状态，仅激活用户可操作
if (!user.isActive()) {
    throw new BusinessException("用户未激活");
}
```

## 5. 日志规范

### 日志级别
- `ERROR`：系统错误，需立即处理
- `WARN`：警告信息，需关注
- `INFO`：重要业务流程
- `DEBUG`：调试信息（生产环境关闭）

### 日志使用示例
```java
@Slf4j
@Service
public class OrderService {
    public void createOrder(OrderDTO orderDTO) {
        log.info("创建订单，用户ID:{}, 商品ID:{}", 
                 orderDTO.getUserId(), orderDTO.getProductId());
        try {
            // 业务逻辑
            log.info("订单创建成功，订单号:{}", order.getOrderNo());
        } catch (Exception e) {
            log.error("订单创建失败，参数:{}", orderDTO, e);
            throw new BusinessException("订单创建失败");
        }
    }
}
```

### 注意事项
- 禁止直接拼接字符串：使用 `log.info("用户:{}", name)` 而非 `log.info("用户:" + name)`
- 敏感信息不记录：密码、身份证号等

## 6. API 设计规范

### RESTful 接口设计
| HTTP方法 | 路径                     | 功能          |
|---------|--------------------------|---------------|
| GET     | /api/v1/users/{id}       | 查询单个用户  |
| GET     | /api/v1/users            | 查询用户列表  |
| POST    | /api/v1/users            | 创建用户      |
| PUT     | /api/v1/users/{id}       | 更新用户      |
| DELETE  | /api/v1/users/{id}       | 删除用户      |

### 统一响应格式
```java
@Data
public class ApiResponse<T> {
    private Integer code;  // 状态码
    private String msg;    // 消息
    private T data;        // 数据
    
    public static <T> ApiResponse<T> success(T data) {
        ApiResponse<T> resp = new ApiResponse<>();
        resp.code = 200;
        resp.msg = "success";
        resp.data = data;
        return resp;
    }
    
    public static <T> ApiResponse<T> error(int code, String msg) {
        ApiResponse<T> resp = new ApiResponse<>();
        resp.code = code;
        resp.msg = msg;
        return resp;
    }
}
```

### 接口示例
```java
@RestController
@RequestMapping("/api/v1/users")
@Validated
public class UserController {
    private final UserService userService;
    
    public UserController(UserService userService) {
        this.userService = userService;
    }
    
    @GetMapping("/{id}")
    public ApiResponse<UserDTO> getUser(@PathVariable @Min(1) Long id) {
        return ApiResponse.success(userService.findById(id));
    }
    
    @PostMapping
    public ApiResponse<UserDTO> createUser(@Valid @RequestBody UserCreateDTO createDTO) {
        return ApiResponse.success(userService.create(createDTO));
    }
}
```

### 异常处理
```java
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiResponse<Void> handleResourceNotFound(ResourceNotFoundException e) {
        return ApiResponse.error(404, e.getMessage());
    }
    
    @ExceptionHandler(BusinessException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponse<Void> handleBusinessException(BusinessException e) {
        return ApiResponse.error(400, e.getMessage());
    }
}
```