# 第 06 周：单体拆分为微服务

## 服务拆分设计

保留 `monolith/` 作为单体版本，在根目录新增 `services/`。按业务领域拆分为 user-service、pet-service 和 appointment-service，分别监听 8081、8082、8083，并分别拥有 `user_db`、`pet_db`、`appointment_db`。详细边界、表归属和 Mermaid 架构图见 [../../microservices-design.md](../../microservices-design.md)。

## 代码拆分

三个服务都具有独立的 `pom.xml`、Spring Boot 启动类、Controller、Service、Repository、配置和 `@SpringBootTest` 启动测试。预约服务以 `RestTemplate` 调用 `http://pet-service:8080/api/pets/{id}`；地址和连接、读取超时均可由环境变量覆盖。宠物不存在时返回 404；宠物服务不可用或超时时返回 503，不创建预约。

独立测试命令：

```bash
cd services/user-service && mvn test
cd ../pet-service && mvn test
cd ../appointment-service && mvn test
```

## 本地编排

在仓库根目录执行：

```bash
docker compose up -d --build
docker compose ps
```

Compose 启动一个 MySQL 8.4.11 实例，其中初始化三个独立数据库和账号；三个服务只能使用自己的数据库账号。MySQL 数据保存到命名卷 `microservices_mysql_data`，容器间通过服务名通信。

## 验证

```bash
bash scripts/smoke-test.sh
```

烟雾脚本依次访问三个服务的 `/actuator/health`。完整成功流程：先创建用户、创建宠物，再向 appointment-service 创建预约；预约服务会 HTTP 校验宠物服务后才写入预约。失败流程：为不存在的 `petId` 创建预约会返回 404，不写入 `appointment_db`；停止 pet-service 后创建预约会返回 503。

“宠物服务校验成功、本地预约写入失败”之间仍可能出现远端成功、本地失败窗口。本周不要求分布式原子事务；后续可用幂等键、事件与补偿机制改善一致性。

## 截图建议

- `01-services-code.png`：预约服务的 `PetClient` 与 `AppointmentService`，显示 RestTemplate 调用和本地写入前校验。
- `02-compose-ps.png`：`docker compose ps` 显示 MySQL 与三个服务均运行。
- `03-appointment-success.png`：创建宠物后创建预约，显示 201 与 `SCHEDULED`。
- `04-appointment-not-found.png`：不存在宠物创建预约返回 404。
- `05-smoke-test.png`：`bash scripts/smoke-test.sh` 三个健康检查均通过。
