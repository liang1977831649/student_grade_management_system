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

 Date: 28/12/2024 21:51:15
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
INSERT INTO `admin` VALUES ('123456', '张三', 'e10adc3949ba59abbe56e057f20f883e', 0);

-- ----------------------------
-- Table structure for course
-- ----------------------------
DROP TABLE IF EXISTS `course`;
CREATE TABLE `course`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `no` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `des` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `times` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `teacherId` int(11) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 12 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of course
-- ----------------------------
INSERT INTO `course` VALUES (1, '体育', '1024', '上的是体育课', '48', 1);
INSERT INTO `course` VALUES (2, '体率', '2048', '上的是体率课', '64', 1);
INSERT INTO `course` VALUES (3, '英语', '3029', '上的是英语课', '24', 2);
INSERT INTO `course` VALUES (4, '物理', '4029', '云里雾里', '64', 3);
INSERT INTO `course` VALUES (5, '化学', '5028', '小鼠实验', '32', 1);
INSERT INTO `course` VALUES (6, '计算机基础', '6055', 'excel/word', '20', 2);
INSERT INTO `course` VALUES (10, '数字电路~', 'cs9814', '为啥要描述？a', '32', 8);
INSERT INTO `course` VALUES (11, 'java程序设计', 'cx601', 'Java程序开发', '64', 9);

-- ----------------------------
-- Table structure for menu
-- ----------------------------
DROP TABLE IF EXISTS `menu`;
CREATE TABLE `menu`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `menuType` tinyint(4) NOT NULL,
  `menuName` varchar(127) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `path` varchar(127) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `component` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `icon` varchar(127) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `father` int(11) NULL DEFAULT NULL,
  `role` varchar(127) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 14 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of menu
-- ----------------------------
INSERT INTO `menu` VALUES (1, 1, '系统首页', '/main', 'Main', 'Avatar', 0, '0,1');
INSERT INTO `menu` VALUES (2, 2, '课程管理', '', NULL, 'Location', 0, '0,1');
INSERT INTO `menu` VALUES (3, 1, '课程信息', '/courseInfo', 'CourseInfo', NULL, 2, '0');
INSERT INTO `menu` VALUES (4, 2, '个人中心', '/user', 'User', 'Document', 0, '0,1');
INSERT INTO `menu` VALUES (5, 1, '教师管理', '/teacher', 'Teacher', 'Ship', 0, '0');
INSERT INTO `menu` VALUES (6, 1, '学生管理', '/student', 'Student', 'Crop', 0, '0');
INSERT INTO `menu` VALUES (7, 1, '选课记录', '/courseRecord', 'CourseRecord', NULL, 2, '0');
INSERT INTO `menu` VALUES (8, 1, '课程评价', '/scoreAccess', 'ScoreAccess', NULL, 2, '0');
INSERT INTO `menu` VALUES (9, 1, '查看成绩', '/scoreQuery', 'ScoreQuery', NULL, 2, '1');
INSERT INTO `menu` VALUES (10, 1, '选定课程', '/courseChoose', 'CourseChoose', NULL, 2, '1');
INSERT INTO `menu` VALUES (11, 1, '个人选课记录', '/courseRecordPerson', 'CourseRecordPerson', NULL, 2, '1');
INSERT INTO `menu` VALUES (12, 1, '修改密码', '/password', 'Password', NULL, 4, '0,1');
INSERT INTO `menu` VALUES (13, 1, '个人资料', '/userInfo', 'UserInfo', NULL, 4, '1');

-- ----------------------------
-- Table structure for student
-- ----------------------------
DROP TABLE IF EXISTS `student`;
CREATE TABLE `student`  (
  `id` int(11) NOT NULL,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `sex` tinyint(4) NULL DEFAULT NULL,
  `phone` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `birthday` date NULL DEFAULT NULL,
  `avatar` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `role` tinyint(4) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of student
-- ----------------------------
INSERT INTO `student` VALUES (2021113, '李楠', 'e10adc3949ba59abbe56e057f20f883e', 0, '123123123', '2003-10-15', 'http://localhost:8080/file/down?name=2024/12/5/5ec0b_1658370964780.jpg', 1);
INSERT INTO `student` VALUES (20211132, '张如意', 'e10adc3949ba59abbe56e057f20f883e', 1, '15224637598', '2024-12-09', 'http://localhost:8080/file/down?name=2024/12/5/5ec0b_1658370964780.jpg', 1);
INSERT INTO `student` VALUES (20211136, '张可可', 'e10adc3949ba59abbe56e057f20f883e', 1, '15224637598', '2018-12-11', 'http://localhost:8080/file/down?name=2024/12/28/963a2_56hgjdf.jpg', 1);
INSERT INTO `student` VALUES (20211137, '张琪', 'e10adc3949ba59abbe56e057f20f883e', 1, '4213212412', '2002-02-20', 'http://localhost:8080/file/down?name=2024/12/4/86ea9_wefwasd.jpg', 1);
INSERT INTO `student` VALUES (20211138, '张扬', 'c33367701511b4f6020ec61ded352059', 1, '12434356758', '2000-01-10', 'http://localhost:8080/file/down?name=2024/12/5/1d48d_xiangjiao.jpg', 1);
INSERT INTO `student` VALUES (20211139, '吴奇隆', 'e10adc3949ba59abbe56e057f20f883e', 1, '21241231', '2003-12-17', 'http://localhost:8080/file/down?name=2024/12/5/0a222_1658370964780.jpg', 1);
INSERT INTO `student` VALUES (20211142, '吴彦祖', 'e10adc3949ba59abbe56e057f20f883e', 1, '136274812', '2001-09-11', 'http://localhost:8080/file/down?name=2024/12/5/00e21_qwesdv.jpg', 1);
INSERT INTO `student` VALUES (2021113789, '梁政@', 'e10adc3949ba59abbe56e057f20f883e', 0, NULL, NULL, NULL, 1);

-- ----------------------------
-- Table structure for studentcourse
-- ----------------------------
DROP TABLE IF EXISTS `studentcourse`;
CREATE TABLE `studentcourse`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `course_no` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `student_id` int(11) NULL DEFAULT NULL,
  `score` float NULL DEFAULT NULL,
  `comment` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `feedback` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `index_courseNo`(`course_no`) USING BTREE,
  INDEX `index_studentId`(`student_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 17 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of studentcourse
-- ----------------------------
INSERT INTO `studentcourse` VALUES (4, '4029', 20211136, 60, '继续努力', '好的老师');
INSERT INTO `studentcourse` VALUES (6, '6055', 20211132, 99, 'good', NULL);
INSERT INTO `studentcourse` VALUES (9, '5028', 20211139, NULL, NULL, NULL);
INSERT INTO `studentcourse` VALUES (10, 'cx601', 20211139, NULL, NULL, NULL);
INSERT INTO `studentcourse` VALUES (11, '3029', 20211142, 87, '不错不错', NULL);
INSERT INTO `studentcourse` VALUES (12, 'cx601', 20211142, 62, '下次再加吧劲', NULL);
INSERT INTO `studentcourse` VALUES (13, '2048', 20211137, NULL, NULL, NULL);
INSERT INTO `studentcourse` VALUES (14, '4029', 20211137, NULL, NULL, NULL);
INSERT INTO `studentcourse` VALUES (15, '6055', 20211137, NULL, NULL, NULL);
INSERT INTO `studentcourse` VALUES (16, '1024', 20211136, 85, '还不错', '可不是嘛');

-- ----------------------------
-- Table structure for teacher
-- ----------------------------
DROP TABLE IF EXISTS `teacher`;
CREATE TABLE `teacher`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `no` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `phone` varchar(12) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `no`(`no`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of teacher
-- ----------------------------
INSERT INTO `teacher` VALUES (1, '1102', '陈晓', '12243541461');
INSERT INTO `teacher` VALUES (2, '1122', '楚歌1', '45223567245');
INSERT INTO `teacher` VALUES (3, '1121', '兰亭集序', '45837458921');
INSERT INTO `teacher` VALUES (8, '2341', '阿迪达斯', '12332112344');
INSERT INTO `teacher` VALUES (9, 'tc7421', '王大明', '12441122311');
INSERT INTO `teacher` VALUES (10, 'cx2201', '张小龙', '1231234567');

SET FOREIGN_KEY_CHECKS = 1;
