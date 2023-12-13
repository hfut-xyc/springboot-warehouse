## 介绍
- 基于 Vue2 和 SpringBoot2 的仓库管理系统，2020 年 HFUT-CS 数据库课设选题
- 本仓库为后端代码，前端代码位于[vue-warehouse](https://github.com/hfut-xyc/vue-warehouse)

## 设计规约
后端接口 url 的采用 RESTful 风格，以用户为例
- 查询用户：GET /user/list
- 添加用户：POST /user
- 修改用户：PUT /user
- 删除用户：DELETE /user/{id}


## 运行
1. 安装 [JDK 1.8](https://www.oracle.com/java/technologies/downloads/archive/)
2. 安装 [Maven 3.6.1](https://archive.apache.org/dist/maven/maven-3/3.6.1/binaries/)
3. 安装 [MySQL 8.0](https://downloads.mysql.com/archives/installer/) ，root 用户名和密码设为 root
4. 运行 `resource/db_warehouse.sql`，初始化数据库
5. Idea 打开项目后，直接运行即可

## 部署
1. 项目部署服务器的是Ubuntu服务器，并使用docker技术部署
2. 服务器需要安装docker，并部署需要的docker容器
3. 部署前需要敲定域名，并且按照该域名修改后端的服务器配置（主要是跨域访问），申请免费SSL证书
4. 部署需要的脚本和配置文件写在deployment文件夹和Makefile中。部署时使用`make build`命令即可编译，`make run`是运行服务的指令
5. 前端编译所得`dist/**`应存放在nginx docker容器的`/var/www`下。nginx的配置文件和dockerfile已经存放在deployment文件夹中。**该docker容器的网络配置使用host模式**
6. 后端编译成单个jar，复制到JRE docker容器运行。**该docker容器的网络配置使用host模式**
7. Redis和MySQL也使用docker容器提供。为了安全，进行了网络隔离，除本机程序以外无法访问。
6. 启动数据库服务程序 `systemctl start mysql`
