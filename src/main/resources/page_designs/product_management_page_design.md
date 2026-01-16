# 商品管理页面设计

## 1. 商品分类管理页面

### 1.1 页面概述
- **页面URL**: `/admin/product/category`
- **功能描述**: 用于管理商品分类，支持多级分类、排序、启用/禁用操作

### 1.2 页面布局
- **顶部操作区**: 搜索框、新增分类按钮
- **左侧分类树**: 展示多级分类结构
- **右侧详情区**: 分类信息表单、操作按钮

### 1.3 表单设计
| 字段名 | 类型 | 必填 | 说明 | 关联字段 |
|-------|------|------|------|----------|
| 分类名称 | 输入框 | 是 | 分类的中文名称 | product_categories.category_name |
| 分类编码 | 输入框 | 是 | 分类的英文编码，唯一 | product_categories.category_code |
| 父分类 | 下拉选择框 | 否 | 选择上级分类，根分类为0 | product_categories.parent_id |
| 分类描述 | 文本域 | 否 | 分类的详细描述 | product_categories.description |
| 排序 | 数字输入框 | 否 | 分类的排序顺序，值越小越靠前 | product_categories.sort_order |
| 是否激活 | 开关 | 否 | 控制分类是否启用 | product_categories.is_active |

### 1.4 表格设计
| 列名 | 类型 | 操作 | 关联字段 |
|------|------|------|----------|
| 分类ID | 文本 | 无 | product_categories.id |
| 分类名称 | 文本 | 无 | product_categories.category_name |
| 分类编码 | 文本 | 无 | product_categories.category_code |
| 父分类 | 文本 | 无 | product_categories.parent_id |
| 分类描述 | 文本 | 无 | product_categories.description |
| 排序 | 数字 | 无 | product_categories.sort_order |
| 是否激活 | 开关 | 编辑状态 | product_categories.is_active |
| 创建时间 | 时间 | 无 | product_categories.created_at |
| 操作 | 按钮组 | 编辑、删除 | 无 |

### 1.5 交互设计
- 点击左侧分类树节点，右侧显示该分类的详细信息
- 支持拖拽调整分类顺序
- 新增分类时，自动生成分类路径
- 删除分类时，级联删除子分类
- 支持批量删除分类

## 2. 商品信息管理页面

### 2.1 页面概述
- **页面URL**: `/admin/product/info`
- **功能描述**: 用于管理商品基本信息，支持添加、编辑、删除、上架/下架操作

### 2.2 页面布局
- **顶部操作区**: 搜索框（按商品名称、编码搜索）、新增商品按钮、批量操作按钮组
- **商品列表区**: 表格展示商品信息，支持分页、排序
- **商品详情区**: 商品信息表单，支持标签页切换（基本信息、属性、图片、SKU）

### 2.3 表单设计（基本信息标签页）
| 字段名 | 类型 | 必填 | 说明 | 关联字段 |
|-------|------|------|------|----------|
| 商品名称 | 输入框 | 是 | 商品的中文名称 | product_products.product_name |
| 商品编码 | 输入框 | 是 | 商品的唯一编码 | product_products.product_code |
| 商品分类 | 树选择器 | 是 | 选择商品所属分类 | product_products.category_id |
| 商品副标题 | 输入框 | 否 | 商品的简短描述 | product_products.subtitle |
| 商品描述 | 富文本编辑器 | 否 | 商品的详细描述 | product_products.description |
| 商品价格 | 数字输入框 | 是 | 商品的销售价格 | product_products.price |
| 市场价 | 数字输入框 | 否 | 商品的市场参考价格 | product_products.market_price |
| 总库存 | 数字输入框 | 否 | 商品的总库存数量 | product_products.stock |
| 排序 | 数字输入框 | 否 | 商品的排序顺序 | product_products.sort_order |
| 是否上架 | 开关 | 否 | 控制商品是否上架销售 | product_products.is_on_sale |
| 是否新品 | 开关 | 否 | 标记商品是否为新品 | product_products.is_new |
| 是否热门 | 开关 | 否 | 标记商品是否为热门商品 | product_products.is_hot |

### 2.4 表格设计
| 列名 | 类型 | 操作 | 关联字段 |
|------|------|------|----------|
| 商品ID | 文本 | 无 | product_products.id |
| 商品名称 | 文本 | 无 | product_products.product_name |
| 商品编码 | 文本 | 无 | product_products.product_code |
| 商品分类 | 文本 | 无 | product_products.category_id |
| 价格 | 文本 | 无 | product_products.price |
| 库存 | 数字 | 无 | product_products.stock |
| 销量 | 数字 | 无 | product_products.sales |
| 浏览量 | 数字 | 无 | product_products.view_count |
| 是否上架 | 开关 | 编辑状态 | product_products.is_on_sale |
| 是否新品 | 开关 | 编辑状态 | product_products.is_new |
| 是否热门 | 开关 | 编辑状态 | product_products.is_hot |
| 创建时间 | 时间 | 无 | product_products.created_at |
| 操作 | 按钮组 | 编辑、删除、查看详情 | 无 |

### 2.5 交互设计
- 支持商品的快速上架/下架操作
- 支持商品的批量删除和批量上下架
- 编辑商品时，标签页切换显示不同维度的信息
- 商品详情页支持查看完整的商品信息，包括属性、图片和SKU

## 3. 商品属性管理页面

### 3.1 页面概述
- **页面URL**: `/admin/product/attribute`
- **功能描述**: 用于管理商品属性，支持按分类设置属性

### 3.2 页面布局
- **顶部操作区**: 搜索框、新增属性按钮
- **属性列表区**: 表格展示属性信息，支持按分类筛选
- **属性详情区**: 属性信息表单

### 3.3 表单设计
| 字段名 | 类型 | 必填 | 说明 | 关联字段 |
|-------|------|------|------|----------|
| 属性名称 | 输入框 | 是 | 属性的中文名称 | product_attributes.attribute_name |
| 属性编码 | 输入框 | 否 | 属性的英文编码 | product_attributes.attribute_code |
| 所属分类 | 树选择器 | 是 | 属性所属的商品分类 | product_attributes.category_id |
| 属性类型 | 单选框 | 是 | 1-销售属性，2-非销售属性 | product_attributes.attribute_type |
| 是否必填 | 开关 | 否 | 控制该属性是否为必填项 | product_attributes.is_required |
| 排序 | 数字输入框 | 否 | 属性的排序顺序 | product_attributes.sort_order |

### 3.4 表格设计
| 列名 | 类型 | 操作 | 关联字段 |
|------|------|------|----------|
| 属性ID | 文本 | 无 | product_attributes.id |
| 属性名称 | 文本 | 无 | product_attributes.attribute_name |
| 属性编码 | 文本 | 无 | product_attributes.attribute_code |
| 所属分类 | 文本 | 无 | product_attributes.category_id |
| 属性类型 | 文本 | 无 | product_attributes.attribute_type |
| 是否必填 | 开关 | 编辑状态 | product_attributes.is_required |
| 排序 | 数字 | 无 | product_attributes.sort_order |
| 创建时间 | 时间 | 无 | product_attributes.created_at |
| 操作 | 按钮组 | 编辑、删除 | 无 |

### 3.5 交互设计
- 支持按分类筛选属性
- 销售属性用于生成SKU，非销售属性用于商品展示
- 支持批量删除属性

## 4. 商品图片管理页面

### 4.1 页面概述
- **页面URL**: `/admin/product/image`
- **功能描述**: 用于管理商品图片，支持上传、预览、排序操作

### 4.2 页面布局
- **顶部操作区**: 商品选择器、上传图片按钮
- **图片列表区**: 网格展示商品图片，支持拖拽排序
- **图片详情区**: 图片信息表单

### 4.3 表单设计
| 字段名 | 类型 | 必填 | 说明 | 关联字段 |
|-------|------|------|------|----------|
| 商品 | 搜索选择器 | 是 | 关联的商品 | product_images.product_id |
| 图片URL | 上传组件 | 是 | 图片的存储路径 | product_images.image_url |
| 图片类型 | 单选框 | 否 | 1-主图，2-详情图，3-缩略图 | product_images.image_type |
| 排序 | 数字输入框 | 否 | 图片的排序顺序 | product_images.sort_order |

### 4.4 表格设计
| 列名 | 类型 | 操作 | 关联字段 |
|------|------|------|----------|
| 图片ID | 文本 | 无 | product_images.id |
| 商品名称 | 文本 | 无 | product_images.product_id |
| 图片URL | 图片预览 | 无 | product_images.image_url |
| 图片类型 | 文本 | 无 | product_images.image_type |
| 排序 | 数字 | 无 | product_images.sort_order |
| 创建时间 | 时间 | 无 | product_images.created_at |
| 操作 | 按钮组 | 编辑、删除、设为主图 | 无 |

### 4.5 交互设计
- 支持多图片上传
- 支持拖拽调整图片顺序
- 支持图片预览
- 支持设为主图操作
- 支持批量删除图片

## 5. 商品SKU管理页面

### 5.1 页面概述
- **页面URL**: `/admin/product/sku`
- **功能描述**: 用于管理商品SKU，支持按商品设置SKU属性和价格

### 5.2 页面布局
- **顶部操作区**: 商品选择器、新增SKU按钮
- **SKU列表区**: 表格展示SKU信息
- **SKU详情区**: SKU信息表单，包括属性组合

### 5.3 表单设计
| 字段名 | 类型 | 必填 | 说明 | 关联字段 |
|-------|------|------|------|----------|
| 商品 | 搜索选择器 | 是 | 关联的商品 | product_skus.product_id |
| SKU编码 | 输入框 | 是 | SKU的唯一编码 | product_skus.sku_code |
| SKU名称 | 输入框 | 是 | SKU的完整名称 | product_skus.sku_name |
| 销售属性组合 | 动态表单 | 是 | 根据商品分类的销售属性动态生成 | product_sku_attributes |
| 价格 | 数字输入框 | 是 | SKU的销售价格 | product_skus.price |
| 库存 | 数字输入框 | 否 | SKU的库存数量 | product_skus.stock |
| 是否上架 | 开关 | 否 | 控制SKU是否上架销售 | product_skus.is_on_sale |

### 5.4 表格设计
| 列名 | 类型 | 操作 | 关联字段 |
|------|------|------|----------|
| SKU ID | 文本 | 无 | product_skus.id |
| 商品名称 | 文本 | 无 | product_skus.product_id |
| SKU编码 | 文本 | 无 | product_skus.sku_code |
| SKU名称 | 文本 | 无 | product_skus.sku_name |
| 属性组合 | 文本 | 无 | product_sku_attributes |
| 价格 | 文本 | 无 | product_skus.price |
| 库存 | 数字 | 无 | product_skus.stock |
| 销量 | 数字 | 无 | product_skus.sales |
| 是否上架 | 开关 | 编辑状态 | product_skus.is_on_sale |
| 创建时间 | 时间 | 无 | product_skus.created_at |
| 操作 | 按钮组 | 编辑、删除 | 无 |

### 5.5 交互设计
- 选择商品后，自动加载该商品分类的销售属性
- 支持动态添加/删除SKU属性值
- 支持批量调整SKU价格和库存
- 支持批量上架/下架SKU

## 6. 商品批量操作页面

### 6.1 页面概述
- **页面URL**: `/admin/product/batch`
- **功能描述**: 用于商品的批量导入导出和批量上下架操作

### 6.2 页面布局
- **顶部操作区**: 操作类型切换（导入/导出/批量上下架）
- **操作内容区**: 根据操作类型显示不同的表单和按钮

### 6.3 表单设计

#### 6.3.1 商品导入
| 字段名 | 类型 | 必填 | 说明 |
|-------|------|------|------|
| 导入模板下载 | 按钮 | 否 | 下载商品导入模板 |
| 导入文件 | 上传组件 | 是 | 选择要导入的商品Excel文件 |
| 导入方式 | 单选框 | 否 | 1-覆盖，2-增量 |

#### 6.3.2 商品导出
| 字段名 | 类型 | 必填 | 说明 |
|-------|------|------|------|
| 导出模板选择 | 下拉选择框 | 是 | 选择导出模板类型 |
| 导出字段选择 | 多选框 | 否 | 选择要导出的商品字段 |
| 导出条件 | 表单 | 否 | 设置商品导出的筛选条件 |

#### 6.3.3 批量上下架
| 字段名 | 类型 | 必填 | 说明 |
|-------|------|------|------|
| 商品选择 | 多选框 | 是 | 选择要操作的商品 |
| 操作类型 | 单选框 | 是 | 1-批量上架，2-批量下架 |
| 操作原因 | 输入框 | 否 | 填写批量操作的原因 |

### 6.4 交互设计
- 支持通过Excel模板批量导入商品信息
- 支持自定义字段导出商品数据
- 支持按条件筛选商品进行批量操作
- 批量操作结果实时反馈
- 支持批量操作记录查询

## 7. 通用设计规范

### 7.1 样式规范
- 使用Ant Design/Element UI组件库
- 统一的颜色主题和字体
- 响应式布局，适配不同屏幕尺寸

### 7.2 交互规范
- 表单验证实时反馈
- 操作成功/失败提示
- 确认弹窗（删除、批量操作等）
- 加载状态提示

### 7.3 数据规范
- 所有数据操作都需记录操作日志
- 支持数据的逻辑删除
- 支持数据的导入导出
- 支持数据的批量操作

## 8. 技术实现建议

### 8.1 前端技术栈
- React/Vue
- Ant Design/Element UI
- Axios
- React Router/Vue Router

### 8.2 后端技术栈
- Spring Boot
- MyBatis-Plus
- MySQL
- Redis

### 8.3 性能优化
- 商品图片采用CDN加速
- 商品数据缓存
- 分页加载商品列表
- 批量操作异步处理

### 8.4 安全考虑
- 图片上传大小限制
- 富文本编辑器XSS防护
- API接口权限控制
- 数据加密传输

## 9. 后续迭代建议

### 9.1 功能扩展
- 商品审核流程
- 商品推荐系统
- 商品评价管理
- 商品库存预警

### 9.2 体验优化
- 商品信息的拖拽排序
- 商品图片的批量上传
- SKU属性的可视化配置
- 商品数据的图表统计

### 9.3 技术升级
- 微服务架构拆分
- 分布式缓存
- 消息队列异步处理
- 容器化部署

## 10. 总结

商品管理模块是电商系统的核心模块之一，包含商品分类、商品信息、商品属性、商品图片、商品SKU和商品批量操作六个主要页面。本设计方案基于MySQL数据库表结构，结合了现代前端技术和最佳实践，实现了商品全生命周期的管理。

设计方案考虑了用户体验、性能优化和安全考虑，并提供了后续迭代建议，为电商系统的商品管理功能提供了完整的解决方案。