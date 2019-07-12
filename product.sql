/*
 Navicat Premium Data Transfer

 Source Server         : 39.108.103.128
 Source Server Type    : MySQL
 Source Server Version : 50557
 Source Host           : 39.108.103.128
 Source Database       : product

 Target Server Type    : MySQL
 Target Server Version : 50557
 File Encoding         : utf-8

 Date: 02/14/2019 00:50:23 AM
*/

SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `app_message`
-- ----------------------------
DROP TABLE IF EXISTS `app_message`;
CREATE TABLE `app_message` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `company` varchar(100) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `copyright` varchar(100) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `remark` varchar(100) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '备注',
  `createTime` bigint(11) DEFAULT NULL,
  `updateTime` bigint(11) DEFAULT NULL,
  `version` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `ip_message`
-- ----------------------------
DROP TABLE IF EXISTS `ip_message`;
CREATE TABLE `ip_message` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `ip` varchar(100) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `count` int(11) DEFAULT NULL COMMENT '领用次数',
  `remark` varchar(200) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `createTime` bigint(20) DEFAULT NULL,
  `updateTime` bigint(20) DEFAULT NULL,
  `status` bit(1) DEFAULT NULL COMMENT '0 不可用，1可用',
  PRIMARY KEY (`id`),
  UNIQUE KEY `INDEX_IP` (`ip`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `ip_message`
-- ----------------------------
BEGIN;
INSERT INTO `ip_message` VALUES ('1', '192.168.1.117', '32', '这是啥', '1547568978', '1547572304', b'0'), ('2', '192.168.0.183', '0', '本机', '1547568978', '1547651780', b'1'), ('3', '192.168.1.123', '7', '1213', '1547570132', '1547570132', b'1'), ('4', '192.31.34.12', '23', '撒手', '1547570293', '1547570293', b'1'), ('5', '192.31.22.14', '20', '动动我', '1547570335', '1547570335', b'1'), ('6', '142.55.65.81', '13', '阿威锋网', '1547570358', '1547570358', b'1'), ('7', '128.2.12.31', '0', 'afwewfae', '1547651495', '1547651495', b'1'), ('8', '121.23.123.43', '0', '4342313', '1547651967', '1547651967', b'1'), ('9', '11', '2', '11', '1548080418', '1548080433', b'1');
COMMIT;

-- ----------------------------
--  Table structure for `ip_record`
-- ----------------------------
DROP TABLE IF EXISTS `ip_record`;
CREATE TABLE `ip_record` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `deviceId` varchar(100) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL COMMENT 'app 设备 id',
  `ipId` int(11) DEFAULT NULL COMMENT '分配的 ip',
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '备注',
  `address` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '地址信息',
  `createTime` bigint(20) DEFAULT NULL COMMENT '创建时间',
  `updateTime` bigint(20) DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `INDEX_IP` (`deviceId`,`ipId`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `ip_record`
-- ----------------------------
BEGIN;
INSERT INTO `ip_record` VALUES ('1', '123', '1', 'mfoied', 'nfseof', '1547572304', '1547572304'), ('2', '21ojawejdoawe', '3', 'mfoiedasa', 'mffwa', '1547648680', '1547648680'), ('3', '12.12.12.12', '2', 'afefawe', 'wefwaefwe', '1547734114', '1547734114'), ('4', '213123', '2', '', '', '1548518585', '1548518585'), ('5', '357043050247450', '2', '', '', '1549990614', '1549990614');
COMMIT;

-- ----------------------------
--  Table structure for `manage_resource`
-- ----------------------------
DROP TABLE IF EXISTS `manage_resource`;
CREATE TABLE `manage_resource` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `resName` varchar(100) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `resKey` varchar(100) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `parentId` int(11) DEFAULT NULL,
  `resType` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `resUrl` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `useable` bit(1) DEFAULT NULL,
  `sort` int(11) DEFAULT NULL,
  `createTime` bigint(11) DEFAULT NULL,
  `updateTime` bigint(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `manage_resource`
-- ----------------------------
BEGIN;
INSERT INTO `manage_resource` VALUES ('1', '系统管理', '系统管理', '0', '1', 'cog', b'1', '1', '1549202022', '1549202022'), ('2', '菜单管理', '菜单管理', '1', '2', 'sitemap', b'1', '2', '1549202129', '1549202129'), ('3', '角色管理', '角色管理', '1', '2', 'user-cog', b'1', '3', '1549202148', '1549202148'), ('7', '管理员管理', '管理员管理', '1', '2', 'user', b'1', '1', '1550069536', '1550069536');
COMMIT;

-- ----------------------------
--  Table structure for `manage_role`
-- ----------------------------
DROP TABLE IF EXISTS `manage_role`;
CREATE TABLE `manage_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `roleName` varchar(100) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `roleKey` varchar(100) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `roleStatus` varchar(100) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `manage_role`
-- ----------------------------
BEGIN;
INSERT INTO `manage_role` VALUES ('1', '管理员', 'admin', '1');
COMMIT;

-- ----------------------------
--  Table structure for `manage_role_relation`
-- ----------------------------
DROP TABLE IF EXISTS `manage_role_relation`;
CREATE TABLE `manage_role_relation` (
  `sysUserId` int(11) NOT NULL,
  `sysRoleId` int(11) NOT NULL,
  PRIMARY KEY (`sysUserId`,`sysRoleId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `manage_role_relation`
-- ----------------------------
BEGIN;
INSERT INTO `manage_role_relation` VALUES ('1', '1');
COMMIT;

-- ----------------------------
--  Table structure for `manage_user`
-- ----------------------------
DROP TABLE IF EXISTS `manage_user`;
CREATE TABLE `manage_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `account` varchar(100) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `pass` varchar(100) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `realName` varchar(100) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `salt` varchar(100) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `locked` bit(1) DEFAULT NULL,
  `createTime` bigint(11) DEFAULT NULL,
  `updateTime` bigint(11) DEFAULT NULL,
  `mobile` varchar(30) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `manage_user`
-- ----------------------------
BEGIN;
INSERT INTO `manage_user` VALUES ('1', 'admin', 'eb5ff191981ef315fa4b954f8c999928', 'admin', 'pA5vvhcZ3WkGjdl', b'0', '1548920474', '1549199097', '15862113245');
COMMIT;

-- ----------------------------
--  Table structure for `product`
-- ----------------------------
DROP TABLE IF EXISTS `product`;
CREATE TABLE `product` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `ssid` varchar(100) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '唯一标示',
  `userId` int(11) DEFAULT NULL COMMENT '所属用户',
  `detail` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '说明',
  `remark` varchar(200) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '备注',
  `typeId` int(11) DEFAULT NULL COMMENT '产品类型 id',
  `createTime` bigint(11) DEFAULT NULL,
  `updateTime` bigint(11) DEFAULT NULL,
  `useable` bit(1) DEFAULT NULL COMMENT '是否可用，1可用，0不可用',
  PRIMARY KEY (`id`),
  KEY `INDEX_USER_ID` (`userId`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `product`
-- ----------------------------
BEGIN;
INSERT INTO `product` VALUES ('1', 'qwert123456', '4', '这个设备不错', '第一个测试数据', '1', '1549467360', '1549467533', b'1'), ('2', 'lkjhg12345', '4', '222222', '22222jjjjjjj', '1', '1549467360', '1549467533', b'1'), ('3', 'poiuy0987', '5', '333333', '3333331qqq', '1', '1549467360', '1549467533', b'1');
COMMIT;

-- ----------------------------
--  Table structure for `productMessage`
-- ----------------------------
DROP TABLE IF EXISTS `productMessage`;
CREATE TABLE `productMessage` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `hasImage` bit(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `product_record`
-- ----------------------------
DROP TABLE IF EXISTS `product_record`;
CREATE TABLE `product_record` (
  `id` int(11) NOT NULL,
  `productId` int(11) DEFAULT NULL,
  `imageUrl` varchar(200) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `resource_role_relation`
-- ----------------------------
DROP TABLE IF EXISTS `resource_role_relation`;
CREATE TABLE `resource_role_relation` (
  `sysResourceId` int(11) NOT NULL,
  `sysRoleId` int(11) NOT NULL,
  PRIMARY KEY (`sysResourceId`,`sysRoleId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `resource_role_relation`
-- ----------------------------
BEGIN;
INSERT INTO `resource_role_relation` VALUES ('1', '1'), ('2', '1'), ('3', '1'), ('7', '1');
COMMIT;

-- ----------------------------
--  Table structure for `sys_dictionary`
-- ----------------------------
DROP TABLE IF EXISTS `sys_dictionary`;
CREATE TABLE `sys_dictionary` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `dicName` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `dicKey` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `dicValue` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `dicExplain` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `sort` int(11) DEFAULT NULL,
  `useable` bit(1) DEFAULT NULL,
  `createTime` int(11) DEFAULT NULL,
  `updateTime` int(11) DEFAULT NULL,
  `parentId` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `type_detail`
-- ----------------------------
DROP TABLE IF EXISTS `type_detail`;
CREATE TABLE `type_detail` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '类型名称',
  `explain` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '介绍',
  `createTime` bigint(20) DEFAULT NULL COMMENT '创建时间',
  `updateTime` bigint(20) DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `type_detail`
-- ----------------------------
BEGIN;
INSERT INTO `type_detail` VALUES ('1', '变下', '改下 ', '1549468309', '1549468529');
COMMIT;

-- ----------------------------
--  Table structure for `user`
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(100) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '用户名，默认手机号',
  `password` varchar(100) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '密码',
  `salt` varchar(100) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '盐',
  `createTime` bigint(11) DEFAULT NULL COMMENT '创建时间',
  `updateTime` bigint(11) DEFAULT NULL COMMENT '更新时间',
  `mobile` varchar(30) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '手机号，登录账号',
  `countryCode` varchar(20) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '国家代码',
  `deviceId` varchar(100) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '设备 id',
  `from` varchar(50) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '终端',
  `status` bit(1) DEFAULT NULL COMMENT '0可用，1不可用',
  PRIMARY KEY (`id`),
  UNIQUE KEY `INDEX_MOBILE` (`mobile`,`countryCode`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `user`
-- ----------------------------
BEGIN;
INSERT INTO `user` VALUES ('1', '18795980531', 'd580526c8174a0014d3ebe0f9ecd6e4c', 'muXICSdcHdd9sb6', '1549515463', '1549515463', '18795980531', '86', null, null, null), ('2', '18795980533', 'd580526c8174a0014d3ebe0f9ecd6e4c', 'muXICSdcHdd9sb6', '1549516550', '1549516550', '18795980533', '86', null, null, null), ('3', '18795980534', '991502530fdd5877ec987274807cfeb9', 'gokFa5H5ARhyI9Q', '1549516649', '1549516649', '18795980534', '86', null, null, null), ('4', '18795980532', 'c141c2556be786e4f532a88c1f8e678e', 'Lzeh3R9VAkpLIEy', '1549516719', '1549516719', '18795980532', '86', null, null, b'1');
COMMIT;

-- ----------------------------
--  Table structure for `version_record`
-- ----------------------------
DROP TABLE IF EXISTS `version_record`;
CREATE TABLE `version_record` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `version` varchar(50) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '版本',
  `remark` varchar(200) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '备注',
  `url` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '下载路径',
  `createTime` bigint(20) DEFAULT NULL COMMENT '创建时间',
  `type` tinyint(1) DEFAULT NULL COMMENT '终端类型：android 1,ios 2',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `version_record`
-- ----------------------------
BEGIN;
INSERT INTO `version_record` VALUES ('1', '1.0.0', 'fsfer', 'http://dwada', '1547572304', '1'), ('2', '1.0.1', 'ffwefa', 'http://dwada', '1547572304', '2'), ('3', '1.0.2', 'fwefwae', 'http://dwada', '1547572304', '1'), ('4', '1.0.2', 'grtdgr', 'http://dwada', '1547572304', '2'), ('5', '1.0.3', 'pkrtgpitm', 'http://www.360doc.com/content/13/0904/09/13084517_312092916.shtml', '1547653214', '2'), ('6', '1.0.4', 'efdfsefse', 'http://adwadw', '1547697830', '2');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
