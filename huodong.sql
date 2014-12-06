/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50528
Source Host           : localhost:3306
Source Database       : huodong

Target Server Type    : MYSQL
Target Server Version : 50528
File Encoding         : 65001

Date: 2014-12-06 23:15:13
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `promotion`
-- ----------------------------
DROP TABLE IF EXISTS `promotion`;
CREATE TABLE `promotion` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `phone` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `phone` (`phone`)
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=gbk;

-- ----------------------------
-- Records of promotion
-- ----------------------------
INSERT INTO `promotion` VALUES ('18', '11111111110');
INSERT INTO `promotion` VALUES ('19', '11111111111');
INSERT INTO `promotion` VALUES ('20', '11111111112');
INSERT INTO `promotion` VALUES ('21', '11111111113');
INSERT INTO `promotion` VALUES ('22', '11111111114');
INSERT INTO `promotion` VALUES ('23', '11111111115');
INSERT INTO `promotion` VALUES ('24', '11111111116');
INSERT INTO `promotion` VALUES ('25', '11111111117');
INSERT INTO `promotion` VALUES ('26', '11111111118');
INSERT INTO `promotion` VALUES ('28', '33333333333');

-- ----------------------------
-- Table structure for `sign`
-- ----------------------------
DROP TABLE IF EXISTS `sign`;
CREATE TABLE `sign` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `phoneSign` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `phoneSign` (`phoneSign`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=gbk;

-- ----------------------------
-- Records of sign
-- ----------------------------
INSERT INTO `sign` VALUES ('1', '0');
INSERT INTO `sign` VALUES ('2', '1');
INSERT INTO `sign` VALUES ('3', '2');
INSERT INTO `sign` VALUES ('4', '3');
INSERT INTO `sign` VALUES ('5', '4');
INSERT INTO `sign` VALUES ('6', '5');
INSERT INTO `sign` VALUES ('7', '6');
INSERT INTO `sign` VALUES ('8', '7');
INSERT INTO `sign` VALUES ('9', '8');
INSERT INTO `sign` VALUES ('11', '9');
