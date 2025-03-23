-- Datos SQL para la base de datos BETDB (Schema Correcto)

-- Insertar datos en la tabla `competitions`
INSERT INTO `competitions` (`competition_id`, `competition_name`) VALUES
(1, 'La Liga'),
(2, 'Premier League'),
(3, 'Champions League'),
(4, 'NBA Finals');

-- Insertar datos en la tabla `teams`
INSERT INTO `teams` (`team_id`, `sport`, `team_name`, `country`) VALUES
(1, 'Football', 'Real Madrid', 'Spain'),
(2, 'Football', 'Barcelona', 'Spain'),
(3, 'Football', 'Manchester United', 'England'),
(4, 'Football', 'Liverpool', 'England'),
(5, 'Basketball', 'Los Angeles Lakers', 'USA'),
(6, 'Basketball', 'Chicago Bulls', 'USA'),
(7, 'Basketball', 'Golden State Warriors', 'USA');

-- Insertar datos en la tabla `matches`
INSERT INTO `matches` (`match_id`, `competition_id`, `date`, `is_playing`, `result`) VALUES
(1, 1, '2025-03-24', b'0', ''), -- Real Madrid vs Barcelona (sin comenzar)
(2, 2, '2025-03-25', b'1', '1-0'), -- Manchester United vs Liverpool (en curso)
(3, 3, '2025-03-26', b'0', '2-1'), -- Real Madrid vs PSG (terminado - is_playing debería ser 0 si ha terminado)
(4, 4, '2025-06-10', b'0', ''); -- Lakers vs Warriors (NBA Finals, sin comenzar)

-- Insertar datos en la tabla `teams_matches` (Relación muchos a muchos entre equipos y partidos)
INSERT INTO `teams_matches` (`fk_team_id`, `fk_match_id`) VALUES
(1, 1), -- Real Madrid en Partido 1
(2, 1), -- Barcelona en Partido 1
(3, 2), -- Manchester United en Partido 2
(4, 2), -- Liverpool en Partido 2
(1, 3), -- Real Madrid en Partido 3
(5, 4), -- Lakers en Partido 4
(7, 4); -- Warriors en Partido 4

-- Insertar datos en la tabla `users`
INSERT INTO `users` (`user_id`, `user_name`, `dni`, `email`, `password`, `points`, `country`) VALUES
(1, 'User1', '12345678A', 'user1@email.com', 'pass123', 100, 'Spain'),
(2, 'User2', '87654321B', 'user2@email.com', 'pass456', 250, 'England'),
(3, 'User3', '99999999C', 'user3@email.com', 'pass789', 50, 'USA'),
(4, 'User4', '55555555D', 'user4@email.com', 'passabc', 1000, 'Canada');

-- Insertar datos en la tabla `bets`
INSERT INTO `bets` (`bet_id`, `user_id`, `match_id`, `points`, `prediction`, `type`) VALUES
(1, 1, 1, 10, 'Real Madrid Win', 'Single'),
(2, 2, 2, 20, 'Over 2.5 Goals', 'Combined'),
(3, 1, 3, 5, 'Draw', 'Single'),
(4, 3, 4, 30, 'Lakers to win', 'Single'); -- Apuesta en partido de baloncesto

-- Insertar datos en la tabla `roles`
INSERT INTO `roles` (`role_id`, `role_name`) VALUES
(1, 'admin'),
(2, 'user'),
(3, 'premium_user');

-- Insertar datos en la tabla `user_roles` (Relación muchos a muchos entre usuarios y roles)
INSERT INTO `user_roles` (`fk_user_id`, `fk_role_id`) VALUES
(1, 1), -- Usuario 1 es admin
(2, 2), -- Usuario 2 es usuario normal
(3, 2), -- Usuario 3 es usuario normal
(4, 3); -- Usuario 4 es usuario premium

-- Insertar datos en la tabla `products`
INSERT INTO `products` (`product_id`, `product_name`, `price`) VALUES
(1, 'Welcome Bonus', 10.00),
(2, 'Free Bet', 5.00),
(3, 'Odds Boost', 2.00);

-- Insertar datos en la tabla `users_products` (Relación muchos a muchos entre usuarios y productos)
INSERT INTO `users_products` (`fk_user_id`, `fk_product_id`) VALUES
(1, 1), -- Usuario 1 tiene Welcome Bonus
(2, 2), -- Usuario 2 tiene Free Bet
(4, 3); -- Usuario 4 tiene Odds Boost
