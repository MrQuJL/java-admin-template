-- 创建数据库
CREATE DATABASE IF NOT EXISTS ecommerce CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- 使用数据库
USE ecommerce;

-- 创建用户表
CREATE TABLE IF NOT EXISTS users (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '用户ID',
    username VARCHAR(50) NOT NULL COMMENT '用户名',
    password VARCHAR(100) NOT NULL COMMENT '密码（加密存储）',
    email VARCHAR(100) NOT NULL COMMENT '邮箱',
    phone VARCHAR(20) COMMENT '手机号',
    nickname VARCHAR(50) COMMENT '昵称',
    avatar VARCHAR(255) COMMENT '头像URL',
    gender TINYINT DEFAULT 0 COMMENT '性别：0-未知，1-男，2-女',
    age TINYINT UNSIGNED COMMENT '年龄',
    status TINYINT NOT NULL DEFAULT 1 COMMENT '状态：0-禁用，1-正常，2-锁定',
    is_admin TINYINT NOT NULL DEFAULT 0 COMMENT '是否管理员：0-否，1-是',
    last_login_at TIMESTAMP NULL COMMENT '最后登录时间',
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    deleted TINYINT NOT NULL DEFAULT 0 COMMENT '逻辑删除：0-未删除，1-已删除',
    
    -- 唯一索引
    UNIQUE KEY uk_users_username (username),
    UNIQUE KEY uk_users_email (email),
    UNIQUE KEY uk_users_phone (phone),
    
    -- 普通索引
    INDEX idx_users_status (status),
    INDEX idx_users_created_at (created_at),
    INDEX idx_users_is_admin (is_admin),
    INDEX idx_users_deleted (deleted)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户表';

-- 插入模拟用户数据
INSERT INTO users (username, password, email, phone, nickname, avatar, gender, age, status, is_admin, last_login_at, created_at, deleted) VALUES
('admin', '$2a$10$EixZaYVK1fsbw1ZfbX3OXePaWxn96p36WQoeG6Lruj3vjPGga31lW', 'admin@example.com', '13800138000', '管理员', 'https://example.com/avatar/admin.jpg', 1, 30, 1, 1, '2026-01-01 10:00:00', '2025-01-01 00:00:00', 0),
('user001', '$2a$10$EixZaYVK1fsbw1ZfbX3OXePaWxn96p36WQoeG6Lruj3vjPGga31lW', 'user001@example.com', '13800138001', '用户001', 'https://example.com/avatar/user001.jpg', 1, 25, 1, 0, '2026-01-01 09:30:00', '2025-02-01 00:00:00', 0),
('user002', '$2a$10$EixZaYVK1fsbw1ZfbX3OXePaWxn96p36WQoeG6Lruj3vjPGga31lW', 'user002@example.com', '13800138002', '用户002', 'https://example.com/avatar/user002.jpg', 2, 22, 1, 0, '2026-01-01 09:00:00', '2025-03-01 00:00:00', 0),
('user003', '$2a$10$EixZaYVK1fsbw1ZfbX3OXePaWxn96p36WQoeG6Lruj3vjPGga31lW', 'user003@example.com', '13800138003', '用户003', 'https://example.com/avatar/user003.jpg', 1, 28, 2, 0, '2025-12-31 18:00:00', '2025-04-01 00:00:00', 0),
('user004', '$2a$10$EixZaYVK1fsbw1ZfbX3OXePaWxn96p36WQoeG6Lruj3vjPGga31lW', 'user004@example.com', '13800138004', '用户004', 'https://example.com/avatar/user004.jpg', 0, 35, 1, 0, '2026-01-01 08:30:00', '2025-05-01 00:00:00', 0),
('user005', '$2a$10$EixZaYVK1fsbw1ZfbX3OXePaWxn96p36WQoeG6Lruj3vjPGga31lW', 'user005@example.com', '13800138005', '用户005', 'https://example.com/avatar/user005.jpg', 2, 26, 1, 0, '2026-01-01 08:00:00', '2025-06-01 00:00:00', 0),
('user006', '$2a$10$EixZaYVK1fsbw1ZfbX3OXePaWxn96p36WQoeG6Lruj3vjPGga31lW', 'user006@example.com', '13800138006', '用户006', 'https://example.com/avatar/user006.jpg', 1, 24, 0, 0, NULL, '2025-07-01 00:00:00', 0),
('user007', '$2a$10$EixZaYVK1fsbw1ZfbX3OXePaWxn96p36WQoeG6Lruj3vjPGga31lW', 'user007@example.com', '13800138007', '用户007', 'https://example.com/avatar/user007.jpg', 2, 29, 1, 0, '2026-01-01 07:30:00', '2025-08-01 00:00:00', 0),
('user008', '$2a$10$EixZaYVK1fsbw1ZfbX3OXePaWxn96p36WQoeG6Lruj3vjPGga31lW', 'user008@example.com', '13800138008', '用户008', 'https://example.com/avatar/user008.jpg', 1, 32, 1, 0, '2025-12-31 17:00:00', '2025-09-01 00:00:00', 0),
('user009', '$2a$10$EixZaYVK1fsbw1ZfbX3OXePaWxn96p36WQoeG6Lruj3vjPGga31lW', 'user009@example.com', '13800138009', '用户009', 'https://example.com/avatar/user009.jpg', 2, 27, 1, 0, '2026-01-01 07:00:00', '2025-10-01 00:00:00', 0),
('user010', '$2a$10$EixZaYVK1fsbw1ZfbX3OXePaWxn96p36WQoeG6Lruj3vjPGga31lW', 'user010@example.com', '13800138010', '用户010', 'https://example.com/avatar/user010.jpg', 1, 23, 1, 0, '2026-01-01 06:30:00', '2025-11-01 00:00:00', 0);

-- 创建商品表
CREATE TABLE IF NOT EXISTS products (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '商品ID',
    product_name VARCHAR(200) NOT NULL COMMENT '商品名称',
    sku VARCHAR(50) NOT NULL COMMENT '商品SKU',
    category_id BIGINT NOT NULL COMMENT '分类ID',
    price DECIMAL(10, 2) NOT NULL COMMENT '商品价格',
    stock INT NOT NULL DEFAULT 0 COMMENT '商品库存',
    description TEXT COMMENT '商品描述',
    is_active TINYINT NOT NULL DEFAULT 1 COMMENT '是否上架：0-下架，1-上架',
    is_hot TINYINT NOT NULL DEFAULT 0 COMMENT '是否热门：0-否，1-是',
    is_new TINYINT NOT NULL DEFAULT 0 COMMENT '是否新品：0-否，1-是',
    sales INT NOT NULL DEFAULT 0 COMMENT '销量',
    image_url VARCHAR(255) COMMENT '商品主图',
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    deleted TINYINT NOT NULL DEFAULT 0 COMMENT '逻辑删除：0-未删除，1-已删除',
    
    -- 唯一索引
    UNIQUE KEY uk_products_sku (sku),
    
    -- 普通索引
    INDEX idx_products_status (is_active),
    INDEX idx_products_category_id (category_id),
    INDEX idx_products_price (price),
    INDEX idx_products_sales (sales),
    INDEX idx_products_created_at (created_at),
    INDEX idx_products_deleted (deleted)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='商品表';

-- 插入模拟商品数据
INSERT INTO products (product_name, sku, category_id, price, stock, description, is_active, is_hot, is_new, sales, image_url, created_at, deleted) VALUES
('笔记本电脑', 'PROD001', 1, 5999.00, 100, '高性能笔记本电脑，适合办公和游戏', 1, 1, 1, 150, 'https://example.com/images/laptop.jpg', '2025-01-15 00:00:00', 0),
('智能手机', 'PROD002', 2, 3999.00, 200, '最新款智能手机，拍照功能强大', 1, 1, 1, 200, 'https://example.com/images/phone.jpg', '2025-02-20 00:00:00', 0),
('无线耳机', 'PROD003', 3, 799.00, 300, '降噪无线耳机，续航持久', 1, 0, 1, 300, 'https://example.com/images/earphones.jpg', '2025-03-10 00:00:00', 0),
('智能手表', 'PROD004', 4, 1299.00, 150, '多功能智能手表，健康监测', 1, 1, 0, 120, 'https://example.com/images/watch.jpg', '2025-04-05 00:00:00', 0),
('平板电脑', 'PROD005', 5, 2499.00, 120, '轻薄平板电脑，适合娱乐和学习', 1, 0, 1, 90, 'https://example.com/images/tablet.jpg', '2025-05-18 00:00:00', 0),
('无线鼠标', 'PROD006', 6, 199.00, 500, '静音无线鼠标，舒适手感', 1, 0, 0, 450, 'https://example.com/images/mouse.jpg', '2025-06-22 00:00:00', 0),
('机械键盘', 'PROD007', 7, 499.00, 200, 'RGB机械键盘，段落手感', 1, 1, 0, 180, 'https://example.com/images/keyboard.jpg', '2025-07-15 00:00:00', 0),
('显示器', 'PROD008', 8, 1499.00, 80, '27英寸4K显示器，色彩准确', 1, 0, 0, 85, 'https://example.com/images/monitor.jpg', '2025-08-08 00:00:00', 0),
('游戏手柄', 'PROD009', 9, 299.00, 250, '蓝牙游戏手柄，兼容多平台', 1, 1, 0, 220, 'https://example.com/images/controller.jpg', '2025-09-25 00:00:00', 0),
('移动电源', 'PROD010', 10, 129.00, 400, '20000mAh大容量移动电源，快充支持', 1, 0, 0, 500, 'https://example.com/images/powerbank.jpg', '2025-10-12 00:00:00', 0);