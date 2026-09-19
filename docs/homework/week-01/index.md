# 第一周作业：开发环境检查与微服务基础

## 环境检查

检查日期：2026-09-19。以下命令在 Windows 11（10.0，amd64）上执行。

```text
> java --version
java 24.0.2 2025-07-15
Java(TM) SE Runtime Environment (build 24.0.2+12-54)
Java HotSpot(TM) 64-Bit Server VM (build 24.0.2+12-54, mixed mode, sharing)

> mvn --version
Apache Maven 3.9.11
Java version: 24.0.2, vendor: Oracle Corporation
OS name: "windows 11", version: "10.0", arch: "amd64"

> git --version
git version 2.53.0.windows.1

> docker version
docker: The term 'docker' is not recognized as a name of a cmdlet, function, script file, or executable program.

> docker compose version
docker: The term 'docker' is not recognized as a name of a cmdlet, function, script file, or executable program.
```

### Docker 问题记录

当前系统为 Windows 11，Java、Maven 和 Git 已安装并能正常运行；Docker Desktop 尚未安装或其命令未加入系统 PATH，因此两条 Docker 命令无法执行。下一步将安装 Docker Desktop for Windows，启用 WSL 2 后重启终端，再重新执行 `docker version` 与 `docker compose version` 验证。

## 概念回答

### 什么是微服务架构？

微服务架构是把一个业务系统按业务能力拆成多个小服务的设计方式。每个服务负责相对单一的职责，可以独立开发、部署和扩缩容；服务之间通常通过 HTTP、消息队列等明确的接口协作。

### 微服务和单体架构的主要区别是什么？

单体架构把功能集中在一个应用和一次发布中，开始开发简单，但规模变大后模块互相影响，局部修改也常需要整体发布。微服务把功能拆成多个可独立演进的服务，团队能分别发布和扩容；代价是需要处理接口版本、服务调用、监控、配置和分布式一致性等问题。

### 为什么本课程先实现单体系统，再逐步拆分为微服务？

先做单体系统可以先理解完整业务流程、数据模型和模块边界，也能较快验证需求。业务稳定后再按真实的变化频率和职责拆分，能避免一开始就拆得过细、服务之间调用混乱。这个过程也便于比较两种架构在开发和部署上的差异。

### 为什么作业需要提供可重复运行的测试或验证脚本？

可重复运行的脚本把验证步骤固定下来，其他人可以用同样的命令复现结果。代码或环境变化后也能快速检查是否引入问题，减少只靠手工操作和口头说明造成的遗漏，为后续持续集成提供基础。

## 提交记录

完成本次作业后，在本地执行以下命令查看提交历史，并将终端截图保存到 `screenshots/git-log.png`：

```bash
git log --oneline --graph
```

## 截图清单

请将以下真实截图保存到本目录，截图中应包含命令和输出：

- `screenshots/environment-check.png`：`java --version`、`mvn --version`、`git --version`、`docker version`、`docker compose version`。
- `screenshots/git-log.png`：`git log --oneline --graph`。
- `screenshots/github-repository.png`：GitHub 公开仓库主页。
