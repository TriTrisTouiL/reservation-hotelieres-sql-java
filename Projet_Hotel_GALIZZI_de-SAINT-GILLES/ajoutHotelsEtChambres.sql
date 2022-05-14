
INSERT INTO HOTEL (IDHotel, NomHotel, VilleH, CPH, NumRueH, RueH, categorie)
    VALUES 
    (1,'California Hotel', 'Besancon', 25000, 36, 'rue des Potiers', 3),
    (2,'Antenne Hotel', 'Besancon', 25260, 2, 'rue des Amandes', 2),
    (3,'Maitre Kebabiste Hotel', 'Saint Maurice en Riviere', 71620, 10, 'rue Fugace', 1),
    (4,'California Hotel', 'Besancon', 25000, 12, 'rue du Chien', 4);

INSERT INTO `CHAMBRE` (`IDHotel`, `NumeroChambre`,`Prix`,`NbPlaces`)
    VALUES
    ((SELECT(IDHotel) FROM HOTEL WHERE NomHotel = 'California Hotel' AND categorie = 3), 23, 69.99, 2),
    ((SELECT(IDHotel) FROM HOTEL WHERE NomHotel = 'California Hotel'), 42, 124.99, 2),
    ((SELECT(IDHotel) FROM HOTEL WHERE NomHotel = 'California Hotel'), 18, 39.99, 1),

    ((SELECT(IDHotel) FROM HOTEL WHERE NomHotel = 'Antenne Hotel'), 101, 99.99, 1),
    ((SELECT(IDHotel) FROM HOTEL WHERE NomHotel = 'Antenne Hotel'), 222, 222.22, 2),
    ((SELECT(IDHotel) FROM HOTEL WHERE NomHotel = 'Antenne Hotel'), 432, 349.99, 4),

    ((SELECT(IDHotel) FROM HOTEL WHERE NomHotel = 'Maitre Kebabiste Hotel'), 1, 999.99, 1),
    ((SELECT(IDHotel) FROM HOTEL WHERE NomHotel = 'Maitre Kebabiste Hotel'), 2, 10, 2),
    ((SELECT(IDHotel) FROM HOTEL WHERE NomHotel = 'Maitre Kebabiste Hotel'), 3, 87379.02, 10)

    (((SELECT(IDHotel) FROM HOTEL WHERE NomHotel = 'California Hotel' AND categorie = 4)), 1, 79.99, 2);