CREATE DATABASE IF NOT EXISTS db_warehouse;
USE db_warehouse;

DROP TABLE IF EXISTS `tb_user`;
CREATE TABLE `tb_user` (
  `id`          INT(11)      NOT NULL AUTO_INCREMENT,
  `username`    VARCHAR(255) NOT NULL,
  `password`    VARCHAR(255) NOT NULL,
  `role`        TINYINT(1)   NOT NULL COMMENT '0表示普通用户，1表示超级管理员',
  `create_time` TIMESTAMP    NOT NULL DEFAULT current_timestamp(),
  `update_time` TIMESTAMP    NOT NULL DEFAULT current_timestamp(),
  PRIMARY KEY (id),
  UNIQUE KEY idx (username, password)
) ENGINE = InnoDB
  CHARSET = utf8;

INSERT INTO `tb_user`(username, password, role) VALUES ('admin', '8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918', 1);
INSERT INTO `tb_user`(username, password, role) VALUES ('xyc', 'ad6b60c1ac2e3d95828b88673e6d22c311c921f9606f08bc2967bfbd117a9a43', 0);


DROP TABLE IF EXISTS `tb_warehouse`;
CREATE TABLE `tb_warehouse` (
  `id`          INT(11)      NOT NULL AUTO_INCREMENT,
  `name`        VARCHAR(255) NOT NULL,
  `create_time` TIMESTAMP DEFAULT current_timestamp(),
  `update_time` TIMESTAMP DEFAULT current_timestamp(),
  PRIMARY KEY (id)
) ENGINE = InnoDB
  AUTO_INCREMENT = 10000
  CHARSET = utf8;

INSERT INTO `tb_warehouse`(name) VALUES ('1号仓库');
INSERT INTO `tb_warehouse`(name) VALUES ('2号仓库');
INSERT INTO `tb_warehouse`(name) VALUES ('3号仓库');
INSERT INTO `tb_warehouse`(name) VALUES ('4号仓库');
INSERT INTO `tb_warehouse`(name) VALUES ('5号仓库');

DROP TABLE IF EXISTS `tb_product`;
CREATE TABLE `tb_product` (
  `id`          INT(11)      NOT NULL AUTO_INCREMENT,
  `name`        VARCHAR(255) NOT NULL,
  `create_time` TIMESTAMP DEFAULT current_timestamp(),
  `update_time` TIMESTAMP DEFAULT current_timestamp(),
  PRIMARY KEY (id)
) ENGINE = InnoDB
  AUTO_INCREMENT = 20000
  CHARSET = utf8;

INSERT INTO `tb_product`(name)
VALUES ('华为p30');
INSERT INTO `tb_product`(name)
VALUES ('小米8');
INSERT INTO `tb_product`(name)
VALUES ('iPhone X');


DROP TABLE IF EXISTS `tb_warehouse_product`;
CREATE TABLE `tb_warehouse_product` (
  `wid`    INT(11) NOT NULL,
  `pid`    INT(11) NOT NULL,
  `amount` INT     NOT NULL,
  PRIMARY KEY (wid, pid),
  KEY idx_pid (pid)
) ENGINE = InnoDB
  CHARSET = utf8;

INSERT INTO `tb_warehouse_product`
VALUES (10001, 20000, 100),
       (10001, 20001, 200),
       (10002, 20001, 300),
       (10002, 20002, 400),
       (10003, 20000, 200),
       (10003, 20001, 500),
       (10003, 20002, 200);

DROP TABLE IF EXISTS `tb_order`;
CREATE TABLE `tb_order` (
  `id`          INT(11)              NOT NULL AUTO_INCREMENT,
  `uid`         INT(11)              NOT NULL,
  `wid`         INT(11)              NOT NULL,
  `pid`         INT(11)              NOT NULL,
  `amount`      INT                  NOT NULL COMMENT '订单数量',
  `status`      TINYINT(1) DEFAULT 1 NOT NULL COMMENT '订单状态，0表示出库，1表示入库，2表示删除',
  `create_time` TIMESTAMP  DEFAULT current_timestamp(),
  `update_time` TIMESTAMP  DEFAULT current_timestamp(),
  PRIMARY KEY (id),
  KEY idx_create_time (create_time)
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  CHARSET = utf8;

INSERT INTO `tb_order`(uid, wid, pid, amount, status)
VALUES (1, 10000, 20000, 100, 1),
       (1, 10000, 20001, 200, 1),
       (1, 10002, 20001, 300, 1),
       (2, 10002, 20002, 400, 1),
       (2, 10003, 20000, 200, 1);


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
tb_order BY id, pid;

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
