INSERT INTO account (
    fullname,
    username,
    password,
    email,
    phone,
    address,
    device_id,
    status,
    created_by,
    created_date,
    last_modified_by,
    last_modified_date
) VALUES (
    'Administrator',
    'admin',
    '$2a$12$Pr/jpDWGStfVaIb30bCK2uzo4sN4r3pjL5jb/mrx20SY.aYYxhZaS',
    'admin@example.com',
    '0123456789',
    'Head Office',
    'device-001',
    'ACTIVE',
    'system',
    CURRENT_TIMESTAMP,
    'system',
    CURRENT_TIMESTAMP
);

INSERT INTO account_role (
    account_id,
    role_id,
    created_by,
    created_date,
    last_modified_by,
    last_modified_date
)
SELECT
    a.id AS account_id,
    r.id AS role_id,
    'system' AS created_by,
    CURRENT_TIMESTAMP AS created_date,
    'system' AS last_modified_by,
    CURRENT_TIMESTAMP AS last_modified_date
FROM
    account a,
    role r
WHERE
    a.username = 'admin' AND r.code = 'ROLE_ADMIN';

INSERT INTO role_permission (
    role_id,
    permission_id,
    created_by,
    created_date,
    last_modified_by,
    last_modified_date
)
SELECT
    r.id AS role_id,
    p.id AS permission_id,
    'system' AS created_by,
    CURRENT_TIMESTAMP AS created_date,
    'system' AS last_modified_by,
    CURRENT_TIMESTAMP AS last_modified_date
FROM
    role r
        CROSS JOIN
    permission p
WHERE
    r.code = 'ROLE_ADMIN';
