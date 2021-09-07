## Introduction
- Course Design of DataBase, HFUT, 2020
- The maintaining of this repository has stopped
- This project is accomplished with the assistance of [@Fward](https://github.com/HowardZorn)

## Display

##### 登录界面
![登录界面](/screenshot/login.png)

##### 仓库信息
![仓库信息](/screenshot/warehouse.png)

##### 库存清单
![库存清单](/screenshot/inventory.png)

##### 订单记录
![订单记录](/screenshot/order-list.png)

##### 创建旧产品订单
![创建旧产品订单](/screenshot/order-add-old.png)

##### 创建新产品订单
![创建新产品订单](/screenshot/order-add-new.png)

##### 订单回收站
![订单回收站](/screenshot/order-dustbin.png)

##### 数据统计图
![数据统计图](/screenshot/chart.png)

##### 员工列表
![员工列表](/screenshot/employee.png)

##### 编辑员工信息
![编辑员工信息](/screenshot/employee-edit.png)

##### 后台用户
![后台用户](/screenshot/user.png)

##### API Doc
![API文档](/screenshot/api-doc.png)

## Running

### Backend

1. 安装 Java 8，Maven 3.6.1
3. 安装 MySQL 8.0+，配置 root 用户的密码为 root，服务器端口号为 3306
4. 安装 Redis
5. 运行 init.sql，初始化数据库
6. 启动数据库服务程序 `systemctl start mysql`
7. 启动缓存数据库服务程序 `systemctl start redis`，Windows下启动 **redis-server.exe**
8. 启动后端服务器`mvn spring-boot:run`
11. 后端运行成功后，可以访问 [http://localhost:8081/swagger-ui.html](http://localhost:8081/swagger-ui.html) 来查看所有的API文档
### Frontend

1. 安装 node.js 12，并换源

```
npm config set registry https://registry.npm.taobao.org
```

2. 安装第三方依赖

```
npm install
```

3. 运行前端项目

```
npm run serve
```

***

## Depolyment

1. 预计作为项目部署服务器的是Aliyun, Ubuntu服务器，并使用docker技术部署

2. 服务器需要安装docker，并部署需要的docker容器

3. 部署前需要敲定域名，并且按照该域名修改后端的服务器配置（主要是跨域访问），申请免费SSL证书

4. 部署需要的脚本和配置文件写在deployment文件夹和Makefile中。部署时使用`make build`命令即可编译，`make run`是运行服务的指令

   ![编译过程截图](/screenshot/build.png)

5. 前端编译所得`dist/**`应存放在nginx docker容器的`/var/www`下。nginx的配置文件和dockerfile已经存放在deployment文件夹中。**该docker容器的网络配置使用host模式**

6. 后端编译成单个jar，复制到JRE docker容器运行。**该docker容器的网络配置使用host模式**

7. Redis和MySQL也使用docker容器提供。为了安全，进行了网络隔离，除本机程序以外无法访问。

   ![服务的运行](/screenshot/run.png)

***

## Designning 

### RESTful API 

1. 后端程序统一返回json格式给前端，只负责提供数据，不用管页面如何跳转
2. 数据库交互时，select操作一律采用`GET`请求，insert和update操作使用`POST`请求，delete操作使用`DELETE`请求
3. 接口url的设计示例如下,以用户为例
> - 查询用户列表： /users
> - 添加用户： /user/add
> - 删除用户： /user/{id}/delete
> - 修改用户基本信息：/user/{id}/update/info

4. 后期设计其他模块也是如此，以员工为例
> - 查询员工列表： /employees
> - 添加员工： /employee/add
> - 删除员工： /employee/{id}/delete
> - 修改员工基本信息：/employee/{id}/update/info
> - 修改员工负责仓库：/employee/{id}/update/warehouse

### CRUD Function Name
在命名Service和Mapper层的函数时，建议遵循以下风格:

如果是对实体类的insert操作，函数命名为**addXXX**
> - 添加用户: addUser
> - 添加员工: addEmpolyee

如果是对实体类的delete操作，函数命名为**deleteXXXById**
> - 删除用户: deleteUserById
> - 删除员工: deleteEmployeeById

如果实体类的field属于Primitive Type，进行update操作时，命名为update**FieldName**ById
> - 修改用户的可用性状态: updateEnabledById

如果实体类的field属于Reference Type，进行update操作时，命名为update**FieldName**By**XId**
> - 修改用户的角色列表：update**Role**By**Uid**
> - 修改员工的仓库列表：update**Warehouse**By**Eid** 
> - 修改仓库的员工列表：update**Employee**By**Wid**

## Technology

### Backend

| 技术                 | 说明                | 官网                                                 |
| -------------------- | ------------------- | ---------------------------------------------------- |
| SpringBoot           | 容器+MVC框架        | https://spring.io/projects/spring-boot               |
| SpringSecurity       | 认证和授权框架      | https://spring.io/projects/spring-security           |
| MyBatis              | ORM框架,用于和数据库交互  | http://www.mybatis.org/mybatis-3/zh/index.html       |
| Redis                | NoSQL缓存          | https://redis.io/                                    |
| Swagger-UI           | 文档生成工具，用于生成API文档         | https://github.com/swagger-api/swagger-ui |
| Lombok               | 代码生成工具，用于简化代码，提高可读性    | https://github.com/rzwitserloot/lombok  |
| Maven                | Java依赖库管理工具                 |https://maven.apache.org/ |
| Docker               | 后端部署工具                    | https://www.docker.com/ |
| Nginx                | 反向代理服务器，前端部署工具       | http://nginx.org/en/download.html |

### Frontend

| 技术       | 说明                  | 官网                                   |
| ---------- | --------------------- | -------------------------------------- |
| Vue        | 前端框架              | https://cn.vuejs.org/                     |
| Vue-router | 路由框架              | https://router.vuejs.org/zh/              |
| Vuex       | 全局状态管理框架      | https://vuex.vuejs.org/zh/               |
| Element-ui    | 前端UI组件框架            | https://element.eleme.cn               |
| Axios      | AJAX请求框架          | https://github.com/axios/axios         |
| v-charts   | 基于Echarts的图表框架 | https://v-charts.js.org/               |


