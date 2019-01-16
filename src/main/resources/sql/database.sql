CREATE USER 'tsvw' IDENTIFIED BY 'tsvw';
CREATE DATABASE tournament;
USE tournament;
GRANT ALL PRIVILEGES ON tournament . * TO 'tsvw';
