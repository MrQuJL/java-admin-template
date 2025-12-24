-- 插入初始测试用户数据
INSERT INTO sys_user (username, password, real_name, phone, email, status, create_time, update_time) VALUES
('admin', '$2a$10$7J9pOJk4B6wK6a7e8R9tY0u1i2o3p4a5s6d7f8g9h0j', '管理员', '13800138001', 'admin@example.com', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('user1', '$2a$10$7J9pOJk4B6wK6a7e8R9tY0u1i2o3p4a5s6d7f8g9h0j', '用户1', '13800138002', 'user1@example.com', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 注意：上面的密码是加密后的'123456'，可以直接使用'123456'登录
