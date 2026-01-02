# Java 项目结构规范

```
src/
├── main/
│   ├── java/
│   │   └── com/company/project/
│   │       ├── config/          # 配置类
│   │       ├── controller/      # 控制器层
│   │       ├── service/         # 服务层
│   │       ├── mapper/          # 数据访问层
│   │       ├── entity/          # 实体类
│   │       ├── dto/             # 传输对象
│   │       ├── exception/       # 异常处理
│   │       ├── util/            # 工具类
│   │       ├── vo/              # 视图对象
│   │       └── Application.java # 启动类
│   └── resources/
│       ├── application.yml
│       ├── application-dev.yml
│       ├── application-prod.yml
└── test/
```