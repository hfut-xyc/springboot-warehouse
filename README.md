# Warehouse Management System

#### 介绍
Course Design of DataBase, HFUT, 2020

#### 软件运行截图

##### 登录界面
![](https://gitee.com/hfut-xyc/Warehouse-Management-System/raw/master/screenshot/login.png)

##### 仓库信息
![](https://gitee.com/hfut-xyc/Warehouse-Management-System/raw/master/screenshot/warehouse.png)

##### 库存清单
![](https://gitee.com/hfut-xyc/Warehouse-Management-System/raw/master/screenshot/inventory.png)

##### 订单记录
![](https://gitee.com/hfut-xyc/Warehouse-Management-System/raw/master/screenshot/order-list.png)

##### 创建旧产品订单
![](https://gitee.com/hfut-xyc/Warehouse-Management-System/raw/master/screenshot/order-add-old.png)

##### 创建新产品订单
![](https://gitee.com/hfut-xyc/Warehouse-Management-System/raw/master/screenshot/order-add-new.png)

##### 订单回收站
![](https://gitee.com/hfut-xyc/Warehouse-Management-System/raw/master/screenshot/order-dustbin.png)

##### 数据统计图
![](https://gitee.com/hfut-xyc/Warehouse-Management-System/raw/master/screenshot/chart.png)

##### 员工列表
![](https://gitee.com/hfut-xyc/Warehouse-Management-System/raw/master/screenshot/employee.png)

##### 编辑员工信息
![](https://gitee.com/hfut-xyc/Warehouse-Management-System/raw/master/screenshot/employee-edit.png)

##### 后台用户
![](https://gitee.com/hfut-xyc/Warehouse-Management-System/raw/master/screenshot/user.png)

##### API文档
![API文档](https://gitee.com/hfut-xyc/Warehouse-Management-System/raw/master/screenshot/api-doc.png)

#### 项目运行和注意事项
1. 安装Java 8、Maven、node.js和npm
2. npm安装@vue/cli和vue的开发服务器(建议换成阿里镜像源cnpm)
3. 安装MySQL 8.0+，配置root用户的密码为root，服务器端口号为3306
4. 安装Redis
5. 运行init.sql，初始化数据库
6. 启动数据库服务程序`systemctl start mysql`(Linux)
7. 启动缓存数据库服务程序`systemctl start redis`(Linux)，Windows下应启动`redis-server.exe`
8. 启动后端服务器`mvn spring-boot:run`(建议提前将maven换成阿里镜像源)
9. 启动前端服务器`npm run serve`
10. 实体类通过使用Lombok库的注解来自动生成setter和getter方法，提高了开发效率，代码变得简洁易读。如果在**Intellij Idea**上运行，记得要去**settings->plugins**下搜索安装Lombok插件 (**需要科学上网**)，不然代码会报错 (不安装的话不影响运行，但是报错总会让人不舒服=_=)
11. 后端程序运行后，可以访问 [http://localhost:8081/swagger-ui.html](http://localhost:8081/swagger-ui.html) 来查看所有的API文档，并且支持在文档上测试接口
12. 测试登录接口可以下列账户，tb_user存放的是加密后的密码
> - 超级管理员 用户名：admin 密码：admin
> - 普通用户 用户名：xyc 密码：xyc

#### 部署方法和注意事项
1. 预计作为项目部署服务器的是Aliyun, Ubuntu服务器，并使用docker技术部署

2. 服务器需要安装docker，并部署需要的docker容器

3. 部署前需要敲定域名，并且按照该域名修改后端的服务器配置（主要是跨域访问），申请免费SSL证书

4. 部署需要的脚本和配置文件写在deployment文件夹和Makefile中。部署时使用`make build`命令即可编译，`make run`是运行服务的指令

   ![编译过程截图](https://gitee.com/hfut-xyc/Warehouse-Management-System/raw/master/screenshot/build.png)

5. 前端编译所得`dist/**`应存放在nginx docker容器的`/var/www`下。nginx的配置文件和dockerfile已经存放在deployment文件夹中。**该docker容器的网络配置使用host模式**

6. 后端编译成单个jar，复制到JRE docker容器运行。**该docker容器的网络配置使用host模式**

7. Redis和MySQL也使用docker容器提供。为了安全，进行了网络隔离，除本机程序以外无法访问。

   ![服务的运行](https://gitee.com/hfut-xyc/Warehouse-Management-System/raw/master/screenshot/run.png)


#### RESTful API设计约定
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

#### CRUD操作的函数命名建议
##### 在命名Service和Mapper层的函数时，建议遵循以下风格:
如果是对实体类的insert操作，函数命名为**addXXX**。例如
> - 添加用户: addUser
> - 添加员工: addEmpolyee

如果是对实体类的delete操作，函数命名为**deleteXXXById**。例如
> - 删除用户: deleteUserById
> - 删除员工: deleteEmployeeById

如果实体类的field属于Primitive Type，进行update操作时，命名为update**FieldName**ById，例如
> - 修改用户的可用性状态: updateEnabledById

如果实体类的field属于Reference Type，进行update操作时，命名为update**FieldName**By**XId**.例如
> - 修改用户的角色列表：update**Role**By**Uid**
> - 修改员工的仓库列表：update**Warehouse**By**Eid** 
> - 修改仓库的员工列表：update**Employee**By**Wid**


#### 后端技术

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

#### 前端技术

| 技术       | 说明                  | 官网                                   |
| ---------- | --------------------- | -------------------------------------- |
| Vue        | 前端框架              | https://cn.vuejs.org/                     |
| Vue-router | 路由框架              | https://router.vuejs.org/zh/              |
| Vuex       | 全局状态管理框架      | https://vuex.vuejs.org/zh/               |
| Element-ui    | 前端UI组件框架            | https://element.eleme.cn               |
| Axios      | AJAX请求框架          | https://github.com/axios/axios         |
| v-charts   | 基于Echarts的图表框架 | https://v-charts.js.org/               |


