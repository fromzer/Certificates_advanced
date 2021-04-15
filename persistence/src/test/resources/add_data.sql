INSERT INTO gift_certificate (id, name, description, price, duration, create_date, last_update_date) VALUES (1, 'TestCertificate', 'TestCertificate Description', 10.00, 3, '2021-03-26 09:41:03', '2021-03-26 09:41:03');
INSERT INTO gift_certificate (id, name, description, price, duration, create_date, last_update_date) VALUES (2, 'Sport Certificate', 'New Sport Certificate Description', 40.00, 1, '2021-03-26 20:50:53', '2021-03-26 20:50:53');
INSERT INTO gift_certificate (id, name, description, price, duration, create_date, last_update_date) VALUES (3, 'NewCerts', 'NewCertDescription', 11.00, 3, '2021-03-28 16:00:00', '2021-03-28 16:00:00');
INSERT INTO gift_certificate (id, name, description, price, duration, create_date, last_update_date) VALUES (5, 'Blizzard entertainment', 'Welcome to WoW', 12.00, 5, '2021-03-28 17:45:51', '2021-03-30 20:32:31');
INSERT INTO gift_certificate (id, name, description, price, duration, create_date, last_update_date) VALUES (6, 'Shop', 'Hi hi hi', 14.00, 1, '2021-03-28 17:47:29', '2021-03-28 17:47:29');
INSERT INTO gift_certificate (id, name, description, price, duration, create_date, last_update_date) VALUES (7, 'New Year', 'Hello!', 110.00, 4, '2021-03-28 17:50:55', '2021-03-28 17:50:55');
INSERT INTO gift_certificate (id, name, description, price, duration, create_date, last_update_date) VALUES (9, 'Aplet', 'Description', 17.00, 1, '2021-03-30 15:02:04', '2021-03-30 15:02:04');

INSERT INTO tag (id, name) VALUES (2, 'test_tag_two');
INSERT INTO tag (id, name) VALUES (7, 'SportMaster');
INSERT INTO tag (id, name) VALUES (8, 'Beautiful');
INSERT INTO tag (id, name) VALUES (9, 'WoW');
INSERT INTO tag (id, name) VALUES (10, 'Game');
INSERT INTO tag (id, name) VALUES (11, 'WoW');
INSERT INTO tag (id, name) VALUES (12, 'Game');
INSERT INTO tag (id, name) VALUES (13, 'one');
INSERT INTO tag (id, name) VALUES (14, 'two');
INSERT INTO tag (id, name) VALUES (17, 'ASC');
INSERT INTO tag (id, name) VALUES (20, 'A1');
INSERT INTO tag (id, name) VALUES (21, 'Wild');
INSERT INTO tag (id, name) VALUES (22, 'MTC');
INSERT INTO tag (id, name) VALUES (23, 'Sommer');

INSERT INTO gift_certificate_tag (gift_certificate_id, tag_id) VALUES (1, 2);
INSERT INTO gift_certificate_tag (gift_certificate_id, tag_id) VALUES (1, 7);
INSERT INTO gift_certificate_tag (gift_certificate_id, tag_id) VALUES (1, 8);
INSERT INTO gift_certificate_tag (gift_certificate_id, tag_id) VALUES (2, 7);
INSERT INTO gift_certificate_tag (gift_certificate_id, tag_id) VALUES (3, 7);
INSERT INTO gift_certificate_tag (gift_certificate_id, tag_id) VALUES (3, 8);
INSERT INTO gift_certificate_tag (gift_certificate_id, tag_id) VALUES (5, 11);
INSERT INTO gift_certificate_tag (gift_certificate_id, tag_id) VALUES (5, 12);
INSERT INTO gift_certificate_tag (gift_certificate_id, tag_id) VALUES (5, 23);
INSERT INTO gift_certificate_tag (gift_certificate_id, tag_id) VALUES (9, 20);
INSERT INTO gift_certificate_tag (gift_certificate_id, tag_id) VALUES (9, 21);
INSERT INTO gift_certificate_tag (gift_certificate_id, tag_id) VALUES (9, 22);