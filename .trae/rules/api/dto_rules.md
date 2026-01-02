# DTO类设计规则

## 1. 基本概念
DTO（Data Transfer Object）是用于在不同层之间传输数据的对象，主要用于接收前端请求参数和返回响应数据。

## 2. 命名规范

### 2.1 文件命名
- 使用大驼峰命名法
- 格式：`{业务模块}DTO.java` 或 `{业务模块}{操作}DTO.java`
- 示例：`UserDTO.java`, `UserCreateDTO.java`, `UserUpdateDTO.java`

### 2.2 包路径
- 接收请求参数的DTO：`com.company.project.dto`
- 返回响应数据的DTO/VO：`com.company.project.vo`

## 3. 类设计规范

### 3.1 注解使用
- 使用 `@Data` 注解自动生成 getter/setter 等方法
- 使用 `@NoArgsConstructor` 和 `@AllArgsConstructor` 提供构造方法
- 使用 `@Builder` 提供建造者模式支持

### 3.2 字段命名
- 使用小驼峰命名法
- 与前端参数名保持一致
- 避免使用保留字和关键字
- 布尔类型字段避免使用 `is` 前缀（避免序列化问题）

### 3.3 类型选择
- 基本类型使用包装类（如 `Integer` 而非 `int`）
- 时间类型统一使用 `LocalDateTime`
- 金额使用 `BigDecimal`

### 3.4 验证规则
- 必须添加 `javax.validation` 或 `jakarta.validation` 验证注解
- 常用验证注解：
  - `@NotNull`：字段不能为空
  - `@NotBlank`：字符串不能为空且长度大于0
  - `@Size`：字符串长度范围
  - `@Min`/`@Max`：数值范围
  - `@Pattern`：正则表达式验证
  - `@Email`：邮箱格式验证

### 3.5 字段注释
- 所有字段必须添加 JavaDoc 注释
- 注释清晰描述字段含义和格式要求

## 4. 设计原则

### 4.1 单一职责
- 每个DTO只负责一个业务场景
- 避免创建通用DTO

### 4.2 按需设计
- 只包含前端需要的字段
- 避免暴露敏感信息

### 4.3 分层设计
- `DTO`：用于接收前端请求参数
- `VO`：用于返回响应数据
- `Entity`：数据库实体，不直接暴露给前端

## 5. 示例代码

### 5.1 请求DTO示例（UserCreateDTO.java）
```java
package com.company.project.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserCreateDTO {
    
    /**
     * 用户名
     */
    @NotBlank(message = "用户名不能为空")
    @Size(min = 3, max = 20, message = "用户名长度必须在3-20个字符之间")
    private String username;
    
    /**
     * 密码
     */
    @NotBlank(message = "密码不能为空")
    @Size(min = 6, max = 32, message = "密码长度必须在6-32个字符之间")
    private String password;
    
    /**
     * 邮箱
     */
    @NotBlank(message = "邮箱不能为空")
    @Email(message = "邮箱格式不正确")
    private String email;
    
    /**
     * 手机号
     */
    @Size(max = 11, message = "手机号长度不正确")
    private String phone;
    
    /**
     * 昵称
     */
    @Size(max = 50, message = "昵称长度不能超过50个字符")
    private String nickname;
}
```

### 5.2 响应VO示例（UserVO.java）
```java
package com.company.project.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserVO {
    
    /**
     * 用户ID
     */
    private Long id;
    
    /**
     * 用户名
     */
    private String username;
    
    /**
     * 邮箱
     */
    private String email;
    
    /**
     * 手机号
     */
    private String phone;
    
    /**
     * 昵称
     */
    private String nickname;
    
    /**
     * 头像URL
     */
    private String avatar;
    
    /**
     * 状态：0-禁用，1-正常，2-锁定
     */
    private Integer status;
    
    /**
     * 创建时间
     */
    private LocalDateTime createdAt;
    
    /**
     * 更新时间
     */
    private LocalDateTime updatedAt;
}
```

## 6. 使用注意事项

### 6.1 避免直接使用Entity
- 禁止将数据库实体类直接作为DTO使用
- 防止数据库字段暴露给前端
- 便于后续数据库结构变更时不影响前端

### 6.2 字段转换
- 使用工具类（如 MapStruct）进行DTO与Entity之间的转换
- 避免手动编写大量转换代码

### 6.3 版本控制
- 当API版本变更时，创建新的DTO类
- 格式：`{业务模块}V2DTO.java` 或 `{业务模块}DTOV2.java`

### 6.4 文档生成
- 添加Swagger/OpenAPI注解，便于自动生成API文档
- 常用注解：`@ApiModel`, `@ApiModelProperty`

## 7. 最佳实践

- 按业务场景拆分DTO，避免大而全的DTO
- 合理使用验证注解，确保数据合法性
- 响应DTO只返回必要字段，保护敏感信息
- 使用建造者模式构建复杂DTO
- 保持DTO与前端接口文档的一致性