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

 Date: 29/05/2024 16:40:04
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_dict
-- ----------------------------
DROP TABLE IF EXISTS `t_dict`;
CREATE TABLE `t_dict`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `dict_code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '字典编码',
  `dict_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '字典名称',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `rds_idx_1`(`dict_code`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '字典' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_dict
-- ----------------------------
INSERT INTO `t_dict` VALUES (1, 'gender', '性别');
INSERT INTO `t_dict` VALUES (2, 'grade', '年级');
INSERT INTO `t_dict` VALUES (3, 'status', '状态');

-- ----------------------------
-- Table structure for t_dict_item
-- ----------------------------
DROP TABLE IF EXISTS `t_dict_item`;
CREATE TABLE `t_dict_item`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `dict_code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '字典编码',
  `item_code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '字典项编码',
  `item_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '字典项名称',
  `sort` smallint(5) NOT NULL DEFAULT 1 COMMENT '字典项排序',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `rds_idx_1`(`dict_code`, `item_code`) USING BTREE,
  INDEX `rds_idx_2`(`dict_code`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '字典项' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_dict_item
-- ----------------------------
INSERT INTO `t_dict_item` VALUES (1, 'gender', 'male', '男', 1);
INSERT INTO `t_dict_item` VALUES (2, 'gender', 'female', '女', 2);
INSERT INTO `t_dict_item` VALUES (3, 'grade', 'grade1', '一年级', 1);
INSERT INTO `t_dict_item` VALUES (4, 'grade', 'grade2', '二年级', 2);
INSERT INTO `t_dict_item` VALUES (5, 'grade', 'grade3', '三年级', 3);
INSERT INTO `t_dict_item` VALUES (6, 'status', '10', '状态一', 1);
INSERT INTO `t_dict_item` VALUES (7, 'status', '30', '状态三', 3);
INSERT INTO `t_dict_item` VALUES (8, 'status', '20', '状态二', 2);

SET FOREIGN_KEY_CHECKS = 1;
