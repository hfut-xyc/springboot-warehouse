## 介绍
- 基于 Vue2 和 SpringBoot2 的仓库管理系统，2020 年 HFUT-CS 数据库课设选题
- 本仓库为后端代码，前端代码位于[vue-warehouse](https://github.com/hfut-xyc/vue-warehouse)

## 接口设计
后端接口 url 的采用 RESTful 风格，以用户为例
- 查询用户：GET /user/list
- 添加用户：POST /user
- 修改用户：PUT /user
- 删除用户：DELETE /user/{id}

## 运行
1. 安装 [JDK 1.8](https://www.oracle.com/java/technologies/downloads/archive/)
2. 安装 [Maven 3.6.1](https://archive.apache.org/dist/maven/maven-3/3.6.1/binaries/)
3. 安装 [MySQL 8.0](https://downloads.mysql.com/archives/installer/) 
4. 运行 `db_warehouse.sql`，初始化数据库
5. Idea 打开项目后，直接运行即可
6. 启动数据库服务程序 `net start mysql`
