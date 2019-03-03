-- insert initial test data
-- the id is hardcode to enable references between further test data
INSERT INTO horse (ID, NAME, BREED, MIN_SPEED, MAX_SPEED, CREATED, UPDATED, DELETED)
VALUES (1, 'Joe', 'Cob', 40.1, 50.0, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), false),
  (2, 'Lisa', 'Arab', 40.5, 50.7, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), false),
  (3, 'Jim', 'Andalusian', 40.0, 60.0, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), false);

-- inser initial test data
-- the id is hardcode to enable references between further test data
INSERT INTO jockey(ID, NAME, SKILL, CREATED, UPDATED)
VALUES (1, 'Hans', 200.0, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP()),
  (2, 'Lisa', -33.7, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP()),
  (3, 'Speedy Gonzales', 943.98, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());