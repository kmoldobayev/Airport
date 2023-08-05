create schema if not exists airport;
--Таблица "Должности"
create table if not exists positions (
    id bigserial primary key,                           -- Уникальный идентификатор
    title varchar not null                              -- Наименование
);

-- ALTER TABLE employees ADD CONSTRAINT
--     position_id_fkey foreign key(position_id) references positions(id);

--drop table roles cascade ;
-- Таблица "Роли"
create table if not exists app_roles(
    id bigserial primary key,                                       -- Уникальный идентификатор
    title varchar not null unique,                                  -- Название роли
    position_id bigint not null references positions(id)            -- Должность
);

--drop table users cascade ;
-- Таблица "Пользователи"
create table if not exists app_users(
    id bigserial primary key,                                       -- Уникальный идентификатор                   -- Ссылка на таблицу "Person" (связь один-к-одному)
    user_login varchar not null unique,                             -- Логин пользователя
    user_password varchar not null,                                 -- Пароль пользователя
    position_id bigint references positions(id),
    status varchar,                                                 -- Статус пользователя в системе (активен, заблокирован)
    date_last_login timestamp without time zone,                    -- Дата последнего входа в систему
    date_begin date not null default now(),                         -- Дата начала
    date_ending date check (date_ending >= app_users.date_begin),   -- Дата увольнения
    is_deleted boolean
);

--drop table customer_reviews cascade ;
-- Таблица "Отзывы клиента"
create table if not exists customer_reviews (
    id bigserial primary key,                                       --
    review varchar not null,                                        --
    date_create timestamp without time zone not null,                                      --
    mark integer not null check (mark in (1, 2, 3, 4, 5)),          --
    user_id bigint not null references app_users(id)            --
);

-- Таблица "Авиакомпании"
create table if not exists aircompanies(
    id bigserial primary key,                                       -- Уникальный идентификатор
    title varchar not null unique                                   -- Название авиакомпании
);

-- Таблица Типы (марки) самолетов
create table if not exists airplane_types(
    id bigserial primary key,                                       -- Уникальный идентификатор
    title varchar not null                                          -- Название марки скмолета
);

-- Таблица Самолеты
create table if not exists airplanes (
    id bigserial primary key,                                       -- Уникальный идентификатор
    model varchar not null,                                         -- Модель самолета
    board_number integer not null,                                  -- Номер борта (Уникальный идентификатор самолета)
    marka bigint not null references airplane_types(id),            -- Тип самолета
    aircompany bigint not null references aircompanies(id),         -- Ссылка на таблицу "Авиакомпания"
    date_manufacturing date not null,                               -- Дата производства
    number_of_seats integer not null,                               -- Максимальная вместимость самолета
    status varchar not null,                                        -- Статус самолета (В посадке, На земле, В полете, Готовится к взлету, Вылет)
    is_available boolean                                            -- Признак доступности
);

-- Таблица Аэропорты
create table if not exists airports(
    id bigserial primary key,                                         -- Уникальный идентификатор
    title varchar not null unique,                                    -- Название аэропорта
    city varchar not null                                             -- Город аэропорта
);

-- Таблица "Рейсы"
create table if not exists flights(
    id bigserial primary key,                                       -- id
    flight_number varchar not null,                                 -- Номер рейса
    source bigint not null references airports(id),                 -- Место отправления
    destination bigint not null references airports(id),            -- Место назначения
    departure_datetime timestamp without time zone,                 -- Время вылета
    arrival_datetime timestamp without time zone,                   -- Время прибытия
    aircompany bigint references aircompanies(id),                  -- Ссылка на таблицу "Авиакомпании"
    is_available boolean not null                                   -- Признак доступности рейса
);

-- Таблица "Сиденья в самолете"
create table if not exists seats (
    id bigserial primary key,                                       --
    flight_id bigint not null references flights(id),               --
    seatNumber int not null,                   --        --
    is_occupied boolean not null                                                  --
);

-- Таблица "Пользователь - Рейс"
create table if not exists users_flights (
    id bigserial primary key,                                       --
    flight_id bigint not null references flights(id),               --
    seat_id bigint not null references seats(id),                   --
    user_id bigint not null references app_users(id),               --
    status varchar                                                  --
);

-- Таблица "Расписание полетов"
-- Связь: Каждый рейс выполняется на определенном самолете, но каждый самолет может выполнять много рейсов
create table if not exists airplanes_flights(
    id bigserial primary key,
    airplane_id bigint not null references airplanes(id),           -- Ссылка на таблицу "Самолеты"
    flight_id bigint not null references flights(id),                -- Ссылка на таблицу "Рейсы"
    user_flight_id bigint references users_flights(id),               -- Ссылка на таблицу "Экипажи" назначенной на этот рейс
    status varchar not null                                         -- Статус рейса (Запланирован, Вылетел, Прибыл)
);
-- Таблица "Технические осмотры"
create table if not exists tech_inspections(
    id bigserial primary key,
    date_inspection date not null,                                  -- Дата тех осмотра
    chief_engineer_id bigint references employees(id),              -- Ответственный главный инженер
    degree varchar,                                                 -- Степень износа
    status varchar not null,                                        -- Статус
    result varchar                                                  -- Результат
);

-- Таблица "Оборудования"
create table if not exists equipments (
    id bigserial primary key,
    title varchar not null,                                         -- Название оборудования
    description varchar,                                            -- Описание оборудования
    date_last date,                                                 -- Дата последнего обслуживания
    status varchar not null,                                        -- Текущее состояние оборудования
    date_next date                                                  -- Дата следующего обслуживания
);

-- Таблица "Ремонтные работы"
create table if not exists repair_works(
    id bigserial primary key,
    title varchar not null,                                         -- Название работы
    description varchar,                                            -- Описание работы
    cost_work integer,                                              -- Стоимость ремонтных работ
    date_begin date not null,                                       -- Дата начала ремонтных работ
    date_ending date,                                               -- Дата окончания ремонтных работ
    equipment_id bigint not null references equipments(id)          -- Ссылка на таблицу "Оборудования"
);
-- Таблица "Технические обслуживания"
create table if not exists tech_maintenances(
    id bigserial primary key,
    tech_insp_id bigint not null references tech_inspections(id),   -- Ссылка на таблицу "Технические осмотры"
    is_fuel boolean not null,                                       -- Признак заправки самолета
    engineer_id bigint not null references app_user(id),           -- Ответственный инженер
    plane_id bigint not null references airplanes(id),              -- Ссылка на таблицу "Самолеты"
    remont_id bigint not null references repair_works(id),          -- Ссылка на таблицу "Ремонтные работы"
    date_maintenance date not null                                  -- Дата обслуживания
);

create table if not exists m2m_user_role (
    user_id bigint references app_users(id),
    role_id bigint references app_roles(id),
    CONSTRAINT user_role_id primary key(user_id, role_id)
);
--
-- ALTER TABLE m2m_user_role ADD CONSTRAINT
--     user_role_unique UNIQUE(user_id, role_id);