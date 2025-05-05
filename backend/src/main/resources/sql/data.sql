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
INSERT INTO matches (date, is_playing, result, competition_id, home_team_id, away_team_id) VALUES  
-- Cuartos de Champions
('2025-04-08 20:00:00', FALSE, '3 - 0', 6, 21, 3),  -- Arsenal vs. Real Madrid
('2025-04-08 20:00:00', FALSE, '1 - 2', 6, 63, 49), -- Bayern Múnich vs Inter
('2025-04-09 20:00:00', FALSE, '3 - 1', 6, 90, 22), -- PSG vs Aston Villa
('2025-04-09 20:00:00', FALSE, '4 - 0', 6, 1, 65),  -- Barça vs Dortmund
('2025-04-15 20:00:00', FALSE, '', 6, 22, 90),
('2025-04-15 20:00:00', FALSE, '', 6, 65, 1),
('2025-04-16 20:00:00', FALSE, '', 6, 3, 21),
('2025-04-16 20:00:00', FALSE, '', 6, 49, 63),

-- Jornada 31 de la liga 

('2025-04-11 21:00:00', FALSE, '1 - 0', 1), -- Valencia vs Sevilla
('2025-04-12 14:00:00', FALSE, '0 - 2', 1), -- Real Sociedad vs Mallorca
('2025-04-12 16:15:00', FALSE, '1 - 3', 1), -- Getafe vs Las Palmas
('2025-04-12 18:30:00', FALSE, '', 1),      -- Betis vs Villarreal
('2025-04-12 21:00:00', FALSE, '', 1),      -- Leganés vs Barcelona
('2025-04-13 14:00:00', FALSE, '', 1),      -- Osasuna vs Girona
('2025-04-13 16:15:00', FALSE, '', 1),      -- Athletic Club vs Rayo Vallecano
('2025-04-13 18:30:00', FALSE, '', 1),      -- Celta de Vigo vs Espanyol
('2025-04-13 21:00:00', FALSE, '', 1),      -- Real Madrid vs Alavés
('2025-04-14 21:00:00', FALSE, '', 1); 


INSERT teams_matches (fk_match_id, fk_team_id) VALUES
(1, 21), (1, 3),
(2, 63), (2, 49), 
(3, 90), (3, 22),
(4, 1), (4, 65),
(5, 22), (5, 90),
(6, 65), (6, 1),
(7, 3), (7, 21),
(8, 49), (8, 63);

INSERT INTO users (user_id, user_name, password, email, dni, points, country) VALUES
(1, 'juanperez', '1234', 'juan.perez@example.com', '12345678A', 1500, 'España'),
(2, 'mariagarcia', 'abcd', 'maria.garcia@example.com', '87654321B', 2200, 'México'),
(3, 'lucasfernandez', 'pass123', 'lucas.fernandez@example.com', '11223344C', 500, 'Argentina'),
(4, 'analopez', 'qwerty', 'ana.lopez@example.com', '44332211D', 3200, 'Colombia'),
(5, 'carlossanchez', 'admin123', 'carlos.sanchez@example.com', '55667788E', 10000, 'Chile');

-- Inserta productos en la tabla 'products'
INSERT INTO products (product_name, product_image, price, state) VALUES
('Teclado Mecánico', 'keyboard.jpg', 59, 'PUBLICO'),
('Monitor 27 pulgadas', 'monitor.jpg', 199, 'PUBLICO'),
('Ratón Gamer', 'mouse.jpg', 29, 'PUBLICO'),
('Silla Ergonómica', 'chair.jpg', 149, 'PUBLICO'),
('Auriculares Inalámbricos', 'headphones.jpg', 89, 'PUBLICO');

