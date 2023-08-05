--INSERT INTO app_roles (title) (
--   SELECT 'ADMIN' WHERE NOT EXISTS (
--            SELECT 1 FROM app_roles WHERE title='ADMIN'
--        )
--);
/*
INSERT INTO app_users (user_login, user_password, status, date_last_login)
values ('ADMIN',
        '$2y$08$KmWy6Z5ix2oP3.pWuqrA1.94wOnuknCmfiPLd31wKlTZvzOFr4fOC',
        'ACTIVE',
        null),
        ('CHIEF',
        '$2y$10$bbAaqqmP0r4Lee7sDONtpeb0ptds/qVvtJJmxeLBJaQQbwbu3nbsi',
        'ACTIVE',
        null)
on conflict (user_login) do nothing;

INSERT INTO app_roles (title, position_id)
values ('ADMIN'),
       ('USER'),
       ('CHIEF'),
       ('СHIEF_DISPATCHER'),
       ('DISPATCHER'),
       ('CHIEF_ENGINEER'),
       ('ENGINEER'),
       ('PILOT'),
       ('CHIEF_STEWARD'),
       ('STEWARD'),
       ('CUSTOMER')
on conflict (title) do nothing;

INSERT INTO m2m_user_role (user_id, role_id)
values
    (1, 1),
    (2, 3)

on conflict (user_id, role_id) do nothing;
*/
