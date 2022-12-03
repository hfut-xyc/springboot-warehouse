CREATE DATABASE IF NOT EXISTS db_warehouse;
USE db_warehouse;

DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id`          INT(11)      NOT NULL AUTO_INCREMENT,
  `username`    VARCHAR(255) NOT NULL,
  `password`    VARCHAR(255) NOT NULL,
  `status`      TINYINT(1)   NOT NULL DEFAULT 1 COMMENT '用户状态，1表示正常，0表示锁定',
  `admin`       TINYINT(1)   NOT NULL DEFAULT 0 COMMENT '用户是否为管理员，1表示是，0表示不是',
  `create_time` TIMESTAMP    NOT NULL DEFAULT current_timestamp(),
  `update_time` TIMESTAMP    NOT NULL DEFAULT current_timestamp(),
  PRIMARY KEY (id),
  UNIQUE KEY idx(username, password)
) ENGINE = InnoDB
  AUTO_INCREMENT = 10000
  CHARSET = utf8;

INSERT INTO `user`(username, password)
VALUES ('admin', '$2a$10$1X8bXToh5IrujFA/HlQPUOjlIdHv19W47FJnXfjY3bqA.lwi/xSDC'),
       ('xyc', '$2a$10$JQSlh37uN3Tn96I1dPZYc.Qz59vnIoqXZe/bAqLq4tIO3gsVuMUYW');


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
  `id`          INT(11)              NOT NULL AUTO_INCREMENT,
  `uid`         INT(11)              NOT NULL,
  `wid`         INT(11)              NOT NULL,
  `pid`         INT(11)              NOT NULL,
  `amount`      INT                  NOT NULL COMMENT '订单数量，正数表示入库, 负数表示出库',
  `status`      TINYINT(1) DEFAULT 1 NOT NULL COMMENT '订单状态，0表示正常，1表示已删除',
  `create_time` TIMESTAMP  DEFAULT current_timestamp(),
  `update_time` TIMESTAMP  DEFAULT current_timestamp(),
  PRIMARY KEY (id),
  KEY idx_create_time (create_time)
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  CHARSET = utf8;

INSERT INTO `order`(uid, wid, pid, amount, status)
VALUES (10000, 1, 20000, 100, 1),
       (10000, 1, 20001, 200, 1),
       (10001, 2, 20001, 300, 1),
       (10001, 2, 20002, 400, 1),
       (10001, 3, 20000, 200, 1);


-- 仓库-产品视图，无需left join，
-- 因为会造成返回产品列表为null，而我们需要的是[]
DROP VIEW IF EXISTS view_warehouse_product;
CREATE VIEW view_warehouse_product AS
SELECT w.id,
       w.name,
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
SELECT p.id,
       p.name,
       sum(wp.amount) AS total
FROM product p,
     warehouse w,
     warehouse_product wp
WHERE p.id = wp.pid
  AND w.id = wp.wid
GROUP BY p.id;
