CREATE USER 'kicker' IDENTIFIED BY 'kicker';
CREATE DATABASE kickerstats;
USE kickerstats;
GRANT ALL PRIVILEGES ON kickerstats . * TO 'kicker';

CREATE TABLE IF NOT EXISTS player (
    email VARCHAR(255) PRIMARY KEY,
    forename VARCHAR(255) NOT NULL,
    surename VARCHAR(255) NOT NULL,
    nickname VARCHAR(255),
    bio VARCHAR(255),
    passwordhash VARCHAR(255) NOT NULL,
    FOREIGN KEY (matches)  REFERENCES match(id)
) CHARACTER SET utf8 COLLATE utf8_bin;

CREATE TABLE IF NOT EXISTS matchtype (
  id VARCHAR(255) PRIMARY KEY,
  name VARCHAR(255) NOT NULL,
  goal_limit INT NOT NULL
) CHARACTER SET utf8 COLLATE utf8_bin;

CREATE TABLE IF NOT EXISTS season (
  id VARCHAR(255) PRIMARY KEY,
  name VARCHAR(255) NOT NULL,
  start DATE NOT NULL,
  end DATE NOT NULL,
  matches VARCHAR(255),
  FOREIGN KEY (matches)  REFERENCES match(id)
) CHARACTER SET utf8 COLLATE utf8_bin;

CREATE TABLE IF NOT EXISTS match (
  id VARCHAR(255) PRIMARY KEY,
  season VARCHAR(255),
  matchtype VARCHAR(255),
  FOREIGN KEY (season)  REFERENCES season(id),
  FOREIGN KEY (matchtype)  REFERENCEs matchtype(id),
  team_black_player1 VARCHAR(255) NOT NULL,
  team_black_player2 VARCHAR(255),
  team_white_player1 VARCHAR(255) NOT NULL,
  team_white_player2 VARCHAR(255)
) CHARACTER SET utf8 COLLATE utf8_bin;
