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

drop table if exists tb_role;
create table tb_role(
    `id` int(11) not null auto_increment,
    `name` varchar(255) not null,
		`remark` varchar(255) not null,
    primary key(id)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

drop table if exists tb_user_role;
create table tb_user_role(
    `uid` int(11) not null,
    `rid` int(11) not null,
    primary key(uid, rid)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

insert into tb_user(username, password, phone)
values ('admin', '$2a$10$1X8bXToh5IrujFA/HlQPUOjlIdHv19W47FJnXfjY3bqA.lwi/xSDC', '15141241345');
insert into tb_user(username, password, phone)
values ('xyc', '$2a$10$JQSlh37uN3Tn96I1dPZYc.Qz59vnIoqXZe/bAqLq4tIO3gsVuMUYW', '151121231');


insert into tb_role values (1, 'admin', '超级管理员');
insert into tb_role values (2, 'user', '普通用户');
insert into tb_user_role values(1000, 1);
insert into tb_user_role values(1000, 2);
insert into tb_user_role values(1001, 1);
insert into tb_user_role values(1001, 2);

