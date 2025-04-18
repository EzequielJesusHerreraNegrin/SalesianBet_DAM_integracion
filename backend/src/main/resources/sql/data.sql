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
('Arsenal', 'Fútbol'),
('Aston Villa', 'Fútbol'),
('Bournemouth', 'Fútbol'),
('Brentford', 'Fútbol'),
('Brighton', 'Fútbol'),
('Chelsea', 'Fútbol'),
('Crystal Palace', 'Fútbol'),
('Everton', 'Fútbol'),
('Fulham', 'Fútbol'),
('Ipswich', 'Fútbol'),
('Leicester City', 'Fútbol'),
('Liverpool', 'Fútbol'),
('Manchester City', 'Fútbol'),
('Manchester United', 'Fútbol'),
('Newcastle', 'Fútbol'),
('Nottingham Forest', 'Fútbol'),
('Southampton', 'Fútbol'),
('Tottenham', 'Fútbol'),
('West Ham', 'Fútbol'),
('Wolves', 'Fútbol'),
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

-- matches --
INSERT INTO matches (date, is_playing, result, competition_id) VALUES  
-- Cuartos de champions
('2025-04-08 20:00:00', FALSE, '3 - 0', 6),  -- 1 Arsenal vs. Real Madrid
('2025-04-08 20:00:00', FALSE, '1 - 2', 6), -- 2 Bayern Munchen vs Inter Milan
('2025-04-09 20:00:00', FALSE, '3 - 1', 6), -- 3 PSG vs Aston Villa
('2025-04-09 20:00:00', FALSE, '4 - 0', 6), -- 4 F.C. Barcelona vs Borussia Dortmund
('2025-04-15 20:00:00', FALSE, '', 6), -- 5 Aston Villa vs PSG
('2025-04-15 20:00:00', FALSE, '', 6), -- 6 Borussia Dortmund vs F.C. Barcelona 
('2025-04-16 20:00:00', FALSE, '', 6), -- 7 Real Madrid vs Arsenal
('2025-04-16 20:00:00', FALSE, '', 6), -- 8 Inter Milan vs Bayern Munchen  

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
