# Reggie Takeout - 瑞吉外卖卖点餐系统

基于 Spring Boot + MyBatis-Plus 的外卖点餐管理系统，包含管理后台和移动端前台。

## 技术栈

**后端**: Java 1.8, Spring Boot 2.4.5, MyBatis-Plus 3.4.2, MySQL 5.7+, Druid 1.1.23, FastJSON, Lombok, 阿里云 SMS  
**前端**: Vue.js + Element UI (后台), Vant UI (移动端)

## 功能模块

**管理后台**: 员工管理、菜品管理、套餐管理、订单管理、分类管理  
**移动端前台**: 手机号登录、在线点餐、购物车、地址管理、订单查询

## 快速开始

### 1. 环境要求
JDK 1.8+ | Maven 3.6+ | MySQL 5.7+

### 2. 创建数据库
```
bash
mysql -u root -p
CREATE DATABASE reggie CHARACTER SET utf8mb4;
use reggie;
source db_reggie.sql;
```
### 3. 配置数据库
编辑 `src/main/resources/application.yml`，修改数据库用户名和密码：
```
yaml
spring:
  datasource:
    druid:
      username: your_username
      password: your_password
```
### 4. 启动应用
```
bash
mvn spring-boot:run
```
### 5. 访问系统
- **管理后台**: http://localhost:8080/backend/index.html （账号: `admin` / 密码: `123456`）
- **移动端**: http://localhost:8080/front/index.html

## 项目结构

```

reggie/
├── controller/     # REST API 控制器
├── service/        # 业务逻辑层
├── mapper/         # MyBatis-Plus Mapper
├── entity/         # 实体类
├── dto/            # 数据传输对象
├── config/         # 配置类
├── filter/         # 登录检查过滤器
├── common/         # 公共组件（异常处理、统一响应）
└── utils/          # 工具类（短信、验证码）
```
## 核心特性

✅ 前后端分离架构 | ✅ 登录状态拦截器 | ✅ 统一响应封装 `R<T>` | ✅ 全局异常处理 | ✅ MyBatis-Plus 元数据自动填充 | ✅ ThreadLocal 用户上下文 | ✅ 阿里云短信验证

## API 概览

| 模块 | 路径 | 说明 |
|------|------|------|
| 员工 | `/employee` | 登录、增删改查 |
| 菜品 | `/dish` | 菜品管理、上下架 |
| 套餐 | `/setmeal` | 套餐管理、组合配置 |
| 订单 | `/order` | 下单、状态流转 |
| 用户 | `/user` | 短信登录、信息管理 |
| 购物车 | `/shoppingCart` | 添加、查询、清空 |
| 地址 | `/addressBook` | 地址 CRUD |

**统一响应格式**: `{"code": 1, "msg": "操作成功", "data": {}}`

## 常见问题

**Q: 数据库连接失败？** A: 检查 MySQL 服务是否启动，用户名密码是否正确。  
**Q: 短信发送失败？** A: 需配置阿里云 AccessKey 和短信签名（可选功能）。  
**Q: 图片上传后无法访问？** A: 检查 `reggie.path` 配置的路径是否存在。

## 安全提示

⚠️ **不要将敏感信息提交到代码仓库！** 数据库密码、AccessKey 等应使用环境变量或本地配置文件，建议添加 `application-local.yml` 到 `.gitignore`。

## 许可证

MIT License
