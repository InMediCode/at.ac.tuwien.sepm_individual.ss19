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