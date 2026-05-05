# TikTok Connector

TikTok 电商平台连接器，用于同步广告、视频和用户数据。

## 版本信息

- **版本**: v2.0
- **描述**: 同步 TikTok 广告、视频和用户数据，支持多账户管理和实时 Webhook 事件订阅
- **分类**: Marketing
- **端口**: 23012

## 功能特性

- 多账户管理
- 广告数据同步
- 视频数据同步
- 用户数据同步
- 洞察数据同步
- 定时同步任务
- Webhook 事件订阅
- RESTful API 接口
- Swagger 文档支持

## 数据模型

### 核心表

- `tiktok_account` - 账户配置表
- `tiktok_ad` - 广告表
- `tiktok_video` - 视频表
- `tiktok_user` - 用户表
- `tiktok_insight` - 洞察数据表
- `tiktok_webhook_log` - Webhook 日志表
- `tiktok_sync_log` - 同步日志表

## 快速开始

### 1. 数据库初始化

```bash
psql -U tiktok_user -d livepulse_tiktok -f docs/sql/tiktok_schema.sql
```

### 2. 配置 Nacos

在 Nacos 中创建以下配置：

- `postgresql-config.yml` - 数据库配置
- `kafka-config.yml` - Kafka 配置

### 3. 启动应用

```bash
java -jar livepulse-connector-tiktok-open.jar \
  --spring.profiles.active=prod \
  --NACOS_SERVER_ADDR=nacos-host:8848
```

### 4. 访问 API 文档

```
http://localhost:23012/swagger-ui.html
```

## API 端点

### 账户管理

- `POST /tiktok/account` - 添加账户配置
- `PUT /tiktok/account/{id}` - 更新账户配置
- `DELETE /tiktok/account/{id}` - 删除账户配置
- `GET /tiktok/account/{id}` - 获取账户配置
- `GET /tiktok/account` - 分页查询账户列表
- `POST /tiktok/account/{id}/enable` - 启用账户
- `POST /tiktok/account/{id}/disable` - 禁用账户

### 广告管理

- `GET /tiktok/ad/{id}` - 获取广告详情
- `GET /tiktok/ad` - 分页查询广告列表
- `POST /tiktok/ad/{id}/process` - 标记为已处理

### 视频管理

- `GET /tiktok/video/{id}` - 获取视频详情
- `GET /tiktok/video` - 分页查询视频列表
- `POST /tiktok/video/{id}/process` - 标记为已处理

### 用户管理

- `GET /tiktok/user/{id}` - 获取用户详情
- `GET /tiktok/user` - 分页查询用户列表
- `POST /tiktok/user/{id}/process` - 标记为已处理

## 定时任务

### 广告同步

- **Cron**: `0 0 */4 * * ?`
- **说明**: 每4小时同步最近30天数据

### 视频同步

- **Cron**: `0 0 2 * * ?`
- **说明**: 每天凌晨2点同步最近30天数据

### 用户同步

- **Cron**: `0 0 3 * * ?`
- **说明**: 每天凌晨3点同步最近30天数据

### 洞察数据同步

- **Cron**: `0 0 */6 * * ?`
- **说明**: 每6小时同步最近7天数据

## Docker 部署

```bash
# 构建镜像
docker build -t livepulse-connector-tiktok:2.0 .

# 启动容器
docker-compose up -d

# 查看日志
docker-compose logs -f
```

## 配置说明

### bootstrap.yml

```yaml
server:
  port: 23012

spring:
  application:
    name: tiktok-open-connector-server

tiktok:
  api:
    version: 2.0
    timeout: 30
  sync:
    batch-size: 100
    max-retries: 3
```

## 环境要求

- Java 17
- Maven 3.8+
- PostgreSQL 15+
- Nacos 2.5.1+

## 文档

详细部署文档请参考：[部署指南.md](部署指南.md)

## 许可证

Copyright © 2025 Livepulse
