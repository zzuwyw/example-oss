/*
 Navicat Premium Data Transfer

 Source Server         : 本地连接-mysql
 Source Server Type    : MySQL
 Source Server Version : 50738 (5.7.38)
 Source Host           : localhost:3306
 Source Schema         : test

 Target Server Type    : MySQL
 Target Server Version : 50738 (5.7.38)
 File Encoding         : 65001

 Date: 26/12/2023 13:23:02
*/

-- ----------------------------
-- Table structure for t_user
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `username` varchar(36) NOT NULL DEFAULT '' COMMENT '登录账号',
  `password` varchar(128) NOT NULL DEFAULT '' COMMENT '登录密码',
  `real_name` varchar(36) NOT NULL DEFAULT '' COMMENT '真实姓名',
  `enabled` tinyint(1) NOT NULL DEFAULT '1' COMMENT '是否可用',
  `register_at` datetime DEFAULT NULL COMMENT '注册时间',
  `last_login` datetime DEFAULT NULL COMMENT '最后登录时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `rds_idx_1` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COMMENT='用户';

-- ----------------------------
-- Records of t_user
-- ----------------------------
INSERT INTO `t_user` VALUES (1, 'admin', '$2a$10$sxhhrBb1DY7GLjOFZzgY4ub.AIzAqmgTtjyJ2MsNGjqO0hHfFs4Z.', '超级管理员', 1, '2023-12-22 10:36:01', null);
