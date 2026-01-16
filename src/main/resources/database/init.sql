-- 创建数据库
CREATE DATABASE IF NOT EXISTS `ecommerce` CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE `ecommerce`;

-- 创建用户表
CREATE TABLE IF NOT EXISTS `sys_users` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `username` VARCHAR(50) NOT NULL COMMENT '用户名',
  `password` VARCHAR(255) NOT NULL COMMENT '密码',
  `real_name` VARCHAR(50) DEFAULT NULL COMMENT '真实姓名',
  `email` VARCHAR(100) DEFAULT NULL COMMENT '邮箱',
  `phone` VARCHAR(20) DEFAULT NULL COMMENT '手机号',
  `avatar` VARCHAR(255) DEFAULT NULL COMMENT '头像',
  `is_active` TINYINT DEFAULT 1 COMMENT '是否激活: 0-禁用, 1-启用',
  `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` TINYINT DEFAULT 0 COMMENT '逻辑删除: 0-未删除, 1-已删除',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `uk_sys_users_username` (`username`),
  UNIQUE KEY `uk_sys_users_email` (`email`),
  UNIQUE KEY `uk_sys_users_phone` (`phone`),
  INDEX `idx_sys_users_deleted` (`deleted`),
  INDEX `idx_sys_users_is_active` (`is_active`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='系统用户表';

-- 插入用户模拟数据
INSERT INTO `sys_users` (`username`, `password`, `real_name`, `email`, `phone`, `is_active`, `created_at`, `updated_at`, `deleted`) VALUES
('admin', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iKaWHNXOWe5MeDFu/p5WQQoTITNi', '超级管理员', 'admin@ecommerce.com', '13800138000', 1, '2024-01-01 00:00:00', '2024-01-01 00:00:00', 0),
('zhangsan', '$2a$10$9vKP4lGYP7bBNbMqG6qyou/QIcFz/q6eB0cJ5Y5p8N5Y5p8N5Y5p8', '张三', 'zhangsan@ecommerce.com', '13800138001', 1, '2024-01-02 10:00:00', '2024-01-02 10:00:00', 0),
('lisi', '$2a$10$9vKP4lGYP7bBNbMqG6qyou/QIcFz/q6eB0cJ5Y5p8N5Y5p8N5Y5p8', '李四', 'lisi@ecommerce.com', '13800138002', 1, '2024-01-03 11:00:00', '2024-01-03 11:00:00', 0),
('wangwu', '$2a$10$9vKP4lGYP7bBNbMqG6qyou/QIcFz/q6eB0cJ5Y5p8N5Y5p8N5Y5p8', '王五', 'wangwu@ecommerce.com', '13800138003', 1, '2024-01-04 14:00:00', '2024-01-04 14:00:00', 0),
('zhaoliu', '$2a$10$9vKP4lGYP7bBNbMqG6qyou/QIcFz/q6eB0cJ5Y5p8N5Y5p8N5Y5p8', '赵六', 'zhaoliu@ecommerce.com', '13800138004', 1, '2024-01-05 16:00:00', '2024-01-05 16:00:00', 0);

-- 创建角色表
CREATE TABLE IF NOT EXISTS `sys_roles` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '角色ID',
  `role_name` VARCHAR(50) NOT NULL COMMENT '角色名称',
  `role_code` VARCHAR(50) NOT NULL COMMENT '角色编码',
  `description` VARCHAR(255) DEFAULT NULL COMMENT '角色描述',
  `is_active` TINYINT DEFAULT 1 COMMENT '是否激活: 0-禁用, 1-启用',
  `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` TINYINT DEFAULT 0 COMMENT '逻辑删除: 0-未删除, 1-已删除',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `uk_sys_roles_role_code` (`role_code`),
  INDEX `idx_sys_roles_deleted` (`deleted`),
  INDEX `idx_sys_roles_is_active` (`is_active`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='系统角色表';

-- 插入角色模拟数据
INSERT INTO `sys_roles` (`role_name`, `role_code`, `description`, `is_active`, `created_at`, `updated_at`, `deleted`) VALUES
('超级管理员', 'SUPER_ADMIN', '拥有系统所有权限', 1, '2024-01-01 00:00:00', '2024-01-01 00:00:00', 0),
('管理员', 'ADMIN', '拥有系统大部分权限', 1, '2024-01-01 00:00:00', '2024-01-01 00:00:00', 0),
('普通用户', 'USER', '拥有系统基础权限', 1, '2024-01-01 00:00:00', '2024-01-01 00:00:00', 0);

-- 创建菜单表
CREATE TABLE IF NOT EXISTS `sys_menus` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '菜单ID',
  `parent_id` BIGINT DEFAULT 0 COMMENT '父菜单ID: 0-根菜单',
  `menu_name` VARCHAR(50) NOT NULL COMMENT '菜单名称',
  `menu_path` VARCHAR(100) DEFAULT NULL COMMENT '菜单路径',
  `component` VARCHAR(100) DEFAULT NULL COMMENT '组件路径',
  `icon` VARCHAR(50) DEFAULT NULL COMMENT '菜单图标',
  `menu_type` TINYINT NOT NULL COMMENT '菜单类型: 1-目录, 2-菜单, 3-按钮',
  `permission` VARCHAR(100) DEFAULT NULL COMMENT '权限标识',
  `order_num` INT DEFAULT 0 COMMENT '排序',
  `is_active` TINYINT DEFAULT 1 COMMENT '是否激活: 0-禁用, 1-启用',
  `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` TINYINT DEFAULT 0 COMMENT '逻辑删除: 0-未删除, 1-已删除',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_sys_menus_parent_id` (`parent_id`),
  INDEX `idx_sys_menus_menu_type` (`menu_type`),
  INDEX `idx_sys_menus_deleted` (`deleted`),
  INDEX `idx_sys_menus_is_active` (`is_active`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='系统菜单表';

-- 插入菜单模拟数据
INSERT INTO `sys_menus` (`parent_id`, `menu_name`, `menu_path`, `component`, `icon`, `menu_type`, `permission`, `order_num`, `is_active`, `created_at`, `updated_at`, `deleted`) VALUES
(0, '系统管理', '/system', NULL, 'Setting', 1, NULL, 1, 1, '2024-01-01 00:00:00', '2024-01-01 00:00:00', 0),
(1, '用户管理', '/system/user', '@/views/system/User', 'User', 2, 'system:user:list', 1, 1, '2024-01-01 00:00:00', '2024-01-01 00:00:00', 0),
(1, '角色管理', '/system/role', '@/views/system/Role', 'Role', 2, 'system:role:list', 2, 1, '2024-01-01 00:00:00', '2024-01-01 00:00:00', 0),
(1, '菜单管理', '/system/menu', '@/views/system/Menu', 'Menu', 2, 'system:menu:list', 3, 1, '2024-01-01 00:00:00', '2024-01-01 00:00:00', 0),
(0, '商品管理', '/product', NULL, 'Goods', 1, NULL, 2, 1, '2024-01-01 00:00:00', '2024-01-01 00:00:00', 0),
(5, '商品列表', '/product/list', '@/views/product/List', 'Goods', 2, 'product:list', 1, 1, '2024-01-01 00:00:00', '2024-01-01 00:00:00', 0),
(5, '商品分类', '/product/category', '@/views/product/Category', 'Category', 2, 'product:category', 2, 1, '2024-01-01 00:00:00', '2024-01-01 00:00:00', 0),
(0, '订单管理', '/order', NULL, 'Order', 1, NULL, 3, 1, '2024-01-01 00:00:00', '2024-01-01 00:00:00', 0),
(8, '订单列表', '/order/list', '@/views/order/List', 'Order', 2, 'order:list', 1, 1, '2024-01-01 00:00:00', '2024-01-01 00:00:00', 0);

-- 创建用户角色关联表
CREATE TABLE IF NOT EXISTS `sys_user_roles` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '关联ID',
  `user_id` BIGINT NOT NULL COMMENT '用户ID',
  `role_id` BIGINT NOT NULL COMMENT '角色ID',
  `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` TINYINT DEFAULT 0 COMMENT '逻辑删除: 0-未删除, 1-已删除',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `uk_sys_user_roles_user_role` (`user_id`, `role_id`),
  INDEX `idx_sys_user_roles_user_id` (`user_id`),
  INDEX `idx_sys_user_roles_role_id` (`role_id`),
  INDEX `idx_sys_user_roles_deleted` (`deleted`),
  CONSTRAINT `fk_sys_user_roles_user_id` FOREIGN KEY (`user_id`) REFERENCES `sys_users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_sys_user_roles_role_id` FOREIGN KEY (`role_id`) REFERENCES `sys_roles` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户角色关联表';

-- 插入用户角色关联模拟数据
INSERT INTO `sys_user_roles` (`user_id`, `role_id`, `created_at`, `updated_at`, `deleted`) VALUES
(1, 1, '2024-01-01 00:00:00', '2024-01-01 00:00:00', 0),
(2, 2, '2024-01-02 10:00:00', '2024-01-02 10:00:00', 0),
(3, 3, '2024-01-03 11:00:00', '2024-01-03 11:00:00', 0),
(4, 3, '2024-01-04 14:00:00', '2024-01-04 14:00:00', 0),
(5, 3, '2024-01-05 16:00:00', '2024-01-05 16:00:00', 0);

-- 创建角色菜单关联表
CREATE TABLE IF NOT EXISTS `sys_role_menus` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '关联ID',
  `role_id` BIGINT NOT NULL COMMENT '角色ID',
  `menu_id` BIGINT NOT NULL COMMENT '菜单ID',
  `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` TINYINT DEFAULT 0 COMMENT '逻辑删除: 0-未删除, 1-已删除',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `uk_sys_role_menus_role_menu` (`role_id`, `menu_id`),
  INDEX `idx_sys_role_menus_role_id` (`role_id`),
  INDEX `idx_sys_role_menus_menu_id` (`menu_id`),
  INDEX `idx_sys_role_menus_deleted` (`deleted`),
  CONSTRAINT `fk_sys_role_menus_role_id` FOREIGN KEY (`role_id`) REFERENCES `sys_roles` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_sys_role_menus_menu_id` FOREIGN KEY (`menu_id`) REFERENCES `sys_menus` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='角色菜单关联表';

-- 插入角色菜单关联模拟数据
INSERT INTO `sys_role_menus` (`role_id`, `menu_id`, `created_at`, `updated_at`, `deleted`) VALUES
-- 超级管理员拥有所有菜单权限
(1, 1, '2024-01-01 00:00:00', '2024-01-01 00:00:00', 0),
(1, 2, '2024-01-01 00:00:00', '2024-01-01 00:00:00', 0),
(1, 3, '2024-01-01 00:00:00', '2024-01-01 00:00:00', 0),
(1, 4, '2024-01-01 00:00:00', '2024-01-01 00:00:00', 0),
(1, 5, '2024-01-01 00:00:00', '2024-01-01 00:00:00', 0),
(1, 6, '2024-01-01 00:00:00', '2024-01-01 00:00:00', 0),
(1, 7, '2024-01-01 00:00:00', '2024-01-01 00:00:00', 0),
(1, 8, '2024-01-01 00:00:00', '2024-01-01 00:00:00', 0),
(1, 9, '2024-01-01 00:00:00', '2024-01-01 00:00:00', 0),
-- 管理员拥有部分菜单权限
(2, 1, '2024-01-01 00:00:00', '2024-01-01 00:00:00', 0),
(2, 2, '2024-01-01 00:00:00', '2024-01-01 00:00:00', 0),
(2, 5, '2024-01-01 00:00:00', '2024-01-01 00:00:00', 0),
(2, 6, '2024-01-01 00:00:00', '2024-01-01 00:00:00', 0),
(2, 7, '2024-01-01 00:00:00', '2024-01-01 00:00:00', 0),
(2, 8, '2024-01-01 00:00:00', '2024-01-01 00:00:00', 0),
(2, 9, '2024-01-01 00:00:00', '2024-01-01 00:00:00', 0),
-- 普通用户拥有有限菜单权限
(3, 5, '2024-01-01 00:00:00', '2024-01-01 00:00:00', 0),
(3, 6, '2024-01-01 00:00:00', '2024-01-01 00:00:00', 0),
(3, 8, '2024-01-01 00:00:00', '2024-01-01 00:00:00', 0),
(3, 9, '2024-01-01 00:00:00', '2024-01-01 00:00:00', 0);

-- 创建登录日志表
CREATE TABLE IF NOT EXISTS `sys_login_logs` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '日志ID',
  `user_id` BIGINT DEFAULT NULL COMMENT '用户ID',
  `username` VARCHAR(50) NOT NULL COMMENT '登录用户名',
  `ip` VARCHAR(50) NOT NULL COMMENT '登录IP地址',
  `user_agent` VARCHAR(255) DEFAULT NULL COMMENT '用户代理',
  `login_status` TINYINT NOT NULL COMMENT '登录状态: 0-失败, 1-成功',
  `error_msg` VARCHAR(255) DEFAULT NULL COMMENT '错误信息',
  `login_time` TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '登录时间',
  `logout_time` TIMESTAMP DEFAULT NULL COMMENT '登出时间',
  `deleted` TINYINT DEFAULT 0 COMMENT '逻辑删除: 0-未删除, 1-已删除',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_sys_login_logs_user_id` (`user_id`),
  INDEX `idx_sys_login_logs_username` (`username`),
  INDEX `idx_sys_login_logs_ip` (`ip`),
  INDEX `idx_sys_login_logs_login_status` (`login_status`),
  INDEX `idx_sys_login_logs_login_time` (`login_time`),
  INDEX `idx_sys_login_logs_deleted` (`deleted`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='登录日志表';

-- 插入登录日志模拟数据
INSERT INTO `sys_login_logs` (`user_id`, `username`, `ip`, `user_agent`, `login_status`, `error_msg`, `login_time`, `logout_time`, `deleted`) VALUES
(1, 'admin', '192.168.1.1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36', 1, NULL, '2024-01-01 08:00:00', '2024-01-01 18:00:00', 0),
(2, 'zhangsan', '192.168.1.2', 'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36', 1, NULL, '2024-01-02 09:30:00', '2024-01-02 17:30:00', 0),
(3, 'lisi', '192.168.1.3', 'Mozilla/5.0 (iPhone; CPU iPhone OS 16_0 like Mac OS X) AppleWebKit/605.1.15', 1, NULL, '2024-01-03 10:15:00', '2024-01-03 16:45:00', 0),
(4, 'wangwu', '192.168.1.4', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36', 0, '密码错误', '2024-01-04 14:20:00', NULL, 0),
(5, 'zhaoliu', '192.168.1.5', 'Mozilla/5.0 (Linux; Android 13; SM-G998B) AppleWebKit/537.36', 1, NULL, '2024-01-05 11:50:00', NULL, 0);

-- 创建操作日志表
CREATE TABLE IF NOT EXISTS `sys_operation_logs` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '日志ID',
  `user_id` BIGINT NOT NULL COMMENT '用户ID',
  `username` VARCHAR(50) NOT NULL COMMENT '用户名',
  `ip` VARCHAR(50) NOT NULL COMMENT '操作IP',
  `module` VARCHAR(50) NOT NULL COMMENT '操作模块',
  `action` VARCHAR(50) NOT NULL COMMENT '操作类型',
  `url` VARCHAR(255) NOT NULL COMMENT '请求URL',
  `method` VARCHAR(10) NOT NULL COMMENT '请求方法',
  `params` TEXT DEFAULT NULL COMMENT '请求参数',
  `result` TEXT DEFAULT NULL COMMENT '返回结果',
  `success` TINYINT NOT NULL COMMENT '操作结果: 0-失败, 1-成功',
  `error_msg` VARCHAR(255) DEFAULT NULL COMMENT '错误信息',
  `operation_time` TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '操作时间',
  `execution_time` INT DEFAULT NULL COMMENT '执行时间(毫秒)',
  `deleted` TINYINT DEFAULT 0 COMMENT '逻辑删除: 0-未删除, 1-已删除',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_sys_operation_logs_user_id` (`user_id`),
  INDEX `idx_sys_operation_logs_username` (`username`),
  INDEX `idx_sys_operation_logs_module` (`module`),
  INDEX `idx_sys_operation_logs_action` (`action`),
  INDEX `idx_sys_operation_logs_operation_time` (`operation_time`),
  INDEX `idx_sys_operation_logs_success` (`success`),
  INDEX `idx_sys_operation_logs_deleted` (`deleted`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='操作日志表';

-- 插入操作日志模拟数据
INSERT INTO `sys_operation_logs` (`user_id`, `username`, `ip`, `module`, `action`, `url`, `method`, `params`, `result`, `success`, `error_msg`, `operation_time`, `execution_time`, `deleted`) VALUES
(1, 'admin', '192.168.1.1', 'system', 'add', '/api/system/user', 'POST', '{"username":"test","password":"123456","realName":"测试用户"}', '{"code":200,"message":"成功"}', 1, NULL, '2024-01-01 09:00:00', 150, 0),
(2, 'zhangsan', '192.168.1.2', 'product', 'list', '/api/product/list', 'GET', '{"page":1,"size":10}', '{"code":200,"message":"成功","data":{"total":100,"list":[]}}', 1, NULL, '2024-01-02 10:30:00', 80, 0),
(3, 'lisi', '192.168.1.3', 'order', 'update', '/api/order/1/status', 'PUT', '{"status":2}', '{"code":200,"message":"成功"}', 1, NULL, '2024-01-03 14:20:00', 120, 0),
(2, 'zhangsan', '192.168.1.2', 'product', 'delete', '/api/product/10', 'DELETE', '{}', '{"code":500,"message":"商品不存在"}', 0, '商品不存在', '2024-01-04 11:15:00', 90, 0),
(1, 'admin', '192.168.1.1', 'system', 'update', '/api/system/role/2', 'PUT', '{"roleName":"管理员","description":"拥有系统大部分权限"}', '{"code":200,"message":"成功"}', 1, NULL, '2024-01-05 16:45:00', 180, 0);

-- 创建网站基本信息表
CREATE TABLE IF NOT EXISTS `sys_settings` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '设置ID',
  `setting_key` VARCHAR(50) NOT NULL COMMENT '设置键',
  `setting_value` TEXT DEFAULT NULL COMMENT '设置值',
  `group_name` VARCHAR(50) DEFAULT NULL COMMENT '分组名称',
  `setting_type` TINYINT DEFAULT 1 COMMENT '设置类型: 1-字符串, 2-数字, 3-布尔值, 4-文本',
  `name` VARCHAR(50) NOT NULL COMMENT '设置名称',
  `description` VARCHAR(255) DEFAULT NULL COMMENT '设置描述',
  `is_required` TINYINT DEFAULT 0 COMMENT '是否必填: 0-否, 1-是',
  `is_active` TINYINT DEFAULT 1 COMMENT '是否激活: 0-禁用, 1-启用',
  `sort_order` INT DEFAULT 0 COMMENT '排序',
  `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` TINYINT DEFAULT 0 COMMENT '逻辑删除: 0-未删除, 1-已删除',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `uk_sys_settings_setting_key` (`setting_key`),
  INDEX `idx_sys_settings_group_name` (`group_name`),
  INDEX `idx_sys_settings_is_active` (`is_active`),
  INDEX `idx_sys_settings_deleted` (`deleted`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='网站基本信息表';

-- 插入网站基本信息模拟数据
INSERT INTO `sys_settings` (`setting_key`, `setting_value`, `group_name`, `setting_type`, `name`, `description`, `is_required`, `is_active`, `sort_order`, `created_at`, `updated_at`, `deleted`) VALUES
('site_name', '电商后台管理系统', 'basic', 1, '网站名称', '网站的中文名称', 1, 1, 1, '2024-01-01 00:00:00', '2024-01-01 00:00:00', 0),
('site_logo', 'https://example.com/logo.png', 'basic', 1, '网站Logo', '网站的Logo图片URL', 1, 1, 2, '2024-01-01 00:00:00', '2024-01-01 00:00:00', 0),
('site_domain', 'admin.ecommerce.com', 'basic', 1, '网站域名', '网站的访问域名', 1, 1, 3, '2024-01-01 00:00:00', '2024-01-01 00:00:00', 0),
('contact_email', 'contact@ecommerce.com', 'contact', 1, '联系邮箱', '网站的联系邮箱', 1, 1, 1, '2024-01-01 00:00:00', '2024-01-01 00:00:00', 0),
('contact_phone', '400-123-4567', 'contact', 1, '联系电话', '网站的联系电话', 1, 1, 2, '2024-01-01 00:00:00', '2024-01-01 00:00:00', 0),
('address', '北京市朝阳区建国路88号', 'contact', 1, '公司地址', '公司的详细地址', 0, 1, 3, '2024-01-01 00:00:00', '2024-01-01 00:00:00', 0),
('seo_title', '电商后台管理系统 - 专业的电商管理平台', 'seo', 1, 'SEO标题', '网站的SEO标题', 0, 1, 1, '2024-01-01 00:00:00', '2024-01-01 00:00:00', 0),
('seo_keywords', '电商,后台管理,商品管理,订单管理', 'seo', 1, 'SEO关键词', '网站的SEO关键词', 0, 1, 2, '2024-01-01 00:00:00', '2024-01-01 00:00:00', 0),
('seo_description', '专业的电商后台管理系统，提供商品管理、订单管理、客户管理等功能', 'seo', 4, 'SEO描述', '网站的SEO描述', 0, 1, 3, '2024-01-01 00:00:00', '2024-01-01 00:00:00', 0),
('copyright', '© 2024 电商后台管理系统 版权所有', 'basic', 1, '版权信息', '网站的版权信息', 1, 1, 4, '2024-01-01 00:00:00', '2024-01-01 00:00:00', 0);

-- 创建消息表
CREATE TABLE IF NOT EXISTS `sys_messages` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '消息ID',
  `title` VARCHAR(100) NOT NULL COMMENT '消息标题',
  `content` TEXT NOT NULL COMMENT '消息内容',
  `message_type` TINYINT NOT NULL COMMENT '消息类型: 1-系统通知, 2-订单通知, 3-活动通知, 4-其他',
  `sender_id` BIGINT DEFAULT NULL COMMENT '发送者ID',
  `sender_name` VARCHAR(50) DEFAULT NULL COMMENT '发送者名称',
  `receiver_id` BIGINT DEFAULT NULL COMMENT '接收者ID, NULL表示全体用户',
  `receiver_type` TINYINT DEFAULT 0 COMMENT '接收者类型: 0-全体用户, 1-特定用户, 2-特定角色',
  `role_id` BIGINT DEFAULT NULL COMMENT '接收角色ID',
  `is_read` TINYINT DEFAULT 0 COMMENT '是否已读: 0-未读, 1-已读',
  `read_time` TIMESTAMP DEFAULT NULL COMMENT '阅读时间',
  `is_important` TINYINT DEFAULT 0 COMMENT '是否重要: 0-否, 1-是',
  `is_active` TINYINT DEFAULT 1 COMMENT '是否有效: 0-无效, 1-有效',
  `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` TINYINT DEFAULT 0 COMMENT '逻辑删除: 0-未删除, 1-已删除',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_sys_messages_receiver_id` (`receiver_id`),
  INDEX `idx_sys_messages_message_type` (`message_type`),
  INDEX `idx_sys_messages_is_read` (`is_read`),
  INDEX `idx_sys_messages_is_important` (`is_important`),
  INDEX `idx_sys_messages_created_at` (`created_at`),
  INDEX `idx_sys_messages_deleted` (`deleted`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='消息表';

-- 插入消息模拟数据
INSERT INTO `sys_messages` (`title`, `content`, `message_type`, `sender_id`, `sender_name`, `receiver_id`, `receiver_type`, `role_id`, `is_read`, `read_time`, `is_important`, `is_active`, `created_at`, `updated_at`, `deleted`) VALUES
('系统维护通知', '尊敬的用户，系统将于2024年1月10日凌晨2点-4点进行维护，请提前做好准备。', 1, 1, '系统管理员', NULL, 0, NULL, 0, NULL, 1, 1, '2024-01-05 10:00:00', '2024-01-05 10:00:00', 0),
('新订单通知', '您有一笔新订单，请及时处理。订单号：20240105000006', 2, 1, '系统管理员', 2, 1, NULL, 1, '2024-01-05 11:30:00', 1, 1, '2024-01-05 11:00:00', '2024-01-05 11:30:00', 0),
('春节活动通知', '春节期间全场商品8折优惠，活动时间：2024年2月1日-2月10日', 3, 1, '系统管理员', NULL, 0, NULL, 0, NULL, 0, 1, '2024-01-15 15:00:00', '2024-01-15 15:00:00', 0),
('密码修改提醒', '您的密码已于2024年1月5日修改，如果不是您本人操作，请及时联系管理员。', 1, 1, '系统管理员', 2, 1, NULL, 1, '2024-01-05 16:00:00', 0, 1, '2024-01-05 15:30:00', '2024-01-05 16:00:00', 0),
('商品审核通知', '您提交的商品"iPhone 15 Pro"已通过审核，现在可以正常销售。', 2, 1, '系统管理员', 3, 1, NULL, 0, NULL, 0, 1, '2024-01-06 09:00:00', '2024-01-06 09:00:00', 0),
('管理员权限变更通知', '您的管理员权限已提升为超级管理员，请注意保管账号安全。', 1, 1, '系统管理员', 1, 1, NULL, 1, '2024-01-01 10:00:00', 1, 1, '2024-01-01 09:30:00', '2024-01-01 10:00:00', 0),
('订单发货通知', '您的订单20240101000001已发货，物流公司：顺丰速运，运单号：SF1234567890', 2, 1, '系统管理员', 2, 1, NULL, 1, '2024-01-02 10:30:00', 0, 1, '2024-01-02 10:00:00', '2024-01-02 10:30:00', 0);

-- 创建商品分类表
CREATE TABLE IF NOT EXISTS `product_categories` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '分类ID',
  `parent_id` BIGINT DEFAULT 0 COMMENT '父分类ID: 0-根分类',
  `category_name` VARCHAR(50) NOT NULL COMMENT '分类名称',
  `category_code` VARCHAR(50) NOT NULL COMMENT '分类编码',
  `description` VARCHAR(255) DEFAULT NULL COMMENT '分类描述',
  `sort_order` INT DEFAULT 0 COMMENT '排序',
  `is_active` TINYINT DEFAULT 1 COMMENT '是否激活: 0-禁用, 1-启用',
  `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` TINYINT DEFAULT 0 COMMENT '逻辑删除: 0-未删除, 1-已删除',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `uk_product_categories_category_code` (`category_code`),
  INDEX `idx_product_categories_parent_id` (`parent_id`),
  INDEX `idx_product_categories_is_active` (`is_active`),
  INDEX `idx_product_categories_sort_order` (`sort_order`),
  INDEX `idx_product_categories_deleted` (`deleted`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='商品分类表';

-- 插入商品分类模拟数据
INSERT INTO `product_categories` (`parent_id`, `category_name`, `category_code`, `description`, `sort_order`, `is_active`, `created_at`, `updated_at`, `deleted`) VALUES
(0, '电子产品', 'ELECTRONICS', '各类电子产品', 1, 1, '2024-01-01 00:00:00', '2024-01-01 00:00:00', 0),
(1, '手机', 'ELECTRONICS_PHONE', '智能手机', 1, 1, '2024-01-01 00:00:00', '2024-01-01 00:00:00', 0),
(1, '电脑', 'ELECTRONICS_COMPUTER', '笔记本电脑和平板', 2, 1, '2024-01-01 00:00:00', '2024-01-01 00:00:00', 0),
(0, '服装', 'CLOTHING', '各类服装', 2, 1, '2024-01-01 00:00:00', '2024-01-01 00:00:00', 0),
(4, '男装', 'CLOTHING_MEN', '男士服装', 1, 1, '2024-01-01 00:00:00', '2024-01-01 00:00:00', 0),
(4, '女装', 'CLOTHING_WOMEN', '女士服装', 2, 1, '2024-01-01 00:00:00', '2024-01-01 00:00:00', 0);

-- 创建商品表
CREATE TABLE IF NOT EXISTS `product_products` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '商品ID',
  `category_id` BIGINT NOT NULL COMMENT '分类ID',
  `product_name` VARCHAR(100) NOT NULL COMMENT '商品名称',
  `product_code` VARCHAR(50) NOT NULL COMMENT '商品编码',
  `subtitle` VARCHAR(255) DEFAULT NULL COMMENT '商品副标题',
  `description` TEXT DEFAULT NULL COMMENT '商品描述',
  `price` DECIMAL(10,2) NOT NULL COMMENT '商品价格',
  `market_price` DECIMAL(10,2) DEFAULT NULL COMMENT '市场价',
  `stock` INT DEFAULT 0 COMMENT '总库存',
  `sales` INT DEFAULT 0 COMMENT '销量',
  `view_count` INT DEFAULT 0 COMMENT '浏览量',
  `is_on_sale` TINYINT DEFAULT 1 COMMENT '是否上架: 0-下架, 1-上架',
  `is_new` TINYINT DEFAULT 0 COMMENT '是否新品: 0-否, 1-是',
  `is_hot` TINYINT DEFAULT 0 COMMENT '是否热门: 0-否, 1-是',
  `sort_order` INT DEFAULT 0 COMMENT '排序',
  `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` TINYINT DEFAULT 0 COMMENT '逻辑删除: 0-未删除, 1-已删除',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `uk_product_products_product_code` (`product_code`),
  INDEX `idx_product_products_category_id` (`category_id`),
  INDEX `idx_product_products_is_on_sale` (`is_on_sale`),
  INDEX `idx_product_products_is_new` (`is_new`),
  INDEX `idx_product_products_is_hot` (`is_hot`),
  INDEX `idx_product_products_sales` (`sales`),
  INDEX `idx_product_products_view_count` (`view_count`),
  INDEX `idx_product_products_deleted` (`deleted`),
  CONSTRAINT `fk_product_products_category_id` FOREIGN KEY (`category_id`) REFERENCES `product_categories` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='商品表';

-- 插入商品模拟数据
INSERT INTO `product_products` (`category_id`, `product_name`, `product_code`, `subtitle`, `description`, `price`, `market_price`, `stock`, `sales`, `view_count`, `is_on_sale`, `is_new`, `is_hot`, `sort_order`, `created_at`, `updated_at`, `deleted`) VALUES
(2, 'Apple iPhone 15 Pro', 'IPHONE15PRO', 'A17 Pro芯片，钛金属设计', 'iPhone 15 Pro 采用钛金属设计，搭载 A17 Pro 芯片，支持 USB-C 接口', 8999.00, 9999.00, 100, 50, 1000, 1, 1, 1, 1, '2024-01-01 00:00:00', '2024-01-01 00:00:00', 0),
(3, 'MacBook Pro 14英寸', 'MACBOOKPRO14', 'M3 Pro芯片，液态视网膜XDR显示屏', 'MacBook Pro 14英寸搭载 M3 Pro 芯片，配备液态视网膜 XDR 显示屏', 15999.00, 16999.00, 50, 20, 500, 1, 1, 1, 2, '2024-01-01 00:00:00', '2024-01-01 00:00:00', 0),
(5, '男士纯棉T恤', 'MEN_T_SHIRT', '舒适透气，多种颜色可选', '男士纯棉T恤，采用100%棉材质，舒适透气，多种颜色和尺码可选', 99.00, 129.00, 200, 100, 2000, 1, 0, 1, 3, '2024-01-01 00:00:00', '2024-01-01 00:00:00', 0),
(6, '女士连衣裙', 'WOMEN_DRESS', '优雅气质，收腰设计', '女士连衣裙，采用收腰设计，展现优雅气质，适合各种场合', 199.00, 259.00, 150, 80, 1500, 1, 1, 0, 4, '2024-01-01 00:00:00', '2024-01-01 00:00:00', 0),
(2, 'Samsung Galaxy S24 Ultra', 'SAMSUNG_S24_ULTRA', 'S Pen内置，200MP主摄像头', 'Samsung Galaxy S24 Ultra 内置 S Pen，配备 200MP 主摄像头', 7999.00, 8999.00, 80, 30, 800, 1, 1, 0, 5, '2024-01-01 00:00:00', '2024-01-01 00:00:00', 0);

-- 创建商品属性表
CREATE TABLE IF NOT EXISTS `product_attributes` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '属性ID',
  `category_id` BIGINT NOT NULL COMMENT '分类ID',
  `attribute_name` VARCHAR(50) NOT NULL COMMENT '属性名称',
  `attribute_code` VARCHAR(50) DEFAULT NULL COMMENT '属性编码',
  `attribute_type` TINYINT NOT NULL COMMENT '属性类型: 1-销售属性, 2-非销售属性',
  `is_required` TINYINT DEFAULT 0 COMMENT '是否必填: 0-否, 1-是',
  `sort_order` INT DEFAULT 0 COMMENT '排序',
  `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` TINYINT DEFAULT 0 COMMENT '逻辑删除: 0-未删除, 1-已删除',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_product_attributes_category_id` (`category_id`),
  INDEX `idx_product_attributes_attribute_type` (`attribute_type`),
  INDEX `idx_product_attributes_deleted` (`deleted`),
  CONSTRAINT `fk_product_attributes_category_id` FOREIGN KEY (`category_id`) REFERENCES `product_categories` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='商品属性表';

-- 插入商品属性模拟数据
INSERT INTO `product_attributes` (`category_id`, `attribute_name`, `attribute_code`, `attribute_type`, `is_required`, `sort_order`, `created_at`, `updated_at`, `deleted`) VALUES
(2, '颜色', 'COLOR', 1, 1, 1, '2024-01-01 00:00:00', '2024-01-01 00:00:00', 0),
(2, '内存', 'MEMORY', 1, 1, 2, '2024-01-01 00:00:00', '2024-01-01 00:00:00', 0),
(2, '存储', 'STORAGE', 1, 1, 3, '2024-01-01 00:00:00', '2024-01-01 00:00:00', 0),
(5, '颜色', 'COLOR', 1, 1, 1, '2024-01-01 00:00:00', '2024-01-01 00:00:00', 0),
(5, '尺码', 'SIZE', 1, 1, 2, '2024-01-01 00:00:00', '2024-01-01 00:00:00', 0),
(6, '颜色', 'COLOR', 1, 1, 1, '2024-01-01 00:00:00', '2024-01-01 00:00:00', 0),
(6, '尺码', 'SIZE', 1, 1, 2, '2024-01-01 00:00:00', '2024-01-01 00:00:00', 0);

-- 创建商品图片表
CREATE TABLE IF NOT EXISTS `product_images` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '图片ID',
  `product_id` BIGINT NOT NULL COMMENT '商品ID',
  `image_url` VARCHAR(255) NOT NULL COMMENT '图片URL',
  `image_type` TINYINT DEFAULT 1 COMMENT '图片类型: 1-主图, 2-详情图, 3-缩略图',
  `sort_order` INT DEFAULT 0 COMMENT '排序',
  `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` TINYINT DEFAULT 0 COMMENT '逻辑删除: 0-未删除, 1-已删除',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_product_images_product_id` (`product_id`),
  INDEX `idx_product_images_image_type` (`image_type`),
  INDEX `idx_product_images_sort_order` (`sort_order`),
  INDEX `idx_product_images_deleted` (`deleted`),
  CONSTRAINT `fk_product_images_product_id` FOREIGN KEY (`product_id`) REFERENCES `product_products` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='商品图片表';

-- 插入商品图片模拟数据
INSERT INTO `product_images` (`product_id`, `image_url`, `image_type`, `sort_order`, `created_at`, `updated_at`, `deleted`) VALUES
(1, 'https://example.com/iphone15pro-1.jpg', 1, 1, '2024-01-01 00:00:00', '2024-01-01 00:00:00', 0),
(1, 'https://example.com/iphone15pro-2.jpg', 2, 2, '2024-01-01 00:00:00', '2024-01-01 00:00:00', 0),
(1, 'https://example.com/iphone15pro-3.jpg', 2, 3, '2024-01-01 00:00:00', '2024-01-01 00:00:00', 0),
(2, 'https://example.com/macbookpro-1.jpg', 1, 1, '2024-01-01 00:00:00', '2024-01-01 00:00:00', 0),
(2, 'https://example.com/macbookpro-2.jpg', 2, 2, '2024-01-01 00:00:00', '2024-01-01 00:00:00', 0),
(3, 'https://example.com/tshirt-1.jpg', 1, 1, '2024-01-01 00:00:00', '2024-01-01 00:00:00', 0),
(4, 'https://example.com/dress-1.jpg', 1, 1, '2024-01-01 00:00:00', '2024-01-01 00:00:00', 0),
(5, 'https://example.com/s24ultra-1.jpg', 1, 1, '2024-01-01 00:00:00', '2024-01-01 00:00:00', 0);

-- 创建商品SKU表
CREATE TABLE IF NOT EXISTS `product_skus` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT 'SKU ID',
  `product_id` BIGINT NOT NULL COMMENT '商品ID',
  `sku_code` VARCHAR(50) NOT NULL COMMENT 'SKU编码',
  `sku_name` VARCHAR(100) NOT NULL COMMENT 'SKU名称',
  `price` DECIMAL(10,2) NOT NULL COMMENT 'SKU价格',
  `stock` INT DEFAULT 0 COMMENT 'SKU库存',
  `sales` INT DEFAULT 0 COMMENT 'SKU销量',
  `is_on_sale` TINYINT DEFAULT 1 COMMENT '是否上架: 0-下架, 1-上架',
  `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` TINYINT DEFAULT 0 COMMENT '逻辑删除: 0-未删除, 1-已删除',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `uk_product_skus_sku_code` (`sku_code`),
  INDEX `idx_product_skus_product_id` (`product_id`),
  INDEX `idx_product_skus_is_on_sale` (`is_on_sale`),
  INDEX `idx_product_skus_stock` (`stock`),
  INDEX `idx_product_skus_deleted` (`deleted`),
  CONSTRAINT `fk_product_skus_product_id` FOREIGN KEY (`product_id`) REFERENCES `product_products` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='商品SKU表';

-- 插入商品SKU模拟数据
INSERT INTO `product_skus` (`product_id`, `sku_code`, `sku_name`, `price`, `stock`, `sales`, `is_on_sale`, `created_at`, `updated_at`, `deleted`) VALUES
(1, 'IPHONE15PRO-128G-BLACK', 'Apple iPhone 15 Pro 128GB 黑色', 8999.00, 30, 20, 1, '2024-01-01 00:00:00', '2024-01-01 00:00:00', 0),
(1, 'IPHONE15PRO-256G-BLACK', 'Apple iPhone 15 Pro 256GB 黑色', 9999.00, 40, 15, 1, '2024-01-01 00:00:00', '2024-01-01 00:00:00', 0),
(1, 'IPHONE15PRO-512G-BLACK', 'Apple iPhone 15 Pro 512GB 黑色', 11999.00, 30, 15, 1, '2024-01-01 00:00:00', '2024-01-01 00:00:00', 0),
(3, 'MEN_TSHIRT-S-WHITE', '男士纯棉T恤 S码 白色', 99.00, 50, 30, 1, '2024-01-01 00:00:00', '2024-01-01 00:00:00', 0),
(3, 'MEN_TSHIRT-M-WHITE', '男士纯棉T恤 M码 白色', 99.00, 60, 40, 1, '2024-01-01 00:00:00', '2024-01-01 00:00:00', 0),
(3, 'MEN_TSHIRT-L-WHITE', '男士纯棉T恤 L码 白色', 99.00, 40, 30, 1, '2024-01-01 00:00:00', '2024-01-01 00:00:00', 0);

-- 创建商品SKU属性关联表
CREATE TABLE IF NOT EXISTS `product_sku_attributes` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '关联ID',
  `sku_id` BIGINT NOT NULL COMMENT 'SKU ID',
  `attribute_id` BIGINT NOT NULL COMMENT '属性ID',
  `attribute_value` VARCHAR(100) NOT NULL COMMENT '属性值',
  `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` TINYINT DEFAULT 0 COMMENT '逻辑删除: 0-未删除, 1-已删除',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `uk_product_sku_attributes_sku_attr` (`sku_id`, `attribute_id`),
  INDEX `idx_product_sku_attributes_sku_id` (`sku_id`),
  INDEX `idx_product_sku_attributes_attribute_id` (`attribute_id`),
  INDEX `idx_product_sku_attributes_deleted` (`deleted`),
  CONSTRAINT `fk_product_sku_attributes_sku_id` FOREIGN KEY (`sku_id`) REFERENCES `product_skus` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_product_sku_attributes_attribute_id` FOREIGN KEY (`attribute_id`) REFERENCES `product_attributes` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='商品SKU属性关联表';

-- 插入商品SKU属性关联模拟数据
INSERT INTO `product_sku_attributes` (`sku_id`, `attribute_id`, `attribute_value`, `created_at`, `updated_at`, `deleted`) VALUES
(1, 1, '黑色', '2024-01-01 00:00:00', '2024-01-01 00:00:00', 0),
(1, 3, '128GB', '2024-01-01 00:00:00', '2024-01-01 00:00:00', 0),
(2, 1, '黑色', '2024-01-01 00:00:00', '2024-01-01 00:00:00', 0),
(2, 3, '256GB', '2024-01-01 00:00:00', '2024-01-01 00:00:00', 0),
(3, 1, '黑色', '2024-01-01 00:00:00', '2024-01-01 00:00:00', 0),
(3, 3, '512GB', '2024-01-01 00:00:00', '2024-01-01 00:00:00', 0),
(4, 4, '白色', '2024-01-01 00:00:00', '2024-01-01 00:00:00', 0),
(4, 5, 'S', '2024-01-01 00:00:00', '2024-01-01 00:00:00', 0),
(5, 4, '白色', '2024-01-01 00:00:00', '2024-01-01 00:00:00', 0),
(5, 5, 'M', '2024-01-01 00:00:00', '2024-01-01 00:00:00', 0),
(6, 4, '白色', '2024-01-01 00:00:00', '2024-01-01 00:00:00', 0),
(6, 5, 'L', '2024-01-01 00:00:00', '2024-01-01 00:00:00', 0);

-- 创建订单主表
CREATE TABLE IF NOT EXISTS `order_orders` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '订单ID',
  `order_no` VARCHAR(50) NOT NULL COMMENT '订单编号',
  `customer_id` BIGINT NOT NULL COMMENT '客户ID',
  `customer_name` VARCHAR(50) NOT NULL COMMENT '客户名称',
  `total_amount` DECIMAL(10,2) NOT NULL COMMENT '订单总金额',
  `actual_amount` DECIMAL(10,2) NOT NULL COMMENT '实际支付金额',
  `discount_amount` DECIMAL(10,2) DEFAULT 0.00 COMMENT '优惠金额',
  `shipping_amount` DECIMAL(10,2) DEFAULT 0.00 COMMENT '运费',
  `order_status` TINYINT NOT NULL COMMENT '订单状态: 0-待付款, 1-待发货, 2-待收货, 3-已完成, 4-已取消, 5-已退款, 6-已退货',
  `pay_status` TINYINT DEFAULT 0 COMMENT '支付状态: 0-未支付, 1-已支付, 2-支付失败',
  `pay_type` TINYINT DEFAULT NULL COMMENT '支付方式: 1-支付宝, 2-微信支付, 3-银行卡',
  `pay_time` TIMESTAMP DEFAULT NULL COMMENT '支付时间',
  `shipping_name` VARCHAR(20) DEFAULT NULL COMMENT '物流公司名称',
  `shipping_no` VARCHAR(50) DEFAULT NULL COMMENT '物流单号',
  `consignee` VARCHAR(50) NOT NULL COMMENT '收货人',
  `phone` VARCHAR(20) NOT NULL COMMENT '收货人电话',
  `province` VARCHAR(50) DEFAULT NULL COMMENT '省份',
  `city` VARCHAR(50) DEFAULT NULL COMMENT '城市',
  `district` VARCHAR(50) DEFAULT NULL COMMENT '区县',
  `address` VARCHAR(255) NOT NULL COMMENT '详细地址',
  `remark` VARCHAR(255) DEFAULT NULL COMMENT '订单备注',
  `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` TINYINT DEFAULT 0 COMMENT '逻辑删除: 0-未删除, 1-已删除',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `uk_order_orders_order_no` (`order_no`),
  INDEX `idx_order_orders_customer_id` (`customer_id`),
  INDEX `idx_order_orders_order_status` (`order_status`),
  INDEX `idx_order_orders_pay_status` (`pay_status`),
  INDEX `idx_order_orders_pay_type` (`pay_type`),
  INDEX `idx_order_orders_created_at` (`created_at`),
  INDEX `idx_order_orders_updated_at` (`updated_at`),
  INDEX `idx_order_orders_deleted` (`deleted`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='订单主表';

-- 插入订单主表模拟数据
INSERT INTO `order_orders` (`order_no`, `customer_id`, `customer_name`, `total_amount`, `actual_amount`, `discount_amount`, `shipping_amount`, `order_status`, `pay_status`, `pay_type`, `pay_time`, `shipping_name`, `shipping_no`, `consignee`, `phone`, `province`, `city`, `district`, `address`, `remark`, `created_at`, `updated_at`, `deleted`) VALUES
('20240101000001', 2, 'zhangsan', 9098.00, 8998.00, 100.00, 0.00, 3, 1, 1, '2024-01-01 10:30:00', '顺丰速运', 'SF1234567890', '张三', '13800138001', '北京市', '北京市', '朝阳区', '朝阳区建国路88号', '尽快发货', '2024-01-01 10:20:00', '2024-01-03 15:00:00', 0),
('20240102000002', 3, 'lisi', 10097.00, 9997.00, 100.00, 0.00, 2, 1, 2, '2024-01-02 14:45:00', '中通快递', 'ZT9876543210', '李四', '13800138002', '上海市', '上海市', '浦东新区', '浦东新区陆家嘴环路1000号', NULL, '2024-01-02 14:30:00', '2024-01-03 10:00:00', 0),
('20240103000003', 4, 'wangwu', 99.00, 99.00, 0.00, 0.00, 0, 0, NULL, NULL, NULL, NULL, '王五', '13800138003', '广州市', '广州市', '天河区', '天河区天河路385号', NULL, '2024-01-03 09:15:00', '2024-01-03 09:15:00', 0),
('20240104000004', 5, 'zhaoliu', 199.00, 199.00, 0.00, 10.00, 1, 1, 1, '2024-01-04 16:20:00', NULL, NULL, '赵六', '13800138004', '深圳市', '深圳市', '南山区', '南山区科技园南区10号', '包装完好', '2024-01-04 16:00:00', '2024-01-04 16:20:00', 0),
('20240105000005', 2, 'zhangsan', 8098.00, 7998.00, 100.00, 0.00, 4, 1, 1, '2024-01-05 11:00:00', NULL, NULL, '张三', '13800138001', '北京市', '北京市', '朝阳区', '朝阳区建国路88号', '商品不需要了', '2024-01-05 10:50:00', '2024-01-05 11:30:00', 0);

-- 创建订单商品表
CREATE TABLE IF NOT EXISTS `order_items` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '订单商品ID',
  `order_id` BIGINT NOT NULL COMMENT '订单ID',
  `product_id` BIGINT NOT NULL COMMENT '商品ID',
  `product_name` VARCHAR(100) NOT NULL COMMENT '商品名称',
  `product_code` VARCHAR(50) NOT NULL COMMENT '商品编码',
  `sku_id` BIGINT NOT NULL COMMENT 'SKU ID',
  `sku_code` VARCHAR(50) NOT NULL COMMENT 'SKU编码',
  `sku_name` VARCHAR(100) NOT NULL COMMENT 'SKU名称',
  `price` DECIMAL(10,2) NOT NULL COMMENT '商品单价',
  `quantity` INT NOT NULL COMMENT '购买数量',
  `total_price` DECIMAL(10,2) NOT NULL COMMENT '商品总价(单价*数量)',
  `image_url` VARCHAR(255) DEFAULT NULL COMMENT '商品图片',
  `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` TINYINT DEFAULT 0 COMMENT '逻辑删除: 0-未删除, 1-已删除',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_order_items_order_id` (`order_id`),
  INDEX `idx_order_items_product_id` (`product_id`),
  INDEX `idx_order_items_sku_id` (`sku_id`),
  INDEX `idx_order_items_deleted` (`deleted`),
  CONSTRAINT `fk_order_items_order_id` FOREIGN KEY (`order_id`) REFERENCES `order_orders` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='订单商品表';

-- 插入订单商品模拟数据
INSERT INTO `order_items` (`order_id`, `product_id`, `product_name`, `product_code`, `sku_id`, `sku_code`, `sku_name`, `price`, `quantity`, `total_price`, `image_url`, `created_at`, `updated_at`, `deleted`) VALUES
(1, 1, 'Apple iPhone 15 Pro', 'IPHONE15PRO', 1, 'IPHONE15PRO-128G-BLACK', 'Apple iPhone 15 Pro 128GB 黑色', 8999.00, 1, 8999.00, 'https://example.com/iphone15pro-1.jpg', '2024-01-01 10:20:00', '2024-01-01 10:20:00', 0),
(2, 2, 'MacBook Pro 14英寸', 'MACBOOKPRO14', 2, 'IPHONE15PRO-256G-BLACK', 'Apple iPhone 15 Pro 256GB 黑色', 9999.00, 1, 9999.00, 'https://example.com/macbookpro-1.jpg', '2024-01-02 14:30:00', '2024-01-02 14:30:00', 0),
(3, 3, '男士纯棉T恤', 'MEN_T_SHIRT', 4, 'MEN_TSHIRT-S-WHITE', '男士纯棉T恤 S码 白色', 99.00, 1, 99.00, 'https://example.com/tshirt-1.jpg', '2024-01-03 09:15:00', '2024-01-03 09:15:00', 0),
(4, 4, '女士连衣裙', 'WOMEN_DRESS', 5, 'MEN_TSHIRT-M-WHITE', '男士纯棉T恤 M码 白色', 199.00, 1, 199.00, 'https://example.com/dress-1.jpg', '2024-01-04 16:00:00', '2024-01-04 16:00:00', 0),
(5, 5, 'Samsung Galaxy S24 Ultra', 'SAMSUNG_S24_ULTRA', 6, 'MEN_TSHIRT-L-WHITE', '男士纯棉T恤 L码 白色', 7999.00, 1, 7999.00, 'https://example.com/s24ultra-1.jpg', '2024-01-05 10:50:00', '2024-01-05 10:50:00', 0);

-- 创建订单物流表
CREATE TABLE IF NOT EXISTS `order_logistics` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '物流ID',
  `order_id` BIGINT NOT NULL COMMENT '订单ID',
  `shipping_name` VARCHAR(20) NOT NULL COMMENT '物流公司名称',
  `shipping_no` VARCHAR(50) NOT NULL COMMENT '物流单号',
  `logistics_status` TINYINT DEFAULT 0 COMMENT '物流状态: 0-待发货, 1-已发货, 2-运输中, 3-已签收, 4-异常',
  `consignee` VARCHAR(50) NOT NULL COMMENT '收货人',
  `phone` VARCHAR(20) NOT NULL COMMENT '收货人电话',
  `province` VARCHAR(50) DEFAULT NULL COMMENT '省份',
  `city` VARCHAR(50) DEFAULT NULL COMMENT '城市',
  `district` VARCHAR(50) DEFAULT NULL COMMENT '区县',
  `address` VARCHAR(255) NOT NULL COMMENT '详细地址',
  `ship_time` TIMESTAMP DEFAULT NULL COMMENT '发货时间',
  `delivery_time` TIMESTAMP DEFAULT NULL COMMENT '配送时间',
  `sign_time` TIMESTAMP DEFAULT NULL COMMENT '签收时间',
  `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` TINYINT DEFAULT 0 COMMENT '逻辑删除: 0-未删除, 1-已删除',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `uk_order_logistics_order_id` (`order_id`),
  INDEX `idx_order_logistics_shipping_no` (`shipping_no`),
  INDEX `idx_order_logistics_logistics_status` (`logistics_status`),
  INDEX `idx_order_logistics_deleted` (`deleted`),
  CONSTRAINT `fk_order_logistics_order_id` FOREIGN KEY (`order_id`) REFERENCES `order_orders` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='订单物流表';

-- 插入订单物流模拟数据
INSERT INTO `order_logistics` (`order_id`, `shipping_name`, `shipping_no`, `logistics_status`, `consignee`, `phone`, `province`, `city`, `district`, `address`, `ship_time`, `delivery_time`, `sign_time`, `created_at`, `updated_at`, `deleted`) VALUES
(1, '顺丰速运', 'SF1234567890', 3, '张三', '13800138001', '北京市', '北京市', '朝阳区', '朝阳区建国路88号', '2024-01-02 10:00:00', '2024-01-03 09:00:00', '2024-01-03 15:00:00', '2024-01-01 10:30:00', '2024-01-03 15:00:00', 0),
(2, '中通快递', 'ZT9876543210', 2, '李四', '13800138002', '上海市', '上海市', '浦东新区', '浦东新区陆家嘴环路1000号', '2024-01-03 10:00:00', '2024-01-04 09:00:00', NULL, '2024-01-02 14:45:00', '2024-01-04 09:00:00', 0);

-- 创建订单状态历史表
CREATE TABLE IF NOT EXISTS `order_status_history` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '状态历史ID',
  `order_id` BIGINT NOT NULL COMMENT '订单ID',
  `order_status` TINYINT NOT NULL COMMENT '订单状态',
  `status_name` VARCHAR(20) NOT NULL COMMENT '状态名称',
  `remark` VARCHAR(255) DEFAULT NULL COMMENT '状态变更备注',
  `operated_by` VARCHAR(50) DEFAULT NULL COMMENT '操作人',
  `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` TINYINT DEFAULT 0 COMMENT '逻辑删除: 0-未删除, 1-已删除',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_order_status_history_order_id` (`order_id`),
  INDEX `idx_order_status_history_order_status` (`order_status`),
  INDEX `idx_order_status_history_created_at` (`created_at`),
  INDEX `idx_order_status_history_deleted` (`deleted`),
  CONSTRAINT `fk_order_status_history_order_id` FOREIGN KEY (`order_id`) REFERENCES `order_orders` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='订单状态历史表';

-- 插入订单状态历史模拟数据
INSERT INTO `order_status_history` (`order_id`, `order_status`, `status_name`, `remark`, `operated_by`, `created_at`, `updated_at`, `deleted`) VALUES
(1, 0, '待付款', '订单创建', NULL, '2024-01-01 10:20:00', '2024-01-01 10:20:00', 0),
(1, 1, '待发货', '支付成功', '系统', '2024-01-01 10:30:00', '2024-01-01 10:30:00', 0),
(1, 2, '待收货', '商家已发货', 'admin', '2024-01-02 10:00:00', '2024-01-02 10:00:00', 0),
(1, 3, '已完成', '用户已签收', NULL, '2024-01-03 15:00:00', '2024-01-03 15:00:00', 0),
(2, 0, '待付款', '订单创建', NULL, '2024-01-02 14:30:00', '2024-01-02 14:30:00', 0),
(2, 1, '待发货', '支付成功', '系统', '2024-01-02 14:45:00', '2024-01-02 14:45:00', 0),
(2, 2, '待收货', '商家已发货', 'admin', '2024-01-03 10:00:00', '2024-01-03 10:00:00', 0),
(3, 0, '待付款', '订单创建', NULL, '2024-01-03 09:15:00', '2024-01-03 09:15:00', 0),
(4, 0, '待付款', '订单创建', NULL, '2024-01-04 16:00:00', '2024-01-04 16:00:00', 0),
(4, 1, '待发货', '支付成功', '系统', '2024-01-04 16:20:00', '2024-01-04 16:20:00', 0),
(5, 0, '待付款', '订单创建', NULL, '2024-01-05 10:50:00', '2024-01-05 10:50:00', 0),
(5, 1, '待发货', '支付成功', '系统', '2024-01-05 11:00:00', '2024-01-05 11:00:00', 0),
(5, 4, '已取消', '用户取消订单', 'zhangsan', '2024-01-05 11:30:00', '2024-01-05 11:30:00', 0);

-- 创建客户等级表
CREATE TABLE IF NOT EXISTS `customer_levels` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '等级ID',
  `level_name` VARCHAR(50) NOT NULL COMMENT '等级名称',
  `level_code` VARCHAR(50) NOT NULL COMMENT '等级编码',
  `min_points` INT NOT NULL COMMENT '最低积分',
  `max_points` INT DEFAULT NULL COMMENT '最高积分',
  `discount` DECIMAL(5,2) DEFAULT 1.00 COMMENT '折扣率',
  `description` VARCHAR(255) DEFAULT NULL COMMENT '等级描述',
  `sort_order` INT DEFAULT 0 COMMENT '排序',
  `is_active` TINYINT DEFAULT 1 COMMENT '是否激活: 0-禁用, 1-启用',
  `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` TINYINT DEFAULT 0 COMMENT '逻辑删除: 0-未删除, 1-已删除',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `uk_customer_levels_level_code` (`level_code`),
  INDEX `idx_customer_levels_min_points` (`min_points`),
  INDEX `idx_customer_levels_is_active` (`is_active`),
  INDEX `idx_customer_levels_sort_order` (`sort_order`),
  INDEX `idx_customer_levels_deleted` (`deleted`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='客户等级表';

-- 插入客户等级模拟数据
INSERT INTO `customer_levels` (`level_name`, `level_code`, `min_points`, `max_points`, `discount`, `description`, `sort_order`, `is_active`, `created_at`, `updated_at`, `deleted`) VALUES
('普通会员', 'LEVEL_1', 0, 999, 1.00, '普通会员，无折扣', 1, 1, '2024-01-01 00:00:00', '2024-01-01 00:00:00', 0),
('银卡会员', 'LEVEL_2', 1000, 4999, 0.95, '银卡会员，享受95折优惠', 2, 1, '2024-01-01 00:00:00', '2024-01-01 00:00:00', 0),
('金卡会员', 'LEVEL_3', 5000, 9999, 0.90, '金卡会员，享受9折优惠', 3, 1, '2024-01-01 00:00:00', '2024-01-01 00:00:00', 0),
('钻石会员', 'LEVEL_4', 10000, NULL, 0.85, '钻石会员，享受85折优惠', 4, 1, '2024-01-01 00:00:00', '2024-01-01 00:00:00', 0);

-- 创建客户表
CREATE TABLE IF NOT EXISTS `customer_customers` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '客户ID',
  `customer_no` VARCHAR(50) NOT NULL COMMENT '客户编号',
  `level_id` BIGINT DEFAULT 1 COMMENT '等级ID',
  `username` VARCHAR(50) NOT NULL COMMENT '用户名',
  `password` VARCHAR(255) NOT NULL COMMENT '密码',
  `real_name` VARCHAR(50) DEFAULT NULL COMMENT '真实姓名',
  `email` VARCHAR(100) DEFAULT NULL COMMENT '邮箱',
  `phone` VARCHAR(20) DEFAULT NULL COMMENT '手机号',
  `avatar` VARCHAR(255) DEFAULT NULL COMMENT '头像',
  `gender` TINYINT DEFAULT 0 COMMENT '性别: 0-未知, 1-男, 2-女',
  `birthday` DATE DEFAULT NULL COMMENT '生日',
  `points` INT DEFAULT 0 COMMENT '当前积分',
  `total_consumption` DECIMAL(10,2) DEFAULT 0.00 COMMENT '累计消费金额',
  `order_count` INT DEFAULT 0 COMMENT '累计订单数',
  `last_order_time` TIMESTAMP DEFAULT NULL COMMENT '最后订单时间',
  `is_active` TINYINT DEFAULT 1 COMMENT '是否激活: 0-禁用, 1-启用',
  `is_vip` TINYINT DEFAULT 0 COMMENT '是否VIP: 0-否, 1-是',
  `register_ip` VARCHAR(50) DEFAULT NULL COMMENT '注册IP',
  `register_time` TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '注册时间',
  `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` TINYINT DEFAULT 0 COMMENT '逻辑删除: 0-未删除, 1-已删除',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `uk_customer_customers_customer_no` (`customer_no`),
  UNIQUE KEY `uk_customer_customers_username` (`username`),
  UNIQUE KEY `uk_customer_customers_email` (`email`),
  UNIQUE KEY `uk_customer_customers_phone` (`phone`),
  INDEX `idx_customer_customers_level_id` (`level_id`),
  INDEX `idx_customer_customers_points` (`points`),
  INDEX `idx_customer_customers_total_consumption` (`total_consumption`),
  INDEX `idx_customer_customers_is_active` (`is_active`),
  INDEX `idx_customer_customers_register_time` (`register_time`),
  INDEX `idx_customer_customers_deleted` (`deleted`),
  CONSTRAINT `fk_customer_customers_level_id` FOREIGN KEY (`level_id`) REFERENCES `customer_levels` (`id`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='客户表';

-- 插入客户模拟数据
INSERT INTO `customer_customers` (`customer_no`, `level_id`, `username`, `password`, `real_name`, `email`, `phone`, `gender`, `birthday`, `points`, `total_consumption`, `order_count`, `last_order_time`, `is_active`, `is_vip`, `register_ip`, `register_time`, `created_at`, `updated_at`, `deleted`) VALUES
('CUST202401010001', 4, 'customer1', '$2a$10$9vKP4lGYP7bBNbMqG6qyou/QIcFz/q6eB0cJ5Y5p8N5Y5p8N5Y5p8', '客户1', 'customer1@ecommerce.com', '13900139001', 1, '1990-01-01', 12000, 50000.00, 15, '2024-01-01 10:00:00', 1, 1, '192.168.1.100', '2023-01-01 00:00:00', '2023-01-01 00:00:00', '2024-01-01 10:00:00', 0),
('CUST202401010002', 3, 'customer2', '$2a$10$9vKP4lGYP7bBNbMqG6qyou/QIcFz/q6eB0cJ5Y5p8N5Y5p8N5Y5p8', '客户2', 'customer2@ecommerce.com', '13900139002', 2, '1995-05-10', 8000, 30000.00, 8, '2024-01-02 14:00:00', 1, 1, '192.168.1.101', '2023-03-15 00:00:00', '2023-03-15 00:00:00', '2024-01-02 14:00:00', 0),
('CUST202401010003', 2, 'customer3', '$2a$10$9vKP4lGYP7bBNbMqG6qyou/QIcFz/q6eB0cJ5Y5p8N5Y5p8N5Y5p8', '客户3', 'customer3@ecommerce.com', '13900139003', 1, '2000-10-20', 3000, 15000.00, 5, '2024-01-03 09:00:00', 1, 0, '192.168.1.102', '2023-06-20 00:00:00', '2023-06-20 00:00:00', '2024-01-03 09:00:00', 0),
('CUST202401010004', 1, 'customer4', '$2a$10$9vKP4lGYP7bBNbMqG6qyou/QIcFz/q6eB0cJ5Y5p8N5Y5p8N5Y5p8', '客户4', 'customer4@ecommerce.com', '13900139004', 2, '1998-03-15', 500, 5000.00, 2, '2024-01-04 16:00:00', 1, 0, '192.168.1.103', '2023-09-05 00:00:00', '2023-09-05 00:00:00', '2024-01-04 16:00:00', 0),
('CUST202401010005', 1, 'customer5', '$2a$10$9vKP4lGYP7bBNbMqG6qyou/QIcFz/q6eB0cJ5Y5p8N5Y5p8N5Y5p8', '客户5', 'customer5@ecommerce.com', '13900139005', 1, '2002-07-08', 100, 1000.00, 1, '2024-01-05 11:00:00', 1, 0, '192.168.1.104', '2024-01-01 00:00:00', '2024-01-01 00:00:00', '2024-01-05 11:00:00', 0);

-- 创建客户分组表
CREATE TABLE IF NOT EXISTS `customer_groups` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '分组ID',
  `group_name` VARCHAR(50) NOT NULL COMMENT '分组名称',
  `group_code` VARCHAR(50) NOT NULL COMMENT '分组编码',
  `description` VARCHAR(255) DEFAULT NULL COMMENT '分组描述',
  `sort_order` INT DEFAULT 0 COMMENT '排序',
  `is_active` TINYINT DEFAULT 1 COMMENT '是否激活: 0-禁用, 1-启用',
  `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` TINYINT DEFAULT 0 COMMENT '逻辑删除: 0-未删除, 1-已删除',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `uk_customer_groups_group_code` (`group_code`),
  INDEX `idx_customer_groups_is_active` (`is_active`),
  INDEX `idx_customer_groups_sort_order` (`sort_order`),
  INDEX `idx_customer_groups_deleted` (`deleted`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='客户分组表';

-- 插入客户分组模拟数据
INSERT INTO `customer_groups` (`group_name`, `group_code`, `description`, `sort_order`, `is_active`, `created_at`, `updated_at`, `deleted`) VALUES
('高价值客户', 'GROUP_HIGH_VALUE', '消费金额高的客户', 1, 1, '2024-01-01 00:00:00', '2024-01-01 00:00:00', 0),
('活跃客户', 'GROUP_ACTIVE', '近期有消费的客户', 2, 1, '2024-01-01 00:00:00', '2024-01-01 00:00:00', 0),
('新客户', 'GROUP_NEW', '新注册的客户', 3, 1, '2024-01-01 00:00:00', '2024-01-01 00:00:00', 0),
('沉睡客户', 'GROUP_SLEEP', '长期未消费的客户', 4, 1, '2024-01-01 00:00:00', '2024-01-01 00:00:00', 0);

-- 创建客户标签表
CREATE TABLE IF NOT EXISTS `customer_tags` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '标签ID',
  `tag_name` VARCHAR(50) NOT NULL COMMENT '标签名称',
  `tag_code` VARCHAR(50) NOT NULL COMMENT '标签编码',
  `description` VARCHAR(255) DEFAULT NULL COMMENT '标签描述',
  `sort_order` INT DEFAULT 0 COMMENT '排序',
  `is_active` TINYINT DEFAULT 1 COMMENT '是否激活: 0-禁用, 1-启用',
  `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` TINYINT DEFAULT 0 COMMENT '逻辑删除: 0-未删除, 1-已删除',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `uk_customer_tags_tag_code` (`tag_code`),
  INDEX `idx_customer_tags_is_active` (`is_active`),
  INDEX `idx_customer_tags_sort_order` (`sort_order`),
  INDEX `idx_customer_tags_deleted` (`deleted`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='客户标签表';

-- 插入客户标签模拟数据
INSERT INTO `customer_tags` (`tag_name`, `tag_code`, `description`, `sort_order`, `is_active`, `created_at`, `updated_at`, `deleted`) VALUES
('喜欢电子产品', 'TAG_ELECTRONICS', '经常购买电子产品的客户', 1, 1, '2024-01-01 00:00:00', '2024-01-01 00:00:00', 0),
('喜欢服装', 'TAG_CLOTHING', '经常购买服装的客户', 2, 1, '2024-01-01 00:00:00', '2024-01-01 00:00:00', 0),
('价格敏感型', 'TAG_PRICE_SENSITIVE', '关注促销活动的客户', 3, 1, '2024-01-01 00:00:00', '2024-01-01 00:00:00', 0),
('品牌忠诚型', 'TAG_BRAND_LOYAL', '只购买特定品牌的客户', 4, 1, '2024-01-01 00:00:00', '2024-01-01 00:00:00', 0),
('高频购买', 'TAG_HIGH_FREQUENCY', '购买频率高的客户', 5, 1, '2024-01-01 00:00:00', '2024-01-01 00:00:00', 0);

-- 创建客户分组关联表
CREATE TABLE IF NOT EXISTS `customer_customer_groups` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '关联ID',
  `customer_id` BIGINT NOT NULL COMMENT '客户ID',
  `group_id` BIGINT NOT NULL COMMENT '分组ID',
  `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` TINYINT DEFAULT 0 COMMENT '逻辑删除: 0-未删除, 1-已删除',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `uk_customer_customer_groups_customer_group` (`customer_id`, `group_id`),
  INDEX `idx_customer_customer_groups_customer_id` (`customer_id`),
  INDEX `idx_customer_customer_groups_group_id` (`group_id`),
  INDEX `idx_customer_customer_groups_deleted` (`deleted`),
  CONSTRAINT `fk_customer_customer_groups_customer_id` FOREIGN KEY (`customer_id`) REFERENCES `customer_customers` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_customer_customer_groups_group_id` FOREIGN KEY (`group_id`) REFERENCES `customer_groups` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='客户分组关联表';

-- 插入客户分组关联模拟数据
INSERT INTO `customer_customer_groups` (`customer_id`, `group_id`, `created_at`, `updated_at`, `deleted`) VALUES
(1, 1, '2024-01-01 00:00:00', '2024-01-01 00:00:00', 0),
(1, 2, '2024-01-01 00:00:00', '2024-01-01 00:00:00', 0),
(2, 1, '2024-01-01 00:00:00', '2024-01-01 00:00:00', 0),
(2, 2, '2024-01-01 00:00:00', '2024-01-01 00:00:00', 0),
(3, 2, '2024-01-01 00:00:00', '2024-01-01 00:00:00', 0),
(4, 3, '2024-01-01 00:00:00', '2024-01-01 00:00:00', 0),
(5, 3, '2024-01-01 00:00:00', '2024-01-01 00:00:00', 0);

-- 创建客户标签关联表
CREATE TABLE IF NOT EXISTS `customer_customer_tags` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '关联ID',
  `customer_id` BIGINT NOT NULL COMMENT '客户ID',
  `tag_id` BIGINT NOT NULL COMMENT '标签ID',
  `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` TINYINT DEFAULT 0 COMMENT '逻辑删除: 0-未删除, 1-已删除',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `uk_customer_customer_tags_customer_tag` (`customer_id`, `tag_id`),
  INDEX `idx_customer_customer_tags_customer_id` (`customer_id`),
  INDEX `idx_customer_customer_tags_tag_id` (`tag_id`),
  INDEX `idx_customer_customer_tags_deleted` (`deleted`),
  CONSTRAINT `fk_customer_customer_tags_customer_id` FOREIGN KEY (`customer_id`) REFERENCES `customer_customers` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_customer_customer_tags_tag_id` FOREIGN KEY (`tag_id`) REFERENCES `customer_tags` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='客户标签关联表';

-- 插入客户标签关联模拟数据
INSERT INTO `customer_customer_tags` (`customer_id`, `tag_id`, `created_at`, `updated_at`, `deleted`) VALUES
(1, 1, '2024-01-01 00:00:00', '2024-01-01 00:00:00', 0),
(1, 4, '2024-01-01 00:00:00', '2024-01-01 00:00:00', 0),
(1, 5, '2024-01-01 00:00:00', '2024-01-01 00:00:00', 0),
(2, 1, '2024-01-01 00:00:00', '2024-01-01 00:00:00', 0),
(2, 3, '2024-01-01 00:00:00', '2024-01-01 00:00:00', 0),
(3, 2, '2024-01-01 00:00:00', '2024-01-01 00:00:00', 0),
(3, 3, '2024-01-01 00:00:00', '2024-01-01 00:00:00', 0),
(4, 2, '2024-01-01 00:00:00', '2024-01-01 00:00:00', 0),
(5, 1, '2024-01-01 00:00:00', '2024-01-01 00:00:00', 0),
(5, 5, '2024-01-01 00:00:00', '2024-01-01 00:00:00', 0);

-- 创建客户积分表
CREATE TABLE IF NOT EXISTS `customer_points` (
  `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '积分ID',
  `customer_id` BIGINT NOT NULL COMMENT '客户ID',
  `points` INT NOT NULL COMMENT '积分变动值(正数增加,负数减少)',
  `balance` INT NOT NULL COMMENT '变动后积分余额',
  `type` TINYINT NOT NULL COMMENT '积分类型: 1-购物获得, 2-活动奖励, 3-积分兑换, 4-积分过期, 5-其他',
  `source` VARCHAR(50) DEFAULT NULL COMMENT '积分来源',
  `order_no` VARCHAR(50) DEFAULT NULL COMMENT '关联订单号',
  `remark` VARCHAR(255) DEFAULT NULL COMMENT '积分变动备注',
  `expire_time` TIMESTAMP DEFAULT NULL COMMENT '积分过期时间',
  `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` TINYINT DEFAULT 0 COMMENT '逻辑删除: 0-未删除, 1-已删除',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_customer_points_customer_id` (`customer_id`),
  INDEX `idx_customer_points_type` (`type`),
  INDEX `idx_customer_points_created_at` (`created_at`),
  INDEX `idx_customer_points_expire_time` (`expire_time`),
  INDEX `idx_customer_points_deleted` (`deleted`),
  CONSTRAINT `fk_customer_points_customer_id` FOREIGN KEY (`customer_id`) REFERENCES `customer_customers` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='客户积分表';

-- 插入客户积分模拟数据
INSERT INTO `customer_points` (`customer_id`, `points`, `balance`, `type`, `source`, `order_no`, `remark`, `expire_time`, `created_at`, `updated_at`, `deleted`) VALUES
(1, 1000, 1000, 1, '购物获得', '202312010001', '购买iPhone获得积分', '2025-12-31 23:59:59', '2023-12-01 10:00:00', '2023-12-01 10:00:00', 0),
(1, 2000, 3000, 1, '购物获得', '202312150001', '购买MacBook获得积分', '2025-12-31 23:59:59', '2023-12-15 14:00:00', '2023-12-15 14:00:00', 0),
(1, 5000, 8000, 1, '购物获得', '202401010001', '购买iPad获得积分', '2025-12-31 23:59:59', '2024-01-01 10:00:00', '2024-01-01 10:00:00', 0),
(2, 500, 500, 1, '购物获得', '202311200001', '购买服装获得积分', '2025-11-30 23:59:59', '2023-11-20 16:00:00', '2023-11-20 16:00:00', 0),
(3, 300, 300, 1, '购物获得', '202310100001', '购买日用品获得积分', '2025-10-31 23:59:59', '2023-10-10 09:00:00', '2023-10-10 09:00:00', 0),
(4, 100, 100, 1, '购物获得', '202309050001', '购买图书获得积分', '2025-09-30 23:59:59', '2023-09-05 11:00:00', '2023-09-05 11:00:00', 0),
(5, 100, 100, 2, '注册奖励', NULL, '新用户注册奖励', '2025-12-31 23:59:59', '2024-01-01 00:00:00', '2024-01-01 00:00:00', 0);
