# VO类设计规则

## 1. 基本概念
VO（Value Object）是用于在不同层之间传输数据的对象，主要用于封装和返回响应数据给前端，不包含业务逻辑，只用于数据展示。

## 2. 命名规范

### 2.1 文件命名
- 使用大驼峰命名法
- 格式：`{业务模块}VO.java` 或 `{业务模块}{结果}VO.java`
- 示例：`UserVO.java`, `UserListVO.java`, `ProductDetailVO.java`

### 2.2 包路径
- 所有VO类统一放在：`com.company.project.vo`

## 3. 类设计规范

### 3.1 注解使用
- 使用 `@Data` 注解自动生成 getter/setter 等方法
- 使用 `@NoArgsConstructor` 和 `@AllArgsConstructor` 提供构造方法
- 使用 `@Builder` 提供建造者模式支持

### 3.2 字段命名
- 使用小驼峰命名法
- 与前端展示字段名保持一致
- 避免使用保留字和关键字
- 布尔类型字段避免使用 `is` 前缀（避免序列化问题）

### 3.3 类型选择
- 基本类型使用包装类（如 `Integer` 而非 `int`）
- 时间类型统一使用 `LocalDateTime`
- 金额使用 `BigDecimal`
- 集合类型使用接口（如 `List` 而非 `ArrayList`）

### 3.4 字段设计
- 只包含前端需要展示的字段
- 敏感字段（如密码）禁止出现在VO中
- 可包含计算字段或聚合字段（如 `totalAmount`, `isFavorite`）

### 3.5 字段注释
- 所有字段必须添加 JavaDoc 注释
- 注释清晰描述字段含义和展示格式

## 4. 设计原则

### 4.1 数据封装
- VO是只读的数据容器，不暴露内部状态修改方法
- 保护业务数据，只返回必要信息

### 4.2 分层设计
- `Entity`：数据库实体，不直接暴露给前端
- `DTO`：用于接收前端请求参数
- `VO`：用于返回响应数据给前端

### 4.3 单一职责
- 每个VO只负责一个响应场景
- 避免创建通用VO

### 4.4 按需设计
- 只包含前端需要的字段
- 避免返回冗余数据

## 5. 示例代码

### 5.1 基本VO示例（UserVO.java）
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

### 5.2 列表VO示例（UserListVO.java）
```java
package com.company.project.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserListVO {
    
    /**
     * 总记录数
     */
    private Long total;
    
    /**
     * 用户列表
     */
    private List<UserVO> users;
    
    /**
     * 当前页码
     */
    private Integer page;
    
    /**
     * 每页大小
     */
    private Integer size;
}
```

### 5.3 详情VO示例（ProductDetailVO.java）
```java
package com.company.project.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDetailVO {
    
    /**
     * 商品ID
     */
    private Long id;
    
    /**
     * 商品名称
     */
    private String name;
    
    /**
     * 商品价格
     */
    private BigDecimal price;
    
    /**
     * 商品库存
     */
    private Integer stock;
    
    /**
     * 商品描述
     */
    private String description;
    
    /**
     * 商品图片列表
     */
    private List<String> images;
    
    /**
     * 商品分类名称
     */
    private String categoryName;
    
    /**
     * 是否为推荐商品
     */
    private Boolean isRecommended;
    
    /**
     * 创建时间
     */
    private LocalDateTime createdAt;
}
```

## 6. 使用注意事项

### 6.1 避免直接使用Entity
- 禁止将数据库实体类直接作为VO返回给前端
- 防止数据库字段暴露给前端
- 便于后续数据库结构变更时不影响前端

### 6.2 字段转换
- 使用工具类（如 MapStruct）进行Entity与VO之间的转换
- 避免手动编写大量转换代码
- 可使用BeanUtils.copyProperties进行简单转换

### 6.3 版本控制
- 当API版本变更时，创建新的VO类
- 格式：`{业务模块}V2VO.java` 或 `{业务模块}VOV2.java`

### 6.4 文档生成
- 添加Swagger/OpenAPI注解，便于自动生成API文档
- 常用注解：`@ApiModel`, `@ApiModelProperty`

### 6.5 空值处理
- 合理处理空值，避免前端展示异常
- 可使用默认值或明确的null表示

## 7. 最佳实践

- 按响应场景拆分VO，避免大而全的VO
- 只返回前端需要的字段，保护敏感信息
- 使用建造者模式构建复杂VO
- 保持VO与前端接口文档的一致性
- 合理使用包装类，避免NPE问题
- 为集合字段提供合理的默认值（如空列表而非null）
- 包含必要的元数据（如分页信息、总数等）
- 使用统一的响应格式包装VO