# 第 04 周作业：宠物预约持久化

## 业务建模

本周实现“注册宠物 → 创建门诊预约 → 按宠物查询预约”的流程。`Pet` 是宠物档案实体，`Appointment` 是预约实体；一个宠物可以拥有多个预约，一个预约必须属于一个已存在的宠物。业务规则为：同一只宠物不能在同一就诊时段重复预约。Service 在写入前查询已有记录并返回冲突错误，数据库的联合唯一约束提供第二层保护。

## 单体应用实现

代码位于 `monolith/src/main/java/com/zjgsu/chr/` 的 `model`、`repository`、`service`、`controller` 和 `dto` 子包。Controller 仅处理 HTTP 请求和响应，数据读写由 Service 与 Spring Data JPA Repository 完成。

| 方法 | 路径 | 结果 |
| --- | --- | --- |
| POST | `/api/pets` | 创建宠物，返回 201 |
| GET | `/api/pets` | 查询全部宠物，返回 200 |
| GET | `/api/pets/{id}` | 查询单个宠物，未找到时返回 404 |
| POST | `/api/appointments` | 创建预约，返回 201；重复时段返回 409 |
| GET | `/api/appointments?petId={id}` | 按宠物查询预约，返回 200 |

## 数据库持久化

`docs/database.md` 定义了 `pets` 与 `appointments` 的关系、主键、外键和联合唯一约束。默认配置使用 H2 内存数据库，方便无需 MySQL 的启动和测试；使用 `mysql` profile 后连接本地 MySQL。测试数据在测试代码中由 Repository 可重复创建。

## 测试与验证

在 `monolith/` 目录执行：

```bash
./mvnw test
```

测试覆盖 Repository 的保存与查询、Service 的重复预约规则、Controller 的创建成功和重复预约 409 响应。已在 Java 21 环境执行 `./mvnw test`，结果为 4 个测试通过、0 个失败、0 个错误，Maven 输出 `BUILD SUCCESS`。对应截图保存到本目录的 `screenshots/`。

## 截图证据

- `01-create-pet.png`：通过 Apifox 创建宠物，返回 201。
- `02-mysql-pets-data.png`：MySQL 的 `pets` 表测试数据。
- `03-create-appointment.png`：通过 Apifox 创建预约，返回 201 和 `SCHEDULED`。
- `04-duplicate-appointment.png`：相同宠物、相同时段重复预约，返回 409。
- `05-test-success.png`：Maven 自动化测试 4 项通过，输出 `BUILD SUCCESS`。
