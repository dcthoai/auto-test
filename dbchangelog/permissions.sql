PRAGMA foreign_keys = ON;

-- === Manage System ===
INSERT INTO permission (id, name, code, description, parent_id, parent_code, created_by)
VALUES (1, 'permission.system', '01', 'permission.system.description', NULL, NULL, 'admin');

INSERT INTO permission (id, name, code, description, parent_id, parent_code, created_by) VALUES
(2, 'permission.system.view', '0101', 'permission.system.view.description', 1, '01', 'admin'),
(3, 'permission.system.update', '0102', 'permission.system.update.description', 1, '01', 'admin');

-- === Manage Accounts ===
INSERT INTO permission (id, name, code, description, parent_id, parent_code, created_by)
VALUES (4, 'permission.account', '02', 'permission.account.description', NULL, NULL, 'admin');

INSERT INTO permission (id, name, code, description, parent_id, parent_code, created_by) VALUES
(5, 'permission.account.view', '0201', 'permission.account.view.description', 4, '02', 'admin'),
(6, 'permission.account.create', '0202', 'permission.account.create.description', 4, '02', 'admin'),
(7, 'permission.account.update', '0203', 'permission.account.update.description', 4, '02', 'admin'),
(8, 'permission.account.delete', '0204', 'permission.account.delete.description', 4, '02', 'admin');

-- === Manage Roles ===
INSERT INTO permission (id, name, code, description, parent_id, parent_code, created_by)
VALUES (9, 'permission.role', '03', 'permission.role.description', NULL, NULL, 'admin');

INSERT INTO permission (id, name, code, description, parent_id, parent_code, created_by) VALUES
(10, 'permission.role.view', '0301', 'permission.role.view.description', 9, '03', 'admin'),
(11, 'permission.role.create', '0302', 'permission.role.create.description', 9, '03', 'admin'),
(12, 'permission.role.update', '0303', 'permission.role.update.description', 9, '03', 'admin'),
(13, 'permission.role.delete', '0304', 'permission.role.delete.description', 9, '03', 'admin');

INSERT INTO role (
    id,
    name,
    code,
    created_by,
    created_date,
    last_modified_by,
    last_modified_date
) VALUES (
    1,
    'Administrator',
    'ROLE_ADMIN',
    'system',
    CURRENT_TIMESTAMP,
    'system',
    CURRENT_TIMESTAMP
);
