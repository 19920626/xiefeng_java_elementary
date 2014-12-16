/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50528
Source Host           : localhost:3306
Source Database       : huodong

Target Server Type    : MYSQL
Target Server Version : 50528
File Encoding         : 65001

Date: 2014-12-16 22:43:42
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `phonecount`
-- ----------------------------
DROP TABLE IF EXISTS `phonecount`;
CREATE TABLE `phonecount` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `count` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=gbk;

-- ----------------------------
-- phonecount表中只有两个元素id=1的是顺序插入的次数，id=2的是插入的数据的总个数（详细说明见下面的sign表说明）
-- ----------------------------

-- ----------------------------
-- Records of phonecount
-- ----------------------------
INSERT INTO `phonecount` VALUES ('1', '16');
INSERT INTO `phonecount` VALUES ('2', '18');

-- ----------------------------
-- Table structure for `promotion`
-- ----------------------------
DROP TABLE IF EXISTS `promotion`;
CREATE TABLE `promotion` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `phone` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `phone` (`phone`)
) ENGINE=InnoDB AUTO_INCREMENT=99 DEFAULT CHARSET=gbk;

-- ----------------------------
-- Records of promotion
-- ----------------------------
INSERT INTO `promotion` VALUES ('74', '15698524785');
INSERT INTO `promotion` VALUES ('91', '26321452153');
INSERT INTO `promotion` VALUES ('85', '32569845687');
INSERT INTO `promotion` VALUES ('88', '52364785214');
INSERT INTO `promotion` VALUES ('95', '52365478521');
INSERT INTO `promotion` VALUES ('90', '52456985621');
INSERT INTO `promotion` VALUES ('87', '53621458796');
INSERT INTO `promotion` VALUES ('96', '54785236547');
INSERT INTO `promotion` VALUES ('86', '54896578941');
INSERT INTO `promotion` VALUES ('81', '55555555555');
INSERT INTO `promotion` VALUES ('94', '56985214568');
INSERT INTO `promotion` VALUES ('98', '56985632147');
INSERT INTO `promotion` VALUES ('75', '58965478521');
INSERT INTO `promotion` VALUES ('83', '68521698547');
INSERT INTO `promotion` VALUES ('78', '75698536541');
INSERT INTO `promotion` VALUES ('89', '78621425655');
INSERT INTO `promotion` VALUES ('82', '79632156478');
INSERT INTO `promotion` VALUES ('97', '95658745962');

-- ----------------------------
-- Table structure for `sign`
-- ----------------------------
DROP TABLE IF EXISTS `sign`;
CREATE TABLE `sign` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `phoneSign` bigint(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `phoneSign` (`phoneSign`)
) ENGINE=InnoDB AUTO_INCREMENT=143 DEFAULT CHARSET=gbk;

-- ----------------------------
-- sign有标志的意思，表示的是号码的存储标志
--phoneSign是里面起主要作用的字段，每个值都是一个标志，这个值是该号码插入前，promotion的表中的数据量count（顺序插入的次数）
--这个值也可能是随机产生的在count～本级活动的阈值。（并发时顺序插入每次只能插入一个，所以为同时发出请求的未获得该次标志的客户随机分配后面的未分配的标志）
-- ----------------------------

-- ----------------------------
-- Records of sign
-- ----------------------------
INSERT INTO `sign` VALUES ('119', '0');
INSERT INTO `sign` VALUES ('120', '1');
INSERT INTO `sign` VALUES ('121', '2');
INSERT INTO `sign` VALUES ('122', '3');
INSERT INTO `sign` VALUES ('123', '4');
INSERT INTO `sign` VALUES ('124', '5');
INSERT INTO `sign` VALUES ('125', '6');
INSERT INTO `sign` VALUES ('126', '7');
INSERT INTO `sign` VALUES ('127', '8');
INSERT INTO `sign` VALUES ('128', '9');
INSERT INTO `sign` VALUES ('129', '10');
INSERT INTO `sign` VALUES ('132', '11');
INSERT INTO `sign` VALUES ('133', '12');
INSERT INTO `sign` VALUES ('134', '13');
INSERT INTO `sign` VALUES ('137', '14');
INSERT INTO `sign` VALUES ('138', '15');
INSERT INTO `sign` VALUES ('141', '91');
INSERT INTO `sign` VALUES ('142', '2434');
