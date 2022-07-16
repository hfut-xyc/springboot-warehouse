CREATE DATABASE IF NOT EXISTS db_warehouse;
USE db_warehouse;

DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id`          INT(11)      NOT NULL AUTO_INCREMENT,
  `username`    VARCHAR(255) NOT NULL,
  `password`    VARCHAR(255) NOT NULL,
  `enabled`     TINYINT(1) DEFAULT 1,
  `create_time` TIMESTAMP  DEFAULT current_timestamp(),
  `update_time` TIMESTAMP  DEFAULT current_timestamp(),
  PRIMARY KEY (id)
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  CHARSET = utf8;

INSERT INTO `user`(username, password)
VALUES ('admin', '$2a$10$1X8bXToh5IrujFA/HlQPUOjlIdHv19W47FJnXfjY3bqA.lwi/xSDC'),
       ('xyc', '$2a$10$JQSlh37uN3Tn96I1dPZYc.Qz59vnIoqXZe/bAqLq4tIO3gsVuMUYW');

DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
  `id`          INT(11)      NOT NULL AUTO_INCREMENT,
  `name`        VARCHAR(255) NOT NULL,
  PRIMARY KEY (id)
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  CHARSET = utf8;

INSERT INTO role(name)
VALUES ('admin'),
       ('user');

DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role` (
  `uid` INT(11) NOT NULL,
  `rid` INT(11) NOT NULL,
  PRIMARY KEY (uid, rid),
  KEY idx_rid (rid)
) ENGINE = InnoDB
  CHARSET = utf8;

INSERT INTO `user_role`
VALUES (1, 1),
       (2, 2);

DROP TABLE IF EXISTS `employee`;
CREATE TABLE `employee` (
  `id`          INT(11)      NOT NULL AUTO_INCREMENT,
  `name`        VARCHAR(255) NOT NULL,
  `gender`      ENUM ('男','女'),
  `phone`       VARCHAR(11),
  `salary`      DECIMAL(10, 2),
  `create_time` TIMESTAMP DEFAULT current_timestamp(),
  `update_time` TIMESTAMP DEFAULT current_timestamp(),
  PRIMARY KEY (id)
) ENGINE = InnoDB
  AUTO_INCREMENT = 10000
  CHARSET = utf8;

INSERT INTO `employee`(name, gender, phone, salary)
VALUES ('aaa', '男', '15323122414', 5000.50),
       ('bbb', '女', '153212314', 6000.50),
       ('ccc', '男', '153222314', 7000.50),
       ('ddd', '男', '1123122414', 5000.50),
       ('eee', '男', '2351411124', 8200.99);

DROP TABLE IF EXISTS `warehouse`;
CREATE TABLE `warehouse` (
  `id`          INT(11)      NOT NULL AUTO_INCREMENT,
  `name`        VARCHAR(255) NOT NULL,
  `create_time` TIMESTAMP DEFAULT current_timestamp(),
  `update_time` TIMESTAMP DEFAULT current_timestamp(),
  PRIMARY KEY (id)
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  CHARSET = utf8;

INSERT INTO `warehouse`(name)
VALUES ('1号仓库'),
       ('2号仓库'),
       ('3号仓库'),
       ('4号仓库'),
       ('5号仓库');

DROP TABLE IF EXISTS `product`;
CREATE TABLE `product` (
  `id`          INT(11)      NOT NULL AUTO_INCREMENT,
  `name`        VARCHAR(255) NOT NULL,
  `create_time` TIMESTAMP DEFAULT current_timestamp(),
  `update_time` TIMESTAMP DEFAULT current_timestamp(),
  PRIMARY KEY (id)
) ENGINE = InnoDB
  AUTO_INCREMENT = 20000
  CHARSET = utf8;

INSERT INTO `product`(name)
VALUES ('华为p30'),
       ('小米8'),
       ('iPhone X');

DROP TABLE IF EXISTS `employee_warehouse`;
CREATE TABLE `employee_warehouse` (
  `eid` INT(11) NOT NULL,
  `wid` INT(11) NOT NULL,
  PRIMARY KEY (eid, wid),
  KEY idx_wid (wid)
) ENGINE = InnoDB
  CHARSET = utf8;

INSERT INTO `employee_warehouse`
VALUES (10000, 1),
       (10000, 2),
       (10001, 3),
       (10001, 4),
       (10002, 1),
       (10002, 2),
       (10002, 3);

DROP TABLE IF EXISTS `warehouse_product`;
CREATE TABLE `warehouse_product` (
  `wid`    INT(11) NOT NULL,
  `pid`    INT(11) NOT NULL,
  `amount` INT     NOT NULL,
  PRIMARY KEY (wid, pid),
  KEY idx_pid (pid)
) ENGINE = InnoDB
  CHARSET = utf8;

INSERT INTO `warehouse_product`
VALUES (1, 20000, 100),
       (1, 20001, 200),
       (2, 20001, 300),
       (2, 20002, 400),
       (3, 20000, 200),
       (3, 20001, 500),
       (3, 20002, 200);

DROP TABLE IF EXISTS `order`;
CREATE TABLE `order` (
  `id`          INT(11)           NOT NULL AUTO_INCREMENT,
  `eid`         INT(11)           NOT NULL,
  `wid`         INT(11)           NOT NULL,
  `pid`         INT(11)           NOT NULL,
  `amount`      INT               NOT NULL COMMENT '订单数量: 正数表示入库, 负数表示出库',
  `status`      ENUM ('正常', '报废') NOT NULL,
  `create_time` TIMESTAMP DEFAULT current_timestamp(),
  `update_time` TIMESTAMP DEFAULT current_timestamp(),
  PRIMARY KEY (id),
  KEY idx_create_time (create_time)
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  CHARSET = utf8;

INSERT INTO `order`(eid, wid, pid, amount, status)
VALUES (10000, 1, 20000, 100, '正常'),
       (10001, 1, 20001, 200, '正常'),
       (10002, 2, 20001, 300, '正常'),
       (10001, 2, 20002, 400, '正常'),
       (10000, 3, 20000, 200, '正常');

-- 用户-角色视图
DROP VIEW IF EXISTS view_user_role;
CREATE VIEW view_user_role AS
SELECT u.id, u.username,
       r.id   AS rid,
       r.name AS rname
FROM user u,
     role r,
     user_role ur
WHERE u.id = ur.uid
  AND r.id = ur.rid
ORDER BY id, rid;

-- 员工-仓库视图
DROP VIEW IF EXISTS view_employee_warehouse;
CREATE VIEW view_employee_warehouse AS
SELECT e.id, e.name, e.gender, e.phone, e.salary,
       w.id   AS wid,
       w.name AS wname
FROM employee e
       LEFT JOIN employee_warehouse ew ON e.id = ew.eid
       LEFT JOIN warehouse w ON w.id = ew.wid;

-- 仓库-员工视图
DROP VIEW IF EXISTS view_warehouse_employee;
CREATE VIEW view_warehouse_employee AS
SELECT w.id, w.name,
       e.id   AS eid,
       e.name AS ename,
       e.gender,
       e.phone,
       e.salary
FROM warehouse w
       LEFT JOIN employee_warehouse ew ON w.id = ew.wid
       LEFT JOIN employee e ON e.id = ew.eid;

-- 仓库-产品视图，无需left join，
-- 因为会造成返回产品列表为null，而我们需要的是[]
DROP VIEW IF EXISTS view_warehouse_product;
CREATE VIEW view_warehouse_product AS
SELECT w.id, w.name,
       p.id      AS pid,
       p.name    AS pname,
       wp.amount AS amount
FROM warehouse w,
     warehouse_product wp,
     product p
WHERE w.id = wp.wid
  AND p.id = wp.pid
ORDER BY id, pid;

-- 产品-仓库视图，用于统计每个产品总数量，这里不需要left join
DROP VIEW IF EXISTS view_product_warehouse;
CREATE VIEW view_product_warehouse AS
SELECT p.id, p.name,
       sum(wp.amount) AS total
FROM product p,
     warehouse w,
     warehouse_product wp
WHERE p.id = wp.pid
  AND w.id = wp.wid
GROUP BY p.id;
