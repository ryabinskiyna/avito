CREATE TABLE houses
(
    id          INTEGER PRIMARY KEY AUTOINCREMENT,
    price       INTEGER CHECK ( price > 0 ),
    district    TEXT NOT NULL,
    underground TEXT

);

INSERT INTO houses (price, district, underground)
VALUES (1000000, "Авиастроительный", "Авиастроительная");
INSERT INTO houses (price, district, underground)
VALUES (2000000, "Московский", "Северный вокзал");
INSERT INTO houses (price, district, underground)
VALUES (3000000, "Московский", "Яшьлек");
INSERT INTO houses (price, district, underground)
VALUES (4000000, "Кировский", "Козья Слобода");
INSERT INTO houses (price, district, underground)
VALUES (5000000, "Вахитовский", "Кремлёвская");