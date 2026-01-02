# Java 项目规则（精简版）

## 项目结构规范

```
src/
├── main/
│   ├── java/
│   │   └── com/company/project/
│   │       ├── config/          # 配置类
│   │       ├── controller/      # 控制器层
│   │       ├── service/         # 服务层
│   │       ├── mapper/          # 数据访问层
│   │       ├── entity/          # 实体类
│   │       ├── dto/             # 传输对象
│   │       ├── exception/       # 异常处理
│   │       ├── util/            # 工具类
│   │       └── Application.java # 启动类
│   └── resources/
│       ├── application.yml
│       ├── application-dev.yml
│       ├── application-prod.yml
└── test/
```

## 代码规范

### 命名规范
- **类名**: PascalCase，如 `UserService`, `OrderController`
- **方法名**: camelCase，如 `getUserById()`, `calculateTotal()`
- **常量**: UPPER_SNAKE_CASE，如 `MAX_RETRY_COUNT`
- **包名**: 小写，反向域名格式，如 `com.company.project`

### 代码结构
```java
@Slf4j
@Service
public class UserService {
    
    private static final int MAX_USERS = 100;
    
    private final UserRepository userRepository;
    
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    
    public UserDTO getUserById(Long userId) {
        if (userId == null) {
            throw new IllegalArgumentException("用户ID不能为空");
        }
        
        return userRepository.findById(userId)
            .map(this::convertToDTO)
            .orElseThrow(() -> new ResourceNotFoundException("用户不存在"));
    }
    
    private UserDTO convertToDTO(User user) {
        return UserDTO.builder()
            .id(user.getId())
            .username(user.getUsername())
            .email(user.getEmail())
            .build();
    }
}
```

## 注释规范

### 类注释
```java
/**
 * 用户服务类
 * 负责用户相关的业务逻辑处理
 * 
 * @author developer
 * @since 1.0.0
 */
@Service
public class UserService {
    // 类实现
}
```

### 方法注释
```java
/**
 * 根据用户ID获取用户信息
 * 
 * @param userId 用户ID，不能为null
 * @return 用户DTO对象
 * @throws ResourceNotFoundException 如果用户不存在
 */
public UserDTO getUserById(Long userId) {
    // 方法实现
}
```

### 复杂逻辑注释
```java
public void processOrder(OrderDTO order) {
    // 验证订单状态
    if (order.getStatus() != OrderStatus.PENDING) {
        throw new BusinessException("订单状态不正确");
    }
    
    // 计算订单总金额（包含税费和折扣）
    BigDecimal totalAmount = order.getItems().stream()
        .map(item -> item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
        .reduce(BigDecimal.ZERO, BigDecimal::add)
        .add(order.getTax())
        .subtract(order.getDiscount());
    
    // 更新订单状态为已处理
    order.setStatus(OrderStatus.PROCESSED);
    order.setTotalAmount(totalAmount);
}
```

## 日志规范

### 日志级别使用
- **ERROR**: 系统错误，需要立即处理
- **WARN**: 潜在问题，需要关注
- **INFO**: 重要业务流程
- **DEBUG**: 调试信息

### 日志格式
```java
@Slf4j
@Service
public class OrderService {
    
    public OrderDTO createOrder(CreateOrderRequest request) {
        log.info("开始创建订单，用户ID: {}", request.getUserId());
        
        try {
            // 业务逻辑
            Order order = orderRepository.save(request.toEntity());
            log.info("订单创建成功，订单ID: {}", order.getId());
            return convertToDTO(order);
            
        } catch (DataIntegrityViolationException e) {
            log.warn("订单创建失败，数据完整性错误: {}", e.getMessage());
            throw new BusinessException("订单创建失败");
            
        } catch (Exception e) {
            log.error("订单创建失败，用户ID: {}", request.getUserId(), e);
            throw new BusinessException("订单创建失败");
        }
    }
}
```

### 日志最佳实践
```java
// 好的做法：使用占位符
log.info("用户 {} 登录成功", username);

// 不好的做法：字符串拼接
log.info("用户 " + username + " 登录成功");

// 敏感信息不要记录
log.info("用户登录，用户名: {}", username); // OK
log.info("用户登录，密码: {}", password);   // 错误！
```

## API 设计规范

### RESTful API 设计
```java
@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUser(@PathVariable Long id) {
        UserDTO user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }
    
    @PostMapping
    public ResponseEntity<UserDTO> createUser(@Valid @RequestBody CreateUserRequest request) {
        UserDTO user = userService.createUser(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable Long id, 
                                             @Valid @RequestBody UpdateUserRequest request) {
        UserDTO user = userService.updateUser(id, request);
        return ResponseEntity.ok(user);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
```

### 统一响应格式
```java
@Data
@Builder
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

### 请求参数验证
```java
@Data
public class CreateUserRequest {
    
    @NotBlank(message = "用户名不能为空")
    @Size(min = 3, max = 20, message = "用户名长度必须在3-20个字符之间")
    private String username;
    
    @NotBlank(message = "邮箱不能为空")
    @Email(message = "邮箱格式不正确")
    private String email;
    
    @NotBlank(message = "密码不能为空")
    @Size(min = 8, message = "密码长度至少为8位")
    private String password;
}
```

### 异常处理
```java
@RestControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handleResourceNotFound(ResourceNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(ApiResponse.error("RESOURCE_NOT_FOUND", e.getMessage()));
    }
    
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ApiResponse<Void>> handleBusinessException(BusinessException e) {
        return ResponseEntity.badRequest()
            .body(ApiResponse.error("BUSINESS_ERROR", e.getMessage()));
    }
    
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleSystemException(Exception e) {
        log.error("系统异常", e);
        return ResponseEntity.internalServerError()
            .body(ApiResponse.error("SYSTEM_ERROR", "系统繁忙，请稍后重试"));
    }
}
```

### API 文档
```java
@RestController
@RequestMapping("/api/v1/users")
@Tag(name = "用户管理", description = "用户相关接口")
public class UserController {
    
    @GetMapping("/{id}")
    @Operation(summary = "获取用户信息", description = "根据用户ID获取详细信息")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "成功"),
        @ApiResponse(responseCode = "404", description = "用户不存在")
    })
    public ResponseEntity<UserDTO> getUser(@PathVariable Long id) {
        // 方法实现
    }
}
```