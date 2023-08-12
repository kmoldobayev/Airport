--INSERT INTO app_roles (title) (
--   SELECT 'ADMIN' WHERE NOT EXISTS (
--            SELECT 1 FROM app_roles WHERE title='ADMIN'
--        )
--);


INSERT INTO positions (title)
values ('CHIEF'),
       ('СHIEF_DISPATCHER'),
       ('DISPATCHER'),
       ('CHIEF_ENGINEER'),
       ('ENGINEER'),
       ('PILOT'),
       ('CHIEF_STEWARD'),
       ('STEWARD')
on conflict (title) do nothing;
commit;

INSERT INTO app_roles (title, position_id)
values ('ADMIN', null),
       ('USER', null),
       ('CHIEF', (select id from positions where title = 'CHIEF')),
       ('СHIEF_DISPATCHER', (select id from positions where title = 'СHIEF_DISPATCHER')),
       ('DISPATCHER',  (select id from positions where title = 'DISPATCHER')),
       ('CHIEF_ENGINEER', (select id from positions where title = 'CHIEF_ENGINEER')),
       ('ENGINEER',  (select id from positions where title = 'ENGINEER')),
       ('PILOT',  (select id from positions where title = 'PILOT')),
       ('CHIEF_STEWARD', (select id from positions where title = 'CHIEF_STEWARD')),
       ('STEWARD', (select id from positions where title = 'STEWARD')),
       ('CUSTOMER', null)
on conflict (title) do nothing;

INSERT INTO app_users (
                       full_name,
                       user_login,
                       user_password,
                       is_enabled,
                       position_id)
values (
        'Администратор системы',
        'ADMIN',
        '$2y$08$KmWy6Z5ix2oP3.pWuqrA1.94wOnuknCmfiPLd31wKlTZvzOFr4fOC',
        true,
        null),
       (
        'Управляющий директор',
        'CHIEF',
        '$2y$10$bbAaqqmP0r4Lee7sDONtpeb0ptds/qVvtJJmxeLBJaQQbwbu3nbsi',
        true,
        (select id from positions where title = 'CHIEF'))
on conflict (user_login) do nothing;


INSERT INTO m2m_user_role (user_id, role_id)
values
    ( (select id from app_users where user_login = 'ADMIN'), (select id from app_roles where title = 'ADMIN')),
    ( (select id from app_users where user_login = 'CHIEF'), (select id from app_roles where title = 'CHIEF'))

on conflict (user_id, role_id) do nothing;

