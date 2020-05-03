# Warehouse Management System

#### 介绍
Course Design of DataBase, HFUT, 2020

#### 软件架构
软件架构说明


#### 开发教程
1. 安装Java 8+、Maven、node.js和npm
2. npm安装@vue/cli和vue的开发服务器
3. 安装MySQL 8.0+，配置root用户密码为root，服务器端口号为3306
4. 运行init.sql，初始化数据库
5. 启动数据库服务程序`systemctl start mysql`(Linux)
6. 启动后端服务器`mvn spring-boot:run`
7. 启动前端服务器`npm run serve`

#### RESTful API设计约定
1. 后端程序统一返回json格式给前端，只负责提供数据，不用管页面如何跳转
2. 数据库交互时，select操作一律采用`GET`请求，insert和update操作使用`POST`请求，delete操作使用`DELETE`请求
3. 接口url的设计示例如下,以用户为例
- 查询用户列表： /users
- 添加用户： /user/add
- 删除用户： /user/{id}/delete
- 修改用户：/user/{id}/edit
4. 后期设计其他模块也是如此，以员工为例
- 查询员工列表： /employees
- 添加员工： /employee/add
- 删除员工： /employee/{id}/delete
- 修改员工：/employee/{id}/edit

#### 后端技术

| 技术                 | 说明                | 官网                                                 |
| -------------------- | ------------------- | ---------------------------------------------------- |
| SpringBoot           | 容器+MVC框架        | https://spring.io/projects/spring-boot               |
| SpringSecurity       | 认证和授权框架      | https://spring.io/projects/spring-security           |
| MyBatis              | ORM框架,用于和数据库交互  | http://www.mybatis.org/mybatis-3/zh/index.html       |

#### 前端技术

| 技术       | 说明                  | 官网                                   |
| ---------- | --------------------- | -------------------------------------- |
| Vue        | 前端框架              | https://cn.vuejs.org/                     |
| Vue-router | 路由框架              | https://router.vuejs.org/zh/              |
| Vuex       | 全局状态管理框架      | https://vuex.vuejs.org/zh/               |
| Element-ui    | 前端UI框架            | https://element.eleme.cn               |
| Axios      | 前端HTTP框架          | https://github.com/axios/axios         |
| v-charts   | 基于Echarts的图表框架 | https://v-charts.js.org/               |

#### TODO

请按重要程度排序

1. Warehouse物品管理部分
2. 页面鉴权
3. `/home`欢迎页



