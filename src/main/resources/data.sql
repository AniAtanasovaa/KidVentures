INSERT INTO users (id, first_name, last_name, username, password, email)
VALUES
    (1, 'Admin','Adminov', 'admin', '95c1933b8ffe84f085f2839899d1673260be58dbd9c2c787ac35515805502c996417596dae9a92880aaa50a046fc7151', 'admin@example.com'),
    (2, 'User','User', 'user', '95c1933b8ffe84f085f2839899d1673260be58dbd9c2c787ac35515805502c996417596dae9a92880aaa50a046fc7151', 'admin@example.com');


INSERT INTO roles (`id`, `role`)
VALUES
    (1, 'ADMIN'),
    (2, 'USER');

INSERT INTO users_roles(`user_entity_id`, `roles_id`)
VALUES
    (1, 1),
    (1, 2),
    (2, 2);