/*
 Navicat MySQL Data Transfer

 Source Server         : mysql5.0
 Source Server Type    : MySQL
 Source Server Version : 50022
 Source Host           : localhost:3306
 Source Schema         : ssh

 Target Server Type    : MySQL
 Target Server Version : 50022
 File Encoding         : 65001

 Date: 24/05/2022 20:29:40
*/

SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for tbl_address
-- ----------------------------
DROP TABLE IF EXISTS `tbl_address`;
CREATE TABLE `tbl_address`  (
  `address_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '地址id',
  `user_phone` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT '用户电话',
  `address` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT '详细地址',
  `priority` int(11) NOT NULL DEFAULT 2 COMMENT '优先级',
  PRIMARY KEY USING BTREE (`address_id`),
  INDEX `FK_USER_PHONE_ADDRESS` USING BTREE(`user_phone`),
  CONSTRAINT `FK_USER_PHONE_ADDRESS` FOREIGN KEY (`user_phone`) REFERENCES `tbl_user` (`phone`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '用户的收货地址; InnoDB free: 8192 kB; (`user_phone`) ; InnoDB free: 10240 kB; (`user_ph' ROW_FORMAT = COMPACT;

-- ----------------------------
-- Table structure for tbl_commodity
-- ----------------------------
DROP TABLE IF EXISTS `tbl_commodity`;
CREATE TABLE `tbl_commodity`  (
  `commodity_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '商品id',
  `title` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT '商品标题',
  `image` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL DEFAULT '/images/commoditys/default.png' COMMENT '商品图片',
  `details` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT '商品详情',
  `price` decimal(10, 2) UNSIGNED NOT NULL DEFAULT '' COMMENT '商品价格',
  `num` int(11) NOT NULL DEFAULT '' COMMENT '商品数量',
  `type` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT '商品类别',
  PRIMARY KEY USING BTREE (`commodity_id`)
) ENGINE = InnoDB AUTO_INCREMENT = 23 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '商品' ROW_FORMAT = COMPACT;

-- ----------------------------
-- Table structure for tbl_order
-- ----------------------------
DROP TABLE IF EXISTS `tbl_order`;
CREATE TABLE `tbl_order`  (
  `order_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '订单id',
  `user_phone` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT '用户电话',
  `date` datetime NOT NULL DEFAULT '' COMMENT '下订单时间',
  `status` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT '状态(配送中/已完成/已取消)',
  `address_id` int(11) NOT NULL DEFAULT '' COMMENT '收货地址',
  PRIMARY KEY USING BTREE (`order_id`)
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = COMPACT;

-- ----------------------------
-- Table structure for tbl_order_details
-- ----------------------------
DROP TABLE IF EXISTS `tbl_order_details`;
CREATE TABLE `tbl_order_details`  (
  `details_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '订单商品id',
  `order_id` int(11) NOT NULL DEFAULT '' COMMENT '来源的订单id',
  `commodity_id` int(11) NOT NULL DEFAULT '' COMMENT '商品id',
  `price` decimal(10, 2) NOT NULL DEFAULT '' COMMENT '价格',
  `num` int(11) NOT NULL DEFAULT 1 COMMENT '商品数量',
  PRIMARY KEY USING BTREE (`details_id`),
  INDEX `FK_ORDER_ID` USING BTREE(`order_id`),
  INDEX `FK_COMMODITY_ID` USING BTREE(`commodity_id`),
  CONSTRAINT `FK_COMMODITY_ID` FOREIGN KEY (`commodity_id`) REFERENCES `tbl_commodity` (`commodity_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `FK_ORDER_ID` FOREIGN KEY (`order_id`) REFERENCES `tbl_order` (`order_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '订单内的商品; InnoDB free: 8192 kB; (`commodity_id`) R; InnoDB free: 10240 kB; (`commo' ROW_FORMAT = COMPACT;

-- ----------------------------
-- Table structure for tbl_shoppingcart
-- ----------------------------
DROP TABLE IF EXISTS `tbl_shoppingcart`;
CREATE TABLE `tbl_shoppingcart`  (
  `shoppingcart_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '购物车商品id',
  `user_phone` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT '用户手机号',
  PRIMARY KEY USING BTREE (`shoppingcart_id`),
  INDEX `FK_USER_PHONE` USING BTREE(`user_phone`),
  INDEX `shoppingcart_id` USING BTREE(`shoppingcart_id`),
  CONSTRAINT `FK_USER_PHONE` FOREIGN KEY (`user_phone`) REFERENCES `tbl_user` (`phone`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = 'InnoDB free: 8192 kB; (`user_phone`) REFER `qiuzking/tbl_use' ROW_FORMAT = COMPACT;

-- ----------------------------
-- Table structure for tbl_shoppingcart_details
-- ----------------------------
DROP TABLE IF EXISTS `tbl_shoppingcart_details`;
CREATE TABLE `tbl_shoppingcart_details`  (
  `details_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '购物车商品ID',
  `shoppingcart_id` int(11) NOT NULL DEFAULT '' COMMENT '购物车ID',
  `title` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT '商品名称',
  `price` decimal(10, 2) NOT NULL DEFAULT '' COMMENT '商品价格',
  `num` int(11) NOT NULL DEFAULT '' COMMENT '商品数量',
  `commodity_id` int(11) NOT NULL DEFAULT '' COMMENT '商品ID',
  `image` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT '商品图片',
  PRIMARY KEY USING BTREE (`details_id`),
  INDEX `FK_SHOPPINGCART_COMMODITY_ID` USING BTREE(`commodity_id`),
  INDEX `FK_SHOPPINGCART_ID` USING BTREE(`shoppingcart_id`),
  CONSTRAINT `FK_SHOPPINGCART_COMMODITY_ID` FOREIGN KEY (`commodity_id`) REFERENCES `tbl_commodity` (`commodity_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `FK_SHOPPINGCART_ID` FOREIGN KEY (`shoppingcart_id`) REFERENCES `tbl_shoppingcart` (`shoppingcart_id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = 'InnoDB free: 3072 kB; (`commodity_id`) REFER `qiuzking/tbl_c' ROW_FORMAT = COMPACT;

-- ----------------------------
-- Table structure for tbl_user
-- ----------------------------
DROP TABLE IF EXISTS `tbl_user`;
CREATE TABLE `tbl_user`  (
  `phone` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT '手机号',
  `user_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL DEFAULT '小松鼠' COMMENT '用户名',
  `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT '密码',
  `balance` int(255) UNSIGNED NOT NULL DEFAULT 100 COMMENT '账户余额',
  `user_image` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '/images/avatars/default.png' COMMENT '头像',
  `sex` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '未知' COMMENT '性别',
  `age` int(11) NULL DEFAULT 0 COMMENT '年龄',
  `hobby` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '爱好',
  PRIMARY KEY USING BTREE (`phone`)
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '用户表' ROW_FORMAT = COMPACT;

-- ----------------------------
-- Table structure for tbl_user_commodity
-- ----------------------------
DROP TABLE IF EXISTS `tbl_user_commodity`;
CREATE TABLE `tbl_user_commodity`  (
  `user_phone` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL DEFAULT '',
  `commodity_id` int(11) NOT NULL DEFAULT '',
  PRIMARY KEY USING BTREE (`commodity_id`),
  INDEX `FK_userPhone` USING BTREE(`user_phone`),
  CONSTRAINT `FK_commodityId` FOREIGN KEY (`commodity_id`) REFERENCES `tbl_commodity` (`commodity_id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `FK_userPhone` FOREIGN KEY (`user_phone`) REFERENCES `tbl_user` (`phone`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'InnoDB free: 10240 kB; (`commodity_id`) REFER `ssh/tbl_commodity`(`commodity_id`' ROW_FORMAT = Compact;

SET FOREIGN_KEY_CHECKS = 1;
