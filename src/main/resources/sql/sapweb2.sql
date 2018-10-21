/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 50720
 Source Host           : localhost
 Source Database       : sapweb

 Target Server Type    : MySQL
 Target Server Version : 50720
 File Encoding         : utf-8

 Date: 09/27/2018 14:46:30 PM
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `QRTZ_BLOB_TRIGGERS`
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_BLOB_TRIGGERS`;
CREATE TABLE `QRTZ_BLOB_TRIGGERS` (
  `SCHED_NAME` varchar(120) COLLATE utf8_bin NOT NULL,
  `TRIGGER_NAME` varchar(200) COLLATE utf8_bin NOT NULL,
  `TRIGGER_GROUP` varchar(200) COLLATE utf8_bin NOT NULL,
  `BLOB_DATA` blob,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  CONSTRAINT `qrtz_blob_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `QRTZ_TRIGGERS` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='Trigger作为Blob类型存储';

-- ----------------------------
--  Table structure for `QRTZ_CALENDARS`
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_CALENDARS`;
CREATE TABLE `QRTZ_CALENDARS` (
  `SCHED_NAME` varchar(120) COLLATE utf8_bin NOT NULL,
  `CALENDAR_NAME` varchar(200) COLLATE utf8_bin NOT NULL,
  `CALENDAR` blob NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`CALENDAR_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='存储Quartz的Calendar信息';

-- ----------------------------
--  Table structure for `QRTZ_CRON_TRIGGERS`
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_CRON_TRIGGERS`;
CREATE TABLE `QRTZ_CRON_TRIGGERS` (
  `SCHED_NAME` varchar(120) COLLATE utf8_bin NOT NULL,
  `TRIGGER_NAME` varchar(200) COLLATE utf8_bin NOT NULL,
  `TRIGGER_GROUP` varchar(200) COLLATE utf8_bin NOT NULL,
  `CRON_EXPRESSION` varchar(200) COLLATE utf8_bin NOT NULL,
  `TIME_ZONE_ID` varchar(80) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  CONSTRAINT `qrtz_cron_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `QRTZ_TRIGGERS` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='存储CronTrigger，包括Cron表达式和时区信息';

-- ----------------------------
--  Records of `QRTZ_CRON_TRIGGERS`
-- ----------------------------
BEGIN;
INSERT INTO `QRTZ_CRON_TRIGGERS` VALUES ('schedulerFactoryBean', 'a_Job', 'job', '*/15 * * * * ?', 'Asia/Shanghai');
COMMIT;

-- ----------------------------
--  Table structure for `QRTZ_FIRED_TRIGGERS`
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_FIRED_TRIGGERS`;
CREATE TABLE `QRTZ_FIRED_TRIGGERS` (
  `SCHED_NAME` varchar(120) COLLATE utf8_bin NOT NULL,
  `ENTRY_ID` varchar(95) COLLATE utf8_bin NOT NULL,
  `TRIGGER_NAME` varchar(200) COLLATE utf8_bin NOT NULL,
  `TRIGGER_GROUP` varchar(200) COLLATE utf8_bin NOT NULL,
  `INSTANCE_NAME` varchar(200) COLLATE utf8_bin NOT NULL,
  `FIRED_TIME` bigint(13) NOT NULL,
  `SCHED_TIME` bigint(13) NOT NULL,
  `PRIORITY` int(11) NOT NULL,
  `STATE` varchar(16) COLLATE utf8_bin NOT NULL,
  `JOB_NAME` varchar(200) COLLATE utf8_bin DEFAULT NULL,
  `JOB_GROUP` varchar(200) COLLATE utf8_bin DEFAULT NULL,
  `IS_NONCONCURRENT` varchar(1) COLLATE utf8_bin DEFAULT NULL,
  `REQUESTS_RECOVERY` varchar(1) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`,`ENTRY_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='存储与已触发的Trigger相关的状态信息，以及相联Job的执行信息';

-- ----------------------------
--  Table structure for `QRTZ_JOB_DETAILS`
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_JOB_DETAILS`;
CREATE TABLE `QRTZ_JOB_DETAILS` (
  `SCHED_NAME` varchar(120) COLLATE utf8_bin NOT NULL,
  `JOB_NAME` varchar(200) COLLATE utf8_bin NOT NULL,
  `JOB_GROUP` varchar(200) COLLATE utf8_bin NOT NULL,
  `DESCRIPTION` varchar(250) COLLATE utf8_bin DEFAULT NULL,
  `JOB_CLASS_NAME` varchar(250) COLLATE utf8_bin NOT NULL,
  `IS_DURABLE` varchar(1) COLLATE utf8_bin NOT NULL,
  `IS_NONCONCURRENT` varchar(1) COLLATE utf8_bin NOT NULL,
  `IS_UPDATE_DATA` varchar(1) COLLATE utf8_bin NOT NULL,
  `REQUESTS_RECOVERY` varchar(1) COLLATE utf8_bin NOT NULL,
  `JOB_DATA` blob,
  PRIMARY KEY (`SCHED_NAME`,`JOB_NAME`,`JOB_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='存储每一个已配置的Job的详细信息';

-- ----------------------------
--  Records of `QRTZ_JOB_DETAILS`
-- ----------------------------
BEGIN;
INSERT INTO `QRTZ_JOB_DETAILS` VALUES ('schedulerFactoryBean', 'a_Job', 'job', '测试 a Job', 'com.ivo.sapweb.quartz.job.TestJob', '0', '0', '0', '0', 0xaced0005737200156f72672e71756172747a2e4a6f62446174614d61709fb083e8bfa9b0cb020000787200266f72672e71756172747a2e7574696c732e537472696e674b65794469727479466c61674d61708208e8c3fbc55d280200015a0013616c6c6f77735472616e7369656e74446174617872001d6f72672e71756172747a2e7574696c732e4469727479466c61674d617013e62ead28760ace0200025a000564697274794c00036d617074000f4c6a6176612f7574696c2f4d61703b787000737200116a6176612e7574696c2e486173684d61700507dac1c31660d103000246000a6c6f6164466163746f724900097468726573686f6c6478703f40000000000010770800000010000000007800);
COMMIT;

-- ----------------------------
--  Table structure for `QRTZ_LOCKS`
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_LOCKS`;
CREATE TABLE `QRTZ_LOCKS` (
  `SCHED_NAME` varchar(120) COLLATE utf8_bin NOT NULL,
  `LOCK_NAME` varchar(40) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`LOCK_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='存储程序的悲观锁的信息';

-- ----------------------------
--  Records of `QRTZ_LOCKS`
-- ----------------------------
BEGIN;
INSERT INTO `QRTZ_LOCKS` VALUES ('schedulerFactoryBean', 'STATE_ACCESS'), ('schedulerFactoryBean', 'TRIGGER_ACCESS');
COMMIT;

-- ----------------------------
--  Table structure for `QRTZ_PAUSED_TRIGGER_GRPS`
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_PAUSED_TRIGGER_GRPS`;
CREATE TABLE `QRTZ_PAUSED_TRIGGER_GRPS` (
  `SCHED_NAME` varchar(120) COLLATE utf8_bin NOT NULL,
  `TRIGGER_GROUP` varchar(200) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='存储已暂停的Trigger组的信息';

-- ----------------------------
--  Table structure for `QRTZ_SCHEDULER_STATE`
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_SCHEDULER_STATE`;
CREATE TABLE `QRTZ_SCHEDULER_STATE` (
  `SCHED_NAME` varchar(120) COLLATE utf8_bin NOT NULL,
  `INSTANCE_NAME` varchar(200) COLLATE utf8_bin NOT NULL,
  `LAST_CHECKIN_TIME` bigint(13) NOT NULL,
  `CHECKIN_INTERVAL` bigint(13) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`INSTANCE_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='存储少量的有关Scheduler的状态信息，和别的Scheduler实例';

-- ----------------------------
--  Records of `QRTZ_SCHEDULER_STATE`
-- ----------------------------
BEGIN;
INSERT INTO `QRTZ_SCHEDULER_STATE` VALUES ('MyScheduler', 'wangjiandeMacBook-Pro.local1536538404511', '1536538456962', '2000'), ('SchedulerFactory', 'wangjiandeMacBook-Pro.local1536538086073', '1536538395502', '2000'), ('schedulerFactoryBean', 'wangjiandeMacBook-Pro.local1538027621755', '1538030789808', '2000');
COMMIT;

-- ----------------------------
--  Table structure for `QRTZ_SIMPLE_TRIGGERS`
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_SIMPLE_TRIGGERS`;
CREATE TABLE `QRTZ_SIMPLE_TRIGGERS` (
  `SCHED_NAME` varchar(120) COLLATE utf8_bin NOT NULL,
  `TRIGGER_NAME` varchar(200) COLLATE utf8_bin NOT NULL,
  `TRIGGER_GROUP` varchar(200) COLLATE utf8_bin NOT NULL,
  `REPEAT_COUNT` bigint(7) NOT NULL,
  `REPEAT_INTERVAL` bigint(12) NOT NULL,
  `TIMES_TRIGGERED` bigint(10) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  CONSTRAINT `qrtz_simple_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `QRTZ_TRIGGERS` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='存储简单的Trigger，包括重复次数、间隔、以及已触的次数';

-- ----------------------------
--  Table structure for `QRTZ_SIMPROP_TRIGGERS`
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_SIMPROP_TRIGGERS`;
CREATE TABLE `QRTZ_SIMPROP_TRIGGERS` (
  `SCHED_NAME` varchar(120) COLLATE utf8_bin NOT NULL,
  `TRIGGER_NAME` varchar(200) COLLATE utf8_bin NOT NULL,
  `TRIGGER_GROUP` varchar(200) COLLATE utf8_bin NOT NULL,
  `STR_PROP_1` varchar(512) COLLATE utf8_bin DEFAULT NULL,
  `STR_PROP_2` varchar(512) COLLATE utf8_bin DEFAULT NULL,
  `STR_PROP_3` varchar(512) COLLATE utf8_bin DEFAULT NULL,
  `INT_PROP_1` int(11) DEFAULT NULL,
  `INT_PROP_2` int(11) DEFAULT NULL,
  `LONG_PROP_1` bigint(20) DEFAULT NULL,
  `LONG_PROP_2` bigint(20) DEFAULT NULL,
  `DEC_PROP_1` decimal(13,4) DEFAULT NULL,
  `DEC_PROP_2` decimal(13,4) DEFAULT NULL,
  `BOOL_PROP_1` varchar(1) COLLATE utf8_bin DEFAULT NULL,
  `BOOL_PROP_2` varchar(1) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  CONSTRAINT `qrtz_simprop_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `QRTZ_TRIGGERS` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
--  Table structure for `QRTZ_TRIGGERS`
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_TRIGGERS`;
CREATE TABLE `QRTZ_TRIGGERS` (
  `SCHED_NAME` varchar(120) COLLATE utf8_bin NOT NULL,
  `TRIGGER_NAME` varchar(200) COLLATE utf8_bin NOT NULL,
  `TRIGGER_GROUP` varchar(200) COLLATE utf8_bin NOT NULL,
  `JOB_NAME` varchar(200) COLLATE utf8_bin NOT NULL,
  `JOB_GROUP` varchar(200) COLLATE utf8_bin NOT NULL,
  `DESCRIPTION` varchar(250) COLLATE utf8_bin DEFAULT NULL,
  `NEXT_FIRE_TIME` bigint(13) DEFAULT NULL,
  `PREV_FIRE_TIME` bigint(13) DEFAULT NULL,
  `PRIORITY` int(11) DEFAULT NULL,
  `TRIGGER_STATE` varchar(16) COLLATE utf8_bin NOT NULL,
  `TRIGGER_TYPE` varchar(8) COLLATE utf8_bin NOT NULL,
  `START_TIME` bigint(13) NOT NULL,
  `END_TIME` bigint(13) DEFAULT NULL,
  `CALENDAR_NAME` varchar(200) COLLATE utf8_bin DEFAULT NULL,
  `MISFIRE_INSTR` smallint(2) DEFAULT NULL,
  `JOB_DATA` blob,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  KEY `SCHED_NAME` (`SCHED_NAME`,`JOB_NAME`,`JOB_GROUP`),
  CONSTRAINT `qrtz_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`) REFERENCES `QRTZ_JOB_DETAILS` (`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='存储已配置的Trigger的信息';

-- ----------------------------
--  Records of `QRTZ_TRIGGERS`
-- ----------------------------
BEGIN;
INSERT INTO `QRTZ_TRIGGERS` VALUES ('schedulerFactoryBean', 'a_Job', 'job', 'a_Job', 'job', null, '1537415700000', '1537415685000', '5', 'PAUSED', 'CRON', '1536845258000', '0', null, '0', '');
COMMIT;

-- ----------------------------
--  Table structure for `sap_bapi`
-- ----------------------------
DROP TABLE IF EXISTS `sap_bapi`;
CREATE TABLE `sap_bapi` (
  `bapi_id` int(11) NOT NULL AUTO_INCREMENT,
  `description` varchar(20) DEFAULT NULL,
  `state` int(11) DEFAULT NULL,
  `group` varchar(10) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `creator` varchar(10) DEFAULT NULL,
  `updater` varchar(10) DEFAULT NULL,
  `is_Valid` int(11) DEFAULT NULL,
  `bapi_Name` varchar(40) DEFAULT NULL,
  `count` int(11) DEFAULT NULL,
  PRIMARY KEY (`bapi_id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 CHECKSUM=1 DELAY_KEY_WRITE=1 COMMENT='BAPI表';

-- ----------------------------
--  Records of `sap_bapi`
-- ----------------------------
BEGIN;
INSERT INTO `sap_bapi` VALUES ('1', 'testtest', '1', 'MM', '2018-09-10 15:23:16', '2018-09-11 14:24:49', 'admin', 'admin', '1', 'ZSD_EIF_VBSN', '7'), ('2', 'test', '0', 'PP', '2018-09-11 13:58:14', '2018-09-11 16:10:29', 'admin', 'admin', '0', 'z', '0'), ('3', '22222', '1', 'MM', '2018-09-11 16:10:53', '2018-09-11 16:10:53', 'admin', 'admin', '0', 't', '0'), ('4', 'PR', '1', 'MM', '2018-09-25 16:46:51', '2018-09-25 16:46:51', 'admin', 'admin', '1', 'ZMM_MSEG_ETL', '2'), ('5', 'testtest22', '1', 'MM', '2018-09-10 15:23:16', '2018-09-11 14:24:49', 'admin', 'admin', '0', 'ZSD_EIF_VBSN', '1'), ('6', '生成SAPCODE', '1', 'PR', '2018-09-27 08:39:07', '2018-09-27 08:39:07', 'admin', 'admin', '1', 'ZMMITF003', '0'), ('7', '生成SAPCODE', '1', 'PR', '2018-09-27 08:39:50', '2018-09-27 08:39:50', 'admin', 'admin', '1', 'ZMMITF002_C', '0'), ('8', '获取料号对应的标准价格', '1', 'PR', '2018-09-27 08:40:42', '2018-09-27 08:40:42', 'admin', 'admin', '1', 'ZMM_MTRL_PRICE', '0'), ('9', 'PR Release', '1', 'PR', '2018-09-27 08:41:46', '2018-09-27 08:41:46', 'admin', 'admin', '1', 'ZMM_PR_FULLRELEASE', '0'), ('10', '生成SAPCODE', '1', 'PO', '2018-09-27 08:45:05', '2018-09-27 08:45:05', 'admin', 'admin', '1', 'ZBAPI_PO_CREATE1', '0'), ('11', 'PO Release', '1', 'PO', '2018-09-27 08:46:22', '2018-09-27 08:46:22', 'admin', 'admin', '1', 'ZMM_PO_RELEASE', '0'), ('12', 'PO 删除/结案', '1', 'PA', '2018-09-27 08:47:40', '2018-09-27 08:47:40', 'admin', 'admin', '1', 'ZBAPI_PO_CHANGE', '0'), ('13', 'PR 删除/结案', '1', 'PA', '2018-09-27 08:49:45', '2018-09-27 08:49:45', 'admin', 'admin', '1', 'ZBAPI_REQUISITION_DELETE', '0'), ('14', 'PO UnRelease', '1', 'PA', '2018-09-27 08:50:53', '2018-09-27 08:50:53', 'admin', 'admin', '1', 'ZMM_PO_UNRELEASE', '0'), ('15', 'PR UnRelease', '1', 'PA', '2018-09-27 08:52:01', '2018-09-27 08:52:01', 'admin', 'admin', '1', 'ZMM_PR_FULLUNRELEASE', '0');
COMMIT;

-- ----------------------------
--  Table structure for `sap_bapi_record`
-- ----------------------------
DROP TABLE IF EXISTS `sap_bapi_record`;
CREATE TABLE `sap_bapi_record` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `bapi_Id` int(11) DEFAULT NULL,
  `is_Success` int(11) DEFAULT NULL,
  `ip` varchar(20) DEFAULT NULL,
  `os` varchar(20) DEFAULT NULL,
  `device` varchar(20) DEFAULT NULL,
  `browser` varchar(20) DEFAULT NULL,
  `time_Consuming` varchar(20) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `creator` varchar(10) DEFAULT NULL,
  `updater` varchar(10) DEFAULT NULL,
  `is_Valid` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='Bapi使用记录';

-- ----------------------------
--  Table structure for `sys_authorities`
-- ----------------------------
DROP TABLE IF EXISTS `sys_authorities`;
CREATE TABLE `sys_authorities` (
  `authority_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '权限id',
  `authority_name` varchar(20) NOT NULL COMMENT '权限名称',
  `authority` varchar(40) DEFAULT NULL COMMENT '授权标识',
  `menu_url` varchar(40) DEFAULT NULL COMMENT '菜单url',
  `parent_id` int(11) NOT NULL DEFAULT '-1' COMMENT '父id',
  `is_menu` int(1) NOT NULL DEFAULT '0' COMMENT '0菜单，1按钮',
  `order_number` int(3) NOT NULL DEFAULT '0' COMMENT '排序号',
  `menu_icon` varchar(20) DEFAULT NULL COMMENT '菜单图标',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `creator` varchar(10) DEFAULT NULL,
  `updater` varchar(10) DEFAULT NULL,
  `is_Valid` int(11) DEFAULT NULL,
  PRIMARY KEY (`authority_id`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8mb4 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC COMMENT='权限表';

-- ----------------------------
--  Records of `sys_authorities`
-- ----------------------------
BEGIN;
INSERT INTO `sys_authorities` VALUES ('1', '系统管理', null, null, '-1', '0', '1', 'layui-icon-set', '2018-06-29 11:05:41', '2018-07-13 09:13:42', null, null, null), ('2', '用户管理', null, 'system/user', '1', '0', '2', null, '2018-06-29 11:05:41', '2018-07-13 09:13:42', null, null, null), ('3', '查询用户', 'user:view', '', '2', '1', '3', '', '2018-07-21 13:54:16', '2018-07-21 13:54:16', null, null, null), ('4', '添加用户', 'user:add', null, '2', '1', '4', null, '2018-06-29 11:05:41', '2018-07-13 09:13:42', null, null, null), ('5', '修改用户', 'user:edit', null, '2', '1', '5', null, '2018-06-29 11:05:41', '2018-07-13 09:13:42', null, null, null), ('6', '删除用户', 'user:delete', null, '2', '1', '6', null, '2018-06-29 11:05:41', '2018-07-13 09:13:42', null, null, null), ('7', '角色管理', null, 'system/role', '1', '0', '7', null, '2018-06-29 11:05:41', '2018-07-13 09:13:42', null, null, null), ('8', '查询角色', 'role:view', '', '7', '1', '8', '', '2018-07-21 13:54:59', '2018-07-21 13:54:58', null, null, null), ('9', '添加角色', 'role:add', '', '7', '1', '9', '', '2018-06-29 11:05:41', '2018-07-13 09:13:42', null, null, null), ('10', '修改角色', 'role:edit', '', '7', '1', '10', '', '2018-06-29 11:05:41', '2018-07-13 09:13:42', null, null, null), ('11', '删除角色', 'role:delete', '', '7', '1', '11', '', '2018-06-29 11:05:41', '2018-07-13 09:13:42', null, null, null), ('12', '角色权限管理', 'role:auth', '', '7', '1', '12', '', '2018-06-29 11:05:41', '2018-07-13 15:27:18', null, null, null), ('13', '权限管理', null, 'system/authorities', '1', '0', '13', null, '2018-06-29 11:05:41', '2018-07-13 15:45:13', null, null, null), ('14', '查询权限', 'authorities:view', '', '13', '1', '14', '', '2018-07-21 13:55:57', '2018-07-21 13:55:56', null, null, null), ('15', '添加权限', 'authorities:add', '', '13', '1', '15', '', '2018-06-29 11:05:41', '2018-06-29 11:05:41', null, null, null), ('16', '修改权限', 'authorities:edit', '', '13', '1', '16', '', '2018-07-13 09:13:42', '2018-07-13 09:13:42', null, null, null), ('17', '删除权限', 'authorities:delete', '', '13', '1', '17', '', '2018-06-29 11:05:41', '2018-06-29 11:05:41', null, null, null), ('18', '登录日志', null, 'system/loginRecord', '1', '0', '18', null, '2018-06-29 11:05:41', '2018-06-29 11:05:41', null, null, null), ('19', '查询登录日志', 'loginRecord:view', '', '18', '1', '19', '', '2018-07-21 13:56:43', '2018-07-21 13:56:43', null, null, null);
COMMIT;

-- ----------------------------
--  Table structure for `sys_dictionary`
-- ----------------------------
DROP TABLE IF EXISTS `sys_dictionary`;
CREATE TABLE `sys_dictionary` (
  `dict_code` int(11) NOT NULL AUTO_INCREMENT COMMENT '字典主键',
  `dict_name` varchar(50) NOT NULL COMMENT '字典名称',
  `description` varchar(100) DEFAULT NULL COMMENT '描述',
  PRIMARY KEY (`dict_code`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COMMENT='字典';

-- ----------------------------
--  Records of `sys_dictionary`
-- ----------------------------
BEGIN;
INSERT INTO `sys_dictionary` VALUES ('1', '性别', null);
COMMIT;

-- ----------------------------
--  Table structure for `sys_dictionarydata`
-- ----------------------------
DROP TABLE IF EXISTS `sys_dictionarydata`;
CREATE TABLE `sys_dictionarydata` (
  `dictdata_code` int(11) NOT NULL AUTO_INCREMENT COMMENT '字典项主键',
  `dict_code` int(11) NOT NULL COMMENT '字典主键',
  `dictdata_name` varchar(40) NOT NULL COMMENT '字典项值',
  `is_delete` int(1) NOT NULL DEFAULT '0' COMMENT '是否删除',
  `sort_number` int(1) NOT NULL COMMENT '排序',
  `description` varchar(100) DEFAULT NULL COMMENT '描述',
  PRIMARY KEY (`dictdata_code`),
  KEY `FK_Reference_36` (`dict_code`),
  CONSTRAINT `sys_dictionarydata_ibfk_1` FOREIGN KEY (`dict_code`) REFERENCES `sys_dictionary` (`dict_code`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC COMMENT='字典项';

-- ----------------------------
--  Records of `sys_dictionarydata`
-- ----------------------------
BEGIN;
INSERT INTO `sys_dictionarydata` VALUES ('1', '1', '男', '0', '0', null), ('2', '1', '女', '0', '1', null);
COMMIT;

-- ----------------------------
--  Table structure for `sys_login_record`
-- ----------------------------
DROP TABLE IF EXISTS `sys_login_record`;
CREATE TABLE `sys_login_record` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` int(11) NOT NULL COMMENT '用户id',
  `os_name` varchar(40) DEFAULT NULL COMMENT '操作系统',
  `device` varchar(40) DEFAULT NULL COMMENT '设备名',
  `browser_type` varchar(40) DEFAULT NULL COMMENT '浏览器类型',
  `ip_address` varchar(40) DEFAULT NULL COMMENT 'ip地址',
  `create_time` datetime NOT NULL COMMENT '登录时间',
  `update_time` datetime DEFAULT NULL,
  `creator` varchar(10) DEFAULT NULL,
  `updater` varchar(10) DEFAULT NULL,
  `is_Valid` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_sys_login_record_user` (`user_id`),
  CONSTRAINT `sys_login_record_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `sys_user` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC COMMENT='登录历史记录';

-- ----------------------------
--  Table structure for `sys_person`
-- ----------------------------
DROP TABLE IF EXISTS `sys_person`;
CREATE TABLE `sys_person` (
  `person_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '人员id',
  `true_name` varchar(20) DEFAULT NULL COMMENT '真实姓名',
  `department_id` varchar(8) DEFAULT NULL COMMENT '部门id',
  `position_id` varchar(8) DEFAULT NULL COMMENT '职位id',
  `birthday` date DEFAULT NULL COMMENT '出生日期',
  `id_card` varchar(20) DEFAULT NULL COMMENT '身份证号',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`person_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC COMMENT='人员表';

-- ----------------------------
--  Table structure for `sys_role`
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `role_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '角色id',
  `role_name` varchar(20) NOT NULL COMMENT '角色名称',
  `comments` varchar(100) DEFAULT NULL COMMENT '备注',
  `is_delete` int(1) NOT NULL DEFAULT '0' COMMENT '是否删除，0否，1是',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `creator` varchar(10) DEFAULT NULL,
  `updater` varchar(10) DEFAULT NULL,
  `is_Valid` int(11) DEFAULT NULL,
  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC COMMENT='角色表';

-- ----------------------------
--  Records of `sys_role`
-- ----------------------------
BEGIN;
INSERT INTO `sys_role` VALUES ('1', '超级管理员', '超级管理员', '0', '2018-07-21 09:58:31', '2018-07-21 11:07:16', null, null, null), ('2', '管理员', '系统管理员', '0', '2018-07-21 09:58:35', '2018-07-21 11:07:18', null, null, null), ('3', '普通用户', '系统普通用户', '0', '2018-07-21 09:58:39', '2018-09-27 09:50:00', null, null, '1');
COMMIT;

-- ----------------------------
--  Table structure for `sys_role_authorities`
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_authorities`;
CREATE TABLE `sys_role_authorities` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `role_id` int(11) NOT NULL COMMENT '角色id',
  `authority_id` int(11) NOT NULL COMMENT '权限id',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL,
  `creator` varchar(10) DEFAULT NULL,
  `updater` varchar(10) DEFAULT NULL,
  `is_Valid` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_sys_role_permission_pm` (`authority_id`),
  KEY `FK_sys_role_permission_role` (`role_id`),
  CONSTRAINT `sys_role_authorities_ibfk_2` FOREIGN KEY (`role_id`) REFERENCES `sys_role` (`role_id`),
  CONSTRAINT `sys_role_authorities_ibfk_3` FOREIGN KEY (`authority_id`) REFERENCES `sys_authorities` (`authority_id`)
) ENGINE=InnoDB AUTO_INCREMENT=65 DEFAULT CHARSET=utf8mb4 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC COMMENT='角色权限关联表';

-- ----------------------------
--  Records of `sys_role_authorities`
-- ----------------------------
BEGIN;
INSERT INTO `sys_role_authorities` VALUES ('1', '1', '1', '2018-07-18 15:23:09', null, null, null, null), ('2', '1', '2', '2018-07-18 15:23:08', null, null, null, null), ('3', '1', '3', '2018-07-18 15:23:04', null, null, null, null), ('4', '1', '4', '2018-07-18 15:23:06', null, null, null, null), ('5', '1', '5', '2018-07-18 15:22:59', null, null, null, null), ('6', '1', '6', '2018-07-18 15:23:06', null, null, null, null), ('7', '1', '7', '2018-07-18 15:23:08', null, null, null, null), ('8', '1', '8', '2018-07-18 15:23:10', null, null, null, null), ('9', '1', '9', '2018-07-18 15:23:03', null, null, null, null), ('10', '1', '10', '2018-07-18 15:23:05', null, null, null, null), ('11', '1', '11', '2018-07-18 15:23:06', null, null, null, null), ('12', '1', '12', '2018-07-18 15:23:05', null, null, null, null), ('13', '1', '13', '2018-07-18 15:22:57', null, null, null, null), ('14', '1', '14', '2018-07-18 15:22:59', null, null, null, null), ('15', '1', '15', '2018-07-18 15:23:04', null, null, null, null), ('16', '1', '16', '2018-06-28 11:09:04', null, null, null, null), ('17', '1', '17', '2018-07-17 16:00:20', null, null, null, null), ('18', '1', '18', '2018-06-27 10:00:19', null, null, null, null), ('19', '1', '19', '2018-07-17 14:12:16', null, null, null, null), ('49', '2', '1', '2018-09-03 15:52:26', null, null, null, null), ('50', '2', '2', '2018-09-03 15:52:26', null, null, null, null), ('51', '2', '3', '2018-09-03 15:52:26', null, null, null, null), ('52', '2', '7', '2018-09-03 15:52:26', null, null, null, null), ('53', '2', '8', '2018-09-03 15:52:26', null, null, null, null), ('54', '2', '9', '2018-09-03 15:52:26', null, null, null, null), ('55', '2', '10', '2018-09-03 15:52:26', null, null, null, null), ('56', '2', '11', '2018-09-03 15:52:26', null, null, null, null), ('57', '2', '12', '2018-09-03 15:52:26', null, null, null, null), ('58', '2', '13', '2018-09-03 15:52:26', null, null, null, null), ('59', '2', '14', '2018-09-03 15:52:26', null, null, null, null), ('60', '2', '15', '2018-09-03 15:52:26', null, null, null, null), ('61', '2', '16', '2018-09-03 15:52:26', null, null, null, null), ('62', '2', '17', '2018-09-03 15:52:26', null, null, null, null), ('63', '2', '18', '2018-09-03 15:52:26', null, null, null, null), ('64', '2', '19', '2018-09-03 15:52:26', null, null, null, null);
COMMIT;

-- ----------------------------
--  Table structure for `sys_user`
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户id',
  `username` varchar(20) NOT NULL COMMENT '账号',
  `password` varchar(128) NOT NULL COMMENT '密码',
  `nick_name` varchar(20) NOT NULL COMMENT '昵称',
  `avatar` varchar(200) DEFAULT NULL COMMENT '头像',
  `sex` varchar(1) NOT NULL DEFAULT '男' COMMENT '性别',
  `phone` varchar(12) DEFAULT NULL COMMENT '手机号',
  `email` varchar(100) DEFAULT NULL COMMENT '邮箱',
  `email_verified` int(11) DEFAULT NULL COMMENT '邮箱是否验证，0未验证，1已验证',
  `person_id` varchar(8) DEFAULT NULL COMMENT '人员id,关联person表',
  `person_type` int(11) DEFAULT NULL COMMENT '人员类型,比如:0学生,1教师',
  `state` int(1) NOT NULL DEFAULT '0' COMMENT '状态，0正常，1冻结',
  `create_time` datetime NOT NULL COMMENT '注册时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `creator` varchar(10) DEFAULT NULL,
  `updater` varchar(10) DEFAULT NULL,
  `is_Valid` int(11) DEFAULT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `user_account` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC COMMENT='用户表';

-- ----------------------------
--  Records of `sys_user`
-- ----------------------------
BEGIN;
INSERT INTO `sys_user` VALUES ('1', 'superadmin', 'bd6b5b654b42b8e3aa59f7be5c739cd1', '超级管理员', null, '男', '12345678901', null, null, null, null, '0', '2018-07-21 10:03:40', '2018-09-27 09:46:35', null, null, '1'), ('2', 'admin', '3fed7a346e430ea4c2aa10250928f4de', '管理员', null, '女', '12345678901', null, null, null, null, '0', '2018-07-21 10:50:18', '2018-09-27 09:46:55', null, null, '1'), ('3', 'demo', '180aca749787e2993b13e186d65a32f0', '测试账号', null, '女', '12345678902', null, null, null, null, '0', '2018-07-21 10:50:27', '2018-09-03 15:53:42', null, null, '1'), ('4', 'demo1', '7d8b316047b198a0a3e1513ee75c86d6', 'admin', null, '男', '12345678901', null, null, null, null, '0', '2018-07-21 10:56:07', '2018-09-03 10:58:54', null, null, '1');
COMMIT;

-- ----------------------------
--  Table structure for `sys_user_role`
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` int(11) NOT NULL COMMENT '用户id',
  `role_id` int(11) NOT NULL COMMENT '角色id',
  `create_time` datetime NOT NULL,
  `update_time` datetime DEFAULT NULL,
  `creator` varchar(10) DEFAULT NULL,
  `updater` varchar(10) DEFAULT NULL,
  `is_Valid` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_sys_user_role` (`user_id`),
  KEY `FK_sys_user_role_role` (`role_id`),
  CONSTRAINT `sys_user_role_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `sys_user` (`user_id`),
  CONSTRAINT `sys_user_role_ibfk_2` FOREIGN KEY (`role_id`) REFERENCES `sys_role` (`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC COMMENT='用户角色关联表';

-- ----------------------------
--  Records of `sys_user_role`
-- ----------------------------
BEGIN;
INSERT INTO `sys_user_role` VALUES ('1', '1', '1', '2018-07-18 15:25:50', null, null, null, null), ('5', '4', '3', '2018-07-21 10:56:15', null, null, null, null), ('9', '3', '3', '2018-09-03 15:53:42', null, null, null, null), ('12', '2', '2', '2018-09-27 09:46:55', null, null, null, null);
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
