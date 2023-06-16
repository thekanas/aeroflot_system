INSERT INTO person (fullname, position, birth_day, role, password, tel, address, description)
VALUES ('Калашников Аполлон Романович', 'Пилот', '1965-02-25', 'EMPLOYEE', '111', '+375298887766', 'г.Гомель ул.Советская д.1',
        'Интеграл по ориентированной области, исключая очевидный случай, соответствует возрастающий минимум'),
       ('Шарова Таисия Максимовна', 'Дизайнер', '1999-04-23', 'EMPLOYEE', '111', '+375298887766', 'г.Гомель ул.Советская д.1',
        'Объект осознаёт гендер'),
       ('Кузьмин Ян Романович', 'Пилот', '1961-06-05', 'EMPLOYEE', '111', '+375298887766', 'г.Гомель ул.Советская д.1',
        'Позиционирование на рынке индуктивно экономит креативный потребительский рынок');

INSERT INTO brand (name)
VALUES ('Boeing'),
       ('Cessna'),
       ('Embraer');

INSERT INTO airplane (brand, model, flight_range_km, passenger_capacity)
SELECT b.brand_id, '737 MAX 8', 6570, 162
    FROM brand b WHERE name = 'Boeing';

INSERT INTO airplane (brand, model, flight_range_km, passenger_capacity)
SELECT b.brand_id, '737-800', 5765, 189
FROM brand b WHERE name = 'Boeing';


INSERT INTO airport (name, cod_IATA)
VALUES ('аэропорт "Минск"', 'MSQ'),
       ('аэропорт "Гомель"', 'GME'),
       ('аэропорт "Москва-Внуково"', 'VKO');

INSERT INTO flight (number, airport_departure_id, airport_arrival_id, airport_reserve_id, time_departure, time_arrival, airplane_id)
SELECT '2989R', airport_id, airport_id, airport_id, '2023-06-15 13:00', '2023-06-15 15:00', airplane_id
    FROM airport a, airplane ap
    WHERE a.name = 'аэропорт "Минск"' AND ap.model = '737 MAX 8';

INSERT INTO flight (number, airport_departure_id, airport_arrival_id, airport_reserve_id, time_departure, time_arrival, airplane_id)
SELECT '2990L', airport_id, airport_id, airport_id, '2023-06-15 13:00', '2023-06-15 15:00', airplane_id
FROM airport a, airplane ap
WHERE a.name = 'аэропорт "Гомель"' AND ap.model = '737-800';


INSERT INTO flight_person (flight_id, person_id)
SELECT f.flight_id, p.person_id
    FROM flight f, person p
    WHERE f.number = '2989R' AND p.fullname = 'Калашников Аполлон Романович';

