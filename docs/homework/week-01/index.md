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
Client: Docker CLI 29.8.0 (windows/amd64)
Server: Docker Desktop 4.91.0 (239619)
Engine: 29.8.0 (linux/amd64)

> docker compose version
Docker Compose version v5.5.1
```

### Docker 问题记录

当前系统为 Windows 11，Java、Maven、Git、Docker Desktop 和 Docker Compose 均已安装并能正常运行。Docker Desktop 使用 WSL 2 后端，程序和容器数据均配置在 D 盘。通过 `docker version` 已确认客户端和服务端均可正常通信，后续可用于运行课程中的容器化服务。

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

## 本周文字说明

本周完成了课程仓库的初始化和开发环境检查。Java、Maven、Git 均可以在 Windows 11 中正常使用，后续可用于构建与管理项目；Docker 目前尚未安装，已记录原因和安装后的验证计划。通过梳理单体与微服务的差异，我理解到服务拆分应建立在明确业务边界之上，并且需要以可重复的验证步骤保障每次修改的质量。

## 截图清单

已保存的真实截图如下：

- `screenshots/java-version.png`：Java 版本。
- `screenshots/maven-version.png`：Maven 版本。
- `screenshots/git-version.png`：Git 版本。
- `screenshots/docker-version.png`：Docker 客户端与服务端版本。
- `screenshots/git-log.png`：Git 提交历史。
- `screenshots/github-repository.png`：GitHub 公开仓库主页。

还需要补充 Docker Compose 版本的终端截图。
