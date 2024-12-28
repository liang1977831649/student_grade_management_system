/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 50719
 Source Host           : localhost:3306
 Source Schema         : student_grade

 Target Server Type    : MySQL
 Target Server Version : 50719
 File Encoding         : 65001

 Date: 22/10/2024 11:50:06
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for admin
-- ----------------------------
DROP TABLE IF EXISTS `admin`;
CREATE TABLE `admin`  (
  `id` varchar(15) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `name` varchar(31) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `password` varchar(127) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `role` tinyint(4) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of admin
-- ----------------------------
INSERT INTO `admin` VALUES ('123456', '张三', 'e10adc3949ba59abbe56e057f20f883e', 2);

-- ----------------------------
-- Table structure for course
-- ----------------------------
DROP TABLE IF EXISTS `course`;
CREATE TABLE `course`  (
  `id` int(11) NOT NULL,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `no` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `des` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `times` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `teacher` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of course
-- ----------------------------

-- ----------------------------
-- Table structure for menu
-- ----------------------------
DROP TABLE IF EXISTS `menu`;
CREATE TABLE `menu`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `menuType` tinyint(4) NOT NULL,
  `menuName` varchar(127) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `path` varchar(127) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `icon` varchar(127) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `father` int(11) NULL DEFAULT NULL,
  `role` varchar(127) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of menu
-- ----------------------------
INSERT INTO `menu` VALUES (1, 1, '系统首页', '/main', 'Avatar', 0, '0,1,2');
INSERT INTO `menu` VALUES (2, 2, '课程管理', '2', 'Location', 0, '0,1,2');
INSERT INTO `menu` VALUES (3, 1, '课程信息', '/courseInfo', NULL, 2, '0,1,2');
INSERT INTO `menu` VALUES (4, 1, '个人中心', '/userInfo', 'Document', 0, '0,1,2');

SET FOREIGN_KEY_CHECKS = 1;
