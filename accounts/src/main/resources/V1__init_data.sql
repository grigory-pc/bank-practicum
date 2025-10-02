INSERT INTO currency (title, name)
VALUES
    ('Рубль', 'RUB'),
    ('Доллар США', 'USD'),
    ('Китайский юань', 'CNY');


INSERT INTO users (login, password, name, birthdate, role)
VALUES
    ('ivan', 'pass', 'Иван Иванов', '1990-05-15', 'USER'),
    ('alex', 'pass', 'Алексей Алексеев', '1991-01-01', 'USER');


INSERT INTO accounts (currency_id, user_id, value, is_exists)
SELECT
    (SELECT id FROM currency WHERE name = 'RUB'),
    (SELECT id FROM users WHERE login = 'Ivan'),
    10000.00,
    true
UNION ALL
SELECT
    (SELECT id FROM currency WHERE name = 'USD'),
    (SELECT id FROM users WHERE login = 'Ivan'),
    10000.00,
    true
UNION ALL
SELECT
    (SELECT id FROM currency WHERE name = 'CNY'),
    (SELECT id FROM users WHERE login = 'Ivan'),
    10000.00,
    true
UNION ALL
SELECT
    (SELECT id FROM currency WHERE name = 'RUB'),
    (SELECT id FROM users WHERE login = 'Petr'),
    10000.00,
    true
UNION ALL
SELECT
    (SELECT id FROM currency WHERE name = 'USD'),
    (SELECT id FROM users WHERE login = 'Petr'),
    10000.00,
    true
UNION ALL
SELECT
    (SELECT id FROM currency WHERE name = 'CNY'),
    (SELECT id FROM users WHERE login = 'Petr'),
    10000.00,
    true;
