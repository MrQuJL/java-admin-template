# MySQL 数据库规范

## 数据库设计规范

### 1. 命名规范

#### 1.1 数据库命名
- 使用小写字母，多个单词用下划线 `_` 分隔
- 格式：`{项目名}_{环境}_{业务模块}`
- 示例：`ecommerce_dev_order`, `ecommerce_prod_user`

```sql
-- 好的命名
CREATE DATABASE ecommerce_dev_order;
CREATE DATABASE ecommerce_prod_user;

-- 不好的命名
CREATE DATABASE EcommerceDevOrder;
CREATE DATABASE ecommercedevorder;
```

#### 1.2 表命名
- 使用小写字母，多个单词用下划线 `_` 分隔
- 使用复数形式表示集合
- 格式：`{业务模块}_{表名}`
- 示例：`user_profiles`, `order_items`, `product_categories`

```sql
-- 好的命名
CREATE TABLE user_profiles (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) NOT NULL
);

-- 不好的命名
CREATE TABLE userprofile (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) NOT NULL
);
```

#### 1.3 字段命名
- 使用小写字母，多个单词用下划线 `_` 分隔
- 避免使用保留字和关键字
- 布尔类型使用 `is_`, `has_`, `can_` 前缀
- 时间类型使用 `_at` 后缀
- 示例：`user_name`, `is_active`, `created_at`, `updated_at`

```sql
-- 好的命名
CREATE TABLE users (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_name VARCHAR(50) NOT NULL,
    email VARCHAR(100) NOT NULL,
    is_active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- 不好的命名
CREATE TABLE users (
    ID BIGINT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) NOT NULL,
    userEmail VARCHAR(100) NOT NULL,
    active BOOLEAN DEFAULT TRUE,
    createTime TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updateTime TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);
```

#### 1.4 索引命名
- 主键：`pk_{表名}`
- 唯一索引：`uk_{表名}_{字段名}`
- 普通索引：`idx_{表名}_{字段名}`
- 复合索引：`idx_{表名}_{字段1}_{字段2}`
- 全文索引：`ft_{表名}_{字段名}`

```sql
-- 主键索引
ALTER TABLE users ADD CONSTRAINT pk_users PRIMARY KEY (id);

-- 唯一索引
ALTER TABLE users ADD CONSTRAINT uk_users_email UNIQUE KEY (email);

-- 普通索引
ALTER TABLE users ADD INDEX idx_users_username (username);

-- 复合索引
ALTER TABLE orders ADD INDEX idx_orders_user_status (user_id, status);
```

### 2. 数据类型规范

#### 2.1 整数类型选择
| 类型 | 字节 | 范围 | 使用场景 |
|------|------|------|----------|
| TINYINT | 1 | -128 to 127 | 状态、布尔值 |
| SMALLINT | 2 | -32,768 to 32,767 | 小范围整数 |
| MEDIUMINT | 3 | -8,388,608 to 8,388,607 | 中等范围整数 |
| INT | 4 | -2,147,483,648 to 2,147,483,647 | 普通整数 |
| BIGINT | 8 | -9,223,372,036,854,775,808 to 9,223,372,036,854,775,807 | 大整数、主键 |

```sql
-- 推荐用法
CREATE TABLE users (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,  -- 主键使用 BIGINT
    age TINYINT UNSIGNED,                   -- 年龄使用 TINYINT
    status TINYINT DEFAULT 1,               -- 状态使用 TINYINT
    score MEDIUMINT                         -- 分数使用 MEDIUMINT
);
```

#### 2.2 字符串类型选择
| 类型 | 最大长度 | 特点 | 使用场景 |
|------|----------|------|----------|
| CHAR | 255 | 定长 | 固定长度代码、MD5值 |
| VARCHAR | 65,535 | 变长 | 普通字符串 |
| TEXT | 65,535 | 大文本 | 文章内容、描述 |
| MEDIUMTEXT | 16,777,215 | 中等文本 | 较长内容 |
| LONGTEXT | 4,294,967,295 | 超大文本 | 非常长的内容 |

```sql
-- 推荐用法
CREATE TABLE articles (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(200) NOT NULL,           -- 标题使用 VARCHAR
    summary VARCHAR(500),                  -- 摘要使用 VARCHAR
    content TEXT,                          -- 内容使用 TEXT
    md5_hash CHAR(32),                     -- MD5 使用 CHAR
    seo_keywords VARCHAR(1000)             -- SEO关键词使用 VARCHAR
);
```

#### 2.3 时间类型选择
| 类型 | 范围 | 精度 | 使用场景 |
|------|------|------|----------|
| DATE | 1000-01-01 to 9999-12-31 | 天 | 生日、到期日 |
| TIME | -838:59:59 to 838:59:59 | 秒 | 时间间隔 |
| DATETIME | 1000-01-01 00:00:00 to 9999-12-31 23:59:59 | 秒 | 创建时间、修改时间 |
| TIMESTAMP | 1970-01-01 00:00:01 UTC to 2038-01-19 03:14:07 UTC | 秒 | 时间戳 |

```sql
-- 推荐用法
CREATE TABLE users (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    birth_date DATE,                       -- 生日使用 DATE
    work_start_time TIME,                  -- 工作时间使用 TIME
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);
```

#### 2.4 小数类型选择
| 类型 | 范围 | 精度 | 使用场景 |
|------|------|------|----------|
| FLOAT | 3.4E-38 to 3.4E+38 | 7位小数 | 科学计算 |
| DOUBLE | 1.7E-308 to 1.7E+308 | 15位小数 | 高精度计算 |
| DECIMAL | 依赖M和D | 精确 | 金额、精确计算 |

```sql
-- 推荐用法
CREATE TABLE orders (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    amount DECIMAL(10, 2) NOT NULL,        -- 金额使用 DECIMAL
    tax_rate DECIMAL(5, 4) DEFAULT 0.0000, -- 税率使用 DECIMAL
    discount_percent DECIMAL(5, 2) DEFAULT 0.00, -- 折扣百分比
    latitude DOUBLE,                       -- 纬度可以使用 DOUBLE
    longitude DOUBLE                       -- 经度可以使用 DOUBLE
);
```

### 3. 表设计规范

#### 3.1 主键设计
```sql
-- 推荐：使用 BIGINT 自增主键
CREATE TABLE users (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) NOT NULL,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP
);

-- 不推荐：使用 UUID 作为主键
CREATE TABLE users (
    id VARCHAR(36) PRIMARY KEY,
    username VARCHAR(50) NOT NULL,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP
);
```

#### 3.2 外键设计
```sql
-- 推荐：添加外键约束并建立索引
CREATE TABLE orders (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL,
    order_no VARCHAR(32) NOT NULL,
    amount DECIMAL(10, 2) NOT NULL,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    
    INDEX idx_orders_user_id (user_id),
    CONSTRAINT fk_orders_user_id FOREIGN KEY (user_id) REFERENCES users(id)
);

-- 复合外键示例
CREATE TABLE order_items (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    order_id BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    quantity INT NOT NULL,
    price DECIMAL(10, 2) NOT NULL,
    
    INDEX idx_order_items_order_id (order_id),
    INDEX idx_order_items_product_id (product_id),
    
    CONSTRAINT fk_order_items_order_id FOREIGN KEY (order_id) REFERENCES orders(id),
    CONSTRAINT fk_order_items_product_id FOREIGN KEY (product_id) REFERENCES products(id),
    
    -- 防止重复订单项
    UNIQUE KEY uk_order_items_order_product (order_id, product_id)
);
```

#### 3.3 字段约束
```sql
CREATE TABLE users (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) NOT NULL COMMENT '用户名',
    email VARCHAR(100) NOT NULL COMMENT '邮箱',
    phone VARCHAR(20) COMMENT '手机号',
    status TINYINT NOT NULL DEFAULT 1 COMMENT '状态：1-正常，0-禁用',
    balance DECIMAL(10, 2) NOT NULL DEFAULT 0.00 COMMENT '余额',
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    
    -- 唯一约束
    UNIQUE KEY uk_users_username (username),
    UNIQUE KEY uk_users_email (email),
    
    -- 普通索引
    INDEX idx_users_status (status),
    INDEX idx_users_created_at (created_at),
    
    -- 检查约束
    CONSTRAINT chk_users_balance CHECK (balance >= 0),
    CONSTRAINT chk_users_status CHECK (status IN (0, 1))
);
```

## 索引设计规范

### 1. 索引类型选择

#### 1.1 普通索引
```sql
-- 单列索引
ALTER TABLE users ADD INDEX idx_users_username (username);

-- 复合索引（遵循最左前缀原则）
ALTER TABLE orders ADD INDEX idx_orders_user_status (user_id, status, created_at);
```

#### 1.2 唯一索引
```sql
-- 单列唯一索引
ALTER TABLE users ADD UNIQUE KEY uk_users_email (email);

-- 复合唯一索引
ALTER TABLE user_roles ADD UNIQUE KEY uk_user_roles (user_id, role_id);
```

#### 1.3 全文索引
```sql
-- MySQL 5.6+ 支持全文索引
ALTER TABLE articles ADD FULLTEXT INDEX ft_articles_title_content (title, content);

-- 使用全文索引
SELECT * FROM articles 
WHERE MATCH(title, content) AGAINST('数据库规范' IN NATURAL LANGUAGE MODE);
```

### 2. 索引设计原则

#### 2.1 适合创建索引的场景
```sql
-- 1. 主键和外键必须创建索引
ALTER TABLE orders ADD PRIMARY KEY (id);
ALTER TABLE order_items ADD INDEX idx_order_items_order_id (order_id);

-- 2. 经常用于 WHERE 条件的字段
ALTER TABLE users ADD INDEX idx_users_status (status);
ALTER TABLE orders ADD INDEX idx_orders_created_at (created_at);

-- 3. 经常用于 JOIN 的字段
ALTER TABLE order_items ADD INDEX idx_order_items_product_id (product_id);

-- 4. 经常用于 ORDER BY 的字段
ALTER TABLE products ADD INDEX idx_products_price (price);

-- 5. 经常用于 GROUP BY 的字段
ALTER TABLE orders ADD INDEX idx_orders_user_id (user_id);
```

#### 2.2 不适合创建索引的场景
```sql
-- 1. 数据量小的表不需要索引
-- 2. 经常更新的字段不适合建索引
-- 3. 区分度低的字段不适合建索引
-- 如：性别、状态等只有几个值的字段

-- 不好的示例：状态字段只有 0 和 1
ALTER TABLE users ADD INDEX idx_users_status (status);  -- 效果不佳
```

### 3. 复合索引设计

#### 3.1 最左前缀原则
```sql
-- 创建复合索引
ALTER TABLE orders ADD INDEX idx_orders_user_status_date (user_id, status, created_at);

-- 有效的查询（遵循最左前缀原则）
SELECT * FROM orders WHERE user_id = 1001;
SELECT * FROM orders WHERE user_id = 1001 AND status = 1;
SELECT * FROM orders WHERE user_id = 1001 AND status = 1 AND created_at > '2023-01-01';

-- 无效的查询（不遵循最左前缀原则）
SELECT * FROM orders WHERE status = 1;  -- 无法使用索引
SELECT * FROM orders WHERE created_at > '2023-01-01';  -- 无法使用索引
```

#### 3.2 索引顺序优化
```sql
-- 根据查询频率和选择性安排索引顺序
-- 高选择性、高查询频率的字段放在前面

-- 推荐：用户ID选择性高，放在前面
ALTER TABLE orders ADD INDEX idx_orders_user_status_date (user_id, status, created_at);

-- 不推荐：状态字段选择性低，放在前面
ALTER TABLE orders ADD INDEX idx_orders_status_user_date (status, user_id, created_at);
```

## SQL 编写规范

### 1. 基本 SQL 规范

#### 1.1 查询语句
```sql
-- 推荐写法
SELECT 
    u.id,
    u.username,
    u.email,
    u.created_at
FROM users u
WHERE u.status = 1
    AND u.created_at >= '2023-01-01'
ORDER BY u.created_at DESC
LIMIT 10;

-- 不推荐写法
SELECT * FROM users WHERE status=1 and created_at>='2023-01-01' order by created_at desc limit 10;
```

#### 1.2 插入语句
```sql
-- 推荐写法：指定字段名
INSERT INTO users (username, email, phone, created_at)
VALUES ('john_doe', 'john@example.com', '13800138000', NOW());

-- 不推荐写法：不指定字段名
INSERT INTO users VALUES (NULL, 'john_doe', 'john@example.com', '13800138000', 1, NOW(), NOW());
```

#### 1.3 更新语句
```sql
-- 推荐写法：使用 LIMIT 和 WHERE 条件
UPDATE users 
SET status = 0, updated_at = NOW()
WHERE id = 1001 
LIMIT 1;

-- 不推荐写法：不使用 WHERE 条件
UPDATE users SET status = 0;
```

### 2. JOIN 使用规范

#### 2.1 JOIN 类型选择
```sql
-- INNER JOIN：只返回匹配的行
SELECT u.id, u.username, o.order_no, o.amount
FROM users u
INNER JOIN orders o ON u.id = o.user_id
WHERE u.status = 1;

-- LEFT JOIN：返回左表所有行
SELECT u.id, u.username, COUNT(o.id) as order_count
FROM users u
LEFT JOIN orders o ON u.id = o.user_id AND o.status = 1
WHERE u.status = 1
GROUP BY u.id;

-- RIGHT JOIN：很少使用，可以用 LEFT JOIN 替代
-- FULL JOIN：MySQL 不支持，可以用 UNION 替代
```

#### 2.2 JOIN 优化
```sql
-- 推荐：小表驱动大表
-- users 表较小，orders 表较大
SELECT u.id, u.username, o.order_no
FROM users u
INNER JOIN orders o ON u.id = o.user_id
WHERE u.status = 1;

-- 确保 JOIN 字段有索引
ALTER TABLE orders ADD INDEX idx_orders_user_id (user_id);
```

### 3. 子查询优化

#### 3.1 避免相关子查询
```sql
-- 不推荐：相关子查询，性能差
SELECT u.*, 
    (SELECT COUNT(*) FROM orders o WHERE o.user_id = u.id) as order_count
FROM users u;

-- 推荐：使用 JOIN 替代
SELECT u.id, u.username, COUNT(o.id) as order_count
FROM users u
LEFT JOIN orders o ON u.id = o.user_id
GROUP BY u.id;
```

#### 3.2 使用 EXISTS 替代 IN
```sql
-- 推荐：使用 EXISTS
SELECT u.*
FROM users u
WHERE EXISTS (
    SELECT 1 FROM orders o WHERE o.user_id = u.id AND o.amount > 1000
);

-- 不推荐：使用 IN 可能导致全表扫描
SELECT u.*
FROM users u
WHERE u.id IN (
    SELECT DISTINCT user_id FROM orders WHERE amount > 1000
);
```

## 事务和锁规范

### 1. 事务使用规范

#### 1.1 事务基本使用
```sql
-- 开启事务
START TRANSACTION;

-- 或者使用 BEGIN
BEGIN;

try {
    -- 扣减库存
    UPDATE products 
    SET stock = stock - 1 
    WHERE id = 1001 AND stock > 0;
    
    -- 创建订单
    INSERT INTO orders (user_id, product_id, quantity, amount)
    VALUES (1001, 1001, 1, 99.99);
    
    -- 提交事务
    COMMIT;
} catch (Exception e) {
    -- 回滚事务
    ROLLBACK;
    throw e;
}
```

#### 1.2 事务隔离级别
```sql
-- 查看当前隔离级别
SELECT @@transaction_isolation;

-- 设置事务隔离级别
SET SESSION TRANSACTION ISOLATION LEVEL READ COMMITTED;

-- 事务隔离级别选择：
-- READ UNCOMMITTED：最低级别，可能产生脏读
-- READ COMMITTED：避免脏读，可能产生不可重复读
-- REPEATABLE READ：避免脏读和不可重复读，可能产生幻读（MySQL默认）
-- SERIALIZABLE：最高级别，避免所有并发问题
```

### 2. 锁机制

#### 2.1 行锁和表锁
```sql
-- 行锁（InnoDB）
SELECT * FROM users WHERE id = 1001 FOR UPDATE;

-- 表锁
LOCK TABLES users WRITE, orders READ;
-- 执行操作...
UNLOCK TABLES;
```

#### 2.2 乐观锁实现
```sql
-- 添加版本号字段
ALTER TABLE products ADD COLUMN version INT DEFAULT 1;

-- 乐观锁更新
UPDATE products 
SET stock = stock - 1, version = version + 1
WHERE id = 1001 AND version = 5;

-- 检查更新是否成功
IF ROW_COUNT() = 0 THEN
    -- 版本号不匹配，更新失败
    SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = '数据已被其他事务修改';
END IF;
```

## 性能优化规范

### 1. 查询优化

#### 1.1 使用 EXPLAIN 分析查询
```sql
-- 使用 EXPLAIN 分析查询计划
EXPLAIN SELECT u.*, COUNT(o.id) as order_count
FROM users u
LEFT JOIN orders o ON u.id = o.user_id
WHERE u.status = 1
GROUP BY u.id;

-- 使用 EXPLAIN FORMAT=JSON 获取更详细信息
EXPLAIN FORMAT=JSON SELECT * FROM users WHERE id = 1001;
```

#### 1.2 避免全表扫描
```sql
-- 不推荐：不使用索引
SELECT * FROM users WHERE YEAR(created_at) = 2023;

-- 推荐：使用索引范围查询
SELECT * FROM users 
WHERE created_at >= '2023-01-01' AND created_at < '2024-01-01';
```

#### 1.3 分页查询优化
```sql
-- 不推荐：深度分页性能差
SELECT * FROM orders ORDER BY id DESC LIMIT 100000, 10;

-- 推荐：使用游标分页
SELECT * FROM orders 
WHERE id < 999000 
ORDER BY id DESC 
LIMIT 10;
```

### 2. 表结构优化

#### 2.1 字段类型优化
```sql
-- 推荐：使用合适的字段类型
CREATE TABLE users (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) NOT NULL,      -- 用户名最长50字符
    age TINYINT UNSIGNED,               -- 年龄使用 TINYINT 足够
    status TINYINT DEFAULT 1,           -- 状态使用 TINYINT
    balance DECIMAL(10, 2) NOT NULL     -- 金额使用 DECIMAL
);

-- 不推荐：使用过大的字段类型
CREATE TABLE users (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(255) NOT NULL,     -- 过长
    age INT,                            -- 过大
    status INT DEFAULT 1,               -- 过大
    balance DOUBLE NOT NULL             -- 精度问题
);
```

#### 2.2 表分区
```sql
-- 按时间分区
CREATE TABLE orders (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL,
    amount DECIMAL(10, 2) NOT NULL,
    created_at DATETIME NOT NULL,
    
    INDEX idx_orders_user_id (user_id),
    INDEX idx_orders_created_at (created_at)
) 
PARTITION BY RANGE (YEAR(created_at)) (
    PARTITION p2022 VALUES LESS THAN (2023),
    PARTITION p2023 VALUES LESS THAN (2024),
    PARTITION p2024 VALUES LESS THAN (2025),
    PARTITION p_future VALUES LESS THAN MAXVALUE
);
```

### 3. 连接池和并发优化

#### 3.1 连接池配置
```properties
# 数据库连接池配置（以 Druid 为例）
spring.datasource.druid.initial-size=10
spring.datasource.druid.min-idle=10
spring.datasource.druid.max-active=100
spring.datasource.druid.max-wait=60000
spring.datasource.druid.time-between-eviction-runs-millis=60000
spring.datasource.druid.min-evictable-idle-time-millis=300000
spring.datasource.druid.validation-query=SELECT 1 FROM DUAL
spring.datasource.druid.test-while-idle=true
spring.datasource.druid.test-on-borrow=false
spring.datasource.druid.test-on-return=false
```

#### 3.2 读写分离
```sql
-- 主库（写操作）
-- 配置在 application-master.yml
spring.datasource.url=jdbc:mysql://master-host:3306/ecommerce?useSSL=false&serverTimezone=UTC

-- 从库（读操作）
-- 配置在 application-slave.yml
spring.datasource.url=jdbc:mysql://slave-host:3306/ecommerce?useSSL=false&serverTimezone=UTC
```

## 安全规范

### 1. 权限管理

#### 1.1 用户权限分配
```sql
-- 创建只读用户
CREATE USER 'read_user'@'%' IDENTIFIED BY 'strong_password';
GRANT SELECT ON ecommerce.* TO 'read_user'@'%';

-- 创建业务用户（读写权限）
CREATE USER 'app_user'@'%' IDENTIFIED BY 'strong_password';
GRANT SELECT, INSERT, UPDATE, DELETE ON ecommerce.* TO 'app_user'@'%';

-- 创建管理员用户
CREATE USER 'admin'@'%' IDENTIFIED BY 'very_strong_password';
GRANT ALL PRIVILEGES ON ecommerce.* TO 'admin'@'%';

-- 刷新权限
FLUSH PRIVILEGES;
```

#### 1.2 最小权限原则
```sql
-- 按业务模块分配权限
-- 订单模块用户
GRANT SELECT, INSERT, UPDATE ON ecommerce.orders TO 'order_service'@'%';
GRANT SELECT, INSERT, UPDATE, DELETE ON ecommerce.order_items TO 'order_service'@'%';

-- 用户模块用户
GRANT SELECT, INSERT, UPDATE ON ecommerce.users TO 'user_service'@'%';
GRANT SELECT ON ecommerce.user_profiles TO 'user_service'@'%';
```

### 2. 数据安全

#### 2.1 敏感数据加密
```sql
-- 使用 AES 加密敏感数据
-- 加密函数
INSERT INTO users (username, email, phone_encrypted)
VALUES ('john_doe', 'john@example.com', AES_ENCRYPT('13800138000', 'encryption_key'));

-- 解密函数
SELECT username, email, AES_DECRYPT(phone_encrypted, 'encryption_key') as phone
FROM users WHERE id = 1001;
```

#### 2.2 数据脱敏
```sql
-- 创建脱敏视图
CREATE VIEW user_public_view AS
SELECT 
    id,
    username,
    -- 邮箱脱敏
    CONCAT(LEFT(email, 3), '***', RIGHT(email, 10)) as email,
    -- 手机号脱敏
    CONCAT(LEFT(AES_DECRYPT(phone_encrypted, 'encryption_key'), 3), 
           '****', 
           RIGHT(AES_DECRYPT(phone_encrypted, 'encryption_key'), 4)) as phone,
    status,
    created_at
FROM users;
```

### 3. SQL 注入防护

#### 3.1 使用参数化查询
```java
// Java 中使用 PreparedStatement
String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
PreparedStatement pstmt = connection.prepareStatement(sql);
pstmt.setString(1, username);
pstmt.setString(2, password);
ResultSet rs = pstmt.executeQuery();
```

#### 3.2 输入验证
```sql
-- 存储参数验证
DELIMITER $$
CREATE PROCEDURE create_user(
    IN p_username VARCHAR(50),
    IN p_email VARCHAR(100),
    IN p_password VARCHAR(255)
)
BEGIN
    -- 输入验证
    IF p_username IS NULL OR LENGTH(p_username) < 3 THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = '用户名长度至少3个字符';
    END IF;
    
    IF p_email IS NULL OR p_email NOT REGEXP '^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,}$' THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = '邮箱格式不正确';
    END IF;
    
    -- 插入数据
    INSERT INTO users (username, email, password)
    VALUES (p_username, p_email, SHA2(p_password, 256));
END$$
DELIMITER ;
```

## 备份和恢复规范

### 1. 备份策略

#### 1.1 逻辑备份
```bash
# 全库备份
mysqldump -u root -p --single-transaction --routines --triggers ecommerce > ecommerce_full.sql

# 单表备份
mysqldump -u root -p ecommerce users > users.sql

# 只备份结构
mysqldump -u root -p --no-data ecommerce > ecommerce_structure.sql

# 只备份数据
mysqldump -u root -p --no-create-info ecommerce > ecommerce_data.sql
```

#### 1.2 物理备份
```bash
# 使用 xtrabackup 进行热备份
xtrabackup --backup --target-dir=/backup/base --user=root --password=password

# 增量备份
xtrabackup --backup --target-dir=/backup/inc1 --incremental-basedir=/backup/base --user=root --password=password
```

### 2. 恢复策略

#### 2.1 逻辑恢复
```bash
# 恢复全库
mysql -u root -p ecommerce < ecommerce_full.sql

# 恢复单表
mysql -u root -p ecommerce < users.sql
```

#### 2.2 物理恢复
```bash
# 准备备份
xtrabackup --prepare --target-dir=/backup/base

# 恢复备份
xtrabackup --copy-back --target-dir=/backup/base --datadir=/var/lib/mysql
```

### 3. 备份验证
```sql
-- 备份完成后验证备份文件
-- 1. 检查备份文件大小和完整性
-- 2. 定期恢复测试

-- 创建测试数据库
CREATE DATABASE backup_test;

-- 恢复备份到测试数据库
mysql -u root -p backup_test < ecommerce_full.sql

-- 验证数据
SELECT COUNT(*) FROM backup_test.users;
SELECT COUNT(*) FROM backup_test.orders;
```

## 监控和报警规范

### 1. 性能监控

#### 1.1 慢查询监控
```sql
-- 开启慢查询日志
SET GLOBAL slow_query_log = 'ON';
SET GLOBAL long_query_time = 2;  -- 超过2秒的查询记录
SET GLOBAL slow_query_log_file = '/var/log/mysql/slow.log';

-- 查看慢查询
SELECT * FROM mysql.slow_log ORDER BY start_time DESC LIMIT 10;
```

#### 1.2 连接数监控
```sql
-- 查看当前连接数
SHOW STATUS LIKE 'Threads_connected';
SHOW STATUS LIKE 'Max_used_connections';

-- 查看连接详情
SHOW PROCESSLIST;
SELECT * FROM information_schema.processlist WHERE COMMAND != 'Sleep';
```

### 2. 数据库健康检查

#### 2.1 表健康检查
```sql
-- 检查表是否需要优化
SELECT 
    table_name,
    data_length,
    index_length,
    data_free
FROM information_schema.tables 
WHERE table_schema = 'ecommerce' 
    AND data_free > 1000000;  -- 碎片超过1MB

-- 优化表
OPTIMIZE TABLE users, orders;
```

#### 2.2 索引使用情况
```sql
-- 查看索引使用情况
SELECT 
    table_name,
    index_name,
    cardinality,
    sub_part
FROM information_schema.statistics 
WHERE table_schema = 'ecommerce';

-- 查看查询缓存命中率
SHOW STATUS LIKE 'Qcache_hits';
SHOW STATUS LIKE 'Qcache_inserts';
```

### 3. 报警配置

#### 3.1 关键指标报警
```yaml
# 报警规则示例
alerts:
  - name: mysql_down
    condition: mysql_up == 0
    duration: 1m
    
  - name: mysql_too_many_connections
    condition: mysql_global_status_threads_connected > 80
    duration: 5m
    
  - name: mysql_slow_queries
    condition: mysql_global_status_slow_queries > 10
    duration: 5m
    
  - name: mysql_replication_lag
    condition: mysql_slave_lag_seconds > 60
    duration: 5m
```

## 版本升级规范

### 1. 升级前准备
```sql
-- 1. 备份数据库
mysqldump -u root -p --all-databases > all_databases_backup.sql

-- 2. 检查兼容性
-- 查看当前版本
SELECT VERSION();

-- 3. 测试升级
-- 在测试环境先进行升级测试
```

### 2. 升级步骤
```bash
# 1. 停止应用服务
# 2. 备份数据库
# 3. 升级 MySQL
# 4. 运行 mysql_upgrade
mysql_upgrade -u root -p

# 5. 验证升级结果
# 6. 启动应用服务
```

### 3. 升级后验证
```sql
-- 检查表是否需要修复
CHECK TABLE users, orders;

-- 验证存储过程和函数
SHOW PROCEDURE STATUS;
SHOW FUNCTION STATUS;

-- 验证权限
SELECT * FROM mysql.user WHERE user = 'app_user';
```

## 最佳实践总结

### 1. 设计阶段
1. 合理选择数据类型，避免过度设计
2. 正确使用索引，遵循最左前缀原则
3. 适当的范式化，避免过度范式化
4. 预留适当的扩展字段

### 2. 开发阶段
1. 编写规范的 SQL，避免全表扫描
2. 使用事务保证数据一致性
3. 避免大事务和长事务
4. 定期优化和重构 SQL

### 3. 运维阶段
1. 定期备份和验证备份
2. 监控数据库性能指标
3. 及时处理慢查询
4. 保持数据库版本更新

### 4. 安全阶段
1. 最小权限原则
2. 敏感数据加密存储
3. 定期审计用户权限
4. 防范 SQL 注入攻击