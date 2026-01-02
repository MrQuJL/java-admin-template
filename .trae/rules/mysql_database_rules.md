# MySQL 数据库规范

## 数据库设计规范

### 1. 命名规范

#### 1.1 表命名
- 使用小写字母，多个单词用下划线 `_` 分隔
- 使用复数形式表示集合
- 格式：`{业务模块}_{表名}`
- 示例：`user_profiles`, `order_items`, `product_categories`

#### 1.2 字段命名
- 使用小写字母，多个单词用下划线 `_` 分隔
- 避免使用保留字和关键字
- 布尔类型使用 `is_`, `has_`, `can_` 前缀
- 时间类型使用 `_at` 后缀
- 逻辑删除字段统一命名为 `deleted`
- 示例：`user_name`, `is_active`, `created_at`, `updated_at`, `deleted`

#### 1.3 索引命名
- 主键：`pk_{表名}`
- 唯一索引：`uk_{表名}_{字段名}`
- 普通索引：`idx_{表名}_{字段名}`
- 逻辑删除字段建议添加索引：`idx_{表名}_deleted`
- 示例：`pk_users`, `uk_users_email`, `idx_users_username`, `idx_users_deleted`

### 2. 数据类型规范

#### 2.1 整数类型选择
- 主键使用 `BIGINT` 类型，自增
- 年龄使用 `TINYINT UNSIGNED` 类型，范围 0-255
- 状态、标志位使用 `TINYINT` 类型，范围 -128 to 127
- 逻辑删除字段使用 `TINYINT` 类型，默认值 `0`

#### 2.2 字符串类型选择
- 标题、名称使用 `VARCHAR` 类型
- 文章内容、描述使用 `TEXT` 类型

#### 2.3 时间类型选择
- 创建时间、修改时间使用 `TIMESTAMP` 类型，默认值 `CURRENT_TIMESTAMP`

#### 2.4 小数类型选择
- 金额、精确计算使用 `DECIMAL` 类型