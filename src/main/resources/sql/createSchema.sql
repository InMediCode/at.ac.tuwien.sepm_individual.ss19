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
  deleted             BOOL         NOT NULL,
  used_in_simulation  BOOL         NOT NULL,
  version             INT          NOT NULL
);

-- create table jockey if not exists
CREATE TABLE IF NOT EXISTS jockey (
  -- use auto incrementing IDs as primary key
  id                  BIGINT AUTO_INCREMENT PRIMARY KEY,
  name                VARCHAR(255) NOT NULL,
  skill               DOUBLE       NOT NULL,
  created             DATETIME     NOT NULL,
  updated             DATETIME     NOT NULL,
  deleted             BOOL         NOT NULL,
  used_in_simulation  BOOL         NOT NULL,
  version             INT          NOT NULL
);

--create table simulation if not exists
CREATE TABLE IF NOT EXISTS simulation (
  -- use auto incrementing IDs as primary key
  id        BIGINT AUTO_INCREMENT PRIMARY KEY,
  name      VARCHAR(255) NOT NULL
);

--create table participant if not exists
CREATE  TABLE  IF NOT EXISTS participants (
  simulationId    BIGINT    NOT NULL,
  horseId         BIGINT    NOT NULL,
  horseVersion    INT       NOT NULL,
  jockyId         BIGINT    NOT NULL,
  jockeyVersion   INT       NOT NULL,
  skill           DOUBLE    NOT NULL,
  avgSpeed        DOUBLE    NOT NULL,
  rank            INT       NOT NULL,
  PRIMARY KEY (simulationId, horseId, jockyId)
);