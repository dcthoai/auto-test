-- Permission Tree Structure in the System
--      Manage System: view, update
--      Manage Accounts: view, create, update, delete
--      Manage Roles: view, create, update, delete

USE `auto_tests`;

-- Reset AUTO_INCREMENT to 1
ALTER TABLE `permission` AUTO_INCREMENT = 1;


-- Manage System
INSERT INTO `permission` (`name`, `code`, `description`, `parent_id`, `parent_code`, `created_by`)
VALUES ('permission.system', '01', 'permission.system.description', NULL, NULL, 'admin');

SET @system_id = LAST_INSERT_ID();

INSERT INTO `permission` (`name`, `code`, `description`, `parent_id`, `parent_code`, `created_by`) VALUES
('permission.system.view', '0101', 'permission.system.view.description', @system_id, '01', 'admin'),
('permission.system.update', '0102', 'permission.system.update.description', @system_id, '01', 'admin');

-- Manage Accounts
INSERT INTO `permission` (`name`, `code`, `description`, `parent_id`, `parent_code`, `created_by`)
VALUES ('permission.account', '02', 'permission.account.description', NULL, NULL, 'admin');

SET @account_id = LAST_INSERT_ID();

INSERT INTO `permission` (`name`, `code`, `description`, `parent_id`, `parent_code`, `created_by`) VALUES
('permission.account.view', '0201', 'permission.account.view.description', @account_id, '02', 'admin'),
('permission.account.create', '0202', 'permission.account.create.description', @account_id, '02', 'admin'),
('permission.account.update', '0203', 'permission.account.update.description', @account_id, '02', 'admin'),
('permission.account.delete', '0204', 'permission.account.delete.description', @account_id, '02', 'admin');

-- Manage Roles
INSERT INTO `permission` (`name`, `code`, `description`, `parent_id`, `parent_code`, `created_by`)
VALUES ('permission.role', '03', 'permission.role.description', NULL, NULL, 'admin');

SET @role_id = LAST_INSERT_ID();

INSERT INTO `permission` (`name`, `code`, `description`, `parent_id`, `parent_code`, `created_by`) VALUES
('permission.role.view', '0301', 'permission.role.view.description', @role_id, '03', 'admin'),
('permission.role.create', '0302', 'permission.role.create.description', @role_id, '03', 'admin'),
('permission.role.update', '0303', 'permission.role.update.description', @role_id, '03', 'admin'),
('permission.role.delete', '0304', 'permission.role.delete.description', @role_id, '03', 'admin');
