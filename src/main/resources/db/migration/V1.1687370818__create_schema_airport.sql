create schema if not exists airport;
--Таблица "Должности"
create table if not exists positions (
    id bigserial primary key,                                       -- Уникальный идентификатор
    title varchar not null unique                                   -- Наименование
);

-- Таблица "Роли"
create table if not exists app_roles(
    id bigserial primary key,                                       -- Уникальный идентификатор
    title varchar not null unique,                                  -- Название роли
    position_id bigint references positions(id)                     -- Должность
);

-- Таблица "Пользователи"
create table if not exists app_users(
    id bigserial primary key,                                       -- Уникальный идентификатор
    full_name varchar not null,                                     -- Полное имя пользователя
    user_login varchar not null unique,                             -- Логин пользователя
    user_password varchar not null,                                 -- Пароль пользователя
    position_id bigint references positions(id),                    -- Должность
    is_enabled boolean,                                             -- Доступен/Недоcтупен
    date_begin date not null default now(),                         -- Дата начала
    date_ending date check (date_ending >= app_users.date_begin)    -- Дата увольнения
);


-- Таблица "Авиакомпании"
create table if not exists aircompanies(
    id bigserial primary key,                                       -- Уникальный идентификатор
    title varchar not null unique                                   -- Название авиакомпании
);

-- -- Таблица "Типы (марки) самолетов"
-- create table if not exists airplane_types(
--     id bigserial primary key,                                       -- Уникальный идентификатор
--     title varchar not null unique                                         -- Название марки скмолета
-- );

-- Таблица "Аэропорты"
create table if not exists airports(
    id bigserial primary key,                                         -- Уникальный идентификатор
    title varchar not null unique,                                    -- Название аэропорта
    city varchar not null                                             -- Город аэропорта
);

-- Таблица "Самолеты"
create table if not exists airplanes (
    id bigserial primary key,                                       -- Уникальный идентификатор
    board_number varchar not null unique,                           -- Номер борта (Уникальный идентификатор самолета)
    marka varchar,                                                  -- Тип самолета
    aircompany bigint not null references aircompanies(id),         -- Ссылка на таблицу "Авиакомпания"
    date_register timestamp without time zone not null,             -- Дата регистрации
    number_seats integer not null check (number_seats > 0),         -- Максимальная вместимость самолета
    status varchar not null,                                        -- Статус самолета (В посадке, На земле, В полете, Готовится к взлету, Вылет)
    user_id bigint references app_users(id)                         -- Пользователь                                        -- Признак доступности
);

-- Таблица "Рейсы"
create table if not exists flights(
    id bigserial primary key,                                       -- id
    flight_number varchar not null,                                 -- Номер рейса
    airplane_id bigint not null references airplanes(id),           -- Ссылка на таблицу "Самолеты"
    destination bigint not null references airports(id),            -- Место назначения
    date_register timestamp without time zone,                      -- Дата и время регистрации
    status varchar,                                                 -- Статус рейса
    tickets_left int,                                               -- Количество оставшихся билетов
    is_available boolean not null                                   -- Признак доступности рейса
);

-- Таблица "Сиденья в самолете"
create table if not exists seats (
    id bigserial primary key,                                       --
    seat_number int not null,
    airplane_id bigint not null references airplanes(id),               --
    is_occupied boolean not null                                                  --
);

-- -- Таблица "Пользователь - Рейс"
-- create table if not exists users_flights (
--     id bigserial primary key,                                       --
--     flight_id bigint not null references flights(id),               --
--     seat_id bigint not null references seats(id),                   --
--     user_id bigint not null references app_users(id),               --
--     status varchar                                                  --
-- );

-- -- Таблица "Расписание полетов"
-- -- Связь: Каждый рейс выполняется на определенном самолете, но каждый самолет может выполнять много рейсов
-- create table if not exists airplanes_flights(
--     id bigserial primary key,
--     airplane_id bigint not null references airplanes(id),           -- Ссылка на таблицу "Самолеты"
--     flight_id bigint not null references flights(id),                -- Ссылка на таблицу "Рейсы"
--     user_flight_id bigint references users_flights(id),               -- Ссылка на таблицу "Экипажи" назначенной на этот рейс
--     status varchar not null                                         -- Статус рейса (Запланирован, Вылетел, Прибыл)
-- );

-- Таблица "Рейс-Пользователь-Сиденье"
create table if not exists users_flights(
    id bigserial primary key,
    seat_id bigint not null references seats(id),                   -- Ссылка на таблицу "Сиденья в самолете"
    flight_id bigint not null references flights(id),               -- Ссылка на таблицу "Рейсы"
    user_id bigint references app_users(id),                        -- Ссылка на таблицу "Пользователи"
    status varchar not null,                                        -- Статус рейса
    date_register timestamp without time zone                       -- Дата и время регистрации
);

-- Таблица "Оборудования"
create table if not exists parts (
     id bigserial primary key,
     title varchar not null,                                         -- Название оборудования
     part_type varchar,                                              -- Тип оборудования
     airplane_type varchar,
     date_register timestamp without time zone                       -- Дата и время регистрации
);

-- Таблица "Технические осмотры"
create table if not exists part_inspections(
    id bigserial primary key,
    date_register timestamp without time zone not null,             -- Дата тех осмотра
    user_id bigint references app_users(id),                        -- Ответственный пользователь (Ссылка на таблицу "Пользователи")
    part_id bigint references parts(id),                          -- Ссылка на таблицу "Оборудования"
    airplane_id bigint references airplanes(id),           -- Ссылка на таблицу "Самолеты"
    status varchar,                                        -- Статус
    inspection_code bigint                                          -- Результат
);


-- -- Таблица "Ремонтные работы"
-- create table if not exists repair_works(
--     id bigserial primary key,
--     title varchar not null,                                         -- Название работы
--     description varchar,                                            -- Описание работы
--     cost_work integer,                                              -- Стоимость ремонтных работ
--     date_begin date not null,                                       -- Дата начала ремонтных работ
--     date_ending date,                                               -- Дата окончания ремонтных работ
--     equipment_id bigint not null references equipments(id)          -- Ссылка на таблицу "Оборудования"
-- );
-- -- Таблица "Технические обслуживания"
-- create table if not exists tech_maintenances(
--     id bigserial primary key,
--     tech_insp_id bigint not null references tech_inspections(id),   -- Ссылка на таблицу "Технические осмотры"
--     is_fuel boolean not null,                                       -- Признак заправки самолета
--     engineer_id bigint not null references app_user(id),           -- Ответственный инженер
--     plane_id bigint not null references airplanes(id),              -- Ссылка на таблицу "Самолеты"
--     remont_id bigint not null references repair_works(id),          -- Ссылка на таблицу "Ремонтные работы"
--     date_maintenance date not null                                  -- Дата обслуживания
-- );

create table if not exists m2m_airplanes_parts (
    airplane_id bigint references airplanes(id),
    part_id bigint references parts(id),
    CONSTRAINT airplane_part_id primary key(airplane_id, part_id)
);

create table if not exists m2m_user_role (
    user_id bigint references app_users(id),
    role_id bigint references app_roles(id),
    CONSTRAINT user_role_id primary key(user_id, role_id)
);

-- Таблица "Отзывы клиента"
create table if not exists customer_reviews (
    id bigserial primary key,                                       --
    review varchar not null,                                        --
    date_register timestamp without time zone not null,                                      --
    mark integer not null check (mark in (1, 2, 3, 4, 5)),          --
    user_id bigint not null references app_users(id),               --
    flight_id bigint not null references flights(id)                -- Ссылка на таблицу "Рейсы"
);
