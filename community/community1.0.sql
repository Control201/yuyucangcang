/*
 Navicat Premium Data Transfer

 Source Server         : MySQL
 Source Server Type    : MySQL
 Source Server Version : 50728
 Source Host           : localhost:3306
 Source Schema         : community

 Target Server Type    : MySQL
 Target Server Version : 50728
 File Encoding         : 65001

 Date: 29/03/2020 06:58:55
*/
-- 创建数据库
CREATE DATABASE community;

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for article
-- ----------------------------
DROP TABLE IF EXISTS `article`;
CREATE TABLE `article`  (
  `aid` int(9) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '文章编号',
  `title` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '文章标题',
  `uid` bigint(20) NOT NULL COMMENT '创建用户uid',
  `create_time` datetime(0) NOT NULL COMMENT '文章创建时间',
  `browse_num` int(9) NOT NULL DEFAULT 0 COMMENT '文章浏览量',
  `content` text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '文章内容',
  `status` tinyint(4) NOT NULL DEFAULT 0 COMMENT '状态  -1：已删除 0：成功发布 1：待审核  2：不通过',
  `top` tinyint(4) NOT NULL DEFAULT 0 COMMENT '是否置顶 0：否，1：是',
  `article_type` tinyint(4) NOT NULL DEFAULT 0 COMMENT '文章类型  0：普通帖子，1：精贴  2：公告',
  `open_procedure` tinyint(4) NOT NULL DEFAULT 0 COMMENT '公开方式  0：公开，1：私密  ',
  `review_status` tinyint(4) NOT NULL DEFAULT 1 COMMENT '是否支持评论  0：否，1：是',
  `comment_num` int(9) NOT NULL DEFAULT 0 COMMENT '评论数',
  `temp_file` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '临时文件',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '时间权重  用于排行显示',
  PRIMARY KEY (`aid`) USING BTREE,
  INDEX `uid`(`uid`) USING BTREE,
  INDEX `aid`(`aid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10086 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of article
-- ----------------------------
INSERT INTO `article` VALUES (1, '留言板', 111, '2020-02-19 19:09:37', 7, '绑定私信', 0, 0, 0, 1, 1, 0, NULL, '2020-03-05 13:01:03');
INSERT INTO `article` VALUES (10080, '发生的方式', 1580265547356934382, '2020-03-05 13:26:53', 16, '第三方第三', 0, 1, 0, 0, 1, 0, '[]', '2020-03-05 13:30:08');
INSERT INTO `article` VALUES (10081, '胜多负少', 1580265547356934382, '2020-03-05 13:27:05', 16, '发顺丰', 0, 1, 1, 0, 1, 0, '[]', '2020-03-28 18:03:18');
INSERT INTO `article` VALUES (10082, '水电费水电费', 1580265547356934382, '2020-03-05 13:27:19', 8, '发送到发多少', 0, 0, 1, 0, 1, 0, '[]', '2020-03-28 18:02:55');
INSERT INTO `article` VALUES (10083, '发送到发送到', 1580265547356934382, '2020-03-05 13:27:28', 4, '放松放松的', 0, 0, 0, 0, 1, 0, '[]', '2020-03-28 18:04:26');
INSERT INTO `article` VALUES (10084, '好久没搞搞啦', 1580265547356934382, '2020-03-28 17:47:37', 5, '滋滋滋', 0, 0, 0, 0, 1, 0, '[]', '2020-03-28 17:58:43');

-- ----------------------------
-- Table structure for article_comment
-- ----------------------------
DROP TABLE IF EXISTS `article_comment`;
CREATE TABLE `article_comment`  (
  `id` int(9) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '评论编号',
  `aid` int(9) UNSIGNED NOT NULL COMMENT '文章编号',
  `uid` bigint(20) NOT NULL COMMENT '创建用户uid',
  `create_time` datetime(0) NOT NULL COMMENT '评论创建时间',
  `comment_status` tinyint(4) NOT NULL DEFAULT 0 COMMENT '评论类型（0：普通评论 1：神评论）',
  `content` text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '评论内容',
  `love_num` int(5) NOT NULL DEFAULT 0 COMMENT '点赞数',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `aid`(`aid`) USING BTREE,
  INDEX `uid`(`uid`) USING BTREE,
  CONSTRAINT `article_comment_ibfk_1` FOREIGN KEY (`aid`) REFERENCES `article` (`aid`) ON DELETE CASCADE ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 1427 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of article_comment
-- ----------------------------
INSERT INTO `article_comment` VALUES (1403, 10081, 1580265547356934382, '2020-03-05 13:27:43', 0, '<img src=\"http://localhost:8080/layui/images/face/69.gif\" alt=\"[话筒]\">', 0);
INSERT INTO `article_comment` VALUES (1404, 10082, 1580265547356934382, '2020-03-05 13:27:50', 0, '水电费', 0);
INSERT INTO `article_comment` VALUES (1405, 10081, 1580265547356934382, '2020-03-05 13:28:06', 0, '水电费', 0);
INSERT INTO `article_comment` VALUES (1406, 10080, 1580265547356934382, '2020-03-05 13:28:13', 0, '胜多负少', 0);
INSERT INTO `article_comment` VALUES (1407, 10080, 1580265547356934382, '2020-03-05 13:28:29', 0, '胜多负少', 0);
INSERT INTO `article_comment` VALUES (1408, 10080, 1582196502455924141, '2020-03-05 13:30:08', 0, '胜多负少', 0);
INSERT INTO `article_comment` VALUES (1409, 10081, 1580265547356934382, '2020-03-28 17:47:51', 0, '是的', 0);
INSERT INTO `article_comment` VALUES (1410, 10081, 1580265547356934382, '2020-03-28 17:51:52', 0, 'sss', 0);
INSERT INTO `article_comment` VALUES (1411, 10081, 1580265547356934382, '2020-03-28 17:51:53', 0, 's', 0);
INSERT INTO `article_comment` VALUES (1412, 10081, 1580265547356934382, '2020-03-28 17:51:55', 0, 's', 0);
INSERT INTO `article_comment` VALUES (1413, 10081, 1580265547356934382, '2020-03-28 17:51:57', 0, 's', 0);
INSERT INTO `article_comment` VALUES (1414, 10081, 1580265547356934382, '2020-03-28 17:51:58', 0, 's', 0);
INSERT INTO `article_comment` VALUES (1415, 10081, 1580265547356934382, '2020-03-28 17:52:00', 0, 's', 0);
INSERT INTO `article_comment` VALUES (1416, 10081, 1580265547356934382, '2020-03-28 17:54:32', 0, 'ss', 0);
INSERT INTO `article_comment` VALUES (1417, 10081, 1580265547356934382, '2020-03-28 17:54:35', 0, 'sss', 0);
INSERT INTO `article_comment` VALUES (1418, 10081, 1580265547356934382, '2020-03-28 17:54:46', 0, 'sss', 0);
INSERT INTO `article_comment` VALUES (1419, 10084, 1580265547356934382, '2020-03-28 17:56:55', 0, 'dsf', 0);
INSERT INTO `article_comment` VALUES (1420, 10084, 1580265547356934382, '2020-03-28 17:58:43', 0, '<img src=\"http://localhost:8080/layui/images/face/2.gif\" alt=\"[哈哈]\">', 0);
INSERT INTO `article_comment` VALUES (1421, 10082, 1580265547356934382, '2020-03-28 17:58:53', 0, '<img src=\"http://localhost:8080/layui/images/face/16.gif\" alt=\"[太开心]\">', 0);
INSERT INTO `article_comment` VALUES (1422, 10082, 1580265547356934382, '2020-03-28 18:02:00', 0, '<img src=\"http://localhost:8080/layui/images/face/48.gif\" alt=\"[伤心]\">', 0);
INSERT INTO `article_comment` VALUES (1423, 10082, 1580265547356934382, '2020-03-28 18:02:55', 0, '<img src=\"http://localhost:8080/layui/images/face/32.gif\" alt=\"[困]\">', 0);
INSERT INTO `article_comment` VALUES (1424, 10081, 1580265547356934382, '2020-03-28 18:03:13', 0, '<img src=\"http://localhost:8080/layui/images/face/59.gif\" alt=\"[草泥马]\">', 0);
INSERT INTO `article_comment` VALUES (1425, 10081, 1580265547356934382, '2020-03-28 18:03:18', 0, '<img src=\"http://localhost:8080/layui/images/face/45.gif\" alt=\"[怒骂]\">', 0);
INSERT INTO `article_comment` VALUES (1426, 10083, 1580265547356934382, '2020-03-28 18:04:26', 0, '<img src=\"http://localhost:8080/layui/images/face/51.gif\" alt=\"[兔子]\">', 0);

-- ----------------------------
-- Table structure for collect_blog
-- ----------------------------
DROP TABLE IF EXISTS `collect_blog`;
CREATE TABLE `collect_blog`  (
  `id` int(9) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '收藏文章id',
  `aid` int(9) UNSIGNED NOT NULL COMMENT '文章编号',
  `uid` bigint(20) NOT NULL COMMENT '创建用户uid',
  `create_time` datetime(0) NOT NULL COMMENT '点赞创建时间',
  `status` tinyint(4) NULL DEFAULT 1 COMMENT '状态  0：没收藏 1：收藏 ',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `aid`(`aid`) USING BTREE,
  CONSTRAINT `collect_blog_ibfk_1` FOREIGN KEY (`aid`) REFERENCES `article` (`aid`) ON DELETE CASCADE ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for love_record
-- ----------------------------
DROP TABLE IF EXISTS `love_record`;
CREATE TABLE `love_record`  (
  `id` int(9) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '点赞id',
  `acid` int(9) UNSIGNED NOT NULL COMMENT '该评论id',
  `uid` bigint(20) NOT NULL COMMENT '创建用户uid',
  `create_time` datetime(0) NOT NULL COMMENT '点赞创建时间',
  `status` tinyint(4) NULL DEFAULT 1 COMMENT '状态  0：没点赞 1：点赞 ',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `acid`(`acid`) USING BTREE,
  CONSTRAINT `love_record_ibfk_3` FOREIGN KEY (`acid`) REFERENCES `article_comment` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for message
-- ----------------------------
DROP TABLE IF EXISTS `message`;
CREATE TABLE `message`  (
  `id` int(9) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'id',
  `aid` int(9) UNSIGNED NOT NULL COMMENT '文章编号',
  `uid` bigint(20) NOT NULL DEFAULT 0 COMMENT '创建用户uid',
  `rid` bigint(20) NOT NULL COMMENT '接受信息用户uid',
  `status` tinyint(4) NOT NULL DEFAULT 0 COMMENT '消息类别  0：评论文章  1：评论回复 2：私信 3：系统消息',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `content` text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '消息类内容',
  `read_status` tinyint(4) NOT NULL DEFAULT 0 COMMENT '是否已读 0：未读 1：已读',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `aid`(`aid`) USING BTREE,
  CONSTRAINT `message_ibfk_1` FOREIGN KEY (`aid`) REFERENCES `article` (`aid`) ON DELETE CASCADE ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 85 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of message
-- ----------------------------
INSERT INTO `message` VALUES (82, 1, 1580265547356934382, 1582196502455924141, 2, '2020-03-05 13:28:52', '请输入内容胜多负少', 1);
INSERT INTO `message` VALUES (83, 1, 1580265547356934382, 1582196502455924141, 2, '2020-03-05 13:28:56', '请输入内容胜多负少', 1);
INSERT INTO `message` VALUES (84, 10080, 1582196502455924141, 1580265547356934382, 0, '2020-03-05 13:30:08', '胜多负少', 1);

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'id',
  `uid` bigint(20) NOT NULL COMMENT '用户id 唯一',
  `nickname` varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '昵称',
  `password` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '登录密码',
  `avatar` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '/images/avatar/default.jpg' COMMENT '头像',
  `sex` int(2) NOT NULL DEFAULT 1 COMMENT '性别 1：男 2：女',
  `ip` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '登录ip',
  `create_time` datetime(0) NOT NULL COMMENT '注册时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `status` int(5) NOT NULL DEFAULT 1 COMMENT '-1：异常（拉黑，无效） 0:删除（注销）  1：正常',
  `last_time` datetime(0) NULL DEFAULT NULL COMMENT '最后登录时间',
  `location` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '喵星球' COMMENT '所在地',
  `birthday` datetime(0) NULL DEFAULT NULL COMMENT '用户生日',
  `channel_id` int(11) NOT NULL DEFAULT 1 COMMENT '注册渠道编号 默认1 自注册 2:qq 3:微博',
  `mobile` char(11) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '手机号',
  `email` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '邮箱',
  `remark` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
  `signature` varchar(400) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '这个人懒得留下签名' COMMENT '签名',
  `authority` int(1) NOT NULL DEFAULT 1 COMMENT '用户类型:(1.为普通用户,2.为管理员,3.为开发者)',
  `exp` int(9) NOT NULL DEFAULT 0 COMMENT '用户经验用于等级判断',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `email`(`email`) USING BTREE,
  UNIQUE INDEX `uid`(`uid`) USING BTREE,
  UNIQUE INDEX `mobile`(`mobile`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10005 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (10000, 1580265547356934382, '郁郁', '934cd11fd04135c255567551a5f82fef', '/images/avatar/1.jpg', 1, '0:0:0:0:0:0:0:1', '2020-01-13 20:58:16', '2020-02-22 15:19:26', 1, '2020-03-29 06:45:07', '北京', '2020-02-22 00:00:00', 1, '', '1523546582@qq.com', '程序猿小哥哥！', '&#128516;&#128517;', 2, 50);
INSERT INTO `user` VALUES (10003, 1581908378401380633, 'Share_380633', '934cd11fd04135c255567551a5f82fef', '/images/avatar/default.jpg', 1, '127.0.0.1', '2020-02-17 10:59:38', NULL, 1, '2020-02-20 18:59:22', '喵星球', NULL, 1, NULL, '546512313@qq.com', NULL, '这家伙很懒，还没有写个性签名', 1, 0);
INSERT INTO `user` VALUES (10004, 1582196502455924141, '哈哈', '934cd11fd04135c255567551a5f82fef', '/images/avatar/default.jpg', 1, '0:0:0:0:0:0:0:1', '2020-02-20 19:01:42', '2020-03-01 18:31:30', 1, '2020-03-05 13:31:37', 'XX', '2020-02-18 00:00:00', 1, '', '243254353@qq.com', NULL, '&#128522;&#128515;&#128516;&#128522;&#128515;&#128516;&#128517;&#128518;&#128521;&#128522;', 1, 10);

-- ----------------------------
-- Table structure for user_relation
-- ----------------------------
DROP TABLE IF EXISTS `user_relation`;
CREATE TABLE `user_relation`  (
  `id` int(9) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'id',
  `uid` bigint(20) NOT NULL COMMENT '创建用户uid',
  `rid` bigint(20) NOT NULL COMMENT '接受信息用户uid',
  `status` tinyint(4) NOT NULL DEFAULT 1 COMMENT '关系类别  0：无关系 1：粉丝 2：朋友 ',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `modify_time` datetime(0) NULL DEFAULT NULL COMMENT '修改关系的时间  暂时默认粉丝关系',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_relation
-- ----------------------------
INSERT INTO `user_relation` VALUES (1, 1580265547356934382, 1581908378401380633, 1, '2020-02-25 12:55:45', NULL);
INSERT INTO `user_relation` VALUES (3, 1582196502455924141, 1580265547356934382, 1, '2020-02-28 12:40:57', NULL);
INSERT INTO `user_relation` VALUES (4, 1580265547356934382, 1582196502455924141, 1, '2020-03-04 11:46:08', NULL);

SET FOREIGN_KEY_CHECKS = 1;
