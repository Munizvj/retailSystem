INSERT INTO permissions (name) VALUES
('GET_STOCK'),
('UPDATE_STOCK'),
('CREATE_STOCK');

INSERT INTO position_permission (position_id, permission_id)
SELECT 1, p.id FROM permissions p
WHERE p.name IN ('GET_STOCK', 'UPDATE_STOCK', 'CREATE_STOCK');

INSERT INTO position_permission (position_id, permission_id)
SELECT 2, p.id FROM permissions p
WHERE p.name IN ('GET_STOCK', 'UPDATE_STOCK');

INSERT INTO position_permission (position_id, permission_id)
SELECT 3, p.id FROM permissions p
WHERE p.name = 'GET_STOCK';