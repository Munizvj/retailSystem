INSERT INTO permissions (name) VALUES
('CREATE_SALE'),
('FINALIZE_SALE'),
('CANCEL_SALE');

INSERT INTO position_permission (position_id, permission_id)
SELECT 1, p.id FROM permissions p
WHERE p.name IN ('CREATE_SALE', 'FINALIZE_SALE', 'CANCEL_SALE');

INSERT INTO position_permission (position_id, permission_id)
SELECT 2, p.id FROM permissions p
WHERE p.name IN ('CREATE_SALE', 'FINALIZE_SALE', 'CANCEL_SALE');

INSERT INTO position_permission (position_id, permission_id)
SELECT 3, p.id FROM permissions p
WHERE p.name IN ('CREATE_SALE', 'FINALIZE_SALE')
