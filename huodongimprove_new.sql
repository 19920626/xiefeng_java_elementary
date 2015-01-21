/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50528
Source Host           : localhost:3306
Source Database       : huodongimprove

Target Server Type    : MYSQL
Target Server Version : 50528
File Encoding         : 65001

Date: 2015-01-21 20:14:38
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `promotion`
-- 促销表
-- ----------------------------
DROP TABLE IF EXISTS `promotion`;
CREATE TABLE `promotion` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '流水号',
  `phone` bigint(20) NOT NULL COMMENT '手机号码',
  `price` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `电话号码` (`phone`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of promotion
-- ----------------------------
-- ----------------------------
-- 手机号码phone是唯一的，price是参与的价格
-- ----------------------------

-- ----------------------------
-- Table structure for `summation`
-- 总量表
-- ----------------------------
DROP TABLE IF EXISTS `summation`;
CREATE TABLE `summation` (
  `count` int(11) NOT NULL DEFAULT '0' COMMENT '计数值',
  PRIMARY KEY (`count`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of summation
-- ----------------------------
INSERT INTO `summation` VALUES ('0');
-- ----------------------------
-- 初始值为0，每插入一个值，count+1，只有一行值，表示参与活动人员总数
-- ----------------------------