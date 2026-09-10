INSERT INTO permissions (name)
VALUES ('GET_SALE');

INSERT INTO position_permission (position_id, permission_id)
SELECT 1, p.id
FROM permissions p
WHERE p.name = 'GET_SALE';

INSERT INTO position_permission (position_id, permission_id)
SELECT 2, p.id
FROM permissions p
WHERE p.name = 'GET_SALE';

INSERT INTO position_permission (position_id, permission_id)
SELECT 3, p.id
FROM permissions p
WHERE p.name = 'GET_SALE';