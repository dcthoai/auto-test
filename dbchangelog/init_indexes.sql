-- Indexes for `account`
CREATE INDEX idx_account_email ON account(email);
CREATE INDEX idx_account_username ON account(username);
CREATE INDEX idx_account_phone ON account(phone);
CREATE INDEX idx_account_status ON account(status);

-- Index for `role`
CREATE INDEX idx_role_code ON role(code);

-- Indexes for `account_role`
CREATE INDEX idx_account_role_account_id ON account_role(account_id);
CREATE INDEX idx_account_role_role_id ON account_role(role_id);

-- Indexes for `permission`
CREATE INDEX idx_permission_code ON permission(code);
CREATE INDEX idx_permission_parent_id ON permission(parent_id);
CREATE INDEX idx_permission_parent_code ON permission(parent_code);

-- Indexes for `role_permission`
CREATE INDEX idx_role_permission_role_id ON role_permission(role_id);
CREATE INDEX idx_role_permission_permission_id ON role_permission(permission_id);

-- Indexes for `system_config`
CREATE INDEX idx_system_config_code ON system_config(code);
CREATE INDEX idx_system_config_enabled ON system_config(enabled);
