# 第 03 周作业：单体 Spring Boot 工程

## 本周计划

沿用“宠安心——宠物医院预约与诊疗管理系统”选题，在仓库根目录的 `monolith/` 创建单体 Spring Boot 工程。本周只验证工程可以启动、配置可以加载、接口和健康检查可以访问；不实现宠物档案、预约、病历、收费、数据库或完整 REST API。

## 工程与配置

- Group 与 Package name：`com.zjgsu.chr`
- Java：21
- Spring Boot：4.0.8
- 构建工具：Maven Wrapper
- Web：Spring Web MVC
- 运行状态：Spring Boot Actuator
- 配置文件：`monolith/src/main/resources/application.yml`
- 默认端口：8080

## 启动与接口验证

在 `monolith/` 目录执行：

```bash
./mvnw spring-boot:run
```

启动后访问：

```text
GET http://localhost:8080/api/hello
{"project":"宠安心——宠物医院预约与诊疗管理系统","message":"宠安心单体应用运行正常"}

GET http://localhost:8080/actuator/health
{"status":"UP"}
```

## 启动测试

在 `monolith/` 目录执行：

```bash
./mvnw test
```

`MonolithApplicationTests` 使用 `@SpringBootTest` 加载 Spring 应用上下文。已在 Java 21 环境执行测试，结果为 `Tests run: 1, Failures: 0, Errors: 0`，Maven 输出 `BUILD SUCCESS`。

## 截图

已保存以下真实验证截图：

- `01-startup-success.png`：Spring Boot 启动成功。
- `02-hello-api.png`：`/api/hello` 的响应。
- `03-health-api.png`：`/actuator/health` 返回 `UP`。
- `04-test-success.png`：`@SpringBootTest` 测试通过且 Maven 输出 `BUILD SUCCESS`。
