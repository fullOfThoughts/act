/*
Navicat MySQL Data Transfer

Source Server         : my
Source Server Version : 80022
Source Host           : localhost:3306
Source Database       : task

Target Server Type    : MYSQL
Target Server Version : 80022
File Encoding         : 65001

Date: 2021-06-18 17:28:50
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for act_his
-- ----------------------------
DROP TABLE IF EXISTS `act_his`;
CREATE TABLE `act_his` (
  `task_id` bigint NOT NULL COMMENT '任务id',
  `ins_id` bigint NOT NULL COMMENT '实例id',
  `assignee_user_ids` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '受理者id列表，以逗号 , 分割',
  `assignee_user_role` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '受理者角色',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_user_id` bigint NOT NULL COMMENT '创建人',
  `psc_id` int NOT NULL COMMENT '对应的流程id',
  `is_back_here` tinyint(1) unsigned zerofill NOT NULL DEFAULT '0' COMMENT '是否是回退到次任务',
  `complete_user_id` bigint DEFAULT NULL COMMENT '完成者id',
  `complete_time` datetime DEFAULT NULL COMMENT '完成时间',
  `is_deliver` tinyint NOT NULL COMMENT '是否是工作转交',
  PRIMARY KEY (`task_id`,`ins_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for act_ins
-- ----------------------------
DROP TABLE IF EXISTS `act_ins`;
CREATE TABLE `act_ins` (
  `ins_id` bigint NOT NULL COMMENT '实例id',
  `ins_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '实例名称',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_user_id` bigint NOT NULL COMMENT '创建者id',
  `is_freeze` tinyint(1) unsigned zerofill NOT NULL DEFAULT '0' COMMENT '实例状态，0：false，1：true',
  `is_completed` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否完成，0：false，1：true',
  `ins_description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '实例描述',
  PRIMARY KEY (`ins_id`),
  KEY `i1` (`is_freeze`,`is_completed`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Table structure for act_pcs
-- ----------------------------
DROP TABLE IF EXISTS `act_pcs`;
CREATE TABLE `act_pcs` (
  `pcs_id` int NOT NULL COMMENT '流程id，',
  `pcs_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '流程名称',
  `pcs_stage` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '流程对应的阶段',
  `pcs_next` int NOT NULL COMMENT '下一流程的id，9527为结束id',
  `pcs_back_id` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '当前流程可以回退到的流程id，以"，"分割，-1表示不可回退',
  PRIMARY KEY (`pcs_id`),
  KEY `i1` (`pcs_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Table structure for act_task
-- ----------------------------
DROP TABLE IF EXISTS `act_task`;
CREATE TABLE `act_task` (
  `task_id` bigint NOT NULL COMMENT '任务id',
  `ins_id` bigint NOT NULL COMMENT '实例id',
  `assignee_user_ids` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '受理者id列表，以逗号 , 分割',
  `assignee_user_role` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '受理者角色',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_user_id` bigint NOT NULL COMMENT '创建人',
  `psc_id` int NOT NULL COMMENT '对应的流程id',
  `is_back_here` tinyint(1) unsigned zerofill NOT NULL DEFAULT '0' COMMENT '是否是回退到次任务',
  `complete_user_id` bigint DEFAULT NULL COMMENT '完成者id',
  `complete_time` datetime DEFAULT NULL COMMENT '完成时间',
  `is_deliver` tinyint(1) NOT NULL DEFAULT '1' COMMENT '是否是工作转交',
  PRIMARY KEY (`task_id`,`ins_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
