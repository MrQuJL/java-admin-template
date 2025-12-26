-- 插入初始测试用户数据
-- 密码: 123456 (BCrypt加密)
INSERT INTO sys_user (username, password, real_name, phone, email, status, create_time, update_time) VALUES
('admin', '$2a$10$dXJ3SW6G7P50lGmMkkmwe.20cQQubK3.HZWzG3YB1tlRy.fqvM/BG', '管理员', '13800138001', 'admin@example.com', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('user1', '$2a$10$dXJ3SW6G7P50lGmMkkmwe.20cQQubK3.HZWzG3YB1tlRy.fqvM/BG', '用户1', '13800138002', 'user1@example.com', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 注意：上面的密码是加密后的'123456'，可以直接使用'123456'登录
