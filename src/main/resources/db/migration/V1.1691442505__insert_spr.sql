INSERT INTO aircompanies (title)
values ('Avia Traffic Company'),
       ('Air Manas'),
       ('Tez Jet'),
       ('Air Astana'),
       ('FlyArystan'),
       ('Аэрофлот'),
       ('Air China')
on conflict (title) do nothing;
commit;

INSERT INTO airports(title, city)
values ('Manas', 'Bishkek'),
       ('Osh', 'Bishkek'),
       ('Tamchy', 'Bishkek'),
       ('Almaty', 'Almaty'),
       ('Astana', 'Astana'),
       ('Pulkovo', 'Moscow'),
       ('Vnukovo', 'Moscow')
on conflict (title) do nothing;
commit;