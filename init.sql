-- 第一次使用时，初始化数据库
-- cat init.sql | mysql -u root -p
create database if not exists warehouse;
use warehouse;

drop table if exists tb_user;
create table tb_user(
    `id` int(11) not null auto_increment,
    `username` varchar(255) not null,
    `password` varchar(255) not null,
    `phone` varchar(11),
    `enabled` tinyint(1) default 1,
    `register_time` timestamp default current_timestamp(),
    primary key(id)
) ENGINE=InnoDB AUTO_INCREMENT=1000 DEFAULT CHARSET=utf8;

insert into tb_user(username, password, phone) values ('admin', '$2a$10$1X8bXToh5IrujFA/HlQPUOjlIdHv19W47FJnXfjY3bqA.lwi/xSDC', '15141241345');
insert into tb_user(username, password, phone) values ('xyc', '$2a$10$JQSlh37uN3Tn96I1dPZYc.Qz59vnIoqXZe/bAqLq4tIO3gsVuMUYW', '151121231');

-- *********************************************************************
drop table if exists tb_role;
create table tb_role(
    `id` int(11) not null auto_increment,
    `name` varchar(255) not null,
		`remark` varchar(255) not null comment '备注',
    primary key(id)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

insert into tb_role values (1, 'admin', '超级管理员');
insert into tb_role values (2, 'user', '普通用户');

-- *********************************************************************
drop table if exists tb_user_role;
create table tb_user_role(
    `uid` int(11) not null,
    `rid` int(11) not null,
    primary key(uid, rid),
		key idx_uid(uid),
		key idx_rid(rid)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

insert into tb_user_role values(1000, 1);
insert into tb_user_role values(1000, 2);
insert into tb_user_role values(1001, 1);
insert into tb_user_role values(1001, 2);

-- *********************************************************************
drop table if exists tb_employee;
create table tb_employee(
    `id` int(11) not null auto_increment,
    `name` varchar(255) not null,
    `gender` enum('男','女'),
    `phone` varchar(11),
    `birthday` date,
    `hire_date` date,
    `salary` decimal(10,2),
    primary key(id)
) ENGINE=InnoDB AUTO_INCREMENT=10000 DEFAULT CHARSET=utf8;

insert into tb_employee(name, gender, phone, birthday, hire_date, salary) values ('aaa', '男', '15323122414', '1999-08-01', '2017-09-01', 5000.50);
insert into tb_employee(name, gender, phone, birthday, hire_date, salary) values ('bbb', '女', '153212314', '1999-08-01', '2017-09-01', 6000.50);
insert into tb_employee(name, gender, phone, birthday, hire_date, salary) values ('ccc', '男', '153222314', '1999-08-01', '2017-09-01', 7000.50);
insert into tb_employee(name, gender, phone, birthday, hire_date, salary) values ('ddd', '男', '1123122414', '1999-08-01', '2017-09-01', 5000.50);

-- *********************************************************************
drop table if exists tb_warehouse;
create table tb_warehouse(
    `id` int(11) not null auto_increment,
    `name` varchar(255) not null,
    primary key(id)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

insert into tb_warehouse(name) values ('1号仓库');
insert into tb_warehouse(name) values ('2号仓库');
insert into tb_warehouse(name) values ('3号仓库');
insert into tb_warehouse(name) values ('4号仓库');

-- *********************************************************************
drop table if exists tb_product;
create table tb_product(
    `id` int(11) not null auto_increment,
    `name` varchar(255) not null,
    `supplier` varchar(255),
    `total` int(11) not null,
    primary key(id)
) ENGINE=InnoDB AUTO_INCREMENT=20000 DEFAULT CHARSET=utf8;

-- *********************************************************************
drop table if exists tb_employee_warehouse;
create table tb_employee_warehouse(
    `eid` int(11) not null,
    `wid` int(11) not null,
    primary key(eid, wid),
		key idx_eid(eid),
		key idx_wid(wid)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

insert into tb_employee_warehouse values (10000, 1);
insert into tb_employee_warehouse values (10000, 2);
insert into tb_employee_warehouse values (10001, 3);
insert into tb_employee_warehouse values (10001, 4);
insert into tb_employee_warehouse values (10002, 1);
insert into tb_employee_warehouse values (10002, 2);
insert into tb_employee_warehouse values (10002, 3);
insert into tb_employee_warehouse values (10003, 1);

-- *********************************************************************
drop table if exists tb_warehouse_product;
create table tb_warehouse_product(
    `wid` int(11) not null,
    `pid` int(11) not null,
    `amount` int not null,
    primary key(wid, pid),
		key idx_wid(wid),
		key idx_pid(pid)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

drop table if exists tb_order;
create table tb_order(
    `id` int(11) not null auto_increment,
    `eid` int(11) not null,
    `wid` int(11) not null,
    `pid` int(11) not null,
    `amount` int not null comment '订单处理数量',
    `status` tinyint not null comment '订单状态：-1表示订单被删除, -2表示商品报废操作, 0表示订单是入库, 1表示订单是出库',
    `create_time` timestamp default current_timestamp(),
    `update_time` timestamp default current_timestamp(),
    primary key(id),
		key idx_eid(eid),		
		key idx_wid(wid),
		key idx_pid(pid)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8;

-- 创建用户-角色视图
drop view if exists view_user_role;
create view view_user_role as
select u.*, r.id as rid, r.name as rname, remark
from tb_user u, tb_role r, tb_user_role ur
where u.id=ur.uid and r.id=ur.rid;

-- 创建员工-仓库视图
drop view if exists view_employee_warehouse;
create view view_employee_warehouse as
select e.*, w.id as wid, w.name as wname
from tb_employee e, tb_warehouse w, tb_employee_warehouse ew
where e.id=ew.eid and w.id=ew.wid;
 
-- 创建仓库-商品视图
