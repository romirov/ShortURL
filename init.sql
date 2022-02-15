CREATE USER shorturl WITH SUPERUSER PASSWORD 'vs23nZ4eoT3afoaB';
CREATE DATABASE shorturldb;
GRANT ALL PRIVILEGES ON DATABASE shorturldb TO shorturl;
\c shorturldb
CREATE TABLE IF NOT EXISTS users (
     id BIGSERIAL PRIMARY KEY,
     username CHARACTER VARYING(255),
     password CHARACTER VARYING(255),
     role CHARACTER VARYING(255)
); 
CREATE TABLE IF NOT EXISTS short_urls (
     id BIGSERIAL PRIMARY KEY,
     user_id BIGSERIAL,
     short_url CHARACTER VARYING(255),
     long_url TEXT,
     lifetime CHARACTER VARYING(255),
     transition_counter BIGSERIAL
); 
CREATE TABLE IF NOT EXISTS users_shorturls (
     user_id BIGSERIAL,
     shorturls_id BIGSERIAL
); 
INSERT INTO users VALUES(1,'admin','$2a$10$Aro1caZsn7gs93Re1vACKumcLFEoqIwdjbGb3XY2RH5OLSbq0d6pO','ROLE_ADMIN');
