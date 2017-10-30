-- --------------------------------------------------------
-- 主机:                           127.0.0.1
-- 服务器版本:                        5.7.20-log - MySQL Community Server (GPL)
-- 服务器操作系统:                      Win64
-- HeidiSQL 版本:                  9.4.0.5125
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


-- 导出 trade 的数据库结构
CREATE DATABASE IF NOT EXISTS `trade` /*!40100 DEFAULT CHARACTER SET utf8 COLLATE utf8_bin */;
USE `trade`;

-- 数据导出被取消选择。
-- 导出  表 trade.trade_coupon 结构
CREATE TABLE IF NOT EXISTS `trade_coupon` (
  `coupon_id` varchar(32) COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT '优惠券ID',
  `coupon_price` decimal(10,0) DEFAULT NULL COMMENT '优惠券金额',
  `user_id` int(11) DEFAULT NULL COMMENT '用户ID',
  `order_id` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '订单ID',
  `is_used` char(1) COLLATE utf8_bin DEFAULT '0' COMMENT '是否已使用 0-未使用 1-已使用',
  `used_time` datetime DEFAULT NULL COMMENT '使用时间',
  PRIMARY KEY (`coupon_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='优惠表';

-- 数据导出被取消选择。
-- 导出  表 trade.trade_goods 结构
CREATE TABLE IF NOT EXISTS `trade_goods` (
  `goods_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '商品ID',
  `goods_name` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '商品名称',
  `goods_number` int(11) DEFAULT NULL COMMENT '商品库存',
  `goods_price` decimal(10,0) DEFAULT NULL COMMENT '商品价格',
  `goods_desc` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '商品描述',
  `add_time` datetime DEFAULT NULL COMMENT '添加时间',
  PRIMARY KEY (`goods_id`)
) ENGINE=InnoDB AUTO_INCREMENT=100002 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='商品表';

-- 数据导出被取消选择。
-- 导出  表 trade.trade_goods_number_log 结构
CREATE TABLE IF NOT EXISTS `trade_goods_number_log` (
  `goods_id` int(11) NOT NULL DEFAULT '0' COMMENT '商品ID',
  `order_id` varchar(32) COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT '订单ID',
  `goods_number` int(11) DEFAULT NULL COMMENT '库存数量',
  `log_time` datetime DEFAULT NULL COMMENT '日志时间',
  PRIMARY KEY (`goods_id`,`order_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='商品数量日志';

-- 数据导出被取消选择。
-- 导出  表 trade.trade_mq_consume_log 结构
CREATE TABLE IF NOT EXISTS `trade_mq_consume_log` (
  `msg_id` varchar(255) COLLATE utf8_bin NOT NULL COMMENT '消息ID',
  `group_name` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '消费组名',
  `msg_tags` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '消息Tag',
  `msg_keys` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '业务ID',
  `msg_body` varchar(1024) COLLATE utf8_bin DEFAULT NULL COMMENT '消息内容',
  `consumer_status` varchar(1) COLLATE utf8_bin DEFAULT NULL COMMENT '消息状态。0-正在处理 1-处理成功 2-处理失败',
  `consumer_times` int(11) DEFAULT '0' COMMENT '消费次数',
  `remark` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '备注（错误原因）',
  PRIMARY KEY (`msg_id`),
  UNIQUE KEY `trade_mq_consumer_log_msg_id_pk` (`msg_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- 数据导出被取消选择。
-- 导出  表 trade.trade_mq_produce_temp 结构
CREATE TABLE IF NOT EXISTS `trade_mq_produce_temp` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `group_name` varchar(255) COLLATE utf8_bin NOT NULL COMMENT '生产者组名',
  `topic` varchar(255) COLLATE utf8_bin NOT NULL COMMENT '消息topic',
  `msg_tag` varchar(255) COLLATE utf8_bin NOT NULL COMMENT '消息tag',
  `msg_keys` varchar(255) COLLATE utf8_bin NOT NULL COMMENT '消息keys',
  `msg_body` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '消息内容',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=94 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- 数据导出被取消选择。
-- 导出  表 trade.trade_order 结构
CREATE TABLE IF NOT EXISTS `trade_order` (
  `order_id` varchar(32) COLLATE utf8_bin NOT NULL COMMENT '订单ID',
  `user_id` int(11) DEFAULT NULL COMMENT '用户ID',
  `order_status` char(1) COLLATE utf8_bin DEFAULT NULL COMMENT '订单状态 0-未确认 1-已确认 2-已取消 3-取消 4-退货',
  `pay_status` char(1) COLLATE utf8_bin DEFAULT NULL COMMENT '支付状态 0-未付款 1-支付中 2-已付款',
  `shipping_status` char(1) COLLATE utf8_bin DEFAULT NULL COMMENT '发货状态 0-未发货 1-已发货 2-已收货',
  `address` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '收货地址',
  `consignee` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '收货人',
  `goods_id` int(11) DEFAULT NULL COMMENT '商品ID',
  `goods_number` int(11) DEFAULT NULL COMMENT '商品数量',
  `goods_price` decimal(10,0) DEFAULT NULL COMMENT '商品价格',
  `goods_amount` decimal(10,0) DEFAULT NULL COMMENT '商品总价',
  `shipping_fee` decimal(10,0) DEFAULT NULL COMMENT '运费',
  `order_amount` decimal(10,0) DEFAULT NULL COMMENT '订单价格',
  `coupon_id` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '优惠券ID',
  `coupon_paid` decimal(10,0) DEFAULT NULL COMMENT '优惠券价格',
  `money_paid` decimal(10,0) DEFAULT NULL COMMENT '已付金额',
  `pay_amount` varchar(32) COLLATE utf8_bin DEFAULT NULL COMMENT '支付金额',
  `add_time` datetime DEFAULT NULL COMMENT '创建时间',
  `confirm_time` datetime DEFAULT NULL COMMENT '订单确认时间',
  `pay_time` datetime DEFAULT NULL COMMENT '支付时间',
  PRIMARY KEY (`order_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='订单表';

-- 数据导出被取消选择。
-- 导出  表 trade.trade_pay 结构
CREATE TABLE IF NOT EXISTS `trade_pay` (
  `pay_id` varchar(255) COLLATE utf8_bin NOT NULL,
  `order_id` varchar(32) COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT '订单编号',
  `pay_amount` decimal(10,0) DEFAULT NULL COMMENT '支付金额',
  `is_paid` char(1) COLLATE utf8_bin DEFAULT NULL COMMENT '是否已支付 0-否 1-是',
  PRIMARY KEY (`pay_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='支付表';

-- 数据导出被取消选择。
-- 导出  表 trade.trade_usr 结构
CREATE TABLE IF NOT EXISTS `trade_usr` (
  `usr_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `usr_name` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '用户姓名',
  `usr_password` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '用户密码',
  `usr_mobile` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '用户手机号',
  `usr_score` int(11) DEFAULT NULL COMMENT '积分',
  `usr_reg_time` datetime DEFAULT NULL COMMENT '积分',
  `usr_money` decimal(10,0) DEFAULT NULL COMMENT '用户余额',
  PRIMARY KEY (`usr_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='用户表';

-- 数据导出被取消选择。
-- 导出  表 trade.trade_usr_money_log 结构
CREATE TABLE IF NOT EXISTS `trade_usr_money_log` (
  `usr_id` int(11) NOT NULL DEFAULT '0' COMMENT '用户ID',
  `order_id` varchar(32) COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT '订单ID',
  `money_log_type` char(1) COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT '日志类型 1-订单付款 2-订单退款',
  `usr_money` decimal(10,2) DEFAULT NULL COMMENT '金额',
  `create_time` datetime DEFAULT NULL COMMENT '发生时间',
  PRIMARY KEY (`usr_id`,`order_id`,`money_log_type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='用户发生金额日志表';

-- 数据导出被取消选择。
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
