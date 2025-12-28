-- 创建数据库
CREATE DATABASE IF NOT EXISTS java_admin DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- 使用数据库
USE java_admin;

-- 创建用户表
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `username` VARCHAR(50) NOT NULL COMMENT '用户名',
  `password` VARCHAR(100) NOT NULL COMMENT '密码',
  `nickname` VARCHAR(50) DEFAULT NULL COMMENT '昵称',
  `gender` TINYINT(1) DEFAULT NULL COMMENT '性别（0-未知，1-男，2-女）',
  `phone` VARCHAR(20) DEFAULT NULL COMMENT '手机号',
  `email` VARCHAR(100) DEFAULT NULL COMMENT '邮箱',
  `avatar` VARCHAR(255) DEFAULT NULL COMMENT '头像URL',
  `status` TINYINT(1) DEFAULT '1' COMMENT '状态（0-禁用，1-启用）',
  `deleted` TINYINT(1) DEFAULT '0' COMMENT '逻辑删除（0-未删除，1-已删除）',
  `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_username` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户表';

-- 插入测试数据
INSERT INTO `user` (`username`, `password`, `nickname`, `gender`, `phone`, `email`, `avatar`, `status`) VALUES
('admin', '123456', '管理员', 1, '13800138000', 'admin@example.com', 'https://example.com/avatar/admin.jpg', 1),
('zhangsan', '123456', '张三', 1, '13800138001', 'zhangsan@example.com', 'https://example.com/avatar/zhangsan.jpg', 1),
('lisi', '123456', '李四', 2, '13800138002', 'lisi@example.com', 'https://example.com/avatar/lisi.jpg', 1),
('wangwu', '123456', '王五', 1, '13800138003', 'wangwu@example.com', 'https://example.com/avatar/wangwu.jpg', 1),
('zhaoliu', '123456', '赵六', 2, '13800138004', 'zhaoliu@example.com', 'https://example.com/avatar/zhaoliu.jpg', 0);

-- 查询数据
SELECT * FROM `user`;