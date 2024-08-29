INSERT INTO access (path) VALUES
('/user/**'),
('/admin/**'),
('/manager/**'),
('/api/user/**');

INSERT INTO role (name) VALUES
('ROLE_USER'),
('ROLE_MANAGER'),
('ROLE_ADMIN');

INSERT INTO access_roles (access_id, role_id) VALUES
((SELECT id FROM access WHERE path = '/user/**'), (SELECT id FROM role WHERE name = 'ROLE_USER')),
((SELECT id FROM access WHERE path = '/admin/**'), (SELECT id FROM role WHERE name = 'ROLE_ADMIN')),
((SELECT id FROM access WHERE path = '/manager/**'), (SELECT id FROM role WHERE name = 'ROLE_MANAGER')),
((SELECT id FROM access WHERE path = '/manager/**'), (SELECT id FROM role WHERE name = 'ROLE_ADMIN')),
((SELECT id FROM access WHERE path = '/api/user/**'), (SELECT id FROM role WHERE name = 'ROLE_USER')),
((SELECT id FROM access WHERE path = '/api/user/**'), (SELECT id FROM role WHERE name = 'ROLE_MANAGER')),
((SELECT id FROM access WHERE path = '/api/user/**'), (SELECT id FROM role WHERE name = 'ROLE_ADMIN'));