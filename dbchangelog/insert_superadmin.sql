USE `auto_tests`;

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
