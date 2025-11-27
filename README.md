# 电商后端服务

基于 Spring Boot 3.x + MyBatis 的高可用电商后端服务

## 技术栈

- **语言**: Java 17
- **框架**: Spring Boot 3.2.0
- **持久层**: MyBatis 3.0.3 + PageHelper 2.1.0
- **数据库**: MySQL 8.0
- **连接池**: HikariCP
- **工具库**: Lombok, Jackson
- **API文档**: Swagger/OpenAPI 3
- **构建工具**: Maven

## 项目结构

```
ecommerce-backend/
├── src/main/java/com/ecommerce/
│   ├── EcommerceBackendApplication.java    # 启动类
│   ├── config/                            # 配置类
│   │   ├── SwaggerConfig.java             # Swagger配置
│   │   ├── CorsConfig.java                # 跨域配置
│   │   └── MyBatisConfig.java             # MyBatis配置
│   ├── controller/                        # 控制器层
│   │   └── ProductController.java         # 商品控制器
│   ├── service/                           # 服务层
│   │   ├── ProductService.java            # 商品服务接口
│   │   └── impl/
│   │       └── ProductServiceImpl.java    # 商品服务实现
│   ├── mapper/                            # 数据访问层
│   │   └── ProductMapper.java             # 商品Mapper接口
│   ├── entity/                            # 实体类
│   │   ├── Product.java                   # 商品实体
│   │   ├── User.java                      # 用户实体
│   │   └── Category.java                  # 分类实体
│   ├── dto/                               # 数据传输对象
│   │   ├── ProductDTO.java                # 商品响应DTO
│   │   ├── ProductQueryDTO.java           # 商品查询DTO
│   │   ├── Result.java                    # 统一响应结果
│   │   └── PageResult.java                # 分页响应结果
│   └── exception/                         # 异常处理
│       ├── GlobalExceptionHandler.java    # 全局异常处理器
│       └── BusinessException.java         # 业务异常类
├── src/main/resources/
│   ├── mapper/                            # MyBatis XML映射文件
│   │   └── ProductMapper.xml              # 商品Mapper XML
│   ├── sql/                               # 数据库脚本
│   │   ├── schema.sql                     # 建表脚本
│   │   └── data.sql                       # 初始化数据
│   └── application.yml                    # 应用配置
├── src/test/
│   └── java/com/ecommerce/
│       └── ProductServiceTest.java        # 服务测试
└── pom.xml                                # Maven配置
```

## 核心特性

### 1. 分层架构
- **Controller层**: 处理HTTP请求，参数验证，统一响应格式
- **Service层**: 业务逻辑处理，事务管理
- **Mapper层**: 数据访问，使用MyBatis XML配置复杂SQL

### 2. RESTful API设计
- 遵循REST规范，使用标准HTTP方法
- 统一的响应格式 `Result<T>`
- 支持分页查询 `PageResult<T>`

### 3. MyBatis最佳实践
- 使用XML Mapper编写动态SQL
- 驼峰命名自动映射
- 支持复杂查询和结果映射

### 4. 统一异常处理
- `@RestControllerAdvice` 全局异常捕获
- 区分业务异常和系统异常
- 详细的错误日志记录

### 5. 数据校验
- 使用 `javax.validation` 进行参数校验
- 支持方法参数和对象属性校验
- 自定义校验错误消息

## 快速开始

### 1. 环境准备
- JDK 17+
- Maven 3.6+
- MySQL 8.0+

### 2. 数据库配置
```sql
-- 创建数据库
CREATE DATABASE ecommerce DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- 执行建表脚本
SOURCE src/main/resources/sql/schema.sql;

-- 导入初始数据（可选）
SOURCE src/main/resources/sql/data.sql;
```

### 3. 修改配置
修改 `src/main/resources/application.yml` 中的数据库连接信息：
```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/ecommerce?useUnicode=true&characterEncoding=utf8&useSSL=false&serverTimezone=Asia/Shanghai
    username: your_username
    password: your_password
```

### 4. 启动应用
```bash
mvn clean compile
mvn spring-boot:run
```

### 5. 访问API文档
启动成功后，访问 Swagger UI：
- http://localhost:8080/api/swagger-ui.html
- OpenAPI JSON: http://localhost:8080/api/api-docs

## API接口

### 商品相关接口

| 方法 | 路径 | 描述 |
|------|------|------|
| GET | `/api/products` | 分页查询商品列表 |
| GET | `/api/products/{id}` | 根据ID查询商品详情 |
| GET | `/api/products/sku/{sku}` | 根据SKU查询商品 |
| GET | `/api/products/search` | 搜索商品 |
| GET | `/api/products/featured` | 获取推荐商品 |
| GET | `/api/products/new` | 获取新品商品 |
| GET | `/api/products/hot` | 获取热销商品 |
| GET | `/api/products/{id}/related` | 获取相关商品 |
| POST | `/api/products/batch` | 批量获取商品 |
| GET | `/api/products/{id}/validate` | 验证商品状态 |

### 请求示例

#### 分页查询商品
```http
GET /api/products?keyword=shoe&categoryIds=1&minPrice=50&maxPrice=200&pageNum=1&pageSize=10&sortBy=price&sortOrder=asc
```

#### 根据ID查询商品
```http
GET /api/products/1
```

#### 批量获取商品
```http
POST /api/products/batch
Content-Type: application/json

[1, 2, 3]
```

## 开发规范

### 1. 命名规范
- 类名：使用 PascalCase
- 方法名：使用 camelCase
- 常量：使用 UPPER_SNAKE_CASE
- 数据库字段：使用 snake_case

### 2. 注释规范
- 类和方法必须添加 JavaDoc 注释
- 复杂业务逻辑添加行内注释
- API接口使用 Swagger 注解

### 3. 异常处理
- 自定义业务异常继承 `BusinessException`
- 使用合适的HTTP状态码
- 记录详细的错误日志

### 4. 日志规范
- 使用 SLF4J 记录日志
- 不同级别使用合适的日志级别
- 避免在生产环境打印敏感信息

## 测试

```bash
# 运行单元测试
mvn test

# 运行特定测试类
mvn test -Dtest=ProductServiceTest

# 生成测试报告
mvn surefire-report:report
```

## 部署

### Docker部署
```bash
# 构建镜像
docker build -t ecommerce-backend:1.0.0 .

# 运行容器
docker run -d -p 8080:8080 --name ecommerce-backend ecommerce-backend:1.0.0
```

### 传统部署
```bash
# 打包
mvn clean package

# 运行
java -jar target/ecommerce-backend-1.0.0.jar
```

## 贡献指南

1. Fork 本项目
2. 创建特性分支 (`git checkout -b feature/AmazingFeature`)
3. 提交更改 (`git commit -m 'Add some AmazingFeature'`)
4. 推送到分支 (`git push origin feature/AmazingFeature`)
5. 打开 Pull Request

## 许可证

本项目采用 Apache License 2.0 许可证。详情请见 [LICENSE](LICENSE) 文件。

## 联系方式

- 项目地址：https://github.com/your-username/ecommerce-backend
- 问题反馈：https://github.com/your-username/ecommerce-backend/issues
- 邮箱：dev@ecommerce.com