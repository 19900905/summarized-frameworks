/*
Navicat MySQL Data Transfer
Source Server         : 192.168.116.131
Source Server Version : 50721
Source Host           : 192.168.116.131:3306
Source Database       : quick-framework
Target Server Type    : MYSQL
Target Server Version : 50721
File Encoding         : 65001
Date: 2019-08-10 23:03:44
*/

-- ----------------------------
-- Table structure for t_uc_user
-- ----------------------------
DROP TABLE IF EXISTS `t_uc_user`;
CREATE TABLE `t_uc_user`
(
    `id`              bigint(20)   NOT NULL AUTO_INCREMENT,
    `user_id`         varchar(32)  NOT NULL COMMENT '用户ID',
    `username`        varchar(255) NOT NULL COMMENT '用户名',
    `salt`            varchar(255) NOT NULL COMMENT '加密盐值',
    `password`        varchar(255) NOT NULL COMMENT '密码',
    `status`          int(11)      NOT NULL COMMENT '用户状态',
    `register_ip`     int(11)      NOT NULL COMMENT '注册IP',
    `register_time`   datetime COMMENT '注册时间',
    `last_login_ip`   int(11) COMMENT '上次登录IP',
    `last_login_time` datetime COMMENT '上次登录时间',
    `login_times`     int(11) COMMENT '登录次数',
    `create_at`       datetime COMMENT '创建时间',
    `update_at`       datetime COMMENT '修改时间',
    `deleted`         int(11)      NOT NULL COMMENT '是否有效',
    `version`         int(11)      NOT NULL COMMENT '版本号',
    PRIMARY KEY (`id`)
)