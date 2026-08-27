CREATE TABLE permissions(
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE positions(
    id BIGSERIAL PRIMARY KEY,
    role VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE position_permission (
    position_id BIGINT NOT NULL REFERENCES positions(id) ON DELETE CASCADE,
    permission_id BIGINT NOT NULL REFERENCES permissions(id) ON DELETE CASCADE,
    CONSTRAINT pk_positions_permissions PRIMARY KEY (position_id, permission_id)
);

CREATE TABLE users_permissions (
    user_id BIGINT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    permission_id BIGINT NOT NULL REFERENCES permissions(id) ON DELETE CASCADE,
    CONSTRAINT pk_users_permissions PRIMARY KEY (user_id, permission_id)
);

ALTER TABLE users ADD COLUMN position_id BIGINT REFERENCES positions(id);

INSERT INTO permissions (id, name) VALUES
(1, 'GET_USER'),
(2, 'REGISTER_USER'),
(3, 'UPDATE_USER'),
(4, 'DELETE_USER'),
(5, 'GET_PRODUCTS'),
(6, 'REGISTER_PRODUCT'),
(7, 'UPDATE_PRODUCT'),
(8, 'DELETE_PRODUCT');

INSERT INTO positions (id, role) VALUES
(1, 'ADMIN'),
(2, 'MANAGER'),
(3, 'OPERATOR');

INSERT INTO position_permission (position_id, permission_id) VALUES
(1, 1), (1, 2), (1, 3), (1, 4), (1, 5), (1, 6), (1, 7), (1, 8),
(2, 1), (2, 2), (2, 5), (2, 6), (2, 7), (2, 8),
(3, 5), (3, 6);

SELECT setval(pg_get_serial_sequence('permissions', 'id'), COALESCE((SELECT MAX(id) FROM permissions), 1));
SELECT setval(pg_get_serial_sequence('positions', 'id'), COALESCE((SELECT MAX(id) FROM positions), 1));