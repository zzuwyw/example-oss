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
   `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '登录账号',
   `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '登录密码',
   `enabled` tinyint(1) NOT NULL DEFAULT 1 COMMENT '是否可用',
   `register_at` datetime NULL DEFAULT NULL COMMENT '注册时间',
   PRIMARY KEY (`id`) USING BTREE,
   UNIQUE INDEX `rds_idx_1`(`username`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_user
-- ----------------------------
INSERT INTO `t_user` VALUES (1, 'admin', '$2a$10$sxhhrBb1DY7GLjOFZzgY4ub.AIzAqmgTtjyJ2MsNGjqO0hHfFs4Z.', 1, '2023-12-22 10:36:01');
