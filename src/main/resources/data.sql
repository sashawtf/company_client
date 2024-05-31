delete from email_person;
delete from phone_person;
delete from person;
delete from status_person;
delete from veriety_person;

INSERT INTO veriety_person (veriety) VALUES ('Инвестор');
INSERT INTO veriety_person (veriety) VALUES ('Доверительное управление');

INSERT INTO status_person (status) VALUES ('Активный');
INSERT INTO status_person (status) VALUES ('Неактивный');

INSERT INTO person (veriety_id, status_id, inn, type, shifer, data)
VALUES
    (1, 1, '1234567890', 'Физическое лицо', 'CL001', '2023-01-15'),
    (2, 2, '0987654321', 'Юридическое лицо', 'CL002', '2022-12-20');

INSERT INTO email_person (person_id, email)
VALUES
    (1, 'example1@example.com'),
    (2, 'example2@example.com');

INSERT INTO phone_person (person_id, phone)
VALUES
    (1, '+12345678901'),
    (2, '+10987654321');

