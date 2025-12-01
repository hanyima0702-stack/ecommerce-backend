# 故障排除指南

## 问题描述

访问 Swagger UI (`http://localhost:8080/api/swagger-ui.html`) 时出现 `ClassCastException`：

```
java.lang.ClassCastException: class jdk.proxy2.$Proxy98 cannot be cast to class jakarta.validation.constraints.Min
```

## 根本原因

这是 **Spring Boot 3.x** 中验证框架从 `javax.validation` 迁移到 `jakarta.validation` 导致的**依赖冲突**问题。

### Spring Boot 3.x 变更

- Spring Boot 3.x 基于 Jakarta EE 9+
- 验证API 从 `javax.validation` 迁移到 `jakarta.validation`
- 但项目中同时存在两个版本的验证API

## 修复方案

### 1. Maven 依赖修复

**问题：** `pom.xml` 中混合了不同版本的验证依赖

```xml
<!-- 问题代码 -->
<dependency>
    <groupId>javax.validation</groupId>
    <artifactId>validation-api</artifactId>
    <version>2.0.1.Final</version>
</dependency>
```

**修复：** 统一使用 Jakarta Validation

```xml
<!-- 修复后 -->
<dependency>
    <groupId>jakarta.validation</groupId>
    <artifactId>jakarta.validation-api</artifactId>
</dependency>
```

### 2. Java 导入修复

需要将所有 Java 文件中的验证注解导入从 `javax` 改为 `jakarta`：

```java
// 修复前
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.ConstraintViolationException;

// 修复后
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.ConstraintViolationException;
```

### 3. 修复的文件

- `pom.xml` - Maven 依赖配置
- `ProductController.java` - Controller 层验证注解
- `GlobalExceptionHandler.java` - 异常处理器验证注解
- `ProductQueryDTO.java` - DTO 层验证注解

## 验证修复

### 1. 重新编译项目

```bash
mvn clean compile
```

### 2. 重新启动应用

```bash
mvn spring-boot:run
```

### 3. 验证 Swagger 访问

访问 `http://localhost:8080/api/swagger-ui.html` 应该能正常显示。

### 4. 验证 API 功能

测试以下 API 确保验证功能正常：

- GET `/api/products?pageNum=1&pageSize=10`
- GET `/api/products/1`
- GET `/api/products/search?keyword=shoe`

## 最佳实践

### 1. 避免依赖冲突

```xml
<!-- 推荐：让 Spring Boot 管理版本 -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-validation</artifactId>
</dependency>

<!-- 可选：明确指定 Jakarta Validation API -->
<dependency>
    <groupId>jakarta.validation</groupId>
    <artifactId>jakarta.validation-api</artifactId>
</dependency>
```

### 2. 统一验证注解

整个项目统一使用 `jakarta.validation.*` 注解：

```java
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
```

### 3. IDE 配置

在 IDE 中设置代码模板，默认使用 Jakarta 验证注解。

## 相关资源

- [Spring Boot 3.0 Migration Guide](https://github.com/spring-projects/spring-boot/wiki/Spring-Boot-3.0-Migration-Guide)
- [Jakarta Bean Validation](https://eclipse-ee4j.github.io/jakartaee-tutorial/#bean-validation)
- [Spring Validation Documentation](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#validation)

---

**问题解决时间：** 2025-11-27
**影响范围：** 所有涉及参数验证的 API
**修复状态：** ✅ 完成