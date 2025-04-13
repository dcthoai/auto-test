PRAGMA foreign_keys = ON;

DROP TABLE IF EXISTS role_permission;
DROP TABLE IF EXISTS permission;
DROP TABLE IF EXISTS account_role;
DROP TABLE IF EXISTS role;
DROP TABLE IF EXISTS account;
DROP TABLE IF EXISTS system_config;

CREATE TABLE account (
    id INTEGER PRIMARY KEY,
    fullname TEXT,
    username TEXT NOT NULL UNIQUE,
    password TEXT NOT NULL,
    email TEXT NOT NULL UNIQUE,
    phone TEXT,
    address TEXT,
    device_id TEXT,
    status TEXT NOT NULL DEFAULT 'ACTIVE',
    created_by TEXT NOT NULL DEFAULT 'SYSTEM',
    created_date TEXT DEFAULT CURRENT_TIMESTAMP,
    last_modified_by TEXT DEFAULT 'SYSTEM',
    last_modified_date TEXT DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE role (
    id INTEGER PRIMARY KEY,
    name TEXT NOT NULL,
    code TEXT NOT NULL,
    created_by TEXT NOT NULL DEFAULT 'SYSTEM',
    created_date TEXT DEFAULT CURRENT_TIMESTAMP,
    last_modified_by TEXT DEFAULT 'SYSTEM',
    last_modified_date TEXT DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE account_role (
    id INTEGER PRIMARY KEY,
    account_id INTEGER NOT NULL,
    role_id INTEGER NOT NULL,
    created_by TEXT NOT NULL DEFAULT 'SYSTEM',
    created_date TEXT DEFAULT CURRENT_TIMESTAMP,
    last_modified_by TEXT DEFAULT 'SYSTEM',
    last_modified_date TEXT DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (account_id) REFERENCES account(id) ON DELETE CASCADE,
    FOREIGN KEY (role_id) REFERENCES role(id) ON DELETE CASCADE
);

CREATE TABLE permission (
    id INTEGER PRIMARY KEY,
    name TEXT NOT NULL,
    code TEXT NOT NULL UNIQUE,
    description TEXT,
    parent_id INTEGER,
    parent_code TEXT,
    created_by TEXT NOT NULL DEFAULT 'SYSTEM',
    created_date TEXT DEFAULT CURRENT_TIMESTAMP,
    last_modified_by TEXT DEFAULT 'SYSTEM',
    last_modified_date TEXT DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (parent_id) REFERENCES permission(id) ON DELETE CASCADE
);

CREATE TABLE role_permission (
    id INTEGER PRIMARY KEY,
    role_id INTEGER NOT NULL,
    permission_id INTEGER NOT NULL,
    created_by TEXT NOT NULL DEFAULT 'SYSTEM',
    created_date TEXT DEFAULT CURRENT_TIMESTAMP,
    last_modified_by TEXT DEFAULT 'SYSTEM',
    last_modified_date TEXT DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (role_id) REFERENCES role(id) ON DELETE CASCADE,
    FOREIGN KEY (permission_id) REFERENCES permission(id) ON DELETE CASCADE
);

CREATE TABLE system_config (
    id INTEGER PRIMARY KEY,
    code TEXT NOT NULL UNIQUE,
    content TEXT,
    description TEXT,
    enabled INTEGER NOT NULL DEFAULT 1,
    created_by TEXT NOT NULL DEFAULT 'SYSTEM',
    created_date TEXT DEFAULT CURRENT_TIMESTAMP,
    last_modified_by TEXT DEFAULT 'SYSTEM',
    last_modified_date TEXT DEFAULT CURRENT_TIMESTAMP
);
