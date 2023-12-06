#
# -- Insert places
# INSERT INTO places (id, address,city, description, name,  author_id, category_id)
# VALUES
#     (1, 'Mладост', 'София', 'Парк', 'Парк 100 години София', 1, 1),
#     (2, 'Младост', 'София', 'Детски кът', 'Dreams', 1, 2),
#     (3, 'Център', 'София', 'Зоопарк', 'Зоологическа градина София', 1, 3);
#
# -- Insert comments
# INSERT INTO comments (id,content, created,  author_id, place_id)
# VALUES
#     (1,'Хубаво място',  '2023-01-01T12:00:00', 1, 1),
#     (2,'Приятно място', '2023-01-02T12:00:00',  1, 2),
#     (3, 'Чудесно място за деца','2023-01-03T12:00:00',  1, 3);
#
# -- Insert pictures
#
# INSERT INTO pictures (id, url, author_id, place_id)
# VALUES (4, '/images/ani.jpg', 1, 1);
# INSERT INTO pictures (id, url, author_id, place_id)
# VALUES (5, '/images/lulka.jpg', 1, 2);
# INSERT INTO pictures (id, url, author_id, place_id)
# VALUES (3, '/images/zaek.jpg', 1, 3);
#
#
