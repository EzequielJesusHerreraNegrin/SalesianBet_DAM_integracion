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
('Barcelona', 'Fútbol'),
('Atlético de Madrid', 'Fútbol'),
('Real Madrid', 'Fútbol'),
('Girona', 'Fútbol'),
('Athletic', 'Fútbol'),
('Real Sociedad', 'Fútbol'),
('Betis', 'Fútbol'),
('Valencia C.F.', 'Fútbol'),
('Villarreal', 'Fútbol'),
('Sevilla', 'Fútbol'),
('Getafe', 'Fútbol'),
('Rayo Vallecano', 'Fútbol'),
('Osasuna', 'Fútbol'),
('U.D. Las Palmas', 'Fútbol'),
('Alavés', 'Fútbol'),
('R.C.D. Mallorca', 'Fútbol'),
('Celta de Vigo', 'Fútbol'),
('Valladolid', 'Fútbol'),
('Leganés', 'Fútbol'),
('RCD Espanyol', 'Fútbol'),
-- Premier League --
('Arsenal', 'Football'),
('Aston Villa', 'Football'),
('Bournemouth', 'Football'),
('Brentford', 'Football'),
('Brighton', 'Football'),
('Chelsea', 'Football'),
('Crystal Palace', 'Football'),
('Everton', 'Football'),
('Fulham', 'Football'),
('Ipswich', 'Football'),
('Leicester City', 'Football'),
('Liverpool', 'Football'),
('Manchester City', 'Football'),
('Manchester United', 'Football'),
('Newcastle', 'Football'),
('Nottingham Forest', 'Football'),
('Southampton', 'Football'),
('Tottenham', 'Football'),
('West Ham', 'Football'),
('Wolves', 'Football'),
-- Serie A --
('Atalanta', 'Fútbol'),
('Bolonia', 'Fútbol'),
('Cagliari', 'Fútbol'),
('Como', 'Fútbol'),
('Empoli', 'Fútbol'),
('Fiorentina', 'Fútbol'),
('Genoa', 'Fútbol'),
('Hellas Verona', 'Fútbol'),
('Inter', 'Fútbol'),
('Juventus', 'Fútbol'),
('Lazio', 'Fútbol'),
('Lecce', 'Fútbol'),
('Milan', 'Fútbol'),
('Monza', 'Fútbol'),
('Napoli', 'Fútbol'),
('Parma', 'Fútbol'),
('Roma', 'Fútbol'),
('Torino', 'Fútbol'),
('Udinese', 'Fútbol'),
('Venezia', 'Fútbol'),
-- BundesLiga --
('Augsburgo', 'Fútbol'),
('Leverkusen', 'Fútbol'),
('Bayern de Múnich', 'Fútbol'),
('Bochum', 'Fútbol'),
('B. Dortmund', 'Fútbol'),
('B. Mönchengladbach', 'Fútbol'),
('Frankfurt', 'Fútbol'),
('Friburgo', 'Fútbol'),
('Heidenheim', 'Fútbol'),
('Hoffenheim', 'Fútbol'),
('KSV Holstein', 'Fútbol'),
('Mainz 05', 'Fútbol'),
('RB Leipzig', 'Fútbol'),
('St. Pauli', 'Fútbol'),
('Stuttgart', 'Fútbol'),
('FC Unión Berlín', 'Fútbol'),
('Werder Bremen', 'Fútbol'),
('Wolfsburgo', 'Fútbol'),
-- Ligue One --
('Angers', 'Fútbol'),
('Auxerre', 'Fútbol'),
('Le Havre', 'Fútbol'),
('Lens', 'Fútbol'),
('LOSC', 'Fútbol'),
('Mónaco', 'Fútbol'),
('Montpellier', 'Fútbol'),
('Nantes', 'Fútbol'),
('Niza', 'Fútbol'),
('Lyon', 'Fútbol'),
('Marsella', 'Fútbol'),
('PSG', 'Fútbol'),
('Racing de Estrasburgo', 'Fútbol'),
('Rennes', 'Fútbol'),
('Saint-Étienne', 'Fútbol'),
('Stade Brestois', 'Fútbol'),
('Reims', 'Fútbol'),
('Toulouse', 'Fútbol'),
-- another teams --
('Sporting Lisboa', 'Fútbol'),
('Benfica', 'Fútbol'),
('PSV', 'Fútbol'),
('Feyenoord', 'Fútbol'),
('Sturm', 'Fútbol'),
('RB Salzburg', 'Fútbol'),
('Club Brujas', 'Fútbol'),
('Celtic F.C.', 'Fútbol'),
('Shakhtar', 'Fútbol'),
('Sparta Praha', 'Fútbol'),
('Young Boys', 'Fútbol'),
('Dinamo Zagreb', 'Fútbol'),
('Estrella Roja', 'Fútbol'),
('Slovan Bratislava', 'Fútbol');






-- roles --
-- Asumiendo que los IDs son auto-generados: 1 = ROLE_ADMIN, 2 = ROLE_USER
INSERT INTO roles (role_name) VALUES
('ROLE_ADMIN'),
('ROLE_USER');

-- users --
-- Asumiendo IDs auto-generados: 1, 2, 3, 4, 5
-- ¡RECUERDA HASHEAR CONTRASEÑAS EN PRODUCCIÓN!
INSERT INTO users (user_name, password, email, dni, points, country) VALUES
('admin_betmaster', 'adminpass123', 'admin@betmaster.com', '00000001A', 1000, 'España'),
('juan_perez', 'juanpass', 'juan.perez@email.es', '11111111B', 500, 'España'),
('lisa_jones', 'lisapass', 'lisa.j@email.co.uk', '22222222C', 750, 'Inglaterra'),
('marco_rossi', 'marcopass', 'm.rossi@email.it', '33333333D', 600, 'Italia'),
('guest_user', 'guestpass', 'guest@example.com', '99999999Z', 100, 'Francia');

-- user_roles (Tabla de unión) --
-- Asignar roles a usuarios (Usando los IDs asumidos de users y roles)
-- admin_betmaster (ID 1) tiene ROLE_ADMIN (ID 1) y ROLE_USER (ID 2)
INSERT INTO user_roles (fk_user_id, fk_role_id) VALUES
(1, 1),
(1, 2);
-- juan_perez (ID 2) tiene ROLE_USER (ID 2)
INSERT INTO user_roles (fk_user_id, fk_role_id) VALUES (2, 2);
-- lisa_jones (ID 3) tiene ROLE_USER (ID 2)
INSERT INTO user_roles (fk_user_id, fk_role_id) VALUES (3, 2);
-- marco_rossi (ID 4) tiene ROLE_USER (ID 2)
INSERT INTO user_roles (fk_user_id, fk_role_id) VALUES (4, 2);
-- guest_user (ID 5) tiene ROLE_USER (ID 2)
INSERT INTO user_roles (fk_user_id, fk_role_id) VALUES (5, 2);

-- products --
-- Asumiendo IDs auto-generados: 1, 2, 3, 4
INSERT INTO products (product_name, price) VALUES
('Suscripción Premium Mensual', 9.99),
('Paquete 100 Puntos Extra', 1.99),
('Paquete 500 Puntos Extra', 8.49),
('Acceso Estadísticas Avanzadas', 4.99);

-- users_products (Tabla de unión) --
-- Asignar productos a usuarios (Usando los IDs asumidos)
-- lisa_jones (ID 3) compra Suscripción Premium (ID 1) y Paquete 100 Puntos (ID 2)
INSERT INTO users_products (fk_user_id, fk_product_id) VALUES
(3, 1),
(3, 2);
-- marco_rossi (ID 4) compra Paquete 500 Puntos (ID 3)
INSERT INTO users_products (fk_user_id, fk_product_id) VALUES (4, 3);
-- admin_betmaster (ID 1) tiene todo
INSERT INTO users_products (fk_user_id, fk_product_id) VALUES
(1, 1),
(1, 4);

-- matches --
-- Asumiendo IDs auto-generados para matches: 1, 2, 3, 4, 5, 6, 7, 8
-- Usando IDs de competitions: La Liga=1, Premier=2, Serie A=3, Champions=6
INSERT INTO matches (date, is_playing, result, competition_id) VALUES
-- La Liga (Comp ID 1)
('2024-08-18 21:00:00', FALSE, '2-1', 1), -- ID Partido: 1 (Ej: Barcelona vs Real Madrid)
('2024-08-19 19:00:00', FALSE, '0-0', 1), -- ID Partido: 2 (Ej: Atlético vs Sevilla)
-- Premier League (Comp ID 2)
('2024-08-17 15:00:00', FALSE, '3-2', 2), -- ID Partido: 3 (Ej: Arsenal vs Liverpool)
('2024-08-20 20:00:00', TRUE, 'PENDING', 2), -- ID Partido: 4 (Ej: Man City vs Chelsea - Jugándose ahora)
-- Serie A (Comp ID 3)
('2024-08-25 18:00:00', FALSE, '1-1', 3), -- ID Partido: 5 (Ej: Inter vs Juventus)
('2024-08-26 20:45:00', FALSE, 'PENDING', 3), -- ID Partido: 6 (Ej: Milan vs Napoli)
-- Champions League (Comp ID 6)
('2024-09-17 21:00:00', FALSE, 'PENDING', 6), -- ID Partido: 7 (Ej: Bayern vs PSG)
('2024-09-18 21:00:00', FALSE, 'PENDING', 6); -- ID Partido: 8 (Ej: Real Madrid vs Man City)

-- teams_matches (Tabla de unión) --
-- Conectar equipos a partidos (Usando IDs asumidos de teams y matches)
-- Asegúrate de que los IDs de los equipos coincidan con el orden de tu INSERT inicial
-- Match 1 (ID 1): Barcelona (ID 1) vs Real Madrid (ID 3)
INSERT INTO teams_matches (fk_match_id, fk_team_id) VALUES (1, 1), (1, 3);
-- Match 2 (ID 2): Atlético (ID 2) vs Sevilla (ID 10)
INSERT INTO teams_matches (fk_match_id, fk_team_id) VALUES (2, 2), (2, 10);
-- Match 3 (ID 3): Arsenal (ID 21) vs Liverpool (ID 32)
INSERT INTO teams_matches (fk_match_id, fk_team_id) VALUES (3, 21), (3, 32);
-- Match 4 (ID 4): Man City (ID 33) vs Chelsea (ID 26)
INSERT INTO teams_matches (fk_match_id, fk_team_id) VALUES (4, 33), (4, 26);
-- Match 5 (ID 5): Inter (ID 49) vs Juventus (ID 50)
INSERT INTO teams_matches (fk_match_id, fk_team_id) VALUES (5, 49), (5, 50);
-- Match 6 (ID 6): Milan (ID 53) vs Napoli (ID 55)
INSERT INTO teams_matches (fk_match_id, fk_team_id) VALUES (6, 53), (6, 55);
-- Match 7 (ID 7): Bayern (ID 63) vs PSG (ID 102)
INSERT INTO teams_matches (fk_match_id, fk_team_id) VALUES (7, 63), (7, 102);
-- Match 8 (ID 8): Real Madrid (ID 3) vs Man City (ID 33)
INSERT INTO teams_matches (fk_match_id, fk_team_id) VALUES (8, 3), (8, 33);


-- bets --
-- Asumiendo IDs auto-generados para bets
-- Crear apuestas de usuarios sobre partidos (Usando IDs asumidos de users y matches)
-- juan_perez (ID 2) apuesta en el Match 1 (Bar-RM)
INSERT INTO bets (points, prediction, match_id, user_id) VALUES
(50, '1', 1, 2); -- Apuesta 50 puntos a victoria local (Barcelona)
-- lisa_jones (ID 3) apuesta en el Match 1 (Bar-RM) y Match 3 (Ars-Liv)
INSERT INTO bets (points, prediction, match_id, user_id) VALUES
(100, '2-1', 1, 3), -- Apuesta 100 puntos al resultado 2-1
(75, 'X', 3, 3);   -- Apuesta 75 puntos al empate
-- marco_rossi (ID 4) apuesta en el Match 5 (Int-Juv) y Match 7 (Bay-PSG)
INSERT INTO bets (points, prediction, match_id, user_id) VALUES
(150, '1-1', 5, 4), -- Apuesta 150 puntos al resultado 1-1
(50, '2', 7, 4);   -- Apuesta 50 puntos a victoria visitante (PSG)
-- admin_betmaster (ID 1) apuesta en el Match 8 (RM-MCI)
INSERT INTO bets (points, prediction, match_id, user_id) VALUES
(200, '1', 8, 1); -- Apuesta 200 puntos a victoria local (Real Madrid)