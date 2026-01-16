# 客户管理页面设计

## 1. 客户列表查询页面

### 1.1 页面概述
- **页面URL**: `/admin/customer/list`
- **功能描述**: 用于查询客户列表，支持按客户等级、注册时间、消费金额等条件筛选

### 1.2 页面布局
- **顶部筛选区**: 客户等级、注册时间、消费金额、客户状态等筛选条件
- **中部操作区**: 新增客户按钮、批量操作按钮组（批量禁用、批量启用、批量删除）
- **底部列表区**: 客户信息表格，支持分页、排序

### 1.3 筛选表单设计
| 字段名 | 类型 | 必填 | 说明 | 关联字段 |
|-------|------|------|------|----------|
| 客户编号 | 输入框 | 否 | 按客户编号模糊搜索 | customer_customers.customer_no |
| 用户名 | 输入框 | 否 | 按用户名模糊搜索 | customer_customers.username |
| 真实姓名 | 输入框 | 否 | 按真实姓名模糊搜索 | customer_customers.real_name |
| 手机号 | 输入框 | 否 | 按手机号模糊搜索 | customer_customers.phone |
| 邮箱 | 输入框 | 否 | 按邮箱模糊搜索 | customer_customers.email |
| 客户等级 | 下拉选择框 | 否 | 按客户等级筛选 | customer_customers.level_id |
| 客户分组 | 下拉选择框 | 否 | 按客户分组筛选 | customer_customer_groups.group_id |
| 客户标签 | 下拉选择框 | 否 | 按客户标签筛选 | customer_customer_tags.tag_id |
| 注册时间 | 日期范围选择器 | 否 | 按注册时间筛选 | customer_customers.register_time |
| 消费金额 | 数字范围选择器 | 否 | 按累计消费金额筛选 | customer_customers.total_consumption |
| 积分范围 | 数字范围选择器 | 否 | 按当前积分筛选 | customer_customers.points |
| 是否激活 | 单选框 | 否 | 按客户状态筛选 | customer_customers.is_active |
| 是否VIP | 单选框 | 否 | 按VIP状态筛选 | customer_customers.is_vip |

### 1.4 表格设计
| 列名 | 类型 | 操作 | 关联字段 |
|------|------|------|----------|
| 复选框 | 选择框 | 批量操作 | 无 |
| 客户编号 | 文本 | 点击查看详情 | customer_customers.customer_no |
| 用户名 | 文本 | 无 | customer_customers.username |
| 真实姓名 | 文本 | 无 | customer_customers.real_name |
| 手机号 | 文本 | 无 | customer_customers.phone |
| 邮箱 | 文本 | 无 | customer_customers.email |
| 客户等级 | 文本 | 无 | customer_levels.level_name |
| 当前积分 | 数字 | 无 | customer_customers.points |
| 累计消费 | 文本 | 无 | customer_customers.total_consumption |
| 累计订单 | 数字 | 无 | customer_customers.order_count |
| 最后订单时间 | 时间 | 无 | customer_customers.last_order_time |
| 注册时间 | 时间 | 无 | customer_customers.register_time |
| 客户状态 | 开关 | 编辑状态 | customer_customers.is_active |
| VIP状态 | 开关 | 编辑状态 | customer_customers.is_vip |
| 操作 | 按钮组 | 查看详情、编辑、禁用/启用、删除 | 无 |

### 1.5 交互设计
- 筛选条件支持实时搜索
- 支持按多条件组合筛选
- 支持表格列的拖拽排序和显示隐藏
- 支持表格数据的导出
- 点击客户编号跳转到客户详情页面
- 支持批量选择客户进行批量操作
- 支持快速切换客户状态和VIP状态

## 2. 客户详情查看页面

### 2.1 页面概述
- **页面URL**: `/admin/customer/detail/:customerId`
- **功能描述**: 用于查看客户的详细信息，包括基本信息、订单历史、积分记录等

### 2.2 页面布局
- **顶部操作区**: 返回按钮、编辑按钮、禁用/启用按钮
- **客户基本信息区**: 客户基本信息表单
- **标签页切换区**: 支持切换查看订单历史、积分记录、分组标签、地址管理等

### 2.3 客户基本信息表单
| 字段名 | 类型 | 说明 | 关联字段 |
|-------|------|------|----------|
| 客户编号 | 文本 | 客户唯一标识 | customer_customers.customer_no |
| 用户名 | 文本 | 客户登录用户名 | customer_customers.username |
| 真实姓名 | 文本 | 客户真实姓名 | customer_customers.real_name |
| 手机号 | 文本 | 客户手机号 | customer_customers.phone |
| 邮箱 | 文本 | 客户邮箱 | customer_customers.email |
| 头像 | 图片 | 客户头像 | customer_customers.avatar |
| 性别 | 文本 | 客户性别 | customer_customers.gender |
| 生日 | 日期 | 客户生日 | customer_customers.birthday |
| 客户等级 | 文本 | 客户等级名称 | customer_levels.level_name |
| 当前积分 | 数字 | 客户当前积分 | customer_customers.points |
| 累计消费 | 文本 | 客户累计消费金额 | customer_customers.total_consumption |
| 累计订单 | 数字 | 客户累计订单数 | customer_customers.order_count |
| 最后订单时间 | 时间 | 客户最后订单时间 | customer_customers.last_order_time |
| 注册IP | 文本 | 客户注册IP | customer_customers.register_ip |
| 注册时间 | 时间 | 客户注册时间 | customer_customers.register_time |
| 客户状态 | 标签 | 客户激活状态 | customer_customers.is_active |
| VIP状态 | 标签 | 客户VIP状态 | customer_customers.is_vip |

### 2.4 订单历史标签页
| 列名 | 类型 | 操作 | 关联字段 |
|------|------|------|----------|
| 订单编号 | 文本 | 点击查看订单详情 | order_orders.order_no |
| 订单金额 | 文本 | 无 | order_orders.actual_amount |
| 订单状态 | 标签 | 无 | order_orders.order_status |
| 下单时间 | 时间 | 无 | order_orders.created_at |
| 支付时间 | 时间 | 无 | order_orders.pay_time |
| 发货时间 | 时间 | 无 | order_logistics.ship_time |
| 签收时间 | 时间 | 无 | order_logistics.sign_time |

### 2.5 积分记录标签页
| 列名 | 类型 | 说明 | 关联字段 |
|------|------|------|----------|
| 积分变动 | 数字 | 积分变动值(正数增加,负数减少) | customer_points.points |
| 变动后余额 | 数字 | 变动后积分余额 | customer_points.balance |
| 积分类型 | 文本 | 积分变动类型 | customer_points.type |
| 积分来源 | 文本 | 积分来源 | customer_points.source |
| 关联订单 | 文本 | 关联订单号 | customer_points.order_no |
| 备注 | 文本 | 积分变动备注 | customer_points.remark |
| 过期时间 | 时间 | 积分过期时间 | customer_points.expire_time |
| 创建时间 | 时间 | 积分变动时间 | customer_points.created_at |

### 2.6 分组标签标签页
| 列名 | 类型 | 操作 | 关联字段 |
|------|------|------|----------|
| 客户分组 | 标签组 | 显示客户所属分组，支持编辑 | customer_groups.group_name |
| 客户标签 | 标签组 | 显示客户所属标签，支持编辑 | customer_tags.tag_name |

### 2.7 交互设计
- 支持返回客户列表页面
- 支持在详情页面直接编辑客户信息
- 支持切换标签页查看不同维度的客户信息
- 订单历史支持分页和筛选
- 积分记录支持分页和筛选
- 支持直接从客户详情跳转到订单详情
- 支持编辑客户分组和标签

## 3. 客户等级管理页面

### 3.1 页面概述
- **页面URL**: `/admin/customer/level`
- **功能描述**: 用于管理客户等级，支持添加、编辑、删除客户等级

### 3.2 页面布局
- **顶部操作区**: 新增等级按钮、批量操作按钮组（批量启用、批量禁用、批量删除）
- **中部列表区**: 客户等级表格，支持分页、排序
- **底部详情区**: 客户等级信息表单，支持编辑和保存

### 3.3 表单设计
| 字段名 | 类型 | 必填 | 说明 | 关联字段 |
|-------|------|------|------|----------|
| 等级名称 | 输入框 | 是 | 客户等级名称 | customer_levels.level_name |
| 等级编码 | 输入框 | 是 | 客户等级编码，唯一 | customer_levels.level_code |
| 最低积分 | 数字输入框 | 是 | 该等级所需最低积分 | customer_levels.min_points |
| 最高积分 | 数字输入框 | 否 | 该等级所需最高积分，最高等级可留空 | customer_levels.max_points |
| 折扣率 | 数字输入框 | 否 | 该等级享受的折扣率，默认为1.00（无折扣） | customer_levels.discount |
| 等级描述 | 文本域 | 否 | 客户等级描述 | customer_levels.description |
| 排序 | 数字输入框 | 否 | 等级排序，值越小越靠前 | customer_levels.sort_order |
| 是否激活 | 开关 | 否 | 控制等级是否启用 | customer_levels.is_active |

### 3.4 表格设计
| 列名 | 类型 | 操作 | 关联字段 |
|------|------|------|----------|
| 复选框 | 选择框 | 批量操作 | 无 |
| 等级ID | 文本 | 无 | customer_levels.id |
| 等级名称 | 文本 | 无 | customer_levels.level_name |
| 等级编码 | 文本 | 无 | customer_levels.level_code |
| 积分范围 | 文本 | 显示最低积分-最高积分 | customer_levels.min_points, customer_levels.max_points |
| 折扣率 | 文本 | 显示折扣率，如95折 | customer_levels.discount |
| 等级描述 | 文本 | 无 | customer_levels.description |
| 排序 | 数字 | 无 | customer_levels.sort_order |
| 是否激活 | 开关 | 编辑状态 | customer_levels.is_active |
| 创建时间 | 时间 | 无 | customer_levels.created_at |
| 操作 | 按钮组 | 编辑、删除 | 无 |

### 3.5 交互设计
- 支持添加新的客户等级
- 支持编辑现有客户等级
- 支持删除客户等级（删除前需确认）
- 支持批量操作客户等级
- 支持拖拽调整客户等级顺序
- 等级积分范围自动验证，确保不重叠
- 客户等级变更后，自动更新相关客户的等级

## 4. 客户分组管理页面

### 4.1 页面概述
- **页面URL**: `/admin/customer/group`
- **功能描述**: 用于管理客户分组，支持添加、编辑、删除客户分组，以及管理客户分组关联

### 4.2 页面布局
- **顶部操作区**: 新增分组按钮、批量操作按钮组（批量启用、批量禁用、批量删除）
- **左侧分组列表区**: 客户分组树状结构
- **右侧客户列表区**: 显示所选分组下的客户，支持添加/移除客户

### 4.3 分组表单设计
| 字段名 | 类型 | 必填 | 说明 | 关联字段 |
|-------|------|------|------|----------|
| 分组名称 | 输入框 | 是 | 客户分组名称 | customer_groups.group_name |
| 分组编码 | 输入框 | 是 | 客户分组编码，唯一 | customer_groups.group_code |
| 分组描述 | 文本域 | 否 | 客户分组描述 | customer_groups.description |
| 排序 | 数字输入框 | 否 | 分组排序，值越小越靠前 | customer_groups.sort_order |
| 是否激活 | 开关 | 否 | 控制分组是否启用 | customer_groups.is_active |

### 4.4 分组表格设计
| 列名 | 类型 | 操作 | 关联字段 |
|------|------|------|----------|
| 复选框 | 选择框 | 批量操作 | 无 |
| 分组ID | 文本 | 无 | customer_groups.id |
| 分组名称 | 文本 | 点击查看分组下客户 | customer_groups.group_name |
| 分组编码 | 文本 | 无 | customer_groups.group_code |
| 分组描述 | 文本 | 无 | customer_groups.description |
| 客户数量 | 数字 | 显示该分组下的客户数量 | 无 |
| 排序 | 数字 | 无 | customer_groups.sort_order |
| 是否激活 | 开关 | 编辑状态 | customer_groups.is_active |
| 创建时间 | 时间 | 无 | customer_groups.created_at |
| 操作 | 按钮组 | 编辑、删除 | 无 |

### 4.5 分组客户表格设计
| 列名 | 类型 | 操作 | 关联字段 |
|------|------|------|----------|
| 复选框 | 选择框 | 批量移除 | 无 |
| 客户编号 | 文本 | 点击查看详情 | customer_customers.customer_no |
| 用户名 | 文本 | 无 | customer_customers.username |
| 真实姓名 | 文本 | 无 | customer_customers.real_name |
| 手机号 | 文本 | 无 | customer_customers.phone |
| 客户等级 | 文本 | 无 | customer_levels.level_name |
| 累计消费 | 文本 | 无 | customer_customers.total_consumption |
| 操作 | 按钮组 | 移除分组 | 无 |

### 4.6 交互设计
- 支持添加新的客户分组
- 支持编辑现有客户分组
- 支持删除客户分组（删除前需确认）
- 支持批量操作客户分组
- 支持拖拽调整客户分组顺序
- 支持点击分组查看该分组下的客户
- 支持从分组中添加/移除客户
- 支持批量从分组中移除客户
- 支持按条件筛选分组下的客户

## 5. 客户标签管理页面

### 5.1 页面概述
- **页面URL**: `/admin/customer/tag`
- **功能描述**: 用于管理客户标签，支持添加、编辑、删除客户标签，以及管理客户标签关联

### 5.2 页面布局
- **顶部操作区**: 新增标签按钮、批量操作按钮组（批量启用、批量禁用、批量删除）
- **左侧标签列表区**: 客户标签表格，支持分页、排序
- **右侧客户列表区**: 显示所选标签下的客户，支持添加/移除客户

### 5.3 标签表单设计
| 字段名 | 类型 | 必填 | 说明 | 关联字段 |
|-------|------|------|------|----------|
| 标签名称 | 输入框 | 是 | 客户标签名称 | customer_tags.tag_name |
| 标签编码 | 输入框 | 是 | 客户标签编码，唯一 | customer_tags.tag_code |
| 标签描述 | 文本域 | 否 | 客户标签描述 | customer_tags.description |
| 排序 | 数字输入框 | 否 | 标签排序，值越小越靠前 | customer_tags.sort_order |
| 是否激活 | 开关 | 否 | 控制标签是否启用 | customer_tags.is_active |

### 5.4 标签表格设计
| 列名 | 类型 | 操作 | 关联字段 |
|------|------|------|----------|
| 复选框 | 选择框 | 批量操作 | 无 |
| 标签ID | 文本 | 无 | customer_tags.id |
| 标签名称 | 文本 | 点击查看标签下客户 | customer_tags.tag_name |
| 标签编码 | 文本 | 无 | customer_tags.tag_code |
| 标签描述 | 文本 | 无 | customer_tags.description |
| 客户数量 | 数字 | 显示该标签下的客户数量 | 无 |
| 排序 | 数字 | 无 | customer_tags.sort_order |
| 是否激活 | 开关 | 编辑状态 | customer_tags.is_active |
| 创建时间 | 时间 | 无 | customer_tags.created_at |
| 操作 | 按钮组 | 编辑、删除 | 无 |

### 5.5 标签客户表格设计
| 列名 | 类型 | 操作 | 关联字段 |
|------|------|------|----------|
| 复选框 | 选择框 | 批量移除 | 无 |
| 客户编号 | 文本 | 点击查看详情 | customer_customers.customer_no |
| 用户名 | 文本 | 无 | customer_customers.username |
| 真实姓名 | 文本 | 无 | customer_customers.real_name |
| 手机号 | 文本 | 无 | customer_customers.phone |
| 客户等级 | 文本 | 无 | customer_levels.level_name |
| 累计消费 | 文本 | 无 | customer_customers.total_consumption |
| 操作 | 按钮组 | 移除标签 | 无 |

### 5.6 交互设计
- 支持添加新的客户标签
- 支持编辑现有客户标签
- 支持删除客户标签（删除前需确认）
- 支持批量操作客户标签
- 支持拖拽调整客户标签顺序
- 支持点击标签查看该标签下的客户
- 支持从标签中添加/移除客户
- 支持批量从标签中移除客户
- 支持按条件筛选标签下的客户

## 6. 通用设计规范

### 6.1 样式规范
- 使用Ant Design/Element UI组件库
- 统一的颜色主题和字体
- 响应式布局，适配不同屏幕尺寸
- 客户状态使用不同颜色的标签标识：
  - 激活：绿色
  - 禁用：红色
  - VIP：黄色

### 6.2 交互规范
- 表单验证实时反馈
- 操作成功/失败提示
- 确认弹窗（删除、批量操作等）
- 加载状态提示
- 支持键盘快捷键操作

### 6.3 数据规范
- 所有数据操作都需记录操作日志
- 支持数据的逻辑删除
- 支持数据的导入导出
- 支持数据的批量操作

## 7. 技术实现建议

### 7.1 前端技术栈
- React/Vue
- Ant Design/Element UI
- Axios
- React Router/Vue Router
- Day.js（时间处理）
- xlsx（Excel导出）

### 7.2 后端技术栈
- Spring Boot
- MyBatis-Plus
- MySQL
- Redis
- Elasticsearch（客户搜索优化）

### 7.3 性能优化
- 客户列表使用分页加载
- 客户详情使用缓存
- 批量操作异步处理
- 客户搜索使用Elasticsearch优化

### 7.4 安全考虑
- API接口权限控制
- 敏感数据加密传输
- 防止SQL注入
- 防止XSS攻击
- 客户密码加密存储

## 8. 后续迭代建议

### 8.1 功能扩展
- 客户画像分析
- 客户行为分析
- 客户关怀功能
- 客户流失预警
- 客户推荐系统

### 8.2 体验优化
- 客户列表拖拽排序
- 客户标签可视化管理
- 客户等级自动升级
- 移动端客户管理

### 8.3 技术升级
- 微服务架构拆分
- 分布式缓存
- 消息队列异步处理
- 容器化部署

## 9. 总结

客户管理模块是电商系统的核心模块之一，包含客户列表查询、客户详情查看、客户等级管理、客户分组管理和客户标签管理五个主要页面。本设计方案基于MySQL数据库表结构，结合了现代前端技术和最佳实践，实现了客户全生命周期的管理。

设计方案考虑了用户体验、性能优化和安全考虑，并提供了后续迭代建议，为电商系统的客户管理功能提供了完整的解决方案。