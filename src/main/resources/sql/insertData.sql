-- insert initial test data
-- the id is hardcode to enable references between further test data
INSERT INTO horse (ID, NAME, BREED, MIN_SPEED, MAX_SPEED, CREATED, UPDATED, DELETED)
VALUES (1, 'Joe', 'Cob', 40.1, 50.0, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), false),
  (2, 'Lisa', 'Arab', 40.5, 50.7, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), false),
  (3, 'Jim', 'Andalusian', 45.0, 48.0, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), false),
  (4, 'Turtle', 'Boerperd', 40.0, 43.0, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), false),
  (5, 'Andre', 'Haflinger', 47.0, 60.0, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), false),
  (6, 'Louis', '', 49.9, 50.0, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), false),
  (7, 'Sunny', 'Tawleed', 50.0, 55.5, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), false),
  (8, 'Wendy', 'Zweibrücker', 44.0, 58.7, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), false),
  (9, 'Lola', 'Austrian Warmblood', 41.0, 43.0, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), false),
  (10, 'Speedy Gonzales', 'Finnhorse,', 58.0, 59.7, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), false);

-- insert initial test data
-- the id is hardcode to enable references between further test data
INSERT INTO jockey(ID, NAME, SKILL, CREATED, UPDATED, DELETED)
VALUES (1, 'Hans', 200.0, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), false),
  (2, 'Lisa', -33.7, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), false),
  (3, 'Lukas', 943.98, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), false),
  (4, 'Andi', -923.93, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), false),
  (5, 'Walter', 100.12, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), false),
  (6, 'Julia', 123.1, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), false),
  (7, 'Marlon', -123.34, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), false),
  (8, 'Lasse', 0.0, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), false),
  (9, 'Hanna', 943.98, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), false),
  (10, 'Sophie', 9999999.98, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), false);

-- insert initial test data
-- the id is hardcode to enable references between further test data
INSERT INTO simulation (ID, NAME, CREATED)
VALUES (1, 'Rennen 1', CURRENT_TIMESTAMP()),
       (2, 'Rennen 2', CURRENT_TIMESTAMP()),
       (3, 'Rennen 3', CURRENT_TIMESTAMP()),
       (4, 'Rennen 4', CURRENT_TIMESTAMP()),
       (5, 'Rennen 5', CURRENT_TIMESTAMP()),
       (6, 'Rennen 6', CURRENT_TIMESTAMP()),
       (7, 'Rennen 7', CURRENT_TIMESTAMP()),
       (8, 'Rennen 8', CURRENT_TIMESTAMP()),
       (9, 'Rennen 9', CURRENT_TIMESTAMP()),
       (10, 'Ultra 10', CURRENT_TIMESTAMP());

-- insert initial test data
-- the id is hardcode to enable references between further test data
INSERT INTO participants (ID, SIMULATION_ID, RANK, HORSE_NAME, JOCKEY_NAME, AVG_SPEED, HORSE_SPEED, SKILL, LUCK_FACTOR)
VALUES (1, 1, 1, 'Jim', 'Lukas', 54.1649, 48.0, 1.0747, 1.05),
       (2, 1, 2, 'Joe', 'Hans', 48.3747, 45.05, 1.0738, 1.0),
       (3, 1, 3, 'Lisa', 'Lisa', 35.8587, 40.5, 0.932, 0.95),
       (4, 2, 1, 'Jim', 'Marlon', 46.7158, 48.0, 0.9269, 1.05),
       (5, 2, 2, 'Joe', 'Lukas', 43.8667, 42.08, 1.0747, 0.97),
       (6, 3, 1, 'Andre', 'Walter', 51.6049, 49.6, 1.0726, 0.97),
       (7, 3, 2, 'Lola', 'Hanna', 43.8667, 43.0, 1.0747, 1.05),
       (8, 4, 1, 'Louis', 'Lasse', 49.95, 49.95, 1.0, 1.0),
       (9, 4, 2, 'Jim', 'Walter', 49.8759, 46.5, 1.0726, 1.0),
       (10, 4, 3, 'Wendy', 'Andi', 47.5142, 51.35, 0.9253, 1.0);