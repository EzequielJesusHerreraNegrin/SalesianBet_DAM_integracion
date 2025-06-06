-- competitions --
INSERT INTO competitions (name, country) VALUES
('La Liga', 'España'),
('Premier League', 'Inglaterra'),
('Serie A', 'Italia'),
('Bundesliga', 'Alemania'),
('Ligue 1', "Francia"),
('Champions League', 'Europa');
-- teams --
INSERT INTO teams (team_name, sport) VALUES
-- La Liga --
('Barcelona', 'Fútbol'), -- 1
('Atlético de Madrid', 'Fútbol'), -- 2
('Real Madrid', 'Fútbol'), -- 3
('Girona', 'Fútbol'), -- 4
('Athletic', 'Fútbol'), -- 5
('Real Sociedad', 'Fútbol'), -- 6
('Betis', 'Fútbol'), -- 7
('Valencia C.F.', 'Fútbol'), -- 8
('Villarreal', 'Fútbol'), -- 9
('Sevilla', 'Fútbol'), -- 10
('Getafe', 'Fútbol'), -- 11
('Rayo Vallecano', 'Fútbol'), -- 12
('Osasuna', 'Fútbol'), -- 13
('U.D. Las Palmas', 'Fútbol'), -- 14
('Alavés', 'Fútbol'), -- 15
('R.C.D. Mallorca', 'Fútbol'), -- 16
('Celta de Vigo', 'Fútbol'), -- 17
('Valladolid', 'Fútbol'), -- 18
('Leganés', 'Fútbol'), -- 19
('RCD Espanyol', 'Fútbol'), -- 20
-- Premier League --
('Arsenal', 'Fútbol'), -- 21
('Aston Villa', 'Fútbol'), -- 22
('Bournemouth', 'Fútbol'), -- 23
('Brentford', 'Fútbol'), -- 24
('Brighton', 'Fútbol'), -- 25
('Chelsea', 'Fútbol'), -- 26
('Crystal Palace', 'Fútbol'), -- 27
('Everton', 'Fútbol'), -- 28
('Fulham', 'Fútbol'), -- 29
('Ipswich', 'Fútbol'), -- 30
('Leicester City', 'Fútbol'), -- 31
('Liverpool', 'Fútbol'), -- 32
('Manchester City', 'Fútbol'), -- 33
('Manchester United', 'Fútbol'), -- 34
('Newcastle', 'Fútbol'), -- 35
('Nottingham Forest', 'Fútbol'), -- 36
('Southampton', 'Fútbol'), -- 37
('Tottenham', 'Fútbol'), -- 38
('West Ham', 'Fútbol'), -- 39
('Wolves', 'Fútbol'), -- 40
-- Serie A --
('Atalanta', 'Fútbol'), -- 41
('Bolonia', 'Fútbol'), -- 42
('Cagliari', 'Fútbol'), -- 43
('Como', 'Fútbol'), -- 44
('Empoli', 'Fútbol'), -- 45
('Fiorentina', 'Fútbol'), -- 46
('Genoa', 'Fútbol'), -- 47
('Hellas Verona', 'Fútbol'), -- 48
('Inter', 'Fútbol'), -- 49
('Juventus', 'Fútbol'), -- 50
('Lazio', 'Fútbol'), -- 51
('Lecce', 'Fútbol'), -- 52
('Milan', 'Fútbol'), -- 53
('Monza', 'Fútbol'), -- 54
('Napoli', 'Fútbol'), -- 55
('Parma', 'Fútbol'), -- 56
('Roma', 'Fútbol'), -- 57
('Torino', 'Fútbol'), -- 58
('Udinese', 'Fútbol'), -- 59
('Venezia', 'Fútbol'), -- 60
-- BundesLiga --
('Augsburgo', 'Fútbol'), -- 61
('Leverkusen', 'Fútbol'), -- 62
('Bayern de Múnich', 'Fútbol'), -- 63
('Bochum', 'Fútbol'), -- 64
('B. Dortmund', 'Fútbol'), -- 65
('B. Mönchengladbach', 'Fútbol'), -- 66
('Frankfurt', 'Fútbol'), -- 67
('Friburgo', 'Fútbol'), -- 68
('Heidenheim', 'Fútbol'), -- 69
('Hoffenheim', 'Fútbol'), -- 70
('KSV Holstein', 'Fútbol'), -- 71
('Mainz 05', 'Fútbol'), -- 72
('RB Leipzig', 'Fútbol'), -- 73
('St. Pauli', 'Fútbol'), -- 74
('Stuttgart', 'Fútbol'), -- 75
('FC Unión Berlín', 'Fútbol'), -- 76
('Werder Bremen', 'Fútbol'), -- 77
('Wolfsburgo', 'Fútbol'), -- 78
-- Ligue One --
('Angers', 'Fútbol'), -- 79
('Auxerre', 'Fútbol'), -- 80
('Le Havre', 'Fútbol'), -- 81
('Lens', 'Fútbol'), -- 82
('LOSC', 'Fútbol'), -- 83
('Mónaco', 'Fútbol'), -- 84
('Montpellier', 'Fútbol'), -- 85
('Nantes', 'Fútbol'), -- 86
('Niza', 'Fútbol'), -- 87
('Lyon', 'Fútbol'), -- 88
('Marsella', 'Fútbol'), -- 89
('PSG', 'Fútbol'), -- 90
('Racing de Estrasburgo', 'Fútbol'), -- 91
('Rennes', 'Fútbol'), -- 92
('Saint-Étienne', 'Fútbol'), -- 93
('Stade Brestois', 'Fútbol'), -- 94
('Reims', 'Fútbol'), -- 95
('Toulouse', 'Fútbol'), -- 96
-- another teams --
('Sporting Lisboa', 'Fútbol'), -- 97
('Benfica', 'Fútbol'), -- 98
('PSV', 'Fútbol'), -- 99
('Feyenoord', 'Fútbol'), -- 100
('Sturm', 'Fútbol'), -- 101
('RB Salzburg', 'Fútbol'), -- 102
('Club Brujas', 'Fútbol'), -- 103
('Celtic F.C.', 'Fútbol'), -- 104
('Shakhtar', 'Fútbol'), -- 105
('Sparta Praha', 'Fútbol'), -- 106
('Young Boys', 'Fútbol'), -- 107
('Dinamo Zagreb', 'Fútbol'), -- 108
('Estrella Roja', 'Fútbol'), -- 109
('Slovan Bratislava', 'Fútbol'); -- 110

-- matches --
INSERT INTO matches (date, playing, result, competition_id, home_team_id, away_team_id) VALUES  
-- Cuartos de Champions
('2025-04-08 20:00:00', FALSE, '3 - 0', 6, 21, 3),  -- Arsenal vs. Real Madrid
('2025-04-08 20:00:00', FALSE, '1 - 2', 6, 63, 49), -- Bayern Múnich vs Inter
('2025-04-09 20:00:00', FALSE, '3 - 1', 6, 90, 22), -- PSG vs Aston Villa
('2025-04-09 20:00:00', FALSE, '4 - 0', 6, 1, 65),  -- Barça vs Dortmund
('2025-04-15 20:00:00', FALSE, '', 6, 22, 90),
('2025-04-15 20:00:00', FALSE, '', 6, 65, 1),
('2025-04-16 20:00:00', FALSE, '', 6, 3, 21),
('2025-04-16 20:00:00', FALSE, '', 6, 49, 63);


INSERT INTO users ( user_name, password, email, dni, points, country) VALUES
( 'juanperez', '$2a$12$fOmKz2JnCxaDxULqWp1oCuGD1YZC1ySHGSWuabIh1cZVxaKgyjcq6', 'juan.perez@example.com', '12345678A', 1500, 'España'),
( 'mariagarcia', '$2a$12$fOmKz2JnCxaDxULqWp1oCuGD1YZC1ySHGSWuabIh1cZVxaKgyjcq6', 'maria.garcia@example.com', '87654321B', 2200, 'México'),
( 'lucasfernandez', '$2a$12$fOmKz2JnCxaDxULqWp1oCuGD1YZC1ySHGSWuabIh1cZVxaKgyjcq6', 'lucas.fernandez@example.com', '11223344C', 500, 'Argentina'),
( 'analopez', '$2a$12$fOmKz2JnCxaDxULqWp1oCuGD1YZC1ySHGSWuabIh1cZVxaKgyjcq6', 'ana.lopez@example.com', '44332211D', 3200, 'Colombia'),
( 'carlossanchez', '$2a$12$fOmKz2JnCxaDxULqWp1oCuGD1YZC1ySHGSWuabIh1cZVxaKgyjcq6', 'carlos.sanchez@example.com', '55667788E', 10000, 'Chile');


-- Inserta productos en la tabla 'products'
INSERT INTO products (product_name, product_image, price, state) VALUES
('Camiseta oficial Madrid', 'camiseta-oficial-Madrid.jpg', 59, 'PUBLICO'),
('Botas Nike', 'botas-Nike.png', 199, 'PUBLICO'),
('Bufanda Madrid oficial', 'bufanda-Madrid-oficial.png', 29, 'PUBLICO'),
('Short deportivo Addidas', 'short-deportivo-Addidas.png', 29, 'PUBLICO'),
('Medias Addidas', 'medias-Addidas.png', 29, 'PUBLICO'),
('Pelota Nike', 'pelota-nike.png', 49, 'PUBLICO'),
('Camiseta de portero Addidas', 'camiseta-de-portero-Addidas.png', 49, 'PUBLICO'),
('Camiseta oficial FCB', 'camiseta-oficial-FCB.png', 49, 'PUBLICO'),
('Camiseta de portero Addidas', 'camiseta-de-portero-Addidas.png', 89, 'DESCATALOGADO'),
('Replica balon de oro', 'replica-balon-de-oro.png', 289, 'DESCATALOGADO');

-- 1. Roles
INSERT INTO roles (role_name) VALUES ('USER'); -- Supongamos ID 1


-- 6. User_Roles (Tabla de unión para la relación ManyToMany entre Users y Roles)
-- John Doe es USER
INSERT INTO user_roles (fk_user_id, fk_role_id) VALUES (1, 1);
-- Jane Smith es USER
INSERT INTO user_roles (fk_user_id, fk_role_id) VALUES (2, 1);
-- Admin User es USER 
INSERT INTO user_roles (fk_user_id, fk_role_id) VALUES (3, 1);

-- 9. Cart_Items (Cesta de la compra)
-- John Doe tiene una camiseta en su cesta
INSERT INTO cart_items (user_id, product_id, cuantity)
VALUES (1, 1, 1); -- Supongamos ID 1
-- John Doe también tiene una bufanda
INSERT INTO cart_items (user_id, product_id, cuantity)
VALUES (1, 2, 2); -- Supongamos ID 2
-- Jane Smith tiene una taza (aunque esté out of stock, podría estar en la cesta esperando reposición)
INSERT INTO cart_items (user_id, product_id, cuantity)
VALUES (2, 4, 1); -- Supongamos ID 3

-- 10. Purchases (Compras realizadas)
-- John Doe compró un Balón Firmado
INSERT INTO purchases (user_id, product_id, quantity, total_price, purchase_date)
VALUES (1, 3, 1, 150, '2024-07-20 10:00:00'); -- Supongamos ID 1
-- Jane Smith compró una Camiseta Oficial
INSERT INTO purchases (user_id, product_id, quantity, total_price, purchase_date)
VALUES (2, 1, 2, 50, '2024-07-21 15:30:00'); -- Supongamos ID 2