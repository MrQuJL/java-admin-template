-- 删除并创建数据库
DROP DATABASE IF EXISTS admin_db;
CREATE DATABASE admin_db DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE admin_db;

-- 创建用户表
CREATE TABLE IF NOT EXISTS sys_user (
    id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '用户ID',
    username VARCHAR(50) NOT NULL COMMENT '用户名',
    password VARCHAR(100) NOT NULL COMMENT '密码',
    nickname VARCHAR(50) COMMENT '昵称',
    email VARCHAR(100) COMMENT '邮箱',
    phone VARCHAR(20) COMMENT '手机号',
    status TINYINT NOT NULL DEFAULT 1 COMMENT '状态：1-正常，0-禁用',
    create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    deleted TINYINT NOT NULL DEFAULT 0 COMMENT '删除标记：0-未删除，1-已删除',
    UNIQUE KEY uk_username (username)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户表';

-- 插入测试数据
INSERT INTO sys_user (username, password, nickname, email, phone, status) VALUES
('admin', '123456', '管理员', 'admin@example.com', '13800138000', 1),
('user1', '123456', '用户1', 'user1@example.com', '13800138001', 1),
('user2', '123456', '用户2', 'user2@example.com', '13800138002', 1),
('user3', '123456', '用户3', 'user3@example.com', '13800138003', 0);

-- 创建部门表
CREATE TABLE IF NOT EXISTS sys_department (
    id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '部门ID',
    name VARCHAR(50) NOT NULL COMMENT '部门名称',
    code VARCHAR(50) COMMENT '部门编码',
    description VARCHAR(200) COMMENT '部门描述',
    parent_id BIGINT COMMENT '父部门ID',
    sort INT COMMENT '排序',
    status TINYINT NOT NULL DEFAULT 1 COMMENT '状态：1-正常，0-禁用',
    create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    deleted TINYINT NOT NULL DEFAULT 0 COMMENT '删除标记：0-未删除，1-已删除',
    UNIQUE KEY uk_code (code)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='部门表';

-- 插入测试数据
INSERT INTO sys_department (name, code, description, parent_id, sort, status) VALUES
('总公司', 'HEAD', '总公司', NULL, 1, 1),
('技术部', 'TECH', '技术研发部门', 1, 1, 1),
('产品部', 'PRODUCT', '产品设计部门', 1, 2, 1),
('运营部', 'OPERATION', '运营管理部门', 1, 3, 1),
('后端组', 'BACKEND', '后端开发组', 2, 1, 1),
('前端组', 'FRONTEND', '前端开发组', 2, 2, 1);
