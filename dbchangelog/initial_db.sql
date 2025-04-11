CREATE DATABASE IF NOT EXISTS `auto_tests`;
-- DEFAULT CHARACTER SET utf8mb4
-- COLLATE utf8mb4_0900_ai_ci
-- DEFAULT ENCRYPTION='N'
USE `auto_tests`;

-- MySQL dump 10.13  Distrib 8.0.36, for Win64 (x86_64)
-- Database: auto_tests
-- Server version 8.0.37

SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE IF EXISTS `role_permission`;
DROP TABLE IF EXISTS `permission`;
DROP TABLE IF EXISTS `account_role`;
DROP TABLE IF EXISTS `role`;
DROP TABLE IF EXISTS `account`;
DROP TABLE IF EXISTS `system_config`;

SET FOREIGN_KEY_CHECKS = 1;


-- Table structure for table `account`
DROP TABLE IF EXISTS `account`;
CREATE TABLE `account` (
    `id` INT NOT NULL AUTO_INCREMENT,
    `fullname` VARCHAR(255),
    `username` VARCHAR(45) NOT NULL,
    `password` VARCHAR(255) NOT NULL,
    `email` VARCHAR(100) NOT NULL,
    `phone` VARCHAR(15) DEFAULT NULL,
    `address` VARCHAR(512) DEFAULT NULL,
    `device_id` VARCHAR(45) DEFAULT NULL,
    `status` ENUM('ACTIVE', 'INACTIVE', 'LOCKED', 'DELETED') NOT NULL DEFAULT 'ACTIVE',
    `created_by` VARCHAR(45) NOT NULL DEFAULT 'SYSTEM',
    `created_date` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `last_modified_by` VARCHAR(45) DEFAULT 'SYSTEM',
    `last_modified_date` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`),
    UNIQUE KEY `username_UNIQUE` (`username`),
    UNIQUE KEY `email_UNIQUE` (`email`),
    KEY `phone_INDEX` (`phone`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Table structure for table `role`
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
    `id` INT NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(45) NOT NULL,
    `code` VARCHAR(45) NOT NULL,
    `created_by` VARCHAR(45) NOT NULL DEFAULT 'SYSTEM',
    `created_date` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `last_modified_by` VARCHAR(45) DEFAULT 'SYSTEM',
    `last_modified_date` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Table structure for table `account_role`
DROP TABLE IF EXISTS `account_role`;
CREATE TABLE `account_role` (
    `id` INT NOT NULL AUTO_INCREMENT,
    `account_id` INT NOT NULL,
    `role_id` INT NOT NULL,
    `created_by` VARCHAR(45) NOT NULL DEFAULT 'SYSTEM',
    `created_date` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `last_modified_by` VARCHAR(45) DEFAULT 'SYSTEM',
    `last_modified_date` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`),
    FOREIGN KEY (`account_id`) REFERENCES `account`(`id`) ON DELETE CASCADE,
    FOREIGN KEY (`role_id`) REFERENCES `role`(`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Table structure for table `permission`
DROP TABLE IF EXISTS `permission`;
CREATE TABLE `permission` (
    `id` INT NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(45) NOT NULL,
    `code` VARCHAR(45) NOT NULL,
    `description` VARCHAR(255) DEFAULT NULL,
    `parent_id` INT DEFAULT NULL,
    `parent_code` VARCHAR(45) DEFAULT NULL,
    `created_by` VARCHAR(45) NOT NULL DEFAULT 'SYSTEM',
    `created_date` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `last_modified_by` VARCHAR(45) DEFAULT 'SYSTEM',
    `last_modified_date` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`),
    UNIQUE KEY `code_UNIQUE` (`code`),
    FOREIGN KEY (`parent_id`) REFERENCES `permission`(`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Table structure for table `role_permission`
DROP TABLE IF EXISTS `role_permission`;
CREATE TABLE `role_permission` (
    `id` INT NOT NULL AUTO_INCREMENT,
    `role_id` INT NOT NULL,
    `permission_id` INT NOT NULL,
    `created_by` VARCHAR(45) NOT NULL DEFAULT 'SYSTEM',
    `created_date` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `last_modified_by` VARCHAR(45) DEFAULT 'SYSTEM',
    `last_modified_date` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`),
    FOREIGN KEY (`role_id`) REFERENCES `role`(`id`) ON DELETE CASCADE,
    FOREIGN KEY (`permission_id`) REFERENCES `permission`(`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Table structure for table `system_config`
DROP TABLE IF EXISTS `system_config`;
CREATE TABLE `system_config` (
    `id` INT NOT NULL AUTO_INCREMENT,
    `code` VARCHAR(45) NOT NULL,
    `content` TEXT,
    `description` VARCHAR(255) DEFAULT NULL,
    `enabled` TINYINT(1) NOT NULL DEFAULT 1,
    `created_by` VARCHAR(45) NOT NULL DEFAULT 'SYSTEM',
    `created_date` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `last_modified_by` VARCHAR(45) DEFAULT 'SYSTEM',
    `last_modified_date` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`),
    UNIQUE KEY `code_UNIQUE` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
