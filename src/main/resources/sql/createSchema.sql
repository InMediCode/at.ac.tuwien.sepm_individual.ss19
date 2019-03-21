--DROP TABLE IF NOT EXISTS horse;
--DROP TABLE IF NOT EXISTS jockey;

-- create table horse if not exists
CREATE TABLE IF NOT EXISTS horse (
  -- use auto incrementing IDs as primary key
  id                  BIGINT AUTO_INCREMENT PRIMARY KEY,
  name                VARCHAR(255) NOT NULL,
  breed               TEXT,
  min_speed           DOUBLE       NOT NULL,
  max_speed           DOUBLE       NOT NULL,
  created             DATETIME     NOT NULL,
  updated             DATETIME     NOT NULL,
  deleted             BOOL         NOT NULL
);

-- create table jockey if not exists
CREATE TABLE IF NOT EXISTS jockey (
  -- use auto incrementing IDs as primary key
  id                  BIGINT AUTO_INCREMENT PRIMARY KEY,
  name                VARCHAR(255) NOT NULL,
  skill               DOUBLE       NOT NULL,
  created             DATETIME     NOT NULL,
  updated             DATETIME     NOT NULL,
  deleted             BOOL         NOT NULL
);

--create table simulation if not exists
CREATE TABLE IF NOT EXISTS simulation (
  -- use auto incrementing IDs as primary key
  id        BIGINT AUTO_INCREMENT PRIMARY KEY,
  name      VARCHAR(255) NOT NULL,
  created   DATETIME     NOT NULL
);

--create table participant if not exists
CREATE  TABLE  IF NOT EXISTS participants (
  id              BIGINT AUTO_INCREMENT PRIMARY KEY,
  simulation_id   BIGINT        NOT NULL,
  rank            INT           NOT NULL,
  horse_name      VARCHAR(255)  NOT NULL,
  jockey_name     VARCHAR(255)  NOT NULL,
  avg_speed       DOUBLE        NOT NULL,
  horse_speed     DOUBLE        NOT NULL,
  skill           DOUBLE        NOT NULL,
  luck_factor     DOUBLE        NOT NULL
);