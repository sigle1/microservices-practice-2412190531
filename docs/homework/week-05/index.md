# 第 05 周作业：Docker 与 Compose

## Dockerfile 与镜像构建

`monolith/Dockerfile` 使用两阶段构建：第一阶段采用 Maven 与 Eclipse Temurin 21 镜像，在容器中执行 Maven Wrapper 打包；第二阶段只保留 Eclipse Temurin 21 JRE 与可运行 JAR。因此运行镜像不包含 Maven、源码或本机 Java 依赖。`monolith/.dockerignore` 排除了 `target/`、IDE 文件、Git 元数据、日志、密钥和环境文件。

在仓库根目录执行：

```bash
docker build -t pet-care-monolith:week05 ./monolith
docker image ls pet-care-monolith
```

已成功构建 `pet-care-monolith:week05` 镜像；构建阶段在 Maven 容器内完成打包，运行阶段使用 Java 21 JRE，最终镜像可直接执行 `/app/app.jar`。

## Compose 编排与启动

根目录 `compose.yaml` 编排 `app` 与 `mysql` 两个服务。MySQL 固定使用 `mysql:8.4.11`，通过 `mysql_data` 命名卷保存 `/var/lib/mysql`；应用通过同一 `petcare` 网络内的服务名 `mysql` 连接数据库。MySQL 健康检查通过后，`app` 才会启动。

```bash
cp .env.example .env
docker compose up -d --build
docker compose ps
docker compose logs app
```

Windows PowerShell 使用 `Copy-Item .env.example .env`。真实 `.env` 不提交，变量模板见 `.env.example`。

实际执行 `docker compose --env-file .env.example up -d --build` 后，`app` 容器处于 Up 状态，端口映射为 `8080:8080`；`mysql` 容器处于 healthy 状态。应用日志显示已通过 `jdbc:mysql://mysql:3306/pet_care` 连接 MySQL 8.4.11。

## 业务 API 验证

容器启动后，健康检查与业务接口通过宿主机 `8080` 端口访问：

```bash
curl http://localhost:8080/actuator/health
curl -X POST http://localhost:8080/api/pets -H "Content-Type: application/json" -d '{"name":"团团","ownerName":"陈昊睿","ownerPhone":"13800000000","species":"猫"}'
curl http://localhost:8080/api/pets
```

实际验证中，`/actuator/health` 返回 `UP`；创建 `DockerCat` 返回 201 和 `id=1`，随后 `GET /api/pets` 返回该记录。

## 数据持久化验证

使用业务 API 创建数据后，执行 `docker compose down`（不使用 `-v`），再执行 `docker compose up -d`。命名卷 `mysql_data` 不会被普通 down 删除；服务启动后查询同一记录，ID 与字段应保持不变。

```bash
docker compose down
docker compose up -d
curl http://localhost:8080/api/pets
```

实际执行普通 `docker compose down` 后重新 `up -d`，再次查询仍返回 `id=1`、名称 `DockerCat`。这说明数据保存在 `mysql_data` 命名卷中；只有使用 `down -v` 才会删除该卷。

验证截图保存于 `screenshots/`，应包含镜像构建/镜像列表、`docker compose ps`、接口创建与查询，以及容器重建后的查询结果。
